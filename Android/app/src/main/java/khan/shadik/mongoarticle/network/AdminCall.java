package khan.shadik.mongoarticle.network;

import khan.shadik.mongoarticle.model.Admin;
import khan.shadik.mongoarticle.model.Register;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface AdminCall {

    @GET("admin")
    Call<Admin> adminHome(@Header("Authorization") String authToken);

    @Multipart
    @POST("upload")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("upload") RequestBody name);

//    @Multipart
//    @POST("registerr")
//    Call<ResponseBody> postImageWithBody(@Part MultipartBody.Part image, @Part("upload") RequestBody name);

    @POST("registerr")
    Call<ResponseBody> registerEmployee(@Header("Authorization") String authToken, @Body Register register);


    @POST("admin/approve-leave/{id}")
    Call<ResponseBody> approveLeave(@Path("id") String id);

    @POST("admin/disapprove-leave/{id}")
    Call<ResponseBody> disapproveLeave(@Path("id") String id);

}
