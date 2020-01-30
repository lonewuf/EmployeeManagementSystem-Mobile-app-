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
import android.widget.Toast;

import khan.shadik.mongoarticle.model.Admin;
import khan.shadik.mongoarticle.network.AdminCall;
import khan.shadik.mongoarticle.network.BackendClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static final String myPreference = "myPref";
    public static final String Auth = "authToken";
    static String authToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Auth, "");

        Toolbar toolbar = (Toolbar) findViewById(R.id.admin_home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Admin Home");

        findViewById(R.id.admin_leaves).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminLeaveActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.admin_reports).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminReportActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.admin_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminUsersActivity.class);
                startActivity(intent);
            }
        });

        init(token);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_admin, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent;

        switch (item.getItemId()) {

            case R.id.action_admin:
                intent = new Intent(AdminHomeActivity.this, AdminHomeActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_employee:
                intent = new Intent(AdminHomeActivity.this, AddEmployeeStaticActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_about:
                intent = new Intent(AdminHomeActivity.this, UserProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_logout:
                sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(Auth);
                editor.commit();
                startActivity(new Intent(this, LoginActivity.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void init(String token) {
        AdminCall service = BackendClient.createRetrofitService(AdminCall.class);
        Call<Admin> call = service.adminHome(token);

        call.enqueue(new AdminHomeCallback());
    }

    public class AdminHomeCallback implements Callback<Admin> {

        @Override
        public void onResponse(Call<Admin> call, Response<Admin> response) {
            Toast.makeText(AdminHomeActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<Admin> call, Throwable t) {

        }
    }

}
