package com.ubipass.middleware.LicenceKey;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author dhs
 */
public class KeyGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out
				.println("******************************************************");
		System.out
				.println("* Welcome to Ubipass Middleware LicenceKey Generator *");
		System.out
				.println("******************************************************");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		try {
				

			System.out.print("\nPlease input userName: ");
			String userName = in.readLine();

			System.out.print("\nPlease input expired date (yyyy-mm-dd): ");
			String expiredDate = in.readLine();
			String[] date = expiredDate.split("-");

			while (date.length != 3) {
				System.out.print("\nPlease input expired date (yyyy-mm-dd): ");
				expiredDate = in.readLine();
				date = expiredDate.split("-");
			}

			date[1] = strReformat(date[1]);
			date[2] = strReformat(date[2]);
			expiredDate = date[0]+"-"+date[1]+"-"+date[2];
			
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1,
					Integer.parseInt(date[2]));
			String time = Long.toString(cal.getTimeInMillis());
			
			String licenceKey = encode(userName,time);

			System.out.println("===============================================");
			
			System.out.println("licenKey:" + licenceKey);
			
			System.out.println("===============================================");
			
			System.out.println("------Presss any key to exist-------");
			String quit=in.readLine();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * reformat month or day
	 * 
	 * @param string
	 * @return reformat String
	 */
	private static String strReformat(String string) {

		if (Integer.parseInt(string) > 9) 
			return string;
		return ("0" + string);
	}
	
	/**
	 * @param userName 
	 * @param expiredDate 
	 * @return String
	 * @throws Exception
	 */
	private static String  encode(String userName,String expiredDate) throws Exception{
//		 DES算法要求有一个可信任的随机数�?
			SecureRandom sr = new SecureRandom();

			byte rawKeyData[] = (userName + "midware").getBytes();

//			 从原始密匙数据创建DESKeySpec对象
			DESKeySpec dks = new DESKeySpec(rawKeyData);

//			 创建�?个密匙工厂，然后用它把DESKeySpec转换�?
//			 �?个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance( "DES" );
			SecretKey key = keyFactory.generateSecret( dks );

//			 Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance( "DES" );

//			 用密匙初始化Cipher对象
			cipher.init( Cipher.ENCRYPT_MODE, key, sr );

//			 现在，获取数据并加密
			byte data[] = (userName+"-"+expiredDate).getBytes();

//			 正式执行加密操作
			byte[] encryptedData = cipher.doFinal( data );
			return (new String(Base64.encodeBase64(encryptedData)));
	}
}
