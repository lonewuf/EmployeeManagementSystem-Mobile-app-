package khan.shadik.mongoarticle.network;

import khan.shadik.mongoarticle.model.InsertLeave;
import khan.shadik.mongoarticle.model.InsertLeaveCall;
import khan.shadik.mongoarticle.model.InsertReport;
import khan.shadik.mongoarticle.model.InsertReportCall;
import khan.shadik.mongoarticle.model.Login;
import khan.shadik.mongoarticle.model.User;
import khan.shadik.mongoarticle.model.UserLoggedIn;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserCall {

    @POST("login-mobile-call")
    Call<UserLoggedIn> login(@Body Login login);

    @GET("profile/samplehere")
    Call<ResponseBody> sampleCallLeave(@Header("Authorization") String authToken);

    @GET("profile/user-mobile-call")
    Call<User> getCurrentUserProfile(@Header("Authorization") String authToken);

    @POST("profile/leaves-mobile-call")
    Call<InsertLeaveCall> postLeave(@Header("Authorization") String authToken, @Body InsertLeave insertLeave);

    @POST("profile/insert-report-mobile-call")
    Call<InsertReportCall> postReport(@Header("Authorization") String authToken, @Body InsertReport insertReport);

    @GET("current")
    Call<ResponseBody> tryandtry(@Header("Authorization") String authToken);

}
