package khan.shadik.mongoarticle.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import khan.shadik.mongoarticle.R;
import khan.shadik.mongoarticle.model.AdminLeaveModel;
import khan.shadik.mongoarticle.network.AdminCall;
import khan.shadik.mongoarticle.network.BackendClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLeaveItemAdapter extends RecyclerView.Adapter<AdminLeaveItemAdapter.ViewHolder> {


    private Context context;
    private List<AdminLeaveModel> data;

    public AdminLeaveItemAdapter(Context context, List<AdminLeaveModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_leave, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final AdminLeaveModel value = data.get(position);
        String date_to = value.getDate_to().toLocaleString();
        String date_from = value.getDate_from().toLocaleString();
        final String userId = value.getId().toString();
        String statusString = "";

        if(value.getStatus().toString() == "0") {
            statusString = "Disapproved";
        } else if(value.getStatus().toString() == "1") {
            statusString = "Pending";
        } else if(value.getStatus().toString() == "0") {
            statusString = "Approve";
        }


        holder.leaveFrom.setText(date_from);
        holder.employeeName.setText(value.getEmployee().getName());
        holder.leaveTo.setText(date_to);
        holder.leaveType.setText(value.getType_leave());
        holder.leaveDays.setText(String.valueOf(value.getDays_leave()));
        holder.leaveReason.setText(value.getReason());
        holder.leaveStatus.setText(statusString);
        holder.btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminCall service = BackendClient.createRetrofitService(AdminCall.class);
                Call<ResponseBody> call = service.approveLeave(userId);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Toast.makeText(context, response.body().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e ) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        holder.btnDisapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminCall service = BackendClient.createRetrofitService(AdminCall.class);
                Call<ResponseBody> call = service.disapproveLeave(userId);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Toast.makeText(context, response.body().string(), Toast.LENGTH_LONG).show();
                        } catch (IOException e ) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAll(List<AdminLeaveModel> list) {
        if (list.size() > 0) {
            data.clear();
            data.addAll(list);
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView employeeName;
        private TextView leaveFrom;
        private TextView leaveTo;
        private TextView leaveType;
        private TextView leaveDays;
        private TextView leaveReason;
        private TextView leaveStatus;
        private Button btnApprove;
        private Button btnDisapprove;

        LinearLayout relative;

        public ViewHolder(View view) {
            super(view);
            employeeName = (TextView) view.findViewById(R.id.tv_emp_name_leave_admin);
            leaveFrom = (TextView) view.findViewById(R.id.tv_from_admin);
            leaveTo = (TextView) view.findViewById(R.id.tv_to_admin);
            leaveType = (TextView) view.findViewById(R.id.tv_typeOfLeave_admin);
            leaveDays = (TextView) view.findViewById(R.id.tv_daysOfLeave_admin);
            leaveReason = (TextView) view.findViewById(R.id.tv_reason_admin);
            leaveStatus = (TextView) view.findViewById(R.id.tv_status_admin);

            btnApprove = (Button) view.findViewById(R.id.btn_leave_approve);
            btnDisapprove = (Button) view.findViewById(R.id.btn_leave_disapprove);
            relative = (LinearLayout) view.findViewById(R.id.relative_leave_admin);
        }
    }

}
