package br.com.arndroid.monitormobile.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.arndroid.monitormobile.R;
import br.com.arndroid.monitormobile.provider.followers.FollowersManager;
import br.com.arndroid.monitormobile.provider.issues.IssuesEntity;
import br.com.arndroid.monitormobile.provider.users.UsersManager;
import br.com.arndroid.monitormobile.utils.DashboardUtils;
import br.com.arndroid.monitormobile.utils.TimeStampUtils;

public class IssuesAdapter extends CursorAdapter {

    UsersManager mUsersManager;
    FollowersManager mFollowersManager;

    public IssuesAdapter(Context context) {
        super(context, null, false);
        mUsersManager = new UsersManager(context);
        mFollowersManager = new FollowersManager(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.issues_list_item, viewGroup, false);

        ViewHolder holder = new ViewHolder(
                (ImageView) view.findViewById(R.id.imgFlagAndClockType),
                (TextView) view.findViewById(R.id.txtSummary),
                (TextView) view.findViewById(R.id.txtOwner),
                (TextView) view.findViewById(R.id.txtFollowers),
                (TextView) view.findViewById(R.id.txtLastAction));

        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        final IssuesEntity entity = IssuesEntity.fromCursor(cursor);
        holder.imgFlagAndClockType.setImageResource(
                DashboardUtils.getImageResourceIdForStateFlagTypeAndClockType(entity.getState(),
                        entity.getFlagType(), entity.getClockType(), true));
        holder.txtSummary.setText(entity.getSummary());
        holder.txtOwner.setText(entity.getOwnerId() == null ?
                "(não atribuído)" : mUsersManager.userFromId(entity.getOwnerId()).getShortName());
        holder.txtFollowers.setText(mFollowersManager.humanPhraseFromIssueId(entity.getId(), mUsersManager.getCurrentUser().getId()));
        holder.txtLastAction.setText(TimeStampUtils.timeStampToFormattedString(entity.getTimeStamp()));
    }

    private static class ViewHolder {

        public final ImageView imgFlagAndClockType;
        public final TextView txtSummary;
        public final TextView txtOwner;
        public final TextView txtFollowers;
        public final TextView txtLastAction;

        private ViewHolder(ImageView imgFlagAndClockType, TextView txtSummary, TextView txtOwner,
                           TextView txtFollowers, TextView txtLastAction) {
            this.imgFlagAndClockType = imgFlagAndClockType;
            this.txtSummary = txtSummary;
            this.txtOwner = txtOwner;
            this.txtFollowers = txtFollowers;
            this.txtLastAction = txtLastAction;
        }
    }
}