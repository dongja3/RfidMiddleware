//$Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/ems/EMSUtil.java,v $
//LastModified By: $Author: shenjun $
//$Date: 2005/05/26 06:52:35 $

package com.ubipass.middleware.ems;

import com.ubipass.middleware.ems.exception.DBOperateException;
import com.ubipass.middleware.ems.exception.InvalidLicenseException;
import com.ubipass.middleware.jdbc.ObservationDAO;
import com.ubipass.middleware.jdbc.SysConfigDAO;
import com.ubipass.middleware.plugin.Event;
import com.ubipass.middleware.util.DBConnPool;
import com.ubipass.middleware.util.ThreadPool;
import com.ubipass.middleware.util.log.SystemLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Initialize EMS components of EventQueue and EventLogger.
 * 
 * @author Dong Hongshan
 * @author $Author: shenjun $
 * @version $Revision: 1.54 $
 */
public final class EMSUtil {

	public static ArrayBlockingQueue<Event> eventQueue;

	public static ObservationDAO observation = null;

	private static EventWorker worker;

	private static TimeUpdater timeUpdater;

	private static boolean isRunning = false;

	private static final String versionNum = "2.0";

	private static final String buildNum = "115";

	// for licence check
	private static final String LICENCE_FILE = "conf/licence.properties";

	private static String path;

	private static String userName;

	private static String licenceKey;

	private static String licenceUser;

	private static String licenceExpDate;

	// static block to initialize event queue
	static {
		// set default queueSize 1000
		int dbQueueSize = 1000;

		try {
			observation = new ObservationDAO();

			// query the database to get the queue size
			// if got exception then use default queue size
			dbQueueSize = SysConfigDAO.getQueueSize();
		} catch (Exception e) {
			SystemLogger.error(e.getMessage());
		}

		eventQueue = new ArrayBlockingQueue<Event>(dbQueueSize);
	}

	/**
	 * Startup EMS.
	 * 
	 * @return boolean
	 */
	public static synchronized boolean startupEMS() {

		// check license
		long expiredDate;

		try {
			expiredDate = EMSUtil.checkLicence();
		} catch (Exception e) {
			SystemLogger.error("System cannot find valid license");
			return false;
		}

		if (expiredDate < new Date().getTime()) {
			SystemLogger.error("License has expired");
			return false;
		}

		if (observation == null) {
			SystemLogger.error("[EMS] Cannot setup database connection");
			return false;
		}

		if (isRunning) {
			// EMS is running
			return true;
		}

		SystemLogger.info("[EMS] EMS is starting...");

		// update table observation
		try {
			observation.updateSystemRemoveTime(getLastShutdownTime());
		} catch (Exception e) {
			SystemLogger.error("[EMS] Update table observation error: "
					+ e.getMessage());
			return false;
		}

		ExecutorService executor = ThreadPool.getInstance();

		if (executor == null) {
			SystemLogger
					.error("[EMS] Cannot get an ExecutorService from thread pool");
			return false;
		}

		try {
			// start event worker
			worker = new EventWorker();

			executor.execute(worker);
		} catch (Exception e) {
			SystemLogger.error("[EMS] Starting EventWorker error: "
					+ e.getMessage());
			return false;
		}

		try {
			timeUpdater = new TimeUpdater(expiredDate);

			// start time updater
			executor.execute(timeUpdater);
		} catch (Exception e) {
			SystemLogger.error("[EMS] Starting TimeUpdater error: "
					+ e.getMessage());
			return false;
		}

		// start all devices
		DeviceList.startupAllDevices();

		SystemLogger.info("[EMS] EMS started successfully");
		isRunning = true;
		return true;

	}

	/**
	 * Stop the Event Management System.
	 * 
	 * @return true if everything is okay
	 */
	public static synchronized boolean stopEMS() {

		boolean isSucceed = true;

		if (isRunning) {
			SystemLogger.info("[EMS] EMS is stopping...");

			// stop all deveices
			isSucceed = DeviceList.stopAllDevices();

			// stop event worker
			worker.stopEventWorker();

			// stop time updater
			timeUpdater.stop();

			// make sure to set removeTime column in table observation
			// for all records whose removeTime is null
			try {
				observation.updateSystemRemoveTime(new Date().getTime());
			} catch (Exception e) {
				SystemLogger.error("[EMS] stopEMS() error: " + e.getMessage());
			}

			isRunning = false;
			SystemLogger.info("[EMS] EMS stopped successfully");
		} else {
			SystemLogger.info("[EMS] EMS stopped");
		}

		return isSucceed;

	}

	/**
	 * Log Event to database.
	 * 
	 * @param event
	 * @throws DBOperateException
	 */
	public static void logEvent(Event event) throws DBOperateException {

		if (observation == null) {
			throw new DBOperateException(
					"[EMS] Database connection is not set up properly");
		}

		try {
			switch (event.getEventType()) {
			case Event.ADD:
				observation.insertAddEvent(event);
				break;

			case Event.READ:
				observation.insertReadEvent(event);
				break;

			case Event.REMOVE:
				observation.updateEvent(event);
				break;

			default:
				SystemLogger.warn("[EMS] Unknown EventType: "
						+ event.getEventType());
			}
		} catch (Exception e) {
			throw new DBOperateException(e.getMessage());
		}

	}

	/**
	 * Get last time of system shutdown.
	 * 
	 * @return time stamp of last system shutdown
	 */
	private static long getLastShutdownTime() {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		long result = 0;

		try {
			conn = DBConnPool.getConnection();

			// get last shutdown time from table sysconfig
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT currentTime FROM sysconfig");
			rs.next();
			result = rs.getLong(1);
		} catch (Exception e) {
			SystemLogger.error("[EMS] getLastShutdownTime() error: "
					+ e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (stmt != null) {
					stmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {

			}
		}

		return (result == 0) ? new Date().getTime() : result;

	}

	/**
	 * Get version number.
	 * 
	 * @return version number.
	 */
	public static String getVersionNum() {
		return versionNum;
	}

	/**
	 * Get build number.
	 * 
	 * @return build number
	 */
	public static String getBuildNum() {
		return buildNum;
	}

	/**
	 * Check expired date.
	 * 
	 * @return long date
	 * @throws Exception
	 */
	private static long checkDate() throws Exception {
		long expDate = Long.parseLong(licenceExpDate);	
		return expDate;
	}

	/**
	 * Check user name.
	 * 
	 * @throws Exception
	 */
	private static void checkUserName() throws Exception {
		if(!userName.equals(licenceUser))
			throw new InvalidLicenseException();
	}

	/**
	 * set all licence parameters
	 * 
	 * @throws Exception
	 */
	private static void setParameters() throws Exception {

		File file = new File(path + LICENCE_FILE);
		FileInputStream is;

		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new InvalidLicenseException();
		}

		Properties properties = new Properties();
		properties.load(is);

		userName = properties.getProperty("userName");
		licenceKey = properties.getProperty("licenceKey");

		if (userName == null || licenceKey == null) {
			throw new InvalidLicenseException();
		}

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

		String tmpStr[] = licenceKey.split("-");

		if (tmpStr.length != 2)
			throw new InvalidLicenseException();

		licenceUser = tmpStr[0];
		licenceExpDate = tmpStr[1];

	}

	/**
	 * Set path for license file.
	 * 
	 * @param path
	 */
	public static void setPath(String path) {
		EMSUtil.path = path;
	}

	/**
	 * Call this method to check the licence Key include Hardwarekey.
	 * 
	 * @return long date
	 * @throws Exception
	 */
	public static long checkLicence() throws Exception {

		setParameters();
		checkUserName();
		return checkDate();

	}

}