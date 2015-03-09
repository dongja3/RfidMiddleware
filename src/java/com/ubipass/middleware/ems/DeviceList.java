//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/ems/DeviceList.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/08 08:14:58 $

package com.ubipass.middleware.ems;

import com.ubipass.middleware.jdbc.DeviceDAO;
import com.ubipass.middleware.jdbc.po.DevicePo;
import com.ubipass.middleware.plugin.Device;
import com.ubipass.middleware.plugin.DeviceOperationException;
import com.ubipass.middleware.plugin.Tag;
import com.ubipass.middleware.plugin.UnsupportedFeatureException;
import com.ubipass.middleware.util.ThreadPool;
import com.ubipass.middleware.util.log.SystemLogger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Class to manipulate device through device plug-in.
 *
 * @author Dong Hongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.50 $
 */
public class DeviceList {

    // deviceMap Key is device's deviceID, value is Device referecne
    private static HashMap<String, Device> deviceMap = new HashMap<String, Device>();

    /**
     * Get device map.
     *
     * @return deviceMap.
     */
    public static HashMap<String, Device> getDeviceMap() {
        return deviceMap;
    }

    /**
     * Stop device.
     *
     * @param deviceID device ID
     * @throws DeviceOperationException
     */
    public static void stopDevice(String deviceID)
        throws DeviceOperationException {

        if (deviceID == null || deviceID.equals("")) {
            return;
        }

        Device device = deviceMap.get(deviceID);

        if (device != null) {
            device.closeDevice();
            deviceMap.remove(deviceID);

            // update removeTime column in table observation
            try {
                EMSUtil.observation.updateReaderRemoveTime(deviceID, new Date().getTime());
            } catch (Exception e) {
                SystemLogger.error("[DeviceList] stopDevice() error: " + e.getMessage());
            }

            SystemLogger.info("Device " + device.getDeviceName() + " stopped");
        } else {
            throw new DeviceOperationException("Device " + deviceID + " does not exist or it has already been closed");
        }

    }

    /**
     * Stop all the devices.
     *
     * @return boolean true if all devices are stopped successfully
     */
    public static synchronized boolean stopAllDevices() {

        boolean isAllStop = true;

        for (Device device : deviceMap.values()) {
            String deviceName = device.getDeviceName();

            try {
                device.closeDevice();
                SystemLogger.info("Device " + deviceName + " stopped");
            } catch (DeviceOperationException e) {
                isAllStop = false;
                SystemLogger.error("Device " + deviceName + " stop failed: " + e.getMessage());
            }
        }

        deviceMap = null;

        return isAllStop;

    }

    /**
     * Check device's status.
     *
     * @param deviceID
     * @return boolean true if device is running normally
     */
    public static boolean getDeviceStatus(String deviceID) {
        Device device = deviceMap.get(deviceID);

        if (device != null)
            return device.isDeviceWorking();
        else
            return false;
    }

    /**
     * Add a device to hashmap and start it.
     *
     * @param device DevicePo
     * @throws DeviceOperationException
     */
    public static void addDevice(DevicePo device)
        throws DeviceOperationException {

        Device deviceInstance;
        int openMode;

        if (getDeviceStatus(device.getDeviceID())) {
            throw new DeviceOperationException("Device " + device.getDeviceName() + " has been started");
        }

        try {
            // new a plugin instance
            deviceInstance = (Device) Class.forName(device.getClassName()).newInstance();
        } catch (Exception e) {
            throw new DeviceOperationException("Cannot create instance for Device "
                + device.getDeviceName() + ", Plugin Class: "
                + device.getClassName());
        }

        // open device
        deviceInstance.setDeviceName(device.getDeviceName());
        deviceInstance.setDeviceID(device.getDeviceID());
        deviceInstance.setEventQueue(EMSUtil.eventQueue);
        deviceInstance.setUserName(device.getUserName());
        deviceInstance.setPassword(device.getPasswd());
        deviceInstance.setPersistTime(device.getPersistTime());

        if ("P".equalsIgnoreCase(device.getDeviceType())) {
            // device is a printer
            openMode = Device.PRINTER_MODE;
        } else {
            // device is a reader
            openMode = Device.READER_MODE;
        }

        if (device.getConnectionType().equalsIgnoreCase("T")) {
            // TCP/IP connection
            try {
                deviceInstance.openNetworkDevice(device.getConnectionName(), device.getPort(), openMode);
            } catch (UnsupportedFeatureException e) {
                throw new DeviceOperationException("Device "
                    + device.getDeviceName()
                    + " does not support openNetworkDevice()");
            }
        } else if (device.getConnectionType().equalsIgnoreCase("S")) {
            // Serial connection
            try {
                deviceInstance.openSerialDevice(device.getConnectionName(), device.getPort(), openMode);
            } catch (UnsupportedFeatureException e) {
                throw new DeviceOperationException("Device "
                    + device.getDeviceName()
                    + " does not support openSerialDevice()");
            }
        } else {
            throw new DeviceOperationException("ConnectionType of device "
                + device.getDeviceName() + " is unknown");
        }

        if (deviceInstance instanceof Runnable && openMode == Device.READER_MODE) {
            // start device thread
            ThreadPool.getInstance().execute((Runnable) deviceInstance);
        }

        SystemLogger.info("Device " + device.getDeviceName() + " started successfully");

        // put the device to hashmap
        deviceMap.put(device.getDeviceID(), deviceInstance);

    }

    /**
     * Start all devices whose startup type is automatic.
     */
    public static synchronized void startupAllDevices() {

        List<DevicePo> deviceList;

        try {
            deviceList = DeviceDAO.getAllDevices();
        } catch (Exception e) {
            SystemLogger.error("[DeviceList] startupAllDevices() error: " + e.getMessage());

            return;
        }

        if (deviceList != null) {
            for (DevicePo devicePo : deviceList) {
                if (devicePo.getStartup().equalsIgnoreCase("A")) {
                    try {
                        addDevice(devicePo);
                    } catch (Exception e) {
                        SystemLogger.error("[DeviceList] startupAllDevices() error: " + e.getMessage());
                    }
                }
            }
        }

    }

    /**
     * Get current tag list of the device.
     *
     * @param deviceId
     * @return array of Tag
     * @throws DeviceOperationException
     * @throws UnsupportedFeatureException
     */
    public static Tag[] getTagList(String deviceId)
        throws DeviceOperationException, UnsupportedFeatureException {

        Device device = deviceMap.get(deviceId);

        if (device != null) {
            return device.getTagList();
        } else {
            return null;
        }

    }

    /**
     * Write tag.
     *
     * @param deviceId
     * @param label
     * @param info
     * @return true if tag is written and verified successfully; false if tag cannot be verifed
     * @throws DeviceOperationException    when tag cannot be written
     * @throws UnsupportedFeatureException
     */
    public static boolean writeTag(String deviceId, String label, String info)
        throws DeviceOperationException, UnsupportedFeatureException {

        Device device = deviceMap.get(deviceId);

        if (device != null) {
            return device.writeTag(label, info);
        } else {
            return false;
        }

    }

    /**
     * Check if the device can view tag.
     *
     * @param deviceID
     * @return true if device is a reader and not an one-time reader
     */
    public static boolean canViewTag(String deviceID) {
        Device device = deviceMap.get(deviceID);

        return device != null && device.isReader() && (!device.isOneTimeReader());
    }

    /**
     * Check if the device can write tag.
     *
     * @param deviceID
     * @return true if device can write tag
     */
    public static boolean canPrintTag(String deviceID) {
        Device device = deviceMap.get(deviceID);

        if (device != null)
            return device.isPrinter();
        else {
            return false;
        }
    }

}