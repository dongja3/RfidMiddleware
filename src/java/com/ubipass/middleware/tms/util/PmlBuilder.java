//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/tms/util/PmlBuilder.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/04/26 09:43:07 $

package com.ubipass.middleware.tms.util;

import com.ubipass.middleware.jdbc.po.ObservationPO;
import com.ubipass.middleware.tms.task.Task;
import com.ubipass.middleware.util.DateUtil;
import com.ubipass.middleware.util.XMLUtil;

import java.util.Date;
import java.util.List;

/**
 * PML Builder.
 *
 * @author Donghongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.22 $
 */
public class PmlBuilder {

    private static final int GTIN = 1;

    private static final String separator = System.getProperty("line.separator");
    private static final String blankLevel1 = "  ";
    private static final String blankLevel2 = blankLevel1 + blankLevel1;
    private static final String blankLevel3 = blankLevel2 + blankLevel1;
    private static final String blankLevel4 = blankLevel2 + blankLevel2;

    private String topLevelIDValue;
    private List<ObservationPO> tagList;
    private String readerID;
    private String command;
    private int pmlType;
    private int encoding;
    private StringBuffer pml;

    /**
     * Constructor.
     *
     * @param topLevelIDValue
     * @param readerID
     * @param command
     * @param pmlType
     */
    public PmlBuilder(String topLevelIDValue, String readerID, String command, int pmlType, int encoding) {
        this.topLevelIDValue = XMLUtil.escape(topLevelIDValue);
        this.readerID = XMLUtil.escape(readerID);
        this.command = (command == null) ? "" : command;
        this.pmlType = pmlType;
        this.encoding = encoding;
    }

    /**
     * Build notification PML.
     *
     * @param tagList
     * @return PML string
     */
    public String buildPml(List<ObservationPO> tagList) {

        this.tagList = tagList;
        pml = new StringBuffer();

        if (pmlType == Task.SIMPLIFIED_PML) {
            // simplified PML
            addSimplifiedPMLHead();
            addSimplifiedPMLObservation();
            addSimplifiedPMLEnd();
        } else {
            // full PML
            addFullPMLHead();
            addFullPMLObservation();
            addFullPMLEnd();
        }

        return pml.toString();

    }

    /**
     * Generate PML for tag printing.
     *
     * @param tagID tag ID
     * @return PML
     */
    public String printTagPml(String tagID) {

        pml = new StringBuffer();

        if (pmlType == Task.SIMPLIFIED_PML) {
            // simplified PML
            printTagSimplifiedPml(tagID);
        } else {
            // full PML
            printTagFullPml(tagID);
        }

        return pml.toString();

    }

    /**
     * Generate FULL PML for tag printing.
     *
     * @param tagID tag ID
     */
    private void printTagFullPml(String tagID) {

        pml = new StringBuffer();

        addFullPMLHead();

        pml.append(blankLevel1).append("<pmlcore:Observation>");
        pml.append(separator);
        pml.append(blankLevel2).append("<pmlcore:DateTime>");
        pml.append(DateUtil.convertToAIIDateFormat(new Date()));
        pml.append("</pmlcore:DateTime>");
        pml.append(separator);
        pml.append(blankLevel2).append("<pmlcore:Command>");
        pml.append(command);
        pml.append("</pmlcore:Command>");
        pml.append(separator);
        pml.append(blankLevel2).append("<pmlcore:Data>");
        pml.append(separator);
        pml.append(blankLevel3).append("<pmlcore:XML>");
        pml.append(separator);
        pml.append(blankLevel4).append("<ReaderID>");
        pml.append(readerID);
        pml.append("</ReaderID>");
        pml.append(separator);

        // add element <GTIN> or <SSCC>
        if (encoding == GTIN) {
            pml.append(blankLevel4).append("<GTIN>");
            pml.append(tagID);
            pml.append("</GTIN>");
        } else {
            pml.append(blankLevel4).append("<SSCC>");
            pml.append(tagID);
            pml.append("</SSCC>");
        }

        pml.append(separator);
        pml.append(blankLevel3).append("</pmlcore:XML>");
        pml.append(separator);
        pml.append(blankLevel2).append("</pmlcore:Data>");
        pml.append(separator);
        pml.append(blankLevel1).append("</pmlcore:Observation>");
        pml.append(separator);

        addFullPMLEnd();

    }

    /**
     * Generate Simplified PML for tag printing.
     *
     * @param tagID tag ID
     */
    private void printTagSimplifiedPml(String tagID) {

        pml = new StringBuffer();

        addSimplifiedPMLHead();

        pml.append(blankLevel1).append("<Observation>");
        pml.append(separator);
        pml.append(blankLevel2).append("<DateTime>");
        pml.append(DateUtil.convertToAIIDateFormat(new Date()));
        pml.append("</DateTime>");
        pml.append(separator);
        pml.append(blankLevel2).append("<Command>");
        pml.append(command);
        pml.append("</Command>");
        pml.append(separator);
        pml.append(blankLevel2).append("<Data>");
        pml.append(separator);
        pml.append(blankLevel3).append("<XML>");
        pml.append(separator);
        pml.append(blankLevel4).append("<ReaderID>");
        pml.append(readerID);
        pml.append("</ReaderID>");
        pml.append(separator);

        // add element <GTIN> or <SSCC>
        if (encoding == GTIN) {
            pml.append(blankLevel4).append("<GTIN>");
            pml.append(tagID);
            pml.append("</GTIN>");
        } else {
            pml.append(blankLevel4).append("<SSCC>");
            pml.append(tagID);
            pml.append("</SSCC>");
        }

        pml.append(separator);
        pml.append(blankLevel3).append("</XML>");
        pml.append(separator);
        pml.append(blankLevel2).append("</Data>");
        pml.append(separator);
        pml.append(blankLevel1).append("</Observation>");
        pml.append(separator);

        addSimplifiedPMLEnd();

    }

    /**
     * Add full pml head tag.
     */
    private void addFullPMLHead() {
        pml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        pml.append(separator);
        pml.append("<pmlcore:Sensor xmlns:pmlcore=\"urn:autoid:specification:interchange:PMLCore:xml:schema:1\"");
        pml.append(" xmlns:pmluid=\"urn:autoid:specification:universal:Identifier:xml:schema:1\"");
        pml.append(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">");
        pml.append(separator);
        pml.append(blankLevel1).append("<pmluid:ID>");
        pml.append(topLevelIDValue);
        pml.append("</pmluid:ID>");
        pml.append(separator);
    }

    /**
     * Add simplified pml head tag.
     */
    private void addSimplifiedPMLHead() {
        pml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        pml.append(separator);
        pml.append("<Sensor>");
        pml.append(separator);
        pml.append(blankLevel1).append("<ID>");
        pml.append(topLevelIDValue);
        pml.append("</ID>");
        pml.append(separator);
    }

    /**
     * Add full pml end tag.
     */
    private void addFullPMLEnd() {
        pml.append("</pmlcore:Sensor>");
    }

    /**
     * Add simplified pml end tag.
     */
    private void addSimplifiedPMLEnd() {
        pml.append("</Sensor>");
    }

    /**
     * Add full pml observation tag.
     */
    private void addFullPMLObservation() {

        pml.append(blankLevel1).append("<pmlcore:Observation>");
        pml.append(separator);
        pml.append(blankLevel2).append("<pmlcore:DateTime>");
        pml.append(DateUtil.convertToAIIDateFormat(new Date()));
        pml.append("</pmlcore:DateTime>");
        pml.append(separator);
        pml.append(blankLevel2).append("<pmlcore:Command>");
        pml.append(command);
        pml.append("</pmlcore:Command>");
        pml.append(separator);

        for (ObservationPO tag : tagList) {
            pml.append(blankLevel2).append("<pmlcore:Tag>");
            pml.append(separator);
            pml.append(blankLevel3).append("<pmluid:ID>");
            pml.append(tag.getTagId());
            pml.append("</pmluid:ID>");
            pml.append(separator);
            pml.append(blankLevel2).append("</pmlcore:Tag>");
            pml.append(separator);
        }

        pml.append(blankLevel2).append("<pmlcore:Data>");
        pml.append(separator);
        pml.append(blankLevel3).append("<pmlcore:XML>");
        pml.append(separator);
        pml.append(blankLevel4).append("<ReaderID>");
        pml.append(readerID);
        pml.append("</ReaderID>");
        pml.append(separator);
        pml.append(blankLevel3).append("</pmlcore:XML>");
        pml.append(separator);
        pml.append(blankLevel2).append("</pmlcore:Data>");
        pml.append(separator);

        pml.append(blankLevel1).append("</pmlcore:Observation>");
        pml.append(separator);
    }

    /**
     * Add simplified pml observation tag.
     */
    private void addSimplifiedPMLObservation() {

        pml.append(blankLevel1).append("<Observation>");
        pml.append(separator);
        pml.append(blankLevel2).append("<DateTime>");
        pml.append(DateUtil.convertToAIIDateFormat(new Date()));
        pml.append("</DateTime>");
        pml.append(separator);
        pml.append(blankLevel2).append("<Command>");
        pml.append(command);
        pml.append("</Command>");
        pml.append(separator);

        for (ObservationPO tag : tagList) {
            pml.append(blankLevel2).append("<Tag>");
            pml.append(separator);
            pml.append(blankLevel3).append("<ID>");
            pml.append(tag.getTagId());
            pml.append("</ID>");
            pml.append(separator);
            pml.append(blankLevel2).append("</Tag>");
            pml.append(separator);
        }

        pml.append(blankLevel2).append("<Data>");
        pml.append(separator);
        pml.append(blankLevel3).append("<XML>");
        pml.append(separator);
        pml.append(blankLevel4).append("<ReaderID>");
        pml.append(readerID);
        pml.append("</ReaderID>");
        pml.append(separator);
        pml.append(blankLevel3).append("</XML>");
        pml.append(separator);
        pml.append(blankLevel2).append("</Data>");
        pml.append(separator);

        pml.append(blankLevel1).append("</Observation>");
        pml.append(separator);

    }

}
