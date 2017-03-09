package ma.sdop.imagelist.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ma.sdop.imagelist.R;

/**
 * PickerDialog provide useful function.
 * if you wanted to picker with any string array,
 * just create with wanted string array!
 *
 * @author parkjoosung
 */
public class PickerDialog extends Dialog {
    private ListView picker_list;
    private List<String> items;
    private int[] stringResIds;
    public static final int NOT_DEFINED = -1;
    private @StringRes int titleId = NOT_DEFINED;

    private OnPickerItemSelectedListener mOnPickerItemSelectedListener;

    private PickerDialog(Context context, int[] resStrings) {
        super(context);
        setItems(resStrings);
    }

    private PickerDialog(Context context, List<String> items) {
        super(context);
        this.items = items;
    }

    public PickerDialog(Context context, String[] items) {
        super(context);
        this.items = Arrays.asList(items);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker);

        if ( titleId != NOT_DEFINED ) {
            TextView titleView = (TextView) findViewById(R.id.title);
            titleView.setText(titleId);
            titleView.setVisibility(View.VISIBLE);
        }

        picker_list = (ListView) findViewById(R.id.picker_list);
        PickerAdapter adapter = new PickerAdapter(getContext(), items);
        picker_list.setAdapter(adapter);
    }

    private final class PickerAdapter extends ArrayAdapter<String> {
        public PickerAdapter(Context context, List<String> items) {
            super(context, R.layout.picker_item, items);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null ) {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.picker_item, parent, false);
                holder.picker_item = (TextView) convertView.findViewById(R.id.picker_item);
                convertView.setTag(holder);
            }  else {
                holder = (ViewHolder) convertView.getTag();
            }

            String data = getItem(position);
            if ( data != null ) {
                holder.picker_item.setText(data);
                holder.picker_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ( mOnPickerItemSelectedListener != null ) {
                            mOnPickerItemSelectedListener.OnPickerItemSelected(v, position, stringResIds == null ? -1 : stringResIds[position]);
                        }
                        dismiss();
                    }
                });
            }
            return convertView;
        }
    }

    private static final class ViewHolder {
        TextView picker_item;
    }

    private void setItems(int[] items) {
        stringResIds = items;
        this.items = new ArrayList<>();
        for (int item : items ) this.items.add(getContext().getString(item));
    }

    private void setOnPickerItemSelectedListener(OnPickerItemSelectedListener listener) {
        mOnPickerItemSelectedListener = listener;
    }

    private void setTitleRes(@StringRes int titleId) {
        this.titleId = titleId;
    }

    public interface OnPickerItemSelectedListener {
        /**
         * callback when user click specific item.
         *
         * @param view : clicked text view
         * @param position : clicked position
         * @param stringResId : if user set string list with R.string.ids then return clicked string id. otherwise return -1.
         */
        void OnPickerItemSelected(View view, int position, int stringResId);
    }

    public static class Builder {
        private PickerDialog dialog = null;

        public Builder(Context context, int[] stringResIds) {
            dialog = new PickerDialog(context, stringResIds);
        }

        public Builder setOnPickerItemSelectedListener(OnPickerItemSelectedListener listener) {
            dialog.setOnPickerItemSelectedListener(listener);
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener listener) {
            dialog.setOnDismissListener(listener);
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            dialog.setCancelable(cancelable);
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean cancel) {
            dialog.setCanceledOnTouchOutside(cancel);
            return this;
        }

        public Builder setTitleRes(@StringRes int titleId) {
            dialog.setTitleRes(titleId);
            return this;
        }

        public PickerDialog create() {
            return dialog;
        }

        public PickerDialog show() {
            dialog.show();
            return dialog;
        }
    }
}
