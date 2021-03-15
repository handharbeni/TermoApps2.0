package dev.mhandharbeni.termoapps20.utils_network;

import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressLint({"TrustAllX509TrustManager", "BadHostnameVerifier"})
public class WebClient {
    private Retrofit retrofit = null;
    public static WebClient webClient;
    public String BASE_URL = AppConstant.HOST_API;
    public static int TIME_OUT = 5;

    public WebClient() {
    }

    public static synchronized WebClient getInstance() {
        if (webClient == null) {
            webClient = new WebClient();
        }

        return webClient;
    }

    private static OkHttpClient.Builder configureToIgnoreCertificate(OkHttpClient.Builder builder) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                                throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                                throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {}
        return builder;
    }

    public OkHttpClient getClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient().newBuilder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder()
                            .header("Authorization", Credentials.basic("trial", "trial"));
                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                })
                .addInterceptor(logging)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS);
        return AppConstant.SAFE_OKHTTP?client.build():configureToIgnoreCertificate(client).build();
    }

    public OkHttpClient getClient(int timeOut){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient().newBuilder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder()
                            .header("Authorization", Credentials.basic("trial", "trial"));
                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                })
                .addInterceptor(logging)
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS);
        return AppConstant.SAFE_OKHTTP?client.build():configureToIgnoreCertificate(client).build();
    }

    public Retrofit getClient(String baseUrl) {
        if (this.retrofit == null) {
            this.retrofit = (new Retrofit.Builder()).baseUrl(baseUrl == null ? this.BASE_URL : baseUrl)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClient()).build();
        }

        return this.retrofit;
    }

    public Retrofit getNewClient(String baseUrl) {
        this.retrofit = (new Retrofit.Builder()).baseUrl(baseUrl == null ? this.BASE_URL : baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient()).build();
        return this.retrofit;
    }

    public Retrofit getNewClient(String baseUrl, int timeOut) {
        this.retrofit = (new Retrofit.Builder()).baseUrl(baseUrl == null ? this.BASE_URL : baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                        getClient(timeOut)
                                .newBuilder()
                                .readTimeout(timeOut, TimeUnit.SECONDS)
                                .writeTimeout(timeOut, TimeUnit.SECONDS)
                                .connectTimeout(timeOut, TimeUnit.SECONDS)
                                .build()
                ).build();
        return this.retrofit;
    }

    public Retrofit getNewClient(String baseUrl, Type type, Object serializer){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(type, serializer)
                .create();

        this.retrofit = (new Retrofit.Builder()).baseUrl(baseUrl == null ? this.BASE_URL : baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getClient()).build();
        return this.retrofit;
    }

    public WebClient URL(String url) {
        this.BASE_URL = url;
        return this;
    }
}