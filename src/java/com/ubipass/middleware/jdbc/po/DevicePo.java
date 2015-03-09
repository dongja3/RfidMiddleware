//$Source $
//LastModified By: $Author: donghongshan $
//$Date: 2005/03/21 02:10:20 $
package com.ubipass.middleware.jdbc.po;

/**
* * ReaderPO class
* 
* @version $Revision: 1.3 $
* @author Donghongshan
* @author $Author: donghongshan $
*/
public class DevicePo {
	private int    id;
	private String connectionType;
	private String deviceType;
	private String deviceName;
	private String deviceID;
	private String userName;
	private String passwd;
	private String connectionName;
	private int port;
	private int persistTime;
	private String className;
	private String startup;
	private String description;
	private String command;
	private boolean status; //only to show
	private boolean isPrinterViewEPC;
	private boolean isReaderViewEPC;
	
	
	/**
	 * constructor
	 */
	public DevicePo(){}

	
	/**
	 * @return Returns the className.
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className The className to set.
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return Returns the connectionName.
	 */
	public String getConnectionName() {
		return connectionName;
	}
	/**
	 * @param connectionName The connectionName to set.
	 */
	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}
	/**
	 * @return Returns the connectionType.
	 */
	public String getConnectionType() {
		return connectionType;
	}
	/**
	 * @param connectionType The connectionType to set.
	 */
	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the deviceID.
	 */
	public String getDeviceID() {
		return deviceID;
	}
	/**
	 * @param deviceID The deviceID to set.
	 */
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	/**
	 * @return Returns the deviceName.
	 */
	public String getDeviceName() {
		return deviceName;
	}
	/**
	 * @param deviceName The deviceName to set.
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	/**
	 * @return Returns the deviceType.
	 */
	public String getDeviceType() {
		return deviceType;
	}
	/**
	 * @param deviceType The deviceType to set.
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	/**
	 * @return Returns the passwd.
	 */
	public String getPasswd() {
		return passwd;
	}
	/**
	 * @param passwd The passwd to set.
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	/**
	 * @return Returns the persistTime.
	 */
	public int getPersistTime() {
		return persistTime;
	}
	/**
	 * @param persistTime The persistTime to set.
	 */
	public void setPersistTime(int persistTime) {
		this.persistTime = persistTime;
	}
	
	/**
	 * @return Returns the port.
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port The port to set.
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * @return Returns the startup.
	 */
	public String getStartup() {
		return startup;
	}
	/**
	 * @param startup The startup to set.
	 */
	public void setStartup(String startup) {
		this.startup = startup;
	}
	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return Returns the command.
	 */
	public String getCommand() {
		return command;
	}
	/**
	 * @param command The command to set.
	 */
	public void setCommand(String command) {
		this.command = command;
	}
	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return Returns the status.
	 */
	public boolean isStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
    /**
     * @return Returns the isPrinterViewEPC.
     */
    public boolean isPrinterViewEPC() {
        return isPrinterViewEPC;
    }
    /**
     * @param isPrinterViewEPC The isPrinterViewEPC to set.
     */
    public void setPrinterViewEPC(boolean isPrinterViewEPC) {
        this.isPrinterViewEPC = isPrinterViewEPC;
    }
    /**
     * @return Returns the isReaderViewEPC.
     */
    public boolean isReaderViewEPC() {
        return isReaderViewEPC;
    }
    /**
     * @param isReaderViewEPC The isReaderViewEPC to set.
     */
    public void setReaderViewEPC(boolean isReaderViewEPC) {
        this.isReaderViewEPC = isReaderViewEPC;
    }
}
