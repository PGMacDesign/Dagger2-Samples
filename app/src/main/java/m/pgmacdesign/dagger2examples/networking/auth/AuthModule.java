package m.pgmacdesign.dagger2examples.networking.auth;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AuthModule {
	
	@Provides
	static AuthAPI provideAuthAPI(Retrofit retrofit){
		return (retrofit == null) ? null : retrofit.create(AuthAPI.class);
	}
}
