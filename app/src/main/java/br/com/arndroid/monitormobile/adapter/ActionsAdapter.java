package br.com.arndroid.monitormobile.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.arndroid.monitormobile.R;
import br.com.arndroid.monitormobile.provider.actions.ActionsEntity;
import br.com.arndroid.monitormobile.provider.comments.CommentsManager;
import br.com.arndroid.monitormobile.provider.users.UsersManager;
import br.com.arndroid.monitormobile.provider.xs.XsManager;
import br.com.arndroid.monitormobile.utils.TimeStampUtils;

public class ActionsAdapter extends CursorAdapter {

    private final CommentsManager mCommentsManager;
    private final XsManager mXsManager;
    private final AlertDialog.Builder mBuilder;
    private UsersManager mUsersManager;

    public ActionsAdapter(Context context) {
        super(context, null, false);
        mUsersManager = new UsersManager(context);
        mCommentsManager = new CommentsManager(context);
        mXsManager = new XsManager(context);
        mBuilder = new AlertDialog.Builder(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.action_list_item, viewGroup, false);

        ViewHolder holder = new ViewHolder(
                (TextView) view.findViewById(R.id.txtDate),
                (TextView) view.findViewById(R.id.txtSummary),
                (TextView) view.findViewById(R.id.txtDescription),
                (TextView) view.findViewById(R.id.txtAgent),
                (ImageView) view.findViewById(R.id.imgXs),
                (TextView) view.findViewById(R.id.txtXsCount),
                (ImageView) view.findViewById(R.id.imgComments),
                (TextView) view.findViewById(R.id.txtCommentsCount));

        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        final ActionsEntity entity = ActionsEntity.fromCursor(cursor);
        holder.txtDate.setText(TimeStampUtils.timeStampToFormattedString(entity.getTimeStamp()));
        holder.txtSummary.setText(entity.getSummary());
        holder.txtDescription.setText(entity.getDescription());
        holder.txtAgent.setText("por " + mUsersManager.userFromId(entity.getAgentId()).getShortName());
        final Long currentUserId = mUsersManager.getCurrentUser().getId();
        holder.imgXs.setImageResource(mXsManager.userHasXsForAction(currentUserId, entity.getId()) ? R.drawable.plus_x_color : R.drawable.plus_x_bw);
        holder.imgXs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXsManager.toggleXsForActionAndUser(entity.getId(), currentUserId);
            }
        });
        holder.txtXsCount.setText(String.valueOf(mXsManager.totalXsForAction(entity.getId())));
        holder.imgComments.setImageResource(mCommentsManager.userHasCommentsForAction(currentUserId, entity.getId()) ? R.drawable.comment_color : R.drawable.comment_bw);
        holder.imgComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuilder.setMessage("Estamos trabalhando para disponbilizar esta funcionalidade nas próximas versões! Por favor, aguardem cenas do próximo episódio...")
                        .setTitle("Em construção")
                        .setPositiveButton("Mesmo ansioso compreendo", null);
                mBuilder.create().show();
            }
        });
        holder.txtCommentsCount.setText(String.valueOf(mCommentsManager.totalCommentsForAction(entity.getId())));

    }

    private static class ViewHolder {

        public final TextView txtDate;
        public final TextView txtSummary;
        public final TextView txtDescription;
        public final TextView txtAgent;
        public final ImageView imgXs;
        public final TextView txtXsCount;
        public final ImageView imgComments;
        public final TextView txtCommentsCount;

        private ViewHolder(TextView txtDate, TextView txtSummary, TextView txtDescription, TextView txtAgent, ImageView imgXs,
                           TextView txtXsCount, ImageView imgComments, TextView txtCommentsCount) {
            this.txtDate = txtDate;
            this.txtSummary = txtSummary;
            this.txtDescription = txtDescription;
            this.txtAgent = txtAgent;
            this.imgXs = imgXs;
            this.txtXsCount = txtXsCount;
            this.imgComments = imgComments;
            this.txtCommentsCount = txtCommentsCount;
        }
    }
}