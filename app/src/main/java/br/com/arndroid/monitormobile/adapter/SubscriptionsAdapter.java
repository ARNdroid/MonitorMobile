package br.com.arndroid.monitormobile.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import br.com.arndroid.monitormobile.R;
import br.com.arndroid.monitormobile.provider.Contract;
import br.com.arndroid.monitormobile.utils.SubscriptionsUtils;

public class SubscriptionsAdapter extends CursorAdapter {

    public SubscriptionsAdapter(Context context) {
        super(context, null, false);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.subscriptions_list_item, viewGroup, false);

        ViewHolder holder = new ViewHolder((TextView) view.findViewById(R.id.txtAcronymId),
                (TextView) view.findViewById(R.id.txtModeType));

        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.acronymId.setText(cursor.getString(
                cursor.getColumnIndex(Contract.Subscriptions.ACRONYM_ID)));
        holder.modeType.setText(SubscriptionsUtils.modeDescriptionFromModeType(
                cursor.getInt(cursor.getColumnIndex(Contract.Subscriptions.MODE_TYPE))));
    }

    private static class ViewHolder {

        public final TextView acronymId;
        public final TextView modeType;

        public ViewHolder(TextView acronymId, TextView modeType) {
            this.acronymId = acronymId;
            this.modeType = modeType;
        }
    }
}
