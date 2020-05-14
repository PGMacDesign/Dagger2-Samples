package m.pgmacdesign.dagger2examples;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.pgmacdesign.pgmactips.utilities.L;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import m.pgmacdesign.dagger2examples.models.User;
import m.pgmacdesign.dagger2examples.ui.auth.AuthActivity;
import m.pgmacdesign.dagger2examples.ui.auth.AuthResource;

public abstract class BaseActivity extends DaggerAppCompatActivity {
	private static final String TAG = "BaseActivity";
	
	@Inject
	public SessionManager sessionManager;
	
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.subscribeObservers();
	}
	
	private void subscribeObservers(){
		this.sessionManager.getAuthUser().observe(this, userAuthResource -> {
			if(userAuthResource != null){
				switch (userAuthResource.status){
					case LOADING:
						
						break;
					case AUTHENTICATED:
						if(userAuthResource.data != null) {
							L.m("Login success. Email == " + userAuthResource.data.getEmail());
						}
						
						break;
					case ERROR:
						L.m("Error! Message: " + userAuthResource.message);
						
						break;
					case NOT_AUTHENTICATED:
						navLoginScreen();
						break;
					
				}
			}
		});
	}
	
	/**
	 * Boots to the login screen
	 */
	private void navLoginScreen(){
		Intent intent = new Intent(BaseActivity.this, AuthActivity.class);
		this.startActivity(intent);
		//Any other logout stuff here
		this.finish();
	}
}
