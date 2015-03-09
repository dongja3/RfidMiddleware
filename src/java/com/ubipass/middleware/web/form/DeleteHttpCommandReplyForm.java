/*
 * Created on Jan 22, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ubipass.middleware.web.form;

import org.apache.struts.action.ActionForm;

/**
 * @author autoid
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DeleteHttpCommandReplyForm extends ActionForm {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private String[] httpCommandId;

    /**
     * @return Returns the id.
     */
    public String[] getHttpCommandId() {
        return httpCommandId;
    }
    /**
     * @param httpCommandId 
     */
    public void setHttpCommandId(String[] httpCommandId) {
        this.httpCommandId = httpCommandId;
    }
}
