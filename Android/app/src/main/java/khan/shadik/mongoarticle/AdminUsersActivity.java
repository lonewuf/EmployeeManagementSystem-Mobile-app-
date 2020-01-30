package khan.shadik.mongoarticle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import khan.shadik.mongoarticle.adapter.AdminUsersItemAdapter;
import khan.shadik.mongoarticle.model.Admin;
import khan.shadik.mongoarticle.model.AdminUsersModel;
import khan.shadik.mongoarticle.network.AdminCall;
import khan.shadik.mongoarticle.network.BackendClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUsersActivity extends AppCompatActivity {

    private RecyclerView adminUsersListRecycler;
    private AdminUsersItemAdapter adminUsersItemAdapter;

    SharedPreferences sharedPreferences;
    public static final String myPreference = "myPref";
    public static final String Auth = "authToken";
    static String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);

        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Auth, "");

        Toolbar toolbar = (Toolbar) findViewById(R.id.users_toolbar_admin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Users Records");

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
                intent = new Intent(AdminUsersActivity.this, AdminHomeActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_employee:
                intent = new Intent(AdminUsersActivity.this, AddEmployeeStaticActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_about:
                intent = new Intent(AdminUsersActivity.this, UserProfileActivity.class);
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
        adminUsersListRecycler = (RecyclerView) findViewById(R.id.rv_users_admin);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adminUsersListRecycler.setLayoutManager(layoutManager);
        adminUsersItemAdapter = new AdminUsersItemAdapter(AdminUsersActivity.this, new ArrayList<AdminUsersModel>());
        adminUsersListRecycler.setAdapter(adminUsersItemAdapter);
        loadPost(token);

    }

    private void loadPost(String token) {
        AdminCall service = BackendClient.createRetrofitService(AdminCall.class);
        Call<Admin> call = service.adminHome(token);
        call.enqueue(new AdminUsersCallback());
    }

    private void updatePost(List<AdminUsersModel> data){
        adminUsersItemAdapter.addAll(data);
        adminUsersItemAdapter.notifyDataSetChanged();
    }

    public class AdminUsersCallback implements Callback<Admin> {

        @Override
        public void onResponse(Call<Admin> call, Response<Admin> response) {
            if (response.body().isSuccess()) {
                updatePost(response.body().getFoundUsers());
                System.out.println(response.body());
            } else {
                Toast.makeText(AdminUsersActivity.this, "Some Error", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Admin> call, Throwable t) {

        }
    }

}
