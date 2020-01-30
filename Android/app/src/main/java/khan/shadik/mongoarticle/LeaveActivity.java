package khan.shadik.mongoarticle;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import khan.shadik.mongoarticle.adapter.LeaveItemAdapter;
import khan.shadik.mongoarticle.model.InsertLeave;
import khan.shadik.mongoarticle.model.InsertLeaveCall;
import khan.shadik.mongoarticle.model.LeaveModel;
import khan.shadik.mongoarticle.model.User;
import khan.shadik.mongoarticle.network.BackendClient;
import khan.shadik.mongoarticle.network.UserCall;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaveActivity extends AppCompatActivity {

    private RecyclerView leaveListRecycler;
    private LeaveItemAdapter leaveAdapter;

    SharedPreferences sharedPreferences;
    public static final String myPreference = "myPref";
    public static final String Auth = "authToken";
    static String authToken;

    private static int admin;

    private TextView fromDisplayDate;
    private DatePickerDialog.OnDateSetListener fromDateSetListener;

    private TextView toDisplayDate;
    private DatePickerDialog.OnDateSetListener toDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Auth, "");
        admin = sharedPreferences.getInt("isAdmin", -1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.leave_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Leaves Records");

        findViewById(R.id.leaveFBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLeave();
            }
        });

        init(token);

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
                intent = new Intent(LeaveActivity.this, AdminHomeActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_employee:
                intent = new Intent(LeaveActivity.this, AddEmployeeStaticActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_about:
                intent = new Intent(LeaveActivity.this, UserProfileActivity.class);
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
        leaveListRecycler = (RecyclerView) findViewById(R.id.rv_leave);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        leaveListRecycler.setLayoutManager(layoutManager);
        leaveAdapter = new LeaveItemAdapter(LeaveActivity.this, new ArrayList<LeaveModel>());
        leaveListRecycler.setAdapter(leaveAdapter);
        loadPost(token);
    }

    private void loadPost(String token) {
        UserCall service = BackendClient.createRetrofitService(UserCall.class);
        Call<User> call = service.getCurrentUserProfile(token);
        call.enqueue(new LeaveListCallback());
    }

    private void updatePost(List<LeaveModel> data){
        leaveAdapter.addAll(data);
        leaveAdapter.notifyDataSetChanged();
    }

    public class LeaveListCallback implements Callback<User> {

        public void onResponse(Call<User> call, Response<User> response) {
            if (response.body() != null) {
                if (response.body().isSuccess()) {
//                    Toast.makeText(LeaveActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    updatePost(response.body().getData().getLeaves());
                    System.out.println(response.body());
                } else {
                    Toast.makeText(LeaveActivity.this, "Some Error", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(LeaveActivity.this, "Response Error", Toast.LENGTH_LONG).show();
            }
        }

        public void onFailure(Call<User> call, Throwable t) {

            t.printStackTrace();
        }
    }

    static String typeOfLeave;
    static String leaveTypeSent;

    private void addLeave() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(LeaveActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_add_leave, null);

//        final EditText dob = (EditText) mView.findViewById(R.id.et_from);
//        final EditText dobTo = (EditText) mView.findViewById(R.id.et_to);
        final EditText reason = (EditText) mView.findViewById(R.id.et_reason);

        final Spinner mySpinner = (Spinner) mView.findViewById(R.id.spinner1);

        fromDisplayDate = (TextView) mView.findViewById(R.id.et_from);
        toDisplayDate = (TextView) mView.findViewById(R.id.et_to);

        fromDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        LeaveActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        fromDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        toDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        LeaveActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        toDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        fromDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = year + "/" + month + "/" + day;
                fromDisplayDate.setText(date);
            }
        };

        toDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = year + "/" + month + "/" + day;
                toDisplayDate.setText(date);
            }
        };

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(LeaveActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type_leaves));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Toast.makeText(mView.getContext(), "Please select type of leave", Toast.LENGTH_SHORT).show();
                } else if (i == 1) {
                    typeOfLeave = "Sick Leave";
                    leaveTypeSent = "sick_leave";
                }else if (i == 2) {
                    typeOfLeave = "Vacation Leave";
                    leaveTypeSent = "vacation_leave";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(mView.getContext(), "Please select type of leave", Toast.LENGTH_SHORT).show();
            }
        });

        mBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
                sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);
                String token = sharedPreferences.getString(Auth, "");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String dateRaw = fromDisplayDate.getText().toString();
                String dateRawTo = toDisplayDate.getText().toString();
                try{
                    Date dob_var = sdf.parse(dateRaw);
                    Date dobTo_var = sdf.parse(dateRawTo);
                    DateFormat dateFormatISO8601 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String strDob = dateFormatISO8601.format(dob_var);
                    String strDobTo = dateFormatISO8601.format(dobTo_var);

                    dob_var = dateFormatISO8601.parse(strDob);
                    dobTo_var = dateFormatISO8601.parse(strDobTo);
                    String reasonSent = reason.getText().toString();

                    InsertLeave il = new InsertLeave(strDob, strDobTo, leaveTypeSent, reasonSent);

                    UserCall service = BackendClient.createRetrofitService(UserCall.class);
                    Call <InsertLeaveCall> call = service.postLeave(token, il);

                    call.enqueue(new InsertLeaveCallback());

                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

    }

    public class InsertLeaveCallback implements  Callback<InsertLeaveCall> {

        @Override
        public void onResponse(Call<InsertLeaveCall> call, Response<InsertLeaveCall> response) {
            Toast.makeText(LeaveActivity.this, "message: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<InsertLeaveCall> call, Throwable t) {
            Toast.makeText(LeaveActivity.this, "Error ", Toast.LENGTH_SHORT).show();
        }
    }

}
