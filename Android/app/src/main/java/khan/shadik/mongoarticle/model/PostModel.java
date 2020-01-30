package khan.shadik.mongoarticle.model;

import java.util.List;

/**
 * Created by Shadik on 1/28/2017.
 */

public class PostModel {


    private boolean success;
    private String message;
    private List<PostItemModel> data;

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

    public List<PostItemModel> getData() {
        return data;
    }

    public void setData(List<PostItemModel> data) {
        this.data = data;
    }


}
