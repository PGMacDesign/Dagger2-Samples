package m.pgmacdesign.dagger2examples.di;

import android.app.Activity;
import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import m.pgmacdesign.dagger2examples.MyApplication;

/**
 * This will persist across the entirety of the application
 */
@Singleton
@Component(
		modules = {
				AndroidSupportInjectionModule.class,
				ActivityBuildersModule.class,
				AppModule.class,
				ViewModelFactoryModule.class,
				
		}
)
public interface AppComponent extends AndroidInjector<MyApplication> {
	
	@Component.Builder
	interface Builder {
		
		/**
		 * Bind a particular object or an instance of an object to a component at the time of construction
		 * @param application
		 * @return
		 */
		@BindsInstance
		Builder application(Application application);
		
		/**
		 * Overriding standard builder
		 * @return
		 */
		AppComponent build();
		
	}
}
