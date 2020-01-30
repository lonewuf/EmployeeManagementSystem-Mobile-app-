package khan.shadik.mongoarticle.model;

public class InsertReport {

    private String date;
    private String report;
    private String fileUp;

    public InsertReport(String date, String report, String fileUp) {
        this.date = date;
        this.report = report;
        this.fileUp = fileUp;
    }
}
