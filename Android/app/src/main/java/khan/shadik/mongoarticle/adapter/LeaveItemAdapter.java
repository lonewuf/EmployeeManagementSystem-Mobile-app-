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
import khan.shadik.mongoarticle.model.LeaveModel;

public class LeaveItemAdapter extends RecyclerView.Adapter<LeaveItemAdapter.ViewHolder> {

    private Context context;
    private List<LeaveModel> data;

    public LeaveItemAdapter(Context context, List<LeaveModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leave, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final LeaveModel value = data.get(position);
        String date_to = value.getDate_to().toLocaleString();
        String date_from = value.getDate_from().toLocaleString();

        holder.leaveFrom.setText(date_from);
        holder.leaveTo.setText(date_to);
        holder.leaveType.setText(value.getType_leave());
        holder.leaveDays.setText(String.valueOf(value.getDays_leave()));
        holder.leaveReason.setText(value.getReason());
        holder.leaveStatus.setText(value.getStatus());

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAll(List<LeaveModel> list) {
        if (list.size() > 0) {
            data.clear();
            data.addAll(list);
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView leaveFrom;
        private TextView leaveTo;
        private TextView leaveType;
        private TextView leaveDays;
        private TextView leaveReason;
        private TextView leaveStatus;


        LinearLayout relative;

        public ViewHolder(View view) {
            super(view);
            leaveFrom = (TextView) view.findViewById(R.id.tv_from);
            leaveTo = (TextView) view.findViewById(R.id.tv_to);
            leaveType = (TextView) view.findViewById(R.id.tv_typeOfLeave);
            leaveDays = (TextView) view.findViewById(R.id.tv_daysOfLeave);
            leaveReason = (TextView) view.findViewById(R.id.tv_reason);
            leaveStatus = (TextView) view.findViewById(R.id.tv_status);
            relative = (LinearLayout) view.findViewById(R.id.relative_leave);
        }
    }
}
