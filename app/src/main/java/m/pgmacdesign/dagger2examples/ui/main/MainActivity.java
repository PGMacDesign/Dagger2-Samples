package m.pgmacdesign.dagger2examples.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import m.pgmacdesign.dagger2examples.BaseActivity;
import m.pgmacdesign.dagger2examples.R;
import m.pgmacdesign.dagger2examples.ui.main.profile.ProfileFragment;

public class MainActivity extends BaseActivity {
	private static final String TAG = "MainActivity";
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		this.testFragment();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = this.getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		
		return true;
		
	}
	
	private void testFragment(){
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.main_container, new ProfileFragment())
				.commit();
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
}
