package m.pgmacdesign.dagger2examples.di.auth;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import m.pgmacdesign.dagger2examples.di.ViewModelKey;
import m.pgmacdesign.dagger2examples.ui.auth.AuthViewModel;

/**
 * Dependency for the auth view model itself
 */
@Module
public abstract class AuthViewModelsModule {
	
	@Binds
	@IntoMap
	@ViewModelKey(AuthViewModel.class)
	public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);
	
//	@Binds
//	@IntoMap
//	@ViewModelKey(SomeOtherViewModelClassHere.class)
//	public abstract ViewModel bindOTHER_COURSE_MODEL_HERE_Model(AuthViewModel viewModel);
	
}
