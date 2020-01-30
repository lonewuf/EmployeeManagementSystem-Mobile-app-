package khan.shadik.mongoarticle.network;

import khan.shadik.mongoarticle.model.PostModel;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Shadik on 1/28/2017.
 */

public interface NetworkCall {

    @GET("post")
    Call<PostModel> getPosts();


}
