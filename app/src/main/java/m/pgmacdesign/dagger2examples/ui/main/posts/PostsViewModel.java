package m.pgmacdesign.dagger2examples.ui.main.posts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.pgmacdesign.pgmactips.utilities.L;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import m.pgmacdesign.dagger2examples.SessionManager;
import m.pgmacdesign.dagger2examples.models.Post;
import m.pgmacdesign.dagger2examples.networking.main.MainAPI;
import m.pgmacdesign.dagger2examples.ui.main.Resource;

public class PostsViewModel extends ViewModel {
	
	private static final String TAG = "PostsViewModel";
	
	private final SessionManager sessionManager;
	private final MainAPI mainAPI;
	
	private MediatorLiveData<Resource<List<Post>>> posts;
	
	@Inject
	public PostsViewModel(SessionManager sessionManager, MainAPI mainAPI){
		this.sessionManager = sessionManager;
		this.mainAPI = mainAPI;
		L.m("Post view model is working");
	}
	
	public LiveData<Resource<List<Post>>> observePost(){
		if(this.posts == null){
			this.posts = new MediatorLiveData<>();
			this.posts.setValue(Resource.loading((List<Post>) null));
			final LiveData<Resource<List<Post>>> source = LiveDataReactiveStreams.fromPublisher(
					this.mainAPI.getPostsFromUser(this.sessionManager.getAuthUser().getValue().data.getId())
					.onErrorReturn(new Function<Throwable, List<Post>>() {
						@Override
						public List<Post> apply(Throwable throwable) throws Exception {
							L.m("Error happened!");
							throwable.printStackTrace();
							Post post = new Post();
							post.setId(-1);
							List<Post> posts = new ArrayList<>();
							posts.add(post);
							return posts;
						}
					})
					.map(new Function<List<Post>, Resource<List<Post>>>() {
						@Override
						public Resource<List<Post>> apply(List<Post> posts) throws Exception {
							if(posts.get(0).getId() == -1){
								return Resource.error("Something went wrong!", null);
							}
							return Resource.success(posts);
						}
					})
					.subscribeOn(Schedulers.io())
			);
			this.posts.addSource(source, listResource -> {
				posts.setValue(listResource);
				posts.removeSource(source);
			});
		}
		return this.posts;
	}
	
}
