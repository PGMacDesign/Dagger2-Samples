package m.pgmacdesign.dagger2examples.networking.main;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {
	
	@Provides
	static MainAPI provideMainAPI(Retrofit retrofit){
		return retrofit.create(MainAPI.class);
	}
	
	
}
