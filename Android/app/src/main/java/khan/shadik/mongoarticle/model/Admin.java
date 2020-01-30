package khan.shadik.mongoarticle.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Admin {

    private boolean success;
    private String message;
    @SerializedName("__v")
    private String id;
    private List<AdminLeaveModel> foundLeaves;
    private List<AdminReportModel> foundReports;
    private List<AdminUsersModel> foundUsers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public List<AdminLeaveModel> getFoundLeaves() {
        return foundLeaves;
    }

    public void setFoundLeaves(List<AdminLeaveModel> foundLeaves) {
        this.foundLeaves = foundLeaves;
    }

    public List<AdminReportModel> getFoundReports() {
        return foundReports;
    }

    public void setFoundReports(List<AdminReportModel> foundReports) {
        this.foundReports = foundReports;
    }

    public List<AdminUsersModel> getFoundUsers() {
        return foundUsers;
    }

    public void setFoundUsers(List<AdminUsersModel> foundUsers) {
        this.foundUsers = foundUsers;
    }

}
