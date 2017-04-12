package ma.sdop.imagelist.mvvm.network.instagram;

/**
 * Created by parkjoosung on 2017. 4. 10..
 */

public class InstagramRequest {
    private String userId;
    private String maxId;

    public InstagramRequest(String userId, String maxId) {
        this.userId = userId;
        this.maxId = maxId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMaxId() {
        return maxId;
    }

    public void setMaxId(String maxId) {
        this.maxId = maxId;
    }
}
