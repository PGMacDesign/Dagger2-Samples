package m.pgmacdesign.dagger2examples.di.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import m.pgmacdesign.dagger2examples.ui.main.posts.PostsFragment;
import m.pgmacdesign.dagger2examples.ui.main.profile.ProfileFragment;

/**
 * Injects fragments inside of the Main Component
 */
@Module
public abstract class MainFragmentBuildersModule {

	@ContributesAndroidInjector
	abstract ProfileFragment contributeProfileFragment();


	@ContributesAndroidInjector
	abstract PostsFragment contributePostsFragment();


}
