package ma.sdop.imagelist.common.web.parameter;

public class InstagramParameter extends BaseParameter {
    private String userId;
    private String maxId;

    public InstagramParameter(String userId, String maxId) {
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

    @Override
    public boolean isNext() {
        return maxId != null;
    }
}
