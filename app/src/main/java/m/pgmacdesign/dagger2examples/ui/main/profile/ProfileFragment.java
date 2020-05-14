package m.pgmacdesign.dagger2examples.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.pgmacdesign.pgmactips.utilities.L;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import dagger.multibindings.IntoMap;
import m.pgmacdesign.dagger2examples.R;
import m.pgmacdesign.dagger2examples.models.User;
import m.pgmacdesign.dagger2examples.ui.auth.AuthResource;
import m.pgmacdesign.dagger2examples.ui.auth.AuthViewModel;
import m.pgmacdesign.dagger2examples.viewmodels.ViewModelProviderFactory;

public class ProfileFragment extends DaggerFragment {
	
	private ProfileViewModel viewModel;
	
	private TextView email, username, website;
	
	@Inject
	ViewModelProviderFactory factory;
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile, container, false);
		return view;
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		L.m("Profile Fragment created.");
		this.email = view.findViewById(R.id.email);
		this.username = view.findViewById(R.id.username);
		this.website = view.findViewById(R.id.website);
		this.viewModel = new ViewModelProvider(this, this.factory).get(ProfileViewModel.class);
		this.subscribeObservers();
	}
	
	/**
	 * Subscribe to the observers
	 */
	private void subscribeObservers(){
		/**
		 * This first line is done purposefully!
		 */
		this.viewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
		this.viewModel.getAuthenticatedUser().observe(this.getViewLifecycleOwner(), resource -> {
			if(resource != null) {
				switch (resource.status){
					case ERROR:
						this.setErrorDetails(resource.message);
						break;
					
					case AUTHENTICATED:
						this.setUserDetails(resource.data);
						break;
						
					case LOADING:
					case NOT_AUTHENTICATED:
						//In theory, neither of these can be reached
						break;
				}
			}
		});
	}
	
	private void setUserDetails(User data){
		this.email.setText(String.format("Email: %s", data.getEmail()));
		this.username.setText(String.format("My Name Is: %s", data.getUsername()));
		this.website.setText(String.format("Visit my website @ %s", data.getWebsite()));
	}
	
	private void setErrorDetails(String message){
		this.email.setText(message);
		this.username.setText("Error");
		this.website.setText("Error");
		
	}
}
