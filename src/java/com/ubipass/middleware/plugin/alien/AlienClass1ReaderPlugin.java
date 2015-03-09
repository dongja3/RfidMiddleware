//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/plugin/alien/AlienClass1ReaderPlugin.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/06/01 04:02:22 $

package com.ubipass.middleware.plugin.alien;

import com.ubipass.middleware.plugin.Device;
import com.ubipass.middleware.plugin.DeviceOperationException;
import com.ubipass.middleware.plugin.Event;
import com.ubipass.middleware.plugin.EventImpl;
import com.ubipass.middleware.plugin.Tag;
import com.ubipass.middleware.plugin.TagImpl;
import com.ubipass.middleware.plugin.UnsupportedFeatureException;
import com.ubipass.middleware.util.log.SystemLogger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

/**
 * Alien Class 1 Reader plug-in.
 *
 * @author shenjun
 * @author $Author: shenjun $
 * @version $Revision: 1.18 $
 */
public class AlienClass1ReaderPlugin extends DefaultHandler
    implements Runnable, Device {

    private static final String NETWORK_PROMPT = "\r\nAlien>";
    private static final String SERIAL_PROMPT = "\r\nAlien >";
    private static final String GET_READER_TYPE = "Get ReaderType";
    private static final String SET_FUNCTION = "Set Function = ";
    private static final String SET_TIME = "Set Time = ";
    private static final String SET_ANTENNA_SEQUENCE = "Set AntennaSequence = 0, 1, 2, 3";
    private static final String SET_ACQUIRE_MODE = "Set AcquireMode = Inventory";
    private static final String SET_PERSIST_TIME = "Set PersistTime = ";
    private static final String SET_TAGLIST_FORMAT = "Set TagListFormat = XML";
    private static final String GET_TAGLIST = "Get TagList";

    private static final String TAGID_ELEMENT = "TagID";
    private static final String DISCOVERYTIME_ELEMENT = "DiscoveryTime";

    private static final int BAUD_RATE = 115200;

    private String readerID = "";
    private int persistTime = 2;
    private String userName;
    private String password;
    private String ip;
    private int port;
    private int openMode;
    private String serialPortName;
    private BlockingQueue<Event> queue = null;
    private SAXParser parser = null;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private ArrayList<Tag> tagList = null;
    private char[] buffer = null;
    private Tag[] oldTags = null;
    private Tag[] newTags = null;
    private String deviceName;
    private StringBuffer tagID;
    private StringBuffer discoverTime;
    private boolean isTcpConnection;
    private boolean isRunning;
    private String prompt;

    private boolean isReadTagID;
    private boolean isReadDiscoverTime;

    private Socket commandSocket = null;
    private SerialPort serialPort = null;
    private BufferedReader commandReader = null;
    private BufferedWriter commandWriter = null;

    private Object replyLock = new Object();
    private Object xmlLock = new Object();

    /**
     * Constructor.
     */
    public AlienClass1ReaderPlugin() {
        try {
            parser = SAXParserFactory.newInstance().newSAXParser();
        } catch (Exception e) {

        }
    }

    /**
     * Set reader ID.
     *
     * @param readerID reader ID
     */
    public void setDeviceID(String readerID) {
        this.readerID = readerID;
    }

    /**
     * Set login user name if required.
     *
     * @param userName user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Set password if required.
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set persist time.
     *
     * @param persistTime persist time in millisecond for tag remove event
     */
    public void setPersistTime(int persistTime) {
        // at least 2 seconds
        this.persistTime = (persistTime < 2) ? 2 : persistTime;
    }

    /**
     * Called by middleware to pass references to system event queue.
     *
     * @param queue Reference to middleware event queue
     */
    public void setEventQueue(BlockingQueue<Event> queue) {
        this.queue = queue;
    }

    /**
     * Return reader ID.
     *
     * @return Reader ID
     */
    public String getDeviceID() {
        return readerID;
    }

    /**
     * Return reader IP address.
     *
     * @return Reader IP address
     */
    public String getDeviceIPAddress() {
        return ip;
    }

    /**
     * Return reader IP listening port.
     *
     * @return Reader listening port
     */
    public int getDeviceIPPort() {
        return port;
    }

    /**
     * Return serial port name of connection to reader.
     *
     * @return serial port
     */
    public String getDeviceSerialPort() {
        return serialPortName;
    }

    /**
     * Return baud rate of serial connection.
     *
     * @return baud rate
     */
    public int getDeviceBaudRate() {
        return BAUD_RATE;
    }

    /**
     * Set user name if required.
     *
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set device name.
     *
     * @param deviceName device name
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * Get device name.
     *
     * @return device name
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * See if device is a reade.
     *
     * @return true
     */
    public boolean isReader() {
        return true;
    }

    /**
     * See if device is a printer.
     *
     * @return true
     */
    public boolean isPrinter() {
        return true;
    }

    /**
     * See if device is an one-time reader.
     *
     * @return false
     */
    public boolean isOneTimeReader() {
        return false;
    }

    /**
     * Set up a TCP/IP connection.
     *
     * @param ip       Reader IP address
     * @param port     Reader listening port
     * @param openMode device open mode
     * @throws DeviceOperationException
     * @throws UnsupportedFeatureException
     */
    public synchronized void openNetworkDevice(String ip, int port, int openMode)
        throws DeviceOperationException, UnsupportedFeatureException {

        if (openMode != Device.READER_MODE && openMode != Device.PRINTER_MODE) {
            throw new UnsupportedFeatureException("Unknown open mode");
        }

        isTcpConnection = true;
        prompt = NETWORK_PROMPT;

        if (commandWriter != null) {
            throw new DeviceOperationException("Device is already open");
        }

        try {
            commandSocket = new Socket(ip, port);

            commandReader = new BufferedReader(new InputStreamReader(commandSocket.getInputStream()));
            commandWriter = new BufferedWriter(new OutputStreamWriter(commandSocket.getOutputStream()));
        } catch (Exception e) {
            // cannot open the socket
            throw new DeviceOperationException("Cannot setup network connection");
        }

        if (buffer == null) {
            buffer = new char[1000];
        }

        String response;
        try {
            // send login username
            getCommandReply(userName, "Password");

            // send password
            commandWriter.write(password);
            commandWriter.flush();

            // note that reader echos *** for password
            response = getCommandReply("", ">");

        } catch (Exception e) {
            try {
                commandReader.close();
                commandWriter.close();
                commandSocket.close();
            } catch (Exception e1) {

            }

            commandReader = null;
            commandWriter = null;
            commandSocket = null;

            throw new DeviceOperationException(e.getMessage());
        }

        // last line is NETWORK_PROMT if login succeeds
        if (!response.endsWith("Alien")) {
            try {
                commandReader.close();
                commandWriter.close();
                commandSocket.close();
            } catch (Exception e) {

            }

            commandReader = null;
            commandWriter = null;
            commandSocket = null;

            throw new DeviceOperationException("Wrong login username or password");
        }

        this.ip = ip;
        this.port = port;
        this.openMode = openMode;

        configureReader();

    }

    /**
     * Set up a serial connection.
     *
     * @param serialPortName serial port name
     * @param baudRate       serial baud rate
     * @param openMode       device open mode
     * @throws DeviceOperationException
     * @throws UnsupportedFeatureException
     */
    public synchronized void openSerialDevice(String serialPortName, int baudRate, int openMode)
        throws DeviceOperationException, UnsupportedFeatureException {

        if (openMode != Device.READER_MODE && openMode != Device.PRINTER_MODE) {
            throw new UnsupportedFeatureException("Unknown open mode");
        }

        isTcpConnection = false;
        prompt = SERIAL_PROMPT;

        if (commandWriter != null) {
            throw new DeviceOperationException("Device is already open");
        }

        CommPortIdentifier portID;

        try {
            portID = CommPortIdentifier.getPortIdentifier(serialPortName);
        } catch (NoSuchPortException e) {
            throw new DeviceOperationException("Port " + serialPortName + " does not exist");
        }

        try {
            // open the port
            serialPort = (SerialPort) portID.open("Alien RFID Class1 Reader Plugin", 5000);
        } catch (Exception e) {
            throw new DeviceOperationException("Cannot open Port " + serialPortName + ": " + e.getMessage());
        }

        // set communication parameters
        try {
            // use default baud rate 115200
            serialPort.setSerialPortParams(BAUD_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
        } catch (UnsupportedCommOperationException e) {
            throw new DeviceOperationException("Port " + serialPortName + " does not support required communication parameters");
        }

        this.serialPortName = serialPortName;
        this.openMode = openMode;

        try {
            commandReader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            commandWriter = new BufferedWriter(new OutputStreamWriter(serialPort.getOutputStream()));

            if (buffer == null) {
                buffer = new char[1000];
            }
        } catch (Exception e) {
            // something wrong
            try {
                closeDevice();
            } catch (Exception e1) {

            }

            throw new DeviceOperationException(e.getMessage());
        }

        configureReader();

    }

    /**
     * Configure Alien Class1 reader.
     *
     * @throws DeviceOperationException
     */
    private void configureReader() throws DeviceOperationException {

        newTags = null;
        oldTags = null;

        try {
            // send command GET_READER_TYPE
            String reply = getCommandReply(GET_READER_TYPE, prompt);

            if (!reply.contains("Alien RFID") || !reply.contains("Class 1")) {
                throw new DeviceOperationException("Device is not Alien RFID Class 1 reader");
            }

            if (commandWriter != null) {
                // set function
                getCommandReply(SET_FUNCTION + (openMode == Device.READER_MODE ? "Reader" : "Programmer"), prompt);
            } else {
                // closeDevice() has been called
                return;
            }

            if (commandWriter != null) {
                // set time
                getCommandReply(SET_TIME + format.format(new java.util.Date()), prompt);
            } else {
                return;
            }

            if (commandWriter != null) {
                // set antenna sequence = 0,1,2,3
                getCommandReply(SET_ANTENNA_SEQUENCE, prompt);
            } else {
                return;
            }

            if (commandWriter != null) {
                // set acquire mode = Inventory
                getCommandReply(SET_ACQUIRE_MODE, prompt);
            } else {
                return;
            }

            if (commandWriter != null) {
                // set taglist format = XML
                getCommandReply(SET_TAGLIST_FORMAT, prompt);
            } else {
                return;
            }

            if (commandWriter != null) {
                // set persist time
                getCommandReply(SET_PERSIST_TIME + persistTime, prompt);
            }
        } catch (Exception e) {
            // something wrong
            try {
                closeDevice();
            } catch (Exception e1) {

            }

            throw new DeviceOperationException(e.getMessage());
        }

    }

    /**
     * Close the connection.
     */
    public void closeDevice() {

        // stop the monitoring task
        isRunning = false;

        try {
            if (commandReader != null) {
                commandReader.close();
            }

            if (commandWriter != null) {
                commandWriter.close();
            }
        } catch (Exception e) {

        } finally {
            commandReader = null;
            commandWriter = null;
        }

        if (isTcpConnection) {
            // close sockets
            if (commandSocket != null) {
                try {
                    commandSocket.close();
                } catch (Exception e) {

                }
            }

            commandSocket = null;
        } else {
            if (serialPort != null) {
                serialPort.close();
            }

            serialPort = null;
        }

        // add REMOVE events for current tag list
        newTags = null;
        addEvent();

    }

    /**
     * Get reader current working status.
     *
     * @return boolean to indicate if reader is working normally
     */
    public boolean isDeviceWorking() {
        try {
            if (commandWriter == null) {
                return false;
            }

            getCommandReply(GET_READER_TYPE, prompt);
            return true;
        } catch (Exception e) {
            // something wrong
            SystemLogger.error("Device " + deviceName + " operation error: " + e.getMessage());
            SystemLogger.info("Device " + deviceName + " is closed");

            try {
                closeDevice();
            } catch (Exception e1) {

            }

            return false;
        }
    }

    /**
     * Monitor task.
     */
    public void run() {
        String reply;

        try {
            isRunning = true;

            while (isRunning) {
                if (commandWriter != null) {
                    // send 'Get TagList'
                    reply = getCommandReply(GET_TAGLIST, prompt).trim();

                    // when stop the device, reply will be empty string
                    if (reply != null && reply.length() != 0) {
                        processTagXML(reply);
                    }
                } else {
                    return;
                }

                // sleep 1s
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
            }
        } catch (Exception e) {
            // something wrong, close device
            SystemLogger.error("Device " + deviceName + " operation error: " + e.getMessage());
            SystemLogger.info("Device " + deviceName + " is closed");

            try {
                closeDevice();
            } catch (Exception e1) {

            }
        }
    }

    /**
     * Pick out ADD and REMOVE events and put them in EventQueue.
     */
    private void addEvent() {

        int i;
        int oldTagCount = (oldTags == null) ? 0 : oldTags.length;
        long now = new Date().getTime();

        // pick out ADD event
        if (newTags != null) {
            for (Tag tag : newTags) {
                for (i = 0; i < oldTagCount; i++) {
                    if (tag.getTagID().equals(oldTags[i].getTagID())) {
                        // mark it with tagID=null
                        oldTags[i].setTagID(null);
                        break;
                    }
                }

                if (i >= oldTagCount) {
                    // ADD event
                    Event event = new EventImpl();
                    event.setEventType(Event.ADD);
                    event.setReaderID(readerID);
                    event.setTagID(trimTagID(tag.getTagID()));
                    event.setEventTime(tag.getFirstSeenTime());

                    try {
                        queue.put(event);
                    } catch (Exception e) {

                    }
                }
            }
        }

        // pick out REMOVE event
        if (oldTags != null) {
            for (i = 0; i < oldTags.length; i++) {
                if (oldTags[i].getTagID() != null) {
                    // REMOVE event
                    Event event = new EventImpl();
                    event.setEventType(Event.REMOVE);
                    event.setReaderID(readerID);
                    event.setTagID(trimTagID(oldTags[i].getTagID()));

                    // note that use different timestamp for remove time
                    event.setEventTime(now + i);

                    try {
                        queue.put(event);
                    } catch (Exception e) {

                    }
                }
            }
        }

        oldTags = newTags;

    }

    /**
     * Return list of tags that reader can detect now.
     *
     * @return Array of Tag
     * @throws DeviceOperationException
     * @throws UnsupportedFeatureException
     */
    public Tag[] getTagList()
        throws DeviceOperationException, UnsupportedFeatureException {
        if (!isDeviceWorking()) {
            throw new DeviceOperationException("Device has been closed");
        }

        return newTags;
    }

    /**
     * Process notification xml.
     *
     * @param xml notification xml
     */
    private void processTagXML(String xml) {
        synchronized (xmlLock) {
            // use SAX parser to parse XML
            try {
                parser.parse(new InputSource(new StringReader(xml)), this);
            } catch (Exception e) {
                SystemLogger.warn("Operation error while parsing XML: " + e.getMessage() + "\r\n"
                    + xml);
            }

            addEvent();
        }
    }

    /**
     * Create an arraylist of Tag.
     *
     * @throws SAXException
     */
    public void startDocument() throws SAXException {
        tagList = new ArrayList<Tag>();
        isReadTagID = false;
        isReadDiscoverTime = false;
    }

    /**
     * Only read element TagID and DiscoveryTime in document 'Alien-RFID-Tag-List'.
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes)
        throws SAXException {
        if (TAGID_ELEMENT.equals(qName)) {
            isReadTagID = true;
            tagID = new StringBuffer();
        } else if (DISCOVERYTIME_ELEMENT.equals(qName)) {
            isReadDiscoverTime = true;
            discoverTime = new StringBuffer();
        }
    }

    /**
     * Only read values of element TagID and DiscoveryTime in document 'Alien-RFID-Tag-List'.
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    public void characters(char[] ch, int start, int length)
        throws SAXException {
        if (isReadTagID) {
            tagID.append(ch, start, length);
        } else if (isReadDiscoverTime) {
            discoverTime.append(ch, start, length);
        }
    }

    /**
     * Process value of element 'TagID' and 'DiscoveryTime'.
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    public void endElement(String uri, String localName, String qName)
        throws SAXException {
        if (TAGID_ELEMENT.equals(qName)) {
            // read <TagID>
            // add it to taglist
            Tag tag = new TagImpl();
            tag.setTagID(trimTagID(tagID.toString()));
            tagList.add(tag);
            isReadTagID = false;
        } else if (DISCOVERYTIME_ELEMENT.equals(qName)) {
            // read <DiscoveryTime>
            try {
                tagList.get(tagList.size() - 1)
                    .setFirstSeenTime(format.parse(discoverTime.toString()).getTime());
            } catch (Exception e) {
                // don't expect error of Alien Reader
                // ignore it
            }

            isReadDiscoverTime = false;
        }
    }

    /**
     * Assign tag list to newTags.
     *
     * @throws SAXException
     */
    public void endDocument() throws SAXException {
        // convert arraylist to array
        Tag[] tags = new Tag[tagList.size()];

        for (int i = 0; i < tagList.size(); i++) {
            tags[i] = tagList.get(i);
        }

        newTags = tags;
    }

    /**
     * Write tag.
     *
     * @param label Print label infomation
     * @param info  Written information
     * @return true write successfully and info verified, false write successfully but info not verified
     * @throws DeviceOperationException    If reader operation error
     * @throws UnsupportedFeatureException If reader doesn't support this feature
     */
    public synchronized boolean writeTag(String label, String info)
        throws DeviceOperationException, UnsupportedFeatureException {

        if (openMode != PRINTER_MODE) {
            throw new DeviceOperationException("Device is not running as a printer");
        }

        return true;
    }

    /**
     * Send command and get reply through serial port.
     *
     * @param command command string
     * @param prompt
     * @return reply for command
     * @throws DeviceOperationException
     */
    private synchronized String getCommandReply(String command, String prompt) throws DeviceOperationException {

        int promptIndex = -1;
        int echoIndex;
        int count;
        StringBuffer response = new StringBuffer();

        synchronized (replyLock) {
            try {
                commandWriter.write(command + "\r\n");
                commandWriter.flush();
            } catch (Exception e) {
                throw new DeviceOperationException("Device sending command error");
            }

            try {
                while (promptIndex < 0) {
                    // wait until reader returns something
                    while (!commandReader.ready()) {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {

                        }
                    }

                    // get response from reader
                    count = commandReader.read(buffer);

                    if (count == -1) {
                        // end of stream
                        // something wrong
                        throw new DeviceOperationException("Operation error while reading data through serial port");
                    }

                    // append to response
                    response.append(buffer, 0, count);

                    // note that prompt is different for socket and serial connection
                    promptIndex = response.indexOf(prompt);
                }
            } catch (Exception e) {
                if (commandReader != null) {
                    throw new DeviceOperationException(e.getMessage());
                } else {
                    // closeDevice() has been called
                    return "";
                }
            }

            // reader echoes the command first
            echoIndex = response.indexOf(command);

            if (echoIndex < 0) {
                return "";
            }

            count = echoIndex + command.length();

            // return real command reply
            return response.substring(count, promptIndex);
        }

    }

    /**
     * Trim spaces in tagID returned by Alien reader.
     *
     * @param tagID tagID returned by Alien reader
     * @return trimmed tagID
     */
    private String trimTagID(String tagID) {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < tagID.length(); i++) {
            if (tagID.charAt(i) != ' ') {
                buffer.append(tagID.charAt(i));
            }
        }

        return buffer.toString();
    }

}
