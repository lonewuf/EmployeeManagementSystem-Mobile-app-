package khan.shadik.mongoarticle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import khan.shadik.mongoarticle.model.User;
import khan.shadik.mongoarticle.network.BackendClient;
import khan.shadik.mongoarticle.network.UserCall;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    String retEmployeeName, retEmployeeNumber, retEmployeeEmail, retEmployeePhone;
    TextView tvEmployeeName, tvEmployeeNumber, tvEmployeeEmail, tvEmployeePhone;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "myPref";
    public static final String Auth = "authToken";
    public static final String Admin = "isAdmin";

    private static int admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.user_profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        String token = sharedpreferences.getString(Auth, "");
        admin = sharedpreferences.getInt("isAdmin", -1);

        init(token);

        findViewById(R.id.btn_leave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, LeaveActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });




    }

//    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        Toast.makeText(UserProfileActivity.this, "Invalid action", Toast.LENGTH_LONG).show();
//        return;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if(admin == 1) {
            getMenuInflater().inflate(R.menu.menu_toolbar_admin, menu);
        } else if (admin == 0 ) {
            getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        }

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {

            case R.id.action_admin:
                intent = new Intent(UserProfileActivity.this, AdminHomeActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_employee:
                intent = new Intent(UserProfileActivity.this, AddEmployeeStaticActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_about:
                intent = new Intent(UserProfileActivity.this, UserProfileActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_logout:
                sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove(Auth);
                editor.commit();
                startActivity(new Intent(this, LoginActivity.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void init(String token) {
        UserCall service = BackendClient.createRetrofitService(UserCall.class);
        Call<User> call = service.getCurrentUserProfile(token);


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {


//                Bitmap myBitmap = BitmapFactory.decodeFile("http://192.168.137.1:8081/employee_images/5d97f335ec540e197036a6ab/person3.jpg");
//
//                ImageView myImage = (ImageView) findViewById(R.id.img_employee);
//
//                myImage.setImageBitmap(myBitmap);


                tvEmployeeName = (TextView) findViewById(R.id.tv_employee_name);
                tvEmployeeNumber = (TextView) findViewById(R.id.tv_employee_number);
                tvEmployeeEmail = (TextView) findViewById(R.id.tv_employee_email);
                tvEmployeePhone = (TextView) findViewById(R.id.tv_employee_email2);

                retEmployeeName = response.body().getData().getName();
                retEmployeeEmail = response.body().getData().getEmail();
                retEmployeePhone = response.body().getData().getPhone_number();
                retEmployeeNumber = response.body().getData().getCompany_num();

                tvEmployeeName.setText(retEmployeeName);
                tvEmployeeNumber.setText("Company Num: " +retEmployeeNumber);
                tvEmployeeEmail.setText("Email: " +retEmployeeEmail);
                tvEmployeePhone.setText("Phone Num: " +retEmployeePhone);

                Toast.makeText(UserProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UserProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
