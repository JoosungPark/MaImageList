package ma.sdop.imagelist.common.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by parkjoosung on 2017. 3. 7..
 */

abstract public class DataBindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getDataBinder(viewType).newViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        getDataBinder(viewHolder.getItemViewType()).bindViewHolder(viewHolder, position);
    }

    @Override
    public abstract int getItemCount();

    @Override
    public abstract int getItemViewType(int position);

    public abstract <T extends DataBinder> T getDataBinder(int viewType);

    public abstract Object getItem(int position);
}
