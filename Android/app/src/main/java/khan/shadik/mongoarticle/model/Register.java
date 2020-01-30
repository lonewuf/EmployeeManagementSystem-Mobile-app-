package khan.shadik.mongoarticle.model;

public class Register {

    private String name;
    private String email;
    private String address;
    private String phone_number;
    private String company_num;

    public Register(String name, String email, String address, String phone_number, String company_num) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone_number = phone_number;
        this.company_num = company_num;
    }
}
