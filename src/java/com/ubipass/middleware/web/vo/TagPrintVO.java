//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/vo/TagPrintVO.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/26 09:21:36 $

package com.ubipass.middleware.web.vo;

/**
 * TagPrintVO class
 *
 * @author Donghongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.6 $
 */
public class TagPrintVO {

    private String writeLabel;
    private String startTagId;
    private String deviceId;
    private String printNum;
    private String deviceName;
    private String printingLabel;
    private String algorithm;
    private String increment;

    /**
     * @return printingLabel
     */
    public String getPrintingLabel() {
        return printingLabel;
    }

    /**
     * @param printingLabel
     */
    public void setPrintingLabel(String printingLabel) {
        this.printingLabel = printingLabel;
    }

    /**
     * @return device Name
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * @param deviceName
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * @return deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    /**
     * @return printNum
     */
    public String getPrintNum() {
        return printNum;
    }

    /**
     * @param printNum
     */
    public void setPrintNum(String printNum) {
        this.printNum = printNum;
    }

    /**
     * @return startTagId
     */
    public String getStartTagId() {
        return startTagId;
    }

    /**
     * @param startTagId
     */
    public void setStartTagId(String startTagId) {
        this.startTagId = startTagId;
    }

    /**
     * @return writeLabel
     */
    public String getWriteLabel() {
        return writeLabel;
    }

    /**
     * @param writeLabel
     */
    public void setWriteLabel(String writeLabel) {
        this.writeLabel = writeLabel;
    }

    /**
     * @return algorithm
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * @param algorithm
     */
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * @return increment;
     */
    public String getIncrement() {
        return increment;
    }

    /**
     * @param increment
     */
    public void setIncrement(String increment) {
        this.increment = increment;
    }

}
