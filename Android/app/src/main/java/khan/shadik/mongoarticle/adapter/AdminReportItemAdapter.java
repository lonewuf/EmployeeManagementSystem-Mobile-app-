package khan.shadik.mongoarticle.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import khan.shadik.mongoarticle.R;
import khan.shadik.mongoarticle.model.AdminReportModel;

public class AdminReportItemAdapter extends RecyclerView.Adapter<AdminReportItemAdapter.ViewHolder> {

    private Context context;
    private List<AdminReportModel> data;

    public AdminReportItemAdapter(Context context, List<AdminReportModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_report, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdminReportItemAdapter.ViewHolder holder, final int position) {
        final AdminReportModel value = data.get(position);
        String date_submitted = value.getDate_logged().toLocaleString();
        String date_report = String.valueOf(value.getYear()) + "-" + String.valueOf(value.getMonth())
                + "-" + String.valueOf(value.getDay());
        holder.employeeName.setText(value.getEmployee().getName());
        holder.dateSubmitted.setText(date_submitted);
        holder.dateReport.setText(date_report);
        holder.report.setText(value.getReport());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAll(List<AdminReportModel> list) {
        if (list.size() > 0) {
            data.clear();
            data.addAll(list);
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView employeeName;
        private TextView dateSubmitted;
        private TextView dateReport;
        private TextView report;

        LinearLayout relative;

        public ViewHolder(View view) {
            super(view);
            employeeName = (TextView) view.findViewById(R.id.tv_emp_name_report_admin);
            dateSubmitted = (TextView) view.findViewById(R.id.tv_dateSubmitted_admin);
            dateReport = (TextView) view.findViewById(R.id.tv_dateOfReport_admin);
            report = (TextView) view.findViewById(R.id.tv_report_admin);
            relative = (LinearLayout) view.findViewById(R.id.relative_report_admin);
        }
    }

}
