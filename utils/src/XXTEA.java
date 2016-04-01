

import java.io.UnsupportedEncodingException;

public class XXTEA {

	public static final String KEY = "1%7jhs#Zjasd&tr*(^)";//%(@%#(@^!)#@!
	
	public static void main(String args[]) {

		for (int i = 100063; i < 100100; i++) {
		    XXTEA.encrypt(String.valueOf(i));
		}

		XXTEA.decrypt("Cd0dHjFByrbyalgm");

		XXTEA.encrypt("118140");
	}
	
	public static String encrypt64(String value) {
		String enVid = "";
		// byte[] k = "KU_6com-2007web2.0".getBytes();
		byte[] v = value.getBytes();
		enVid = Base64.encode(XXTEA.encrypt(Base64.encode(v).getBytes(), KEY.getBytes()));
		enVid = enVid.replace('+', '-');
		enVid = enVid.replace('/', '_');
		enVid = enVid.replace('=', '.');
		return enVid;
	}
	
	public static String encrypt(String value) {
		String enVid = "";
		// byte[] k = "KU_6com-2007web2.0".getBytes();
		byte[] v = value.getBytes();
		enVid = Base64.encode(XXTEA.encrypt(v, KEY.getBytes()));
		enVid = enVid.replace('+', '-');
		enVid = enVid.replace('/', '_');
		enVid = enVid.replace('=', '.');
		return enVid;
	}

	/**
	 * 先Base64解密，后XXXTEA解密
	 * 
	 * @param value
	 * @return
	 */
	public static String decrypt(String value) {
		String deVid = "";
		value = value.replace('-', '+');
		value = value.replace('_', '/');
		value = value.replace('.', '=');
		// byte[] k = "KU_6com-2007web2.0".getBytes();
		byte[] v = Base64.decode(value);
		deVid = new String(XXTEA.decrypt(v));
		return deVid;
	}
	
	/**
	 * 先base64解密，后xxTEA解密，最后再base64解密
	 * @param value
	 * @return
	 */
	public static String decrypt64(String value) {
		String deVid = "";
		value = value.replace('-', '+');
		value = value.replace('_', '/');
		value = value.replace('.', '=');
		// byte[] k = "KU_6com-2007web2.0".getBytes();
		byte[] v = Base64.decode(value);// 解码
		deVid = new String(XXTEA.decrypt(v));
		
		try {
			return new String(Base64.decode(deVid),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new String(Base64.decode(deVid));
		}
	}

	/**
	 * Encrypt data with key.
	 * 
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] encrypt(byte[] data, byte[] key) {
		if (data.length == 0) {
			return data;
		}
		return toByteArray(
				encrypt(toIntArray(data, true), toIntArray(key, false)), false);
	}

	/**
	 * Decrypt data with key.
	 * 解密数据与关键
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] decrypt(byte[] data) {
		if (data.length == 0) {
			return data;
		}
		return toByteArray(
				decrypt(toIntArray(data, false), toIntArray(KEY.getBytes(), false)), true);
	}

	/**
	 * Encrypt data with key.
	 * 
	 * @param v
	 * @param k
	 * @return
	 */
	public static int[] encrypt(int[] v, int[] k) {
		int n = v.length - 1;

		if (n < 1) {
			return v;
		}
		if (k.length < 4) {
			int[] key = new int[4];

			System.arraycopy(k, 0, key, 0, k.length);
			k = key;
		}
		int z = v[n], y = v[0], delta = 0x9E3779B9, sum = 0, e;
		int p, q = 6 + 52 / (n + 1);

		while (q-- > 0) {
			sum = sum + delta;
			e = sum >>> 2 & 3;
			for (p = 0; p < n; p++) {
				y = v[p + 1];
				z = v[p] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
						+ (k[p & 3 ^ e] ^ z);
			}
			y = v[0];
			z = v[n] += (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
					+ (k[p & 3 ^ e] ^ z);
		}
		return v;
	}

	/**
	 * Decrypt data with key.
	 * 
	 * @param v
	 * @param k
	 * @return
	 */
	public static int[] decrypt(int[] v, int[] k) {
		int n = v.length - 1;

		if (n < 1) {
			return v;
		}
		if (k.length < 4) {
			int[] key = new int[4];

			System.arraycopy(k, 0, key, 0, k.length);
			k = key;
		}
		int z = v[n], y = v[0], delta = 0x9E3779B9, sum, e;
		int p, q = 6 + 52 / (n + 1);

		sum = q * delta;
		while (sum != 0) {
			e = sum >>> 2 & 3;
			for (p = n; p > 0; p--) {
				z = v[p - 1];
				y = v[p] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
						+ (k[p & 3 ^ e] ^ z);
			}
			z = v[n];
			y = v[0] -= (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y)
					+ (k[p & 3 ^ e] ^ z);
			sum = sum - delta;
		}
		return v;
	}

	/**
	 * Convert byte array to int array.
	 * 
	 * @param data
	 * @param includeLength
	 * @return
	 */
	private static int[] toIntArray(byte[] data, boolean includeLength) {
		int n = (((data.length & 3) == 0) ? (data.length >>> 2)
				: ((data.length >>> 2) + 1));
		int[] result;

		if (includeLength) {
			result = new int[n + 1];
			result[n] = data.length;
		} else {
			result = new int[n];
		}
		n = data.length;
		for (int i = 0; i < n; i++) {
			result[i >>> 2] |= (0x000000ff & data[i]) << ((i & 3) << 3);
		}
		return result;
	}

	/**
	 * Convert int array to byte array.
	 * 
	 * @param data
	 * @param includeLength
	 * @return
	 */
	private static byte[] toByteArray(int[] data, boolean includeLength) {
		int n = data.length << 2;
		if (includeLength) {
			int m = data[data.length - 1];

			if (m > n) {
				return null;
			} else {
				n = m;
			}
		}
		byte[] result = new byte[n];

		for (int i = 0; i < n; i++) {
			result[i] = (byte) ((data[i >>> 2] >>> ((i & 3) << 3)) & 0xff);
		}
		return result;
	}
}
