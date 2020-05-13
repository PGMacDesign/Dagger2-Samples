package m.pgmacdesign.dagger2examples.ui.auth;

import android.widget.ProgressBar;

import androidx.annotation.AnimatorRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.pgmacdesign.pgmactips.utilities.L;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import m.pgmacdesign.dagger2examples.models.User;
import m.pgmacdesign.dagger2examples.networking.auth.AuthAPI;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {
	
	private final AuthAPI authAPI;
	private MediatorLiveData<AuthResource<User>> authUser = new MediatorLiveData<>();
	
	@Inject
	public AuthViewModel(AuthAPI authAPI){
		this.authAPI = authAPI;
		L.m("Making API Call");
		
		
	}
	
	public void authenticateWithId(int id){
		this.authUser.setValue(AuthResource.loading((User)null));
		final LiveData<AuthResource<User>> source = LiveDataReactiveStreams.fromPublisher(
				this.authAPI.getUser(id)
					.onErrorReturn(new Function<Throwable, User>() {
						@Override
						public User apply(Throwable throwable) throws Exception {
							User errorUser = new User();
							errorUser.setId(-1);
							return errorUser;
						}
					})
					.map(new Function<User, AuthResource<User>>() {
						@Override
						public AuthResource<User> apply(User user) throws Exception {
							if(user == null){
								return AuthResource.error("Unknown error", (User)null);
							}
							if(user.getId() == -1){
								return AuthResource.error("Could not authenticate", (User) null);
							}
							return AuthResource.authenticated(user);
							
						}
					})
					.subscribeOn(Schedulers.io())
		);
		this.authUser.addSource(source, new androidx.lifecycle.Observer<AuthResource<User>>() {
			@Override
			public void onChanged(AuthResource<User> resource) {
				authUser.setValue(resource);
				authUser.removeSource(source);
			}
		});
	}
	
	public LiveData<AuthResource<User>> observeUser(){
		return this.authUser;
	}
	
	private void ignoreSampleCode(){
		this.authAPI.getUser(1)
				.toObservable()
				.subscribeOn(Schedulers.io())
				.subscribe(new Observer<User>() {
					@Override
					public void onSubscribe(Disposable d) {
					
					}
					
					@Override
					public void onNext(User user) {
						L.m("Request was successful");
						if(user != null) {
							L.m("USER EMAIL == " + user.getEmail());
						} else {
							L.m("USER == NULL!");
						}
					}
					
					@Override
					public void onError(Throwable e) {
						L.m("Error occurred:");
						e.printStackTrace();
					}
					
					@Override
					public void onComplete() {
						L.m("API call onComplete triggered");
					}
				});
	}
}
