package com.may.sys;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

public class IOTest extends TestCase {
	public void testReader() throws IOException {
		InputStream is = new FileInputStream("E:\\Repositories\\GIT\\ssh\\test\\FINA_DETAIL.del");
		byte[] buf = new byte[1024];
        StringBuffer sb = new StringBuffer();
		while (is.read(buf) != -1) {
			sb.append(new String(buf, "GBK"));    
            buf = new byte[1024];//重新生成，避免和上次读取的数据重复
		}
		String[] arr = sb.toString().split("\n");
		for (String str : arr) {
			String[] line = str.split("GS");
			StringBuilder s = new StringBuilder();
			for (String l : line) {
				s.append(l + ",");
			}
			System.out.println(s.toString());
		}
	}
}
