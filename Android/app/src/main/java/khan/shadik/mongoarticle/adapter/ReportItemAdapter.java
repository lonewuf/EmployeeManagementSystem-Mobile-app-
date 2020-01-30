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
import khan.shadik.mongoarticle.model.ReportModel;

public class ReportItemAdapter extends RecyclerView.Adapter<ReportItemAdapter.ViewHolder> {

    private Context context;
    private List<ReportModel> data;

    public ReportItemAdapter(Context context, List<ReportModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReportItemAdapter.ViewHolder holder, final int position) {
        final ReportModel value = data.get(position);
        String date_submitted = value.getDate_logged().toLocaleString();
        String date_report = String.valueOf(value.getYear()) + "-" + String.valueOf(value.getMonth())
                + "-" + String.valueOf(value.getDay());

        holder.dateSubmitted.setText(date_submitted);
        holder.dateReport.setText(date_report);
        holder.report.setText(value.getReport());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAll(List<ReportModel> list) {
        if (list.size() > 0) {
            data.clear();
            data.addAll(list);
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView dateSubmitted;
        private TextView dateReport;
        private TextView report;

        LinearLayout relative;

        public ViewHolder(View view) {
            super(view);
            dateSubmitted = (TextView) view.findViewById(R.id.tv_dateSubmitted);
            dateReport = (TextView) view.findViewById(R.id.tv_dateOfReport);
            report = (TextView) view.findViewById(R.id.tv_report);
            relative = (LinearLayout) view.findViewById(R.id.relative_report);
        }
    }

}
