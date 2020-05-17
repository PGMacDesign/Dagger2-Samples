package m.pgmacdesign.dagger2examples.ui.main.posts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pgmacdesign.pgmactips.utilities.L;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import m.pgmacdesign.dagger2examples.R;
import m.pgmacdesign.dagger2examples.VerticalSpacingItemDecoration;
import m.pgmacdesign.dagger2examples.models.Post;
import m.pgmacdesign.dagger2examples.ui.main.PostsRecyclerAdapter;
import m.pgmacdesign.dagger2examples.ui.main.Resource;
import m.pgmacdesign.dagger2examples.ui.main.profile.ProfileViewModel;
import m.pgmacdesign.dagger2examples.viewmodels.ViewModelProviderFactory;

public class PostsFragment extends DaggerFragment {
	
	private static final String TAG = "PostsFragment";
	
	private PostsViewModel viewModel;
	private RecyclerView recyclerView;
	
	@Inject
	PostsRecyclerAdapter adapter;
	
	@Inject
	ViewModelProviderFactory providerFactory;
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_posts, container, false);
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		this.recyclerView = view.findViewById(R.id.recycler_view);
		this.viewModel = new ViewModelProvider(this, this.providerFactory).get(PostsViewModel.class);
		this.initRecyclerview();
		this.subscribeObservers();
	}
	
	/**
	 * Subscribe to the observer
	 */
	private void subscribeObservers(){
		this.viewModel.observePost().removeObservers(this.getViewLifecycleOwner());
		this.viewModel.observePost().observe(this.getViewLifecycleOwner(), listResource -> {
			if(listResource != null){
				switch (listResource.status){
					case SUCCESS:
						L.m("got posts!");
						this.adapter.setPosts(listResource.data);
						break;
						
					case LOADING:
						L.m("Loading...");
						break;
						
					case ERROR:
						L.m("Error! " + listResource.message);
						break;
				}
			}
		});
	}
	
	private void initRecyclerview(){
		this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		VerticalSpacingItemDecoration itemDecoration = new VerticalSpacingItemDecoration(15);
		this.recyclerView.addItemDecoration(itemDecoration);
		this.recyclerView.setAdapter(this.adapter);
		
	}
}
