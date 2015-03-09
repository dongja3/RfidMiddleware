/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/SaveLicenceMgtAction.java,v $
 * LastModified By: $Author: donghongshan $
 * $Date: 2005/04/26 06:39:08 $
 * 
 */

package com.ubipass.middleware.web.action;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import com.ubipass.middleware.web.form.SaveLicenceKeyForm;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * *SaveSysConfigAction class
 * 
 * @author Donghongshan
 * @author $Author: donghongshan $
 * @version $Revision: 1.4 $
 */
public class SaveLicenceMgtAction extends Action {
	private static final String LICENCE_FILE = "conf/licence.properties";
	/**
	 * Action class to save Licence key information
	 * 
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward forward to Struts page
	 * @throws Exception
	 * @see org.apache.struts.action.Action
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		request.getSession().setAttribute("page", "0,2");

		if (form == null) {
			throw new NoParameterException("No action From found");
		}
		
		SaveLicenceKeyForm s = (SaveLicenceKeyForm) form;
		
		String userName = s.getUserName();
		String licenceKey = s.getLicenceKey();
		
		File file = new File(this.getServlet().getServletContext().getRealPath("/")+LICENCE_FILE);
		
		if(!file.exists())
			file.createNewFile();
		
		FileOutputStream os = new FileOutputStream(file);
		
		Properties properties = new Properties();
		
		properties.setProperty("userName",userName);
		properties.setProperty("licenceKey",licenceKey);
		
		properties.store(os,"Ubipass Auto-ID Middleware Licence Key file");
		
		request.setAttribute("userName",userName);
		request.setAttribute("licenceKey",licenceKey);

		os.close();
		
		return mapping.findForward("Successful");

	}

}
