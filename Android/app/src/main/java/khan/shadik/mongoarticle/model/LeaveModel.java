package khan.shadik.mongoarticle.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class LeaveModel {
    @SerializedName("_id")
    private String id;
    private String status;
    private int statusInt;
    private Date date_from;
    private Date date_to;
    private int days_leave;
    private String reason;
    private String type_leave;
    private String employee;

    public int getStatusInt() {
        return statusInt;
    }

    public void setStatusInt(int statusInt) {
        this.statusInt = statusInt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date date_from) {
        this.date_from = date_from;
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date date_to) {
        this.date_to = date_to;
    }

    public int getDays_leave() {
        return days_leave;
    }

    public void setDays_leave(int days_leave) {
        this.days_leave = days_leave;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getType_leave() {
        return type_leave;
    }

    public void setType_leave(String type_leave) {
        this.type_leave = type_leave;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }
}
