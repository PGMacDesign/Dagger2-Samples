package m.pgmacdesign.dagger2examples.di;

import android.app.Application;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pgmacdesign.pgmactips.misc.PGMacTipsConstants;
import com.pgmacdesign.pgmactips.networkclasses.retrofitutilities.CustomConverterFactory;
import com.pgmacdesign.pgmactips.networkclasses.retrofitutilities.RetrofitClient;
import com.pgmacdesign.pgmactips.networkclasses.retrofitutilities.RetryCallAdapterFactory;
import com.pgmacdesign.pgmactips.utilities.DatabaseUtilities;
import com.pgmacdesign.pgmactips.utilities.L;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import m.pgmacdesign.dagger2examples.Constants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * App-level dependencies all go here
 * Ideally, use all static methods for better overall performance
 *
 * todo read - https://www.techyourchance.com/dependency-injection-viewmodel-with-dagger-2
 */
@Module
public class AppModule {
	
	//region Networking
	
	@Singleton
	@Provides
	static Retrofit provideRetrofitInstance(){
//		RetrofitClient.Builder builder = new RetrofitClient.Builder(dddd, Constants.BASE_URL);
//		builder.setLogLevel(HttpLoggingInterceptor.Level.BODY);
//		builder.setRetryCount(2);
//		builder.setTimeouts(PGMacTipsConstants.ONE_MINUTE, PGMacTipsConstants.ONE_MINUTE);
//		return builder.build().buildServiceClient();
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(90, TimeUnit.SECONDS);
		builder.readTimeout(90, TimeUnit.SECONDS);
		builder.writeTimeout(90, TimeUnit.SECONDS);
		OkHttpClient client = builder.build();
		try {
			return new Retrofit.Builder()
					.baseUrl(Constants.BASE_URL)
					.addConverterFactory(new CustomConverterFactory())
//					.addCallAdapterFactory(RetryCallAdapterFactory.create(2))
					.client(client)
					.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
					.build();
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	//endregion
	
	//region My Custom Ones
	
	@Singleton
	@Provides
	static DatabaseUtilities provideDBInstance(Application application){
		String dbName = "dbname.db";
		String salt = "saltltlt";
		String pw = "some pw yo";
		return new DatabaseUtilities(application, dbName, 1, true, pw, salt);
	}
	
	@Singleton
	@Provides
	static Gson provideGson(){
		return new GsonBuilder()
				.setLenient()
				.create();
	}
	
	@Singleton
	@Provides
	static Picasso providePicassoInstance(Application application){
		return new Picasso.Builder(application)
				.listener((picasso, uri, exception) -> {
					L.m("Picasso image failed to load");
					//
				})
				.build();
	}
	
	//endregion
	
}
