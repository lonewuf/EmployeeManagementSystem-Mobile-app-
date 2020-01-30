package khan.shadik.mongoarticle.model;

public class InsertLeave {

    private String date_from;
    private String date_to;
    private String leave;
    private String reason;

    public InsertLeave(String date_from, String date_to, String leave, String reason) {
        this.date_from = date_from;
        this.date_to = date_to;
        this.leave = leave;
        this.reason = reason;
    }
}
