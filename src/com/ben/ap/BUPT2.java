package com.ben.ap;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.ben.util.Method;

public class BUPT2 extends AP {
	private ArrayList<NameValuePair> param;
	private final String mConnPath = "http://10.4.1.2/a11.htm";
	private final String mDisconnPath = "http://10.4.1.2/F.htm";

	public String getConnPath() {
		return mConnPath;
	}

	public String getDisconnPath() {
		return mDisconnPath;
	}

	public BUPT2(String userName, String userPwd) {
		this.mSsid = "BUPT-2";

		param = new ArrayList<NameValuePair>();
//		param.add(new BasicNameValuePair("DDDDD", userName));
//		param.add(new BasicNameValuePair("upass", Method.xproc1(userPwd)));
//		param.add(new BasicNameValuePair("R1", "0"));
//		param.add(new BasicNameValuePair("R2", "0"));
//		param.add(new BasicNameValuePair("para", "00"));
//		param.add(new BasicNameValuePair("0MKKey", "123456"));
		param.add(new BasicNameValuePair("DDDDD", userName));
		param.add(new BasicNameValuePair("upass", userPwd));
		param.add(new BasicNameValuePair("AMKKey", ""));
	}

	public BUPT2() {
		this.mSsid = "BUPT-2";

	}

	public ArrayList<NameValuePair> getLoginParam() {
		return param;
	}

	public int startDisconnect() {
		HttpGet httpRequest = new HttpGet(this.getDisconnPath());
		try {
			System.out.println("���ԶϿ�");
			HttpResponse httpResponse;
			httpResponse = new DefaultHttpClient().execute(httpRequest);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* ȡ����Ӧ�ַ��� */
				String strResult;
				strResult = EntityUtils.toString(httpResponse.getEntity());
				byte[] b = strResult.getBytes();
				strResult = new String(b, "GBK");
				System.out.println("�Ͽ����");
				System.out.println(strResult);
			} else {
				System.out.println(httpResponse.getStatusLine().getStatusCode());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 755;

		}
		return 555;
	}

}
