package m.pgmacdesign.dagger2examples.ui.main.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.pgmacdesign.pgmactips.utilities.L;

import javax.inject.Inject;

import m.pgmacdesign.dagger2examples.SessionManager;
import m.pgmacdesign.dagger2examples.models.User;
import m.pgmacdesign.dagger2examples.ui.auth.AuthResource;

public class ProfileViewModel extends ViewModel {
	
	private static final String TAG = "ProfileViewModel";
	
	private final SessionManager sessionManager;
	
	@Inject
	public ProfileViewModel(SessionManager sessionManager){
		this.sessionManager = sessionManager;
		L.m("ProfileViewModel is injected and ready");
	}
	
	public LiveData<AuthResource<User>> getAuthenticatedUser(){
		return this.sessionManager.getAuthUser();
	}
	
}
