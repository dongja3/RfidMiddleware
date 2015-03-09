//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/form/TagPrintForm.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/29 02:31:32 $

package com.ubipass.middleware.web.form;

import org.apache.struts.action.ActionForm;

/**
 * TagPrint form class.
 *
 * @author Donghongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.4 $
 */
public class TagPrintForm extends ActionForm {

    private String writeLabel;
    private String printingTagId;
    private String startTagId;
    private String deviceId;
    private String printNum;
    private String algorithm;
    private String increment;
    private String printStyle;

    /**
     * @return printStyle
     */
    public String getPrintStyle() {
        return printStyle;
    }

    /**
     * @param printStyle
     */
    public void setPrintStyle(String printStyle) {
        this.printStyle = printStyle;
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
     * @return increment
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
     * @return printngTagId
     */
    public String getPrintingTagId() {
        return printingTagId;
    }

    /**
     * @param printingTagId
     */
    public void setPrintingTagId(String printingTagId) {
        this.printingTagId = printingTagId;
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

}
