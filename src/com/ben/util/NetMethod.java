package com.ben.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NetMethod {
	public static int testConn() {
		Date begin = new Date();
		URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL("http://www.baidu.com");
			int cnt = 0;
			while (cnt<3) {
				try {
					System.out.println("�������Ӳ���~");
					conn = (HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(1000);
					conn.setReadTimeout(1000);
					Map<String, List<String>> map = conn.getHeaderFields();
					System.out.println("������ݣ�");
					List<String> list = map.get("Set-Cookie");
					Iterator<String> it = list.iterator();
					while (it.hasNext()) {
						if (it.next().contains("domain=.baidu.com")) {
							System.out.println("�������ӳɹ���");
							Date end = new Date();
							System.out.println(end.getTime() - begin.getTime());
							return 200;
						}
					}
					System.out.println("��������ʧ�ܣ�");
				} catch (Exception e) {
					cnt++;
					System.out.println("Try for " + cnt +" times");
					e.printStackTrace();
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} finally {

		}
		Date end2 = new Date();
		System.out.println(end2.getTime() - begin.getTime());
		return 0;

	}
}
