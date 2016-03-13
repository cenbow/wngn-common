package com.zzia.wngn.util;

import java.io.IOException;

public class UnicodeUtil {

	private static char ascii2Char(int ASCII) {
		return (char) ASCII;
	}

	private static String ascii2String(int[] ASCIIs) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ASCIIs.length; i++) {
			sb.append((char) ascii2Char(ASCIIs[i]));
		}
		return sb.toString();
	}

	public static String chinaToUnicode(String str){  
	    String result="";  
	    for (int i = 0; i < str.length(); i++){  
	        int chr1 = (char) str.charAt(i);  
	        if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)  
	            result+="\\u" + Integer.toHexString(chr1);  
	        }else{  
	            result+=str.charAt(i);  
	        }  
	    }  
	    return result;  
	} 
	
	public static String unicodeToChina(String unicode){
		unicode = unicode.replace("\\u", "-");
		unicode = unicode.substring(1);
		String[] unicodes = unicode.split("-");
		int[] binarys = new int[unicodes.length];
		for (int i=0;i<unicodes.length;i++) {
			binarys[i] = Integer.parseInt((unicodes[i]),16);
		}
		return ascii2String(binarys);
		
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(unicodeToChina(chinaToUnicode("中国")));
	}

}
