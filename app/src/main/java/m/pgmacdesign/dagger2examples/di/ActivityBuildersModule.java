package m.pgmacdesign.dagger2examples.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import m.pgmacdesign.dagger2examples.di.auth.AuthScope;
import m.pgmacdesign.dagger2examples.di.auth.AuthViewModelsModule;
import m.pgmacdesign.dagger2examples.di.main.MainFragmentBuildersModule;
import m.pgmacdesign.dagger2examples.di.main.MainScope;
import m.pgmacdesign.dagger2examples.di.main.MainViewModelsModule;
import m.pgmacdesign.dagger2examples.networking.auth.AuthModule;
import m.pgmacdesign.dagger2examples.networking.main.MainModule;
import m.pgmacdesign.dagger2examples.ui.auth.AuthActivity;
import m.pgmacdesign.dagger2examples.ui.main.MainActivity;

@Module
public abstract class ActivityBuildersModule {
	
	/**
	 * This creates a scope that is used for reference to the AuthActivity
	 * @return
	 */
	@AuthScope
	@ContributesAndroidInjector(
			modules = {
					AuthViewModelsModule.class,
					AuthModule.class
			}
	)
	abstract AuthActivity contributeAuthActivity();
	
	/**
	 * This creates a scope that is used for reference to the MainActivity and respective sub-components
	 * @return
	 */
	@MainScope
	@ContributesAndroidInjector(
			modules = {
					MainFragmentBuildersModule.class,
					MainViewModelsModule.class,
					MainModule.class
			}
	)
	abstract MainActivity contributeMainActivity();
	
	
}
