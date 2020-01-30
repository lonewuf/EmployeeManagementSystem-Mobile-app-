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
import khan.shadik.mongoarticle.model.AdminUsersModel;

public class AdminUsersItemAdapter extends RecyclerView.Adapter<AdminUsersItemAdapter.ViewHolder> {

    private Context context;
    private List<AdminUsersModel> data;

    public AdminUsersItemAdapter(Context context, List<AdminUsersModel> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_users, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final AdminUsersModel value = data.get(position);

        holder.employeeName.setText(value.getName());
        holder.employeeNum.setText(value.getCompany_num());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAll(List<AdminUsersModel> list) {
        if (list.size() > 0) {
            data.clear();
            data.addAll(list);
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView employeeName;
        private TextView employeeNum;

        LinearLayout relative;

        public ViewHolder(View view) {
            super(view);
            employeeName = (TextView) view.findViewById(R.id.tv_users_emp_name_admin);
            employeeNum = (TextView) view.findViewById(R.id.tv_users_emp_num_admin);
            relative = (LinearLayout) view.findViewById(R.id.relative_users_admin);
        }
    }

}
