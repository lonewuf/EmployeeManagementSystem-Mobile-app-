package khan.shadik.mongoarticle.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class AdminReportModel {
    @SerializedName("_id")
    private String id;
    private Date date_logged;
    private int year;
    private int day;
    private int month;
    private String file;
    private String report;
    private AdminEmployeeModel employee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate_logged() {
        return date_logged;
    }

    public void setDate_logged(Date date_logged) {
        this.date_logged = date_logged;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public AdminEmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(AdminEmployeeModel employee) {
        this.employee = employee;
    }

}
