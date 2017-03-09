package ma.sdop.imagelist.common.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

abstract public class DataBinder<T extends RecyclerView.ViewHolder> {
    protected DataBindAdapter dataBindAdapter;

    public DataBinder(DataBindAdapter dataBindAdapter) {
        this.dataBindAdapter = dataBindAdapter;
    }

    abstract public T newViewHolder(ViewGroup parent);

    abstract public void bindViewHolder(T holder, int position);
}
