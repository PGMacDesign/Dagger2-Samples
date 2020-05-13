package m.pgmacdesign.dagger2examples.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import m.pgmacdesign.dagger2examples.viewmodels.ViewModelProviderFactory;

@Module(

)
public abstract class ViewModelFactoryModule {
	
	@Binds
	public abstract ViewModelProvider.Factory bindViewModelFactory(
			ViewModelProviderFactory modelProviderFactory);
	
	/**
	 * Note! This method is identical to the {@link #bindViewModelFactory(ViewModelProviderFactory)}
	 * one above in function, but this is less efficient because since nothing is actually happening
	 * in the body of the method, it is taking an extra step to do it.
	 * @param factory
	 * @return
	 */
	@Provides
	@Deprecated
	static ViewModelProvider.Factory provideFactory(ViewModelProviderFactory factory){
		return factory;
	}
	
}
