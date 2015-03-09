//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/plugin/VirtualReaderPlugin.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/18 05:28:31 $

package com.ubipass.middleware.plugin;

import java.util.concurrent.BlockingQueue;

/**
 * Virtual reader plug-in.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.7 $
 */
public abstract class VirtualReaderPlugin implements Runnable, Device {

    protected int tagIdLength;

    protected BlockingQueue<Event> queue;
    protected String readerID = "";
    protected String deviceName;
    protected String userName;
    protected String ip;
    protected int port;
    protected int baudRate;
    protected int openMode;
    protected String serialPortName;
    protected int persistTime = 2;
    protected boolean isRunning = false;

    /**
     * Subclass to implement to simulate tag id reading.
     */
    public abstract void run();

    /**
     * Return list of tags that reader can detect now.
     *
     * @return Array of Tag
     * @throws DeviceOperationException
     * @throws UnsupportedFeatureException
     */
    public abstract Tag[] getTagList()
        throws DeviceOperationException, UnsupportedFeatureException;

    /**
     * Set reader ID.
     *
     * @param readerID reader ID
     */
    public void setDeviceID(String readerID) {
        this.readerID = readerID;
    }

    /**
     * Set login user name if required
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

    }

    /**
     * Set persist time.
     *
     * @param persistTime persist time in millisecond for tag remove event
     */
    public void setPersistTime(int persistTime) {
        if (persistTime < 1) {
            persistTime = 1;
        }

        this.persistTime = persistTime;
    }

    /**
     * Set up a IP connection.
     *
     * @param ip       Reader IP address
     * @param port     Reader listening port
     * @param openMode device open mode
     * @throws DeviceOperationException
     * @throws UnsupportedFeatureException
     */
    public void openNetworkDevice(String ip, int port, int openMode)
        throws DeviceOperationException, UnsupportedFeatureException {
        this.ip = ip;
        this.port = port;
        this.openMode = openMode;
        isRunning = true;
    }

    /**
     * Set up a serial connection.
     *
     * @param serialPortName serial port name
     * @param baudRate       baud rate
     * @param openMode       device open mode
     * @throws DeviceOperationException
     * @throws UnsupportedFeatureException
     */
    public void openSerialDevice(String serialPortName, int baudRate, int openMode)
        throws DeviceOperationException, UnsupportedFeatureException {
        this.serialPortName = serialPortName;
        this.baudRate = baudRate;
        this.openMode = openMode;
        isRunning = true;
    }

    /**
     * Close the connection.
     *
     * @throws DeviceOperationException
     */
    public void closeDevice() throws DeviceOperationException {
        isRunning = false;
    }

    /**
     * Return Device ID.
     *
     * @return Device ID
     */
    public String getDeviceID() {
        return readerID;
    }

    /**
     * Return Device IP address.
     *
     * @return Device IP address
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
     * Return reader IP listening port.
     *
     * @return Reader listening port
     */
    public int getDeviceBaudRate() {
        return baudRate;
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
     * Get Device current working status.
     *
     * @return boolean to indicate if reader is working normally
     */
    public boolean isDeviceWorking() {
        return isRunning;
    }

    /**
     * Write tag.
     *
     * @param label Print label infomation
     * @param info  Written information
     * @return boolean
     * @throws DeviceOperationException    If reader operation error
     * @throws UnsupportedFeatureException If reader doesn't support this feature
     */
    public boolean writeTag(String label, String info)
        throws DeviceOperationException, UnsupportedFeatureException {
        if (isRunning) {
            if (openMode == PRINTER_MODE)
                return true;
            else
                throw new DeviceOperationException("Device is not running as a printer");
        } else {
            throw new DeviceOperationException("Device is already closed");
        }
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
     * Check if device is a reader.
     *
     * @return true
     */
    public boolean isReader() {
        return true;
    }

    /**
     * Check if device is a printer.
     *
     * @return true
     */
    public boolean isPrinter() {
        return true;
    }

    /**
     * Check if device is an one-time reader.
     *
     * @return false
     */
    public boolean isOneTimeReader() {
        return false;
    }

    /**
     * Convert id to formalized hexadecimal.
     *
     * @param id tag ID
     * @return formalized tag ID
     */
    protected String convertTagID(String id) {
        StringBuffer tagID = new StringBuffer(tagIdLength / 4);

        // first fill leading zero
        for (int i = id.length(); i < tagIdLength / 4; i++) {
            tagID.append("0");
        }

        // convert to upper case
        tagID.append(id.toUpperCase());

        return tagID.toString();
    }

}
