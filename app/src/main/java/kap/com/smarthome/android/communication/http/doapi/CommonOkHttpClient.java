package kap.com.smarthome.android.communication.http.doapi;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import kap.com.smarthome.android.communication.http.cookie.SimpleCookieJar;
import kap.com.smarthome.android.communication.http.httputils.HttpsSSLCertifiUtils;
import kap.com.smarthome.android.communication.http.listener.DealHttpResponseHandle;
import kap.com.smarthome.android.communication.http.response.CommomJsonClassCallback;
import kap.com.smarthome.android.communication.http.response.CommonFileDownResponseCallback;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author vision
 * @function 用来发送get,post请求的工具类，包括设置一些请求的共用参数
 */
public class CommonOkHttpClient
{
	private static final int TIME_OUT = 30;
	private static OkHttpClient mOkHttpClient;

	static
	{
		OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
		okHttpClientBuilder.hostnameVerifier(new HostnameVerifier()
		{
			@Override
			public boolean verify(String hostname, SSLSession session)
			{
				return true;
			}
		});
		okHttpClientBuilder.cookieJar(new SimpleCookieJar());
		okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
		okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
		okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
		okHttpClientBuilder.followRedirects(true);
		/**
		 * trust all the https point
		 */
		//okHttpClientBuilder.sslSocketFactory(HttpsSSLCertifiUtils.getSslSocketFactory());
		mOkHttpClient = okHttpClientBuilder.build();
	}

	/**
	 * 指定cilent信任指定证书
	 * 
	 * @param certificates
	 */
	public static void setCertificates(InputStream... certificates)
	{
		mOkHttpClient.newBuilder().sslSocketFactory(HttpsSSLCertifiUtils.getSslSocketFactory(certificates, null, null)).build();
	}

	/**
	 * 指定client信任所有证书
	 */
	public static void setCertificates()
	{
		mOkHttpClient.newBuilder().sslSocketFactory(HttpsSSLCertifiUtils.getSslSocketFactory());
	}

	/**
	 * 通过构造好的Request,Callback去发送请求
	 * 
	 * @param request
	 * @param handle
	 */
	public static Call get(Request request, DealHttpResponseHandle handle)
	{
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new CommomJsonClassCallback(handle));
		return call;
	}


	public static Call post(Request request, DealHttpResponseHandle handle)
	{
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new CommomJsonClassCallback(handle));
		return call;
	}



	public static Call downloadFile(Request request, DealHttpResponseHandle handle)
	{
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new CommonFileDownResponseCallback(handle));
		return call;
	}
}