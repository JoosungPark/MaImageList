package ma.sdop.imagelist.web.parameter;

/**
 * Created by parkjoosung on 2017. 3. 9..
 */

public class NParameterData extends ParameterBaseData{
    private String query;

    // count of wanted result.
    private int display;

    // wanted start index.
    private int start;

    // result count
    private int totalCount;

    public NParameterData(String query, int display, int start) {
        this.query = query;
        this.display = display;
        this.start = start;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public boolean isNext() {
        return totalCount > start + display;
    }

    public void setNextStart() {
        start = start + display;
    }
}
