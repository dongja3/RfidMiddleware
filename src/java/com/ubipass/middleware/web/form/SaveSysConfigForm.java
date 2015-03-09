/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/form/SaveSysConfigForm.java,v $
 * LastModified By: $Author: fengjianyuan $
 * $Date: 2005/01/25 04:10:52 $
 * 
 */
package com.ubipass.middleware.web.form;

import org.apache.struts.action.ActionForm;

/**
 * * SysConfigForm class
 * 
 * @version $Revision: 1.2 $
 * @author Feng jianyuan
 * @author $Author: fengjianyuan $
 */

public class SaveSysConfigForm extends ActionForm{

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    private String systemName;
    private String queuesize;
    private String soapStartup;
    private String messageStartup;

    

    /**
     * @return Returns the serialVersionUID.
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
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
     * @return Returns the queuesize.
     */
    public String getQueuesize() {
        return queuesize;
    }
    /**
     * @param queuesize The queuesize to set.
     */
    public void setQueuesize(String queuesize) {
        this.queuesize = queuesize;
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
