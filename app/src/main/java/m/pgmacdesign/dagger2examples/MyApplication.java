package m.pgmacdesign.dagger2examples;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import m.pgmacdesign.dagger2examples.di.DaggerAppComponent;

public class MyApplication extends DaggerApplication {
	
	
	
	@Override
	protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
		// TODO: 5/8/20
		return DaggerAppComponent.builder().application(this).build();
	}
}
