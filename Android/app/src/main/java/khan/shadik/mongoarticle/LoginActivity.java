package khan.shadik.mongoarticle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import khan.shadik.mongoarticle.model.Login;
import khan.shadik.mongoarticle.model.UserLoggedIn;
import khan.shadik.mongoarticle.network.BackendClient;
import khan.shadik.mongoarticle.network.UserCall;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String mypreference = "myPref";
    public static final String Auth = "authToken";
    public static final String Admin = "isAdmin";

    static EditText emailSent;
    static EditText passwordSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        emailSent = (EditText) findViewById(R.id.et_login_email);
        passwordSent = (EditText) findViewById(R.id.et_login_password);
        emailSent.setText("");
        passwordSent.setText("");

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }



    private static String token;

    private void login() {

        emailSent = (EditText) findViewById(R.id.et_login_email);
        passwordSent = (EditText) findViewById(R.id.et_login_password);

        String emailThis = emailSent.getText().toString();
        String passwordThis = passwordSent.getText().toString();
        Toast.makeText(LoginActivity.this,"Logging in..", Toast.LENGTH_SHORT).show();

        Login login = new Login(emailThis, passwordThis);
        UserCall service = BackendClient.createRetrofitService(UserCall.class);
        Call<UserLoggedIn> call = service.login(login);

        call.enqueue(new Callback<UserLoggedIn>() {
            @Override
            public void onResponse(Call<UserLoggedIn> call, Response<UserLoggedIn> response) {
                if(response.body().isSuccess()) {
                    String auth = response.body().getToken();
                    int isAdmin = response.body().getAdmin();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Auth, auth);
                    editor.putInt(Admin, isAdmin);
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, response.body().getMessage() , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserLoggedIn> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Connection Error Occur" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void secret(){
        UserCall service = BackendClient.createRetrofitService(UserCall.class);
        Call<ResponseBody> call = service.tryandtry(token);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Toast.makeText(LoginActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error !", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
