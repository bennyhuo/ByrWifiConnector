package com.ben.ap;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.Handler;

public abstract class AP {
	public String mSsid;
	public Handler mHandler;

	public abstract ArrayList<NameValuePair> getLoginParam();

	public abstract int startDisconnect();

	protected abstract String getConnPath();

	public int startConnect() {
		HttpPost httpRequest = new HttpPost(this.getConnPath());
		System.out.println("�������ӣ�");
		HttpResponse httpResponse;
		HttpClient httpClient = this.getHttpClient();
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(this.getLoginParam(), HTTP.UTF_8));

			httpResponse = httpClient.execute(httpRequest);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* ȡ����Ӧ�ַ��� */
				String strResult;
				strResult = EntityUtils.toString(httpResponse.getEntity());
				byte[] b = strResult.getBytes();
				strResult = new String(b, "GBK");
				System.out.println("�������");
				System.out.println(strResult);
			} else {
				System.out.println(httpResponse.getStatusLine().getStatusCode());
			}

			return 201;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return 0;
	}

//	protected int testConn() {
//		return SysCommand.pingHost("www.baidu.com");
//	}



	private static final int REQUEST_TIMEOUT = 10 * 1000;// ��������ʱ10����
	private static final int SO_TIMEOUT = 10 * 1000; // ���õȴ����ݳ�ʱʱ��10����

	/**
	 * �������ʱʱ��͵ȴ�ʱ��
	 * 
	 * @author spring sky Email vipa1888@163.com QQ: 840950105 My name: ʯ����
	 * @return HttpClient����
	 */
	public HttpClient getHttpClient() {
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
		HttpClient client = new DefaultHttpClient(httpParams);
		return client;
	}
}
