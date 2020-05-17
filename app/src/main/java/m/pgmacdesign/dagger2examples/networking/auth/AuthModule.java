package m.pgmacdesign.dagger2examples.networking.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import m.pgmacdesign.dagger2examples.di.auth.AuthScope;
import retrofit2.Retrofit;

@Module
public class AuthModule {
	
	@AuthScope
	@Provides
	static AuthAPI provideAuthAPI(Retrofit retrofit){
		return (retrofit == null) ? null : retrofit.create(AuthAPI.class);
	}
	
	
	@AuthScope
	@Provides
	@Named("module-level")
	static Gson provideGson(){
		return new GsonBuilder()
				.setLenient()
				.create();
	}
	
}
