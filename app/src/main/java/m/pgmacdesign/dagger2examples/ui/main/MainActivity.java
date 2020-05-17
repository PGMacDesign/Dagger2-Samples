package m.pgmacdesign.dagger2examples.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import m.pgmacdesign.dagger2examples.BaseActivity;
import m.pgmacdesign.dagger2examples.R;
import m.pgmacdesign.dagger2examples.ui.main.posts.PostsFragment;
import m.pgmacdesign.dagger2examples.ui.main.profile.ProfileFragment;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
	private static final String TAG = "MainActivity";
	
	private DrawerLayout drawerLayout;
	private NavigationView navigationView;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		this.drawerLayout = this.findViewById(R.id.drawer_layout);
		this.navigationView = this.findViewById(R.id.nav_view);
		this.init();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = this.getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		
		return true;
		
	}
	
	/**
	 * Needs to run after UI init
	 */
	private void init(){
		NavController navController = this.getNavController();
		NavigationUI.setupActionBarWithNavController(this, navController, this.drawerLayout);
		this.navigationView.setNavigationItemSelectedListener(this);
	}
	
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()){
			case R.id.logout:
				sessionManager.logout();
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
		NavOptions navOptions;
		switch (menuItem.getItemId()){
			
			case R.id.nav_profile:
				navOptions = new NavOptions.Builder()
						.setPopUpTo(R.id.main, true)
						.build();
				this.getNavController().navigate(R.id.profileScreen, null, navOptions);
				break;
				
			case R.id.nav_posts:
				if (this.isValidDestination(R.id.postsScreen)) {
					navOptions = new NavOptions.Builder()
							.setPopUpTo(R.id.main, true)
							.build();
					this.getNavController().navigate(R.id.postsScreen, null, navOptions);
				}
				break;
				
			case android.R.id.home:
				if(this.drawerLayout.isDrawerOpen(GravityCompat.START)){
					this.drawerLayout.closeDrawer(GravityCompat.START);
					return true;
				}
				return false;
				
			case -1:
				
				break;
				
		}
		menuItem.setChecked(true);
		this.drawerLayout.closeDrawer(GravityCompat.START);
		return true;
	}
	
	@Override
	public boolean onSupportNavigateUp() {
		return NavigationUI.navigateUp(this.getNavController(), this.drawerLayout);
	}
	
	private boolean isValidDestination(int destination){
		return (this.getNavController().getCurrentDestination() == null) ? true :
				(destination != this.getNavController().getCurrentDestination().getId());

	}
	
	private NavController getNavController(){
		return Navigation.findNavController(this, R.id.nav_host_fragment);
	}
}
