// $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/plugin/Device.java,v $
// LastModified By: $Author: shenjun $
// $Date: 2005/03/28 01:49:52 $

package com.ubipass.middleware.plugin;

import java.util.concurrent.BlockingQueue;

/**
 * Common interface implemented by all device plug-in.
 *
 * @author Shen Jun
 * @author $Author: shenjun $
 * @version $Revision: 1.11 $
 */
public interface Device {

    public static final int READER_MODE = 1;
    public static final int PRINTER_MODE = 2;

    /**
     * Set device ID.
     *
     * @param deviceID device ID
     */
    public void setDeviceID(String deviceID);

    /**
     * Set user name if required.
     *
     * @param userName user name
     */
    public void setUserName(String userName);

    /**
     * Set password if required.
     *
     * @param password password
     */
    public void setPassword(String password);

    /**
     * Set persist time.
     *
     * @param persistTime persist time in millisecond for tag remove event
     */
    public void setPersistTime(int persistTime);

    /**
     * Called by middleware to pass references to system event queue.
     *
     * @param queue Reference to middleware event queue
     */
    public void setEventQueue(BlockingQueue<Event> queue);

    /**
     * Set device name.
     *
     * @param deviceName device name
     */
    public void setDeviceName(String deviceName);

    /**
         * Set up a TCP/IP connection.
         *
         * @param ip       Device IP address
         * @param port     Device listening port
         * @param openMode
         * @throws DeviceOperationException
         * @throws UnsupportedFeatureException
         */
    public void openNetworkDevice(String ip, int port, int openMode)
        throws DeviceOperationException, UnsupportedFeatureException;

    /**
     * Set up a serial connection.
     *
     * @param serialPortName serial port name
     * @param baudRate       serial baud rate
     * @param openMode
     * @throws DeviceOperationException
     * @throws UnsupportedFeatureException
     */
    public void openSerialDevice(String serialPortName, int baudRate, int openMode)
        throws DeviceOperationException, UnsupportedFeatureException;

    /**
     * Close the connection.
     *
     * @throws DeviceOperationException
     */
    public void closeDevice() throws DeviceOperationException;

    /**
     * Return device ID.
     *
     * @return Device ID
     */
    public String getDeviceID();

    /**
     * Get device name.
     *
     * @return device name
     */
    public String getDeviceName();

    /**
     * Return device IP address.
     *
     * @return Device IP address
     */
    public String getDeviceIPAddress();

    /**
     * Return device IP listening port.
     *
     * @return Device listening port
     */
    public int getDeviceIPPort();

    /**
     * Return serial port name of connection to device.
     *
     * @return serial port
     */
    public String getDeviceSerialPort();

    /**
     * Return baud rate of serial connection.
     *
     * @return serial port
     */
    public int getDeviceBaudRate();

    /**
     * Return user name used to set up connection.
     *
     * @return user name
     */
    public String getUserName();

    /**
     * See if device is a reader.
     *
     * @return boolean to indicate if device is a reader
     */
    public boolean isReader();

    /**
     * See if device is a printer.
     *
     * @return boolean to indicate if device is a printer
     */
    public boolean isPrinter();

    /**
     * See if device is an one-time reader.
     *
     * @return boolean to indicate if device is an one-time reader
     */
    public boolean isOneTimeReader();

    /**
     * Get device current working status.
     *
     * @return boolean to indicate if device is working normally
     */
    public boolean isDeviceWorking();

    /**
     * Return list of tags that device can detect now.
     *
     * @return Array of Tag
     * @throws DeviceOperationException
     * @throws UnsupportedFeatureException
     */
    public Tag[] getTagList()
        throws DeviceOperationException, UnsupportedFeatureException;

    /**
     * Write tag.
     *
     * @param label Print label infomation
     * @param info  Written information
     * @return true if written and verified successfully, false if not verified
     * @throws DeviceOperationException    If device operation error
     * @throws UnsupportedFeatureException If device doesn't support this feature
     */
    public boolean writeTag(String label, String info)
        throws DeviceOperationException, UnsupportedFeatureException;

}
