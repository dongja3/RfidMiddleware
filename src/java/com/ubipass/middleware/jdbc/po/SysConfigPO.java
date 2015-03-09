/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/po/SysConfigPO.java,v $
 * LastModified By: $Author: fengjianyuan $
 * $Date: 2005/01/21 07:45:26 $
 * 
 */
package com.ubipass.middleware.jdbc.po;

/**
 * *SysConfigPO class
 * 
 * @version $Revision: 1.1 $
 * @author Feng Jianyuan
 * @author $Author: fengjianyuan $
 */
public class SysConfigPO {

    private String systemName;
    private int queueSize=1000;
    private String soapStartup;
    private String messageStartup;
    private String httpStartup;
    
    /**
     * @return Returns the httpStartup.
     */
    public String getHttpStartup() {
        return httpStartup;
    }
    /**
     * @param httpStartup The httpStartup to set.
     */
    public void setHttpStartup(String httpStartup) {
        this.httpStartup = httpStartup;
    }

    /**
     * @return Returns the messageStartup.
     */
    public String getMessageStartup() {
        return messageStartup;
    }
    /**
     * @param messageStartup The messageStartup to set.
     */
    public void setMessageStartup(String messageStartup) {
        this.messageStartup = messageStartup;
    }
    /**
     * @return Returns the queueSize.
     */
    public int getQueueSize() {
        return queueSize;
    }
    /**
     * @param queueSize The queueSize to set.
     */
    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }
    /**
     * @return Returns the soapStartup.
     */
    public String getSoapStartup() {
        return soapStartup;
    }
    /**
     * @param soapStartup The soapStartup to set.
     */
    public void setSoapStartup(String soapStartup) {
        this.soapStartup = soapStartup;
    }
    /**
     * @return Returns the systemName.
     */
    public String getSystemName() {
        return systemName;
    }
    /**
     * @param systemName The systemName to set.
     */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
