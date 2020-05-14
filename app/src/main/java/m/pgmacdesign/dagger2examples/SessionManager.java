package m.pgmacdesign.dagger2examples;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import javax.inject.Inject;
import javax.inject.Singleton;

import m.pgmacdesign.dagger2examples.models.User;
import m.pgmacdesign.dagger2examples.ui.auth.AuthResource;

/**
 * Session manager for active authentication checking
 * LiveData is used for observing this into any class they have this class injected into to.
 * Basically, it allows for active observation as opposed to having to check if they are still
 * logged in manually.
 */
@Singleton
public class SessionManager {
	
	private static final String TAG = "SessionManager";
	
	private MediatorLiveData<AuthResource<User>> cachedUser = new MediatorLiveData<>();
	
	@Inject
	public SessionManager(){
	
	}
	
	/**
	 * Authenticate a user with an ID
	 * @param source
	 */
	public void authenticateWithId(final LiveData<AuthResource<User>> source){
		if(this.cachedUser != null){
			this.cachedUser.setValue(AuthResource.loading((User)null));
			this.cachedUser.addSource(source, resource -> {
				this.cachedUser.setValue(resource);
				this.cachedUser.removeSource(source);
			});
		}
	}
	
	/**
	 * Calls logout
	 */
	public void logout(){
		if(this.cachedUser != null){
			this.cachedUser.setValue(AuthResource.<User>logout());
		}
	}
	
	/**
	 * Getter
	 * @return
	 */
	public LiveData<AuthResource<User>> getAuthUser(){
		return this.cachedUser;
	}
}
