package kap.com.smarthome.android.presenter.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {
	
	public static void writeFile(Context context, String str) {
//		str += " " + getDateTime();
		String path = context.getFilesDir().getAbsolutePath()+"/log.txt";
		try {
			File file = new File(path);
			if(!file.exists()) {
				file.createNewFile();
			}

			RandomAccessFile raf = new RandomAccessFile(path, "rw");
			raf.seek(file.length());
			raf.write(str.getBytes());
			raf.writeChars("\r\n");
			
			raf.close();
		} catch(Exception e) {
		} 
	}
	
	public static void writeFileTest(Context context, String str) {
//		str += " " + getDateTime();
		String path = context.getFilesDir().getAbsolutePath()+"/log_test.txt";
		try {
			File file = new File(path);
			if(!file.exists()) {
				file.createNewFile();
			}
			
			RandomAccessFile raf = new RandomAccessFile(path, "rw");
			raf.seek(file.length());
			raf.write(str.getBytes());
			raf.writeChars("\r\n");
			
			raf.close();
		} catch(Exception e) {
		} 
	}

	@SuppressLint("SimpleDateFormat")
	public static String getDateTime() {
		Date nowTime = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd hh:mm:ss:SSS");
		String strDate = sdf.format(nowTime);
		return strDate;
	}
	
	/** 
     * 把byte转为字符串的bit 
     */  
    public static String byteToBit(byte b) {  
        return ""  
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)  
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)  
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)  
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);  
    }  
    
    /** 
     * Bit转Byte 
     */  
    public static byte BitToByte(String byteStr) {  
        int re, len;  
        if (null == byteStr) {  
            return 0;  
        }  
        len = byteStr.length();  
        if (len != 4 && len != 8) {  
            return 0;  
        }  
        if (len == 8) {// 8 bit处理  
            if (byteStr.charAt(0) == '0') {// 正数  
                re = Integer.parseInt(byteStr, 2);  
            } else {// 负数  
                re = Integer.parseInt(byteStr, 2) - 256;  
            }  
        } else {//4 bit处理  
            re = Integer.parseInt(byteStr, 2);  
        }  
        return (byte) re;  
    }  
	
	public static String printBytes(byte[] value) {
		StringBuffer sb = new StringBuffer();
		for(byte b : value)
			sb.append(b + " ");
		
		return sb.toString();
	}
	
	/** 
     * 转换short为byte 
     *  
     * @param b 
     * @param s 
     * 需要转换的short
     * @param index 
     */  
    public static byte[] shortToBytes(short s) {  
    	byte[] buf = new byte[2];
        buf[1] = (byte) (s >> 8);  
        buf[0] = (byte) (s >> 0);  
        return buf;
    }  
    
    /** 
     * 通过byte数组取到short
     * @param b 
     * @param index 
     * 第几位开始取
     * @return 
     */  
    public static short bytesToShort(byte[] b) {  
        return (short) (((b[1] << 8) | b[0] & 0xff));  
    }  
	
	/** 
	 * 浮点转换为字节
	 * @param f 
	 * @return 
	 */  
	public static byte[] float2byte(float f) {  
	      
	    // 把float转换为byte[]  
	    int fbit = Float.floatToIntBits(f);  
	      
	    byte[] b = new byte[4];    
	    for (int i = 0; i < 4; i++) {    
	        b[i] = (byte) (fbit >> (24 - i * 8));    
	    }   
	      
	    // 翻转数组  
	    int len = b.length;  
	    // 建立一个与源数组元素类型相同的数组  
	    byte[] dest = new byte[len];  
	    // 为了防止修改源数组，将源数组拷贝一份副本  
	    System.arraycopy(b, 0, dest, 0, len);  
	    byte temp;  
	    // 将顺位第i个与倒数第i个交换  
	    for (int i = 0; i < len / 2; ++i) {  
	        temp = dest[i];  
	        dest[i] = dest[len - i - 1];  
	        dest[len - i - 1] = temp;  
	    }  
	      
	    return dest;  
	      
	}  
	  
	/** 
	 * 字节转换为浮点
	 * @param b 字节（至少4个字节） 
	 * @param index 开始位置 
	 * @return 
	 */  
	public static float byte2float(byte[] b, int index) {    
	    int l;                                             
	    l = b[index + 0];                                  
	    l &= 0xff;                                         
	    l |= ((long) b[index + 1] << 8);                   
	    l &= 0xffff;                                       
	    l |= ((long) b[index + 2] << 16);                  
	    l &= 0xffffff;                                     
	    l |= ((long) b[index + 3] << 24);                  
	    return Float.intBitsToFloat(l);                    
	}  
	
	/**
	 * byte转十六进制String
	 * @param src
	 * @return
	 */
	public static String byteToHexString(byte value) {
		StringBuilder stringBuilder = new StringBuilder("");   
		int v = value & 0xFF;
		String hv = Integer.toHexString(v);
		if (hv.length() < 2) {   
            stringBuilder.append(0);   
        }   
		stringBuilder.append(hv.toUpperCase());   
		return stringBuilder.toString();
	}
	
	
	/* Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。   
	 * @param src byte[] data   
	 * @return hex string   
	 */      
	public static String bytesToHexString(byte[] values){   
	    StringBuilder stringBuilder = new StringBuilder("");   
	    if (values == null || values.length <= 0) {   
	        return null;   
	    }   
	    for (int i = 0; i < values.length; i++) {   
	        int v = values[i] & 0xFF;   
	        String hv = Integer.toHexString(v);   
	        if (hv.length() < 2) {   
	            stringBuilder.append(0);   
	        }   
	        stringBuilder.append(hv.toUpperCase());   
	    }   
	    return stringBuilder.toString();   
	}   
	/**  
	 * Convert hex string to byte[]  
	 * @param hexString the hex string  
	 * @return byte[]  
	 */  
	public static byte[] hexStringToBytes(String hexString) {   
	    if (hexString == null || hexString.equals("")) {   
	        return null;   
	    }   
	    hexString = hexString.toUpperCase();   
	    int length = hexString.length() / 2;   
	    char[] hexChars = hexString.toCharArray();   
	    byte[] d = new byte[length];   
	    for (int i = 0; i < length; i++) {   
	        int pos = i * 2;   
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));   
	    }   
	    return d;   
	}   
	/**  
	 * Convert char to byte  
	 * @param c char  
	 * @return byte  
	 */  
	 private static byte charToByte(char c) {   
	    return (byte) "0123456789ABCDEF".indexOf(c);   
	}  
	 

	 

	//将指定byte数组以16进制的形式打印到控制台   
	public static void printHexString( byte[] b) {     
	   for (int i = 0; i < b.length; i++) {    
	     String hex = Integer.toHexString(b[i] & 0xFF);    
	     if (hex.length() == 1) {    
	       hex = '0' + hex;    
	     }    
	     System.out.print(hex.toUpperCase() );    
	   }    
	  
	}  
	
	
	/**
	 * int 转 byte[]
	 * @param iSource
	 * @param 整体转换的byte数值长度
	 * @return
	 */
	public static byte[] toByteArray(int iSource, int iArrayLen) {
	    byte[] bLocalArr = new byte[iArrayLen];
	    for (int i = 0; (i < 4) && (i < iArrayLen); i++) {
	        bLocalArr[i] = (byte) (iSource >> 8 * i & 0xFF);
	    }
	    return bLocalArr;
	}
	
	/**
	 * 十进制转化为十六进制
	 * @param value
	 * @return
	 */
	public static String toIntHex(int value) {		
		return Integer.toHexString(value);
	}
		
	/**
	 * 十六进制转十进制转
	 * @param str
	 * @return
	 */
	public static int toHexInt(String str) {
		return Integer.parseInt(str,16);
	}
	 
	/*public static byte[] toByteArrInt(int num) {
		
	}*/
	
	/** 
	 * int值转成4字节的byte数组 
	 * @param num 
	 * @return 
	 */  
	public static byte[] int2byteArray(int num) {  
	    byte[] result = new byte[4];  
	    result[0] = (byte)(num >>> 24);//取最高8位放到0下标  
	    result[1] = (byte)(num >>> 16);//取次高8为放到1下标  
	    result[2] = (byte)(num >>> 8); //取次低8位放到2下标   
	    result[3] = (byte)(num );      //取最低8位放到3下标  
	    return result;  
	}  
	
	
	/*public static int byte2int(byte b[]) {  
        if (b != null && b.length > 0) {  
            if (b.length >= 4) {  
                return b[3] & 0xff | (b[2] & 0xff) << 8 | (b[1] & 0xff) << 16  
                        | (b[0] & 0xff) << 24;  
            } else if (b.length == 2) {  
                return byte2int2(b);  
            } else {  
                return 0;  
            }  
  
        } else {  
            return 0;  
        }  
  
    }
	
	public static int byte2int2(byte b[]) {  
        return b[1] & 0xff | (b[0] & 0xff) << 8;  
    }*/  
	
	/** 
	 * 将4字节的byte数组转成int值 
	 * @param b 
	 * @return 
	 */  
	public static int byteArray2int(byte[] b){  
	    byte[] a = new byte[4];  
	    int i = a.length - 1,j = b.length - 1;  
	    for (; i >= 0 ; i--,j--) {//从b的尾部(即int值的低位)开始copy数据  
	        if(j >= 0)  
	            a[i] = b[j];  
	        else  
	            a[i] = 0;//如果b.length不足4,则将高位补0  
	    }  
	    int v0 = (a[0] & 0xff) << 24;//&0xff将byte值无差异转成int,避免Java自动类型提升后,会保留高位的符号位  
	    int v1 = (a[1] & 0xff) << 16;  
	    int v2 = (a[2] & 0xff) << 8;  
	    int v3 = (a[3] & 0xff) ;  
	    return v0 + v1 + v2 + v3;  
	} 
	
	 /** 
     * 将16位的int转换成byte数组 
     *  
     * @param s 
     *            short 
     * @return byte[] 长度为2 
     * */  
    public static byte[] shortToByteArray(int s) {  
        byte[] targets = new byte[2];  
        for (int i = 0; i < 2; i++) {  
            int offset = (targets.length - 1 - i) * 8;  
            targets[i] = (byte) ((s >>> offset) & 0xff);  
        }  
        return targets;  
    }  
    
    /** 
     * 将一个单字节的byte转换成32位的int
     * @param byte
     * @return convert result 
     */  
    public static int unsignedByteToInt(byte b) {  
        return (int) b & 0xFF;  
    }  
}
