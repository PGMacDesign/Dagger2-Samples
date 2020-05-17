package m.pgmacdesign.dagger2examples.di.main;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import m.pgmacdesign.dagger2examples.di.ViewModelKey;
import m.pgmacdesign.dagger2examples.ui.main.posts.PostsViewModel;
import m.pgmacdesign.dagger2examples.ui.main.profile.ProfileViewModel;

@Module
public abstract class MainViewModelsModule {
	
	@Binds
	@IntoMap
	@ViewModelKey(ProfileViewModel.class)
	public abstract ViewModel bindProfileViewModel(ProfileViewModel viewModel);
	
	@Binds
	@IntoMap
	@ViewModelKey(PostsViewModel.class)
	public abstract ViewModel bindPostsViewModel(PostsViewModel viewModel);
	
}
