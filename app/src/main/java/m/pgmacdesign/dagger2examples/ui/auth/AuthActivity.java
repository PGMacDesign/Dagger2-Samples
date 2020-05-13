package m.pgmacdesign.dagger2examples.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pgmacdesign.pgmactips.utilities.DatabaseUtilities;
import com.pgmacdesign.pgmactips.utilities.L;
import com.pgmacdesign.pgmactips.utilities.NumberUtilities;
import com.pgmacdesign.pgmactips.utilities.StringUtilities;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import m.pgmacdesign.dagger2examples.R;
import m.pgmacdesign.dagger2examples.models.User;
import m.pgmacdesign.dagger2examples.viewmodels.ViewModelProviderFactory;

public class AuthActivity extends DaggerAppCompatActivity {
	
	//region vars
	private AuthViewModel viewModel;
	private EditText auth_activity_et;
	private TextView auth_activity_tv;
	private AppCompatButton auth_activity_button;
	private ProgressBar progress_bar;
	//endregion
	
	
	//region Injected Vars
	
	@Inject
	ViewModelProviderFactory providerFactory;
	@Inject
	DatabaseUtilities dbUtilities;
	@Inject
	Gson gson;
	@Inject
	Picasso picasso;
	
	//endregion
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auth_activity);
		this.initVars();
		this.initUI();
		L.m("Start Activity");
		this.dbUtilities.printOutDatabase();
		this.subscribeObservers();
	}
	
	private void initVars(){
		this.viewModel = new ViewModelProvider(this, this.providerFactory).get(AuthViewModel.class);
		L.m("Viewmodel == null? " + (this.viewModel == null));
	}
	
	private void initUI(){
		this.auth_activity_et = this.findViewById(R.id.auth_activity_et);
		this.auth_activity_tv = this.findViewById(R.id.auth_activity_tv);
		this.auth_activity_button = this.findViewById(R.id.auth_activity_button);
		this.progress_bar = this.findViewById(R.id.progress_bar);
		this.auth_activity_button.setOnClickListener(view -> {
			this.attemptLogin();
		});
	}
	
	private void attemptLogin(){
		this.auth_activity_tv.setText("");
		if(StringUtilities.isNullOrEmpty(this.auth_activity_et.getText().toString())){
			return;
		}
		int x = NumberUtilities.parseIntegerSafe(this.auth_activity_et.getText().toString(), -1);
		if(x < 0){
			return;
		}
		this.viewModel.authenticateWithId(x);
	}
	
	private void subscribeObservers(){
//		this.viewModel.observeUser().observe(this, user -> {
//			if(user != null){
//				L.m("User successfully authenticated");
//				this.auth_activity_tv.setText(new Gson().toJson(user, User.class));
//			}
//		});
		this.viewModel.observeUser().observe(this, new Observer<AuthResource<User>>() {
			@Override
			public void onChanged(AuthResource<User> userAuthResource) {
				if(userAuthResource != null){
					switch (userAuthResource.status){
						case LOADING:
							showProgressBar(true);
							
							break;
						case AUTHENTICATED:
							showProgressBar(false);
							if(userAuthResource.data != null) {
								L.m("Login success. Email == " + userAuthResource.data.getEmail());
							}
							
							break;
						case ERROR:
							showProgressBar(false);
							L.Toast(AuthActivity.this, "Login failed. Error: " + userAuthResource.message);
							
							break;
						case NOT_AUTHENTICATED:
							showProgressBar(false);
							
							break;
							
					}
				}
			}
		});
	}
	
	private void showProgressBar(boolean isVisible){
		if(isVisible){
			progress_bar.setVisibility(View.VISIBLE);
		} else {
			progress_bar.setVisibility(View.GONE);
		}
	}
}
