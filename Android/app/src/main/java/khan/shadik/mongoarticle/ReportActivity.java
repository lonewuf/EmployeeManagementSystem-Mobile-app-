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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import khan.shadik.mongoarticle.adapter.ReportItemAdapter;
import khan.shadik.mongoarticle.model.InsertReport;
import khan.shadik.mongoarticle.model.InsertReportCall;
import khan.shadik.mongoarticle.model.ReportModel;
import khan.shadik.mongoarticle.model.User;
import khan.shadik.mongoarticle.network.BackendClient;
import khan.shadik.mongoarticle.network.UserCall;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity {

    private RecyclerView reportListRecycler;
    private ReportItemAdapter reportAdapter;

    SharedPreferences sharedPreferences;
    public static final String myPreference = "myPref";
    public static final String Auth = "authToken";
    static String authToken;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private static int admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(Auth, "");
        admin = sharedPreferences.getInt("isAdmin", -1);


        Toolbar toolbar = (Toolbar) findViewById(R.id.report_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reports Records");

        findViewById(R.id.reportFBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReport();
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
                intent = new Intent(ReportActivity.this, AdminHomeActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_employee:
                intent = new Intent(ReportActivity.this, AddEmployeeStaticActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_about:
                intent = new Intent(ReportActivity.this, UserProfileActivity.class);
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
        reportListRecycler = (RecyclerView) findViewById(R.id.rv_report);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        reportListRecycler.setLayoutManager(layoutManager);
        reportAdapter = new ReportItemAdapter(ReportActivity.this, new ArrayList<ReportModel>());
        reportListRecycler.setAdapter(reportAdapter);
        loadPost(token);
    }

    private void loadPost(String token) {
        UserCall service = BackendClient.createRetrofitService(UserCall.class);
        Call<User> call = service.getCurrentUserProfile(token);
        call.enqueue(new ReportListCallback());
    }

    private void updatePost(List<ReportModel> data){
        reportAdapter.addAll(data);
        reportAdapter.notifyDataSetChanged();
    }

    public class ReportListCallback implements Callback<User> {

        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.body() != null) {
                if (response.body().isSuccess()) {
//                    Toast.makeText(LeaveActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    updatePost(response.body().getData().getReports());
                    System.out.println(response.body());
                } else {
                    Toast.makeText(ReportActivity.this, "Some Error", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(ReportActivity.this, "Response Error", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
            t.printStackTrace();
        }
    }

    public void addReport() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ReportActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_add_report, null);

        final EditText report = (EditText) mView.findViewById(R.id.et_report);

        mDisplayDate = (TextView) mView.findViewById(R.id.tv_dateSubmitted);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ReportActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = year + "/" + month + "/" + day;
                mDisplayDate.setText(date);
            }
        };

        mBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE);
                String token = sharedPreferences.getString(Auth, "");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String dateRaw = mDisplayDate.getText().toString();
                try{
                    Date dob_var = sdf.parse(dateRaw);
                    DateFormat dateFormatISO8601 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String strDob = dateFormatISO8601.format(dob_var);

                    dob_var = dateFormatISO8601.parse(strDob);
                    String reportSent = report.getText().toString();

                    InsertReport ir = new InsertReport(strDob, reportSent, "");

                    UserCall service = BackendClient.createRetrofitService(UserCall.class);
                    Call <InsertReportCall> call = service.postReport(token, ir);

                    call.enqueue(new InsertReportCallBack());

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

    public class InsertReportCallBack implements Callback<InsertReportCall> {

        @Override
        public void onResponse(Call<InsertReportCall> call, Response<InsertReportCall> response) {
            Toast.makeText(ReportActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<InsertReportCall> call, Throwable t) {

        }
    }

}
