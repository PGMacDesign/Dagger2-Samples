package m.pgmacdesign.dagger2examples.networking.main;

import java.util.List;

import io.reactivex.Flowable;
import m.pgmacdesign.dagger2examples.models.Post;
import m.pgmacdesign.dagger2examples.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MainAPI {
	
	
	@GET("/posts")
	Flowable<List<Post>> getPostsFromUser(@Query("userId") int id);
	
	@GET("/posts")
	Call<ResponseBody> getPostsFromUser2(@Query("userId") int id);
	
	
}
