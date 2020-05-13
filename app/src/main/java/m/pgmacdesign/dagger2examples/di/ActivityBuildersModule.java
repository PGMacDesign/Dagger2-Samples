package m.pgmacdesign.dagger2examples.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import m.pgmacdesign.dagger2examples.di.auth.AuthViewModelsModule;
import m.pgmacdesign.dagger2examples.networking.auth.AuthModule;
import m.pgmacdesign.dagger2examples.ui.auth.AuthActivity;

@Module
public abstract class ActivityBuildersModule {
	
	/**
	 * This creates a scope that is used for reference to the AuthActivity
	 * @return
	 */
	@ContributesAndroidInjector(
			modules = {AuthViewModelsModule.class, AuthModule.class}
	)
	abstract AuthActivity contributeAuthActivity();
	
	
}
