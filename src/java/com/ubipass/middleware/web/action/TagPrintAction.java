//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/web/action/TagPrintAction.java,v $
//LastModified By: $Author: donghongshan $
//$Date: 2005/06/01 06:32:53 $

package com.ubipass.middleware.web.action;

import com.ubipass.middleware.ems.DeviceList;
import com.ubipass.middleware.jdbc.DeviceDAO;
import com.ubipass.middleware.plugin.Device;
import com.ubipass.middleware.web.form.TagPrintForm;
import com.ubipass.middleware.web.vo.TagPrintVO;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;

/**
 * Print Tag Action.
 * 
 * @author Donghongshan
 * @author $Author: donghongshan $
 * @version $Revision: 1.17 $
 */
public class TagPrintAction extends Action {

	/**
	 * Action to write tag.
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
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		request.getSession().setAttribute("page", "1,3");

		if (form == null) {
			throw new NoParameterException();
		}

		TagPrintForm tagPrintForm = (TagPrintForm) form;
		String deviceId;
		Device device = null;

		// do if call this action first time
		deviceId = request.getParameter("deviceId");
		if (deviceId != null && !deviceId.equals("")) {
			// get device From DevicList
			device = DeviceList.getDeviceMap().get(deviceId);

			if (device == null) {
				throw new Exception("Device " + deviceId + " stopped");
			}

			checkAuthorization(request, deviceId);

			TagPrintVO tagPrintVo = new TagPrintVO();
			tagPrintVo.setDeviceId(deviceId);
			tagPrintVo.setDeviceName(device.getDeviceName());
			tagPrintVo.setPrintNum("");
			tagPrintVo.setStartTagId("");
			tagPrintVo.setWriteLabel("");

			request.setAttribute("tagPrint", tagPrintVo);
			return mapping.findForward("tagPrint");
		}

		deviceId = tagPrintForm.getDeviceId();
		device = DeviceList.getDeviceMap().get(deviceId);

		// check if the device stopped
		if (device == null) {
			throw new Exception("Device " + deviceId + " stopped");
		}

		TagPrintVO tagPrintVo = new TagPrintVO();
		tagPrintVo.setDeviceId(deviceId);
		tagPrintVo.setDeviceName(device.getDeviceName());

		String writeLabel = tagPrintForm.getWriteLabel();
		tagPrintVo.setWriteLabel(writeLabel);

		String epc = tagPrintForm.getStartTagId().toUpperCase();

		String increment = tagPrintForm.getIncrement();
		String algorithm = tagPrintForm.getAlgorithm();
		boolean isHex = "HEX".equalsIgnoreCase(algorithm);
		boolean isIncrement = "YES".equalsIgnoreCase(increment);

		tagPrintVo.setIncrement(increment);
		tagPrintVo.setAlgorithm(algorithm);

		// check the epc
		if (isIncrement == true) {
			if (!checkEPC(epc, isHex)) {
				tagPrintVo.setPrintingLabel("");
				tagPrintVo.setStartTagId(epc);
				tagPrintVo.setPrintNum(tagPrintForm.getPrintNum());

				if (isHex) {
					request.setAttribute("printError", "EPCError.hex");
				} else {
					request.setAttribute("printError", "EPCError.dec");
				}

				request.setAttribute("tagPrint", tagPrintVo);
				return mapping.findForward("tagPrint");
			}
		} else if (epc.trim().length() < 1) {
			request.setAttribute("printError", "EPCError.empty");
			request.setAttribute("tagPrint", tagPrintVo);
			return mapping.findForward("tagPrint");
		}
		// check printNum
		int printNum = 0;

		tagPrintVo.setStartTagId(epc);

		try {
			printNum = Integer.parseInt(tagPrintForm.getPrintNum());
		} catch (Exception e) {
			tagPrintVo.setPrintNum("0");
			tagPrintVo.setPrintingLabel("");
			request.setAttribute("printError", "CountIsNotNum");
			request.setAttribute("tagPrint", tagPrintVo);
			return mapping.findForward("tagPrint");
		}

		if (printNum <= 0) {
			tagPrintVo.setPrintNum("0");
			tagPrintVo.setPrintingLabel("");
			request.setAttribute("tagPrint", tagPrintVo);
			request.setAttribute("printError", "CountIsZero");
			return mapping.findForward("tagPrint");
		}

		if (printNum > 10000) {
			tagPrintVo.setPrintNum("10000");
			tagPrintVo.setPrintingLabel("");
			request.setAttribute("tagPrint", tagPrintVo);
			request.setAttribute("printError", "OutofMax");
			return mapping.findForward("tagPrint");
		}

		// do batchprint
		if (tagPrintForm.getPrintStyle().equalsIgnoreCase("BATCH")) {
			String printingTagId = tagPrintForm.getStartTagId().toUpperCase();

			for (int i = 0; i < printNum; i++) {
				if (DeviceList.writeTag(deviceId, writeLabel, printingTagId)) {
					request.setAttribute("printInfo", "succeed");
				} else {
					request.setAttribute("printInfo", "failed");
				}

				if (isIncrement && (i < printNum - 1))
					printingTagId = epcIncrease(printingTagId, isHex);
			}

			tagPrintVo.setPrintNum("0");
			tagPrintVo
					.setStartTagId(tagPrintForm.getStartTagId().toUpperCase());
			tagPrintVo.setPrintingLabel("");
			tagPrintVo.setAlgorithm(tagPrintForm.getAlgorithm());
			tagPrintVo.setIncrement(tagPrintVo.getIncrement());
			request.setAttribute("tagPrint", tagPrintVo);
			request.setAttribute("printedNumber", tagPrintForm.getPrintNum());

			if (isIncrement == true)
				request.setAttribute("printedTag", "["
						+ tagPrintForm.getStartTagId().toUpperCase() + "]~["
						+ printingTagId + "]");

			return mapping.findForward("tagPrint");
		}

		// item print
		if (isIncrement == true)
			request.setAttribute("printedTag", "[" + epc + "]");

		if (DeviceList.writeTag(deviceId, writeLabel, epc)) {
			request.setAttribute("printInfo", "succeed");
		} else {
			request.setAttribute("printInfo", "failed");
		}

		tagPrintVo.setPrintNum(String.valueOf(printNum - 1));

		if (isIncrement) {
			if (checkEPC(epc, isHex)) {
				epc = epcIncrease(epc, isHex);
			} else {
				tagPrintVo.setPrintingLabel("");
				request.setAttribute("tagPrint", tagPrintVo);

				if (isHex) {
					request.setAttribute("printError", "EPCError.hex");
				} else {
					request.setAttribute("printError", "EPCError.dec");
				}

				tagPrintVo.setStartTagId(epc);
				return mapping.findForward("tagPrint");
			}
		}

		tagPrintVo.setPrintingLabel(checkWriteLabel(writeLabel, epc));
		tagPrintVo.setStartTagId(epc);

		request.setAttribute("tagPrint", tagPrintVo);
		return mapping.findForward("tagPrint");

	}

	/**
	 * Check if written label contains %.
	 * 
	 * @param label
	 * @param printingTagId
	 * @return String
	 */
	private String checkWriteLabel(String label, String printingTagId) {

		if (label.startsWith("%")) {
			label = printingTagId + label.substring(1, label.length());
		}

		if (label.endsWith("%")) {
			if (label.length() >= 2)
				label = label.substring(0, label.length() - 1) + printingTagId;
			else
				label = printingTagId;
		}

		String[] splitLabel = label.split("%");
		StringBuffer sb = new StringBuffer();

		if (splitLabel.length > 1) {
			for (int i = 0; i < splitLabel.length - 1; i++) {
				sb.append(splitLabel[i]);
				sb.append(printingTagId);
			}

			sb.append(splitLabel[splitLabel.length - 1]);
		} else {
			sb.append(label);
		}

		return sb.toString();

	}

	/**
	 * Check if tag ID is correct hex or decimal.
	 * 
	 * @param epc
	 * @param isHex
	 * @return true if epc is correct
	 */
	private boolean checkEPC(String epc, boolean isHex) {
		if (epc.length() == 0)
			return false;

		epc = epc.toUpperCase();
		byte[] bytes = epc.getBytes();

		if (isHex) {
			for (byte value : bytes) {
				if (value < 48 || value > 70 || (value > 57 && value < 65))
					return false;
			}

			return true;
		}

		for (byte value : bytes) {
			if (value < 48 || value > 57) {
				return false;
			}
		}

		return true;

	}

	/**
	 * Increase ID by 1.
	 * 
	 * @param epc
	 * @param isHex
	 * @return number string after increment
	 */
	private String epcIncrease(String epc, boolean isHex) {

		BigInteger bigInt;

		if (isHex) {
			bigInt = new BigInteger(epc, 16);
			bigInt = bigInt.add(new BigInteger("1"));
			return bigInt.toString(16).toUpperCase();
		} else {
			bigInt = new BigInteger(epc, 10);
			bigInt = bigInt.add(new BigInteger("1"));
			return bigInt.toString(10).toUpperCase();
		}

	}

	/**
	 * Check if the device is assigned to the user.
	 * 
	 * @param request
	 * @param deviceId
	 * @throws Exception
	 */
	private void checkAuthorization(HttpServletRequest request, String deviceId)
			throws Exception {

		if (!request.isUserInRole("Administrator")
				&& !DeviceDAO.isDeviceAssignedToUser(request.getRemoteUser(),
						deviceId)) {
			// device isn't assigned to the user
			throw new Exception("Device " + deviceId
					+ " is not assigned to the user");
		}

	}

}
