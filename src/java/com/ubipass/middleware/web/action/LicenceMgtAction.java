/*
 * $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/LicenceMgtAction.java,v $
 * LastModified By: $Author: donghongshan $
 * $Date: 2005/05/11 03:54:42 $
 * 
 */

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.ems.exception.InvalidLicenseException;

import org.apache.commons.codec.binary.Base64;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Properties;

/**
 * Action class to list HTTP command interface.
 * 
 * @author Donghongshan
 * @author $Author: donghongshan $
 * @version $Revision: 1.10 $
 */
public class LicenceMgtAction extends Action {
	private static final String LICENCE_FILE = "conf/licence.properties";

	/**
	 * Action of display licence management page.
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

		File file = new File(this.getServlet().getServletContext().getRealPath(
				"/" + LICENCE_FILE));
		FileInputStream is;
		String userName = "";
		String licenceKey="";
		String ExperDate = "0000-00-00";
		try {
			is = new FileInputStream(file);

			Properties properties = new Properties();
			properties.load(is);

			userName = properties.getProperty("userName");
			licenceKey = properties.getProperty("licenceKey");

			ExperDate = getDate(userName, licenceKey);
			Calendar cal = Calendar.getInstance();

			try {
				cal.setTimeInMillis(Long.parseLong(ExperDate));

				ExperDate = String.valueOf(cal.get(Calendar.YEAR)) + "-"
						+ strReformat(cal.get(Calendar.MONTH) + 1) + "-"
						+ strReformat(cal.get(Calendar.DATE));

			} catch (Exception e) {
				ExperDate = "0000-00-00";
			}

			request.setAttribute("userName", userName);
			request.setAttribute("ExperDate", ExperDate);
			request.setAttribute("licenceKey", licenceKey);

			is.close();

			return mapping.findForward("Successful");
		} catch (Exception e) {
			request.setAttribute("error","error");
			request.setAttribute("userName", userName);
			request.setAttribute("ExperDate", ExperDate);
			request.setAttribute("licenceKey", licenceKey);
			return mapping.findForward("Successful");
		}
	}

	/**
	 * reformat month or day.
	 * 
	 * @param number
	 * @return reformat String
	 */
	private String strReformat(int number) {
		if (number > 10)
			return String.valueOf(number);
		else
			return ("0" + String.valueOf(number));
	}

	private String getDate(String userName, String licenceKey) throws Exception {

		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		byte rawKeyData[] = (userName + "midware").getBytes();

		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(rawKeyData);

		// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
		// 一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey key = keyFactory.generateSecret(dks);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");

		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, key, sr);

		// 正式执行解密操作
		licenceKey = new String(cipher.doFinal(Base64.decodeBase64(licenceKey
				.getBytes())));

		String[] tmpStr = licenceKey.split("-");

		if (tmpStr.length == 2)
			return tmpStr[1];

		throw new InvalidLicenseException();

	}

}
