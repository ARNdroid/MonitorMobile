package br.com.arndroid.monitormobile.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import br.com.arndroid.monitormobile.R;
import br.com.arndroid.monitormobile.provider.comments.CommentsEntity;
import br.com.arndroid.monitormobile.provider.users.UsersManager;
import br.com.arndroid.monitormobile.utils.TimeStampUtils;

public class CommentsAdapter extends CursorAdapter {

    private UsersManager mUsersManager;

    public CommentsAdapter(Context context) {
        super(context, null, false);
        mUsersManager = new UsersManager(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.comments_list_item, viewGroup, false);

        ViewHolder holder = new ViewHolder((TextView) view.findViewById(R.id.txtDescription),
                (TextView) view.findViewById(R.id.txtCommenter));

        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final CommentsEntity entity = CommentsEntity.fromCursor(cursor);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.description.setText(entity.getDescription());
        final String commenterString = "por " + mUsersManager.userFromId(entity.getCommenterId()).getShortName()
                + " em " + TimeStampUtils.timeStampToFormattedString(entity.getTimeStamp());
        holder.commenter.setText(commenterString);
    }

    private static class ViewHolder {

        public final TextView description;
        public final TextView commenter;

        public ViewHolder(TextView description, TextView commenter) {
            this.description = description;
            this.commenter = commenter;
        }
    }
}