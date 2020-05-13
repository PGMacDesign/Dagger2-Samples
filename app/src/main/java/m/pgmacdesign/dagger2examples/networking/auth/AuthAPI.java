package m.pgmacdesign.dagger2examples.networking.auth;

import io.reactivex.Flowable;
import m.pgmacdesign.dagger2examples.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthAPI {
	
	@GET("/users/{id}")
	Flowable<User> getUser(@Path("id") int id);
	
	@GET("/users/{id}")
	Call<ResponseBody> getUser2(@Path("id") int id);
	
}
