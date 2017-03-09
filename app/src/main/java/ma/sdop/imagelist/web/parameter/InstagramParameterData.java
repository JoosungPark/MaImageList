package ma.sdop.imagelist.web.parameter;

public class InstagramParameterData extends ParameterBaseData {
    private String userId;
    private String maxId;

    public InstagramParameterData(String userId, String maxId) {
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
