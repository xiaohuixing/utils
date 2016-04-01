

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Encoder;

/**
 * MD5������
 *
 */
public class MD5 {
	
	/**
	 * DES算法密钥
	 */
	private static final byte[] DES_KEY = { 21, 1, -110, 82, -32, -85, -128,
			-65 };
	
	 public static String encode(String password){   
	        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',   
	                'a', 'b', 'c', 'd', 'e', 'f'};   
	        try {   
	            byte[] strTemp = password.getBytes();   
	            MessageDigest mdTemp = MessageDigest.getInstance("MD5");   
	            mdTemp.update(strTemp);   
	            byte[] md = mdTemp.digest();   
	            int j = md.length;   
	            char str[] = new char[j * 2];   
	            int k = 0;   
	            for (int i = 0; i < j; i++) {   
	                byte byte0 = md[i];   
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];   
	                str[k++] = hexDigits[byte0 & 0xf];
	                
	            }   
	            return new String(str);   
	        } catch (Exception e) {   
	            return null;   
	        }   
	    } 
	 
	 /**
		 * 数据加密，算法（DES）
		 * 
		 * @param data
		 *            要进行加密的数据
		 * @return 加密后的数据
		 */
		public static String encryptBasedDes(String data) {
			String encryptedData = null;
			try {
				// DES算法要求有一个可信任的随机数源
				SecureRandom sr = new SecureRandom();
				DESKeySpec deskey = new DESKeySpec(DES_KEY);
				// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				SecretKey key = keyFactory.generateSecret(deskey);
				// 加密对象
				Cipher cipher = Cipher.getInstance("DES");
				cipher.init(Cipher.ENCRYPT_MODE, key, sr);
				// 加密，并把字节数组编码成字符串
				encryptedData = new BASE64Encoder().encode(cipher.doFinal(data
						.getBytes()));
			} catch (Exception e) {
				
				throw new RuntimeException("加密错误，错误信息：", e);
			}
			return encryptedData;
		}

		/**
		 * 数据解密，算法（DES）
		 * 
		 * @param cryptData
		 *            加密数据
		 * @return 解密后的数据
		 */
		public static String decryptBasedDes(String cryptData) {
			String decryptedData = null;
			try {
				// DES算法要求有一个可信任的随机数源
				SecureRandom sr = new SecureRandom();
				DESKeySpec deskey = new DESKeySpec(DES_KEY);
				// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				SecretKey key = keyFactory.generateSecret(deskey);
				// 解密对象
				Cipher cipher = Cipher.getInstance("DES");
				cipher.init(Cipher.DECRYPT_MODE, key, sr);
				// 把字符串解码为字节数组，并解密
				decryptedData = new String(cipher
						.doFinal(new sun.misc.BASE64Decoder()
								.decodeBuffer(cryptData)));
			} catch (Exception e) {
				throw new RuntimeException("解密错误，错误信息：", e);
			}
			return decryptedData;
		}

	 
	 public static void main(String[] args) {
		String md5=MD5.encode("zhouwei1234");  //4a6dcfe15cf26f3f2bac218d8782244c
		System.out.println(md5);
		if(md5.equals("4a6dcfe15cf26f3f2bac218d8782244c")){
			System.out.println(11);
		}
	}
}
