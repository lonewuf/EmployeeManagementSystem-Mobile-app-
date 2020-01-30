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
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import khan.shadik.mongoarticle.model.Register;
import khan.shadik.mongoarticle.network.AdminCall;
import khan.shadik.mongoarticle.network.BackendClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmployeeStaticActivity extends AppCompatActivity {

    private static EditText registerName;
    private static EditText registercompanyNumber;
    private static EditText registerAddress;
    private static EditText registerPhoneNumber;
    private static EditText registerEmailAddress;

    SharedPreferences sharedPreferences;
    public static final String myPreference = "myPref";
    public static final String Auth = "authToken";
    static String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee_static);


        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        authToken = sharedPreferences.getString(Auth, "");

        Toolbar toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register Employee");

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerEmployee();
            }
        });

        findViewById(R.id.btn_cancel_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelRegisterEmployee();
            }
        });

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
                intent = new Intent(AddEmployeeStaticActivity.this, AdminHomeActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_employee:
                intent = new Intent(AddEmployeeStaticActivity.this, AddEmployeeStaticActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_about:
                intent = new Intent(AddEmployeeStaticActivity.this, UserProfileActivity.class);
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

    private void registerEmployee() {

        registerName = (EditText) findViewById(R.id.et_register_name);
        registerAddress = (EditText) findViewById(R.id.et_register_address);
        registercompanyNumber = (EditText) findViewById(R.id.et_register_company_id);
        registerEmailAddress = (EditText) findViewById(R.id.et_register_email_add);
        registerPhoneNumber = (EditText) findViewById(R.id.et_register_phone_number);

        String nameReg = registerName.getText().toString();
        String companyIDReg = registercompanyNumber.getText().toString();
        String addressReg = registerAddress.getText().toString();
        String phoneNumberReg = registerPhoneNumber.getText().toString();
        String emailAddReg = registerEmailAddress.getText().toString();

        Register register = new Register(nameReg, emailAddReg, addressReg, companyIDReg, phoneNumberReg);

        AdminCall service = BackendClient.createRetrofitService(AdminCall.class);
        Call<ResponseBody> call = service.registerEmployee(authToken, register);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Toast.makeText(AddEmployeeStaticActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddEmployeeStaticActivity.this, AdminHomeActivity.class);
                    startActivity(intent);
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddEmployeeStaticActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cancelRegisterEmployee() {
        Toast.makeText(AddEmployeeStaticActivity.this, "Register Cancelled", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddEmployeeStaticActivity.this, AdminHomeActivity.class);
        startActivity(intent);
    }

}
