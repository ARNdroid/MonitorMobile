package br.com.arndroid.monitormobile.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.arndroid.monitormobile.R;
import br.com.arndroid.monitormobile.utils.DashboardUtils;
import br.com.arndroid.monitormobile.utils.IssueUtils;

public class DashboardAdapter extends BaseAdapter {

    private static final boolean DEBUG_MODE = false;

    private final Context mContext;
    private DashboardPanel mPanel = null;
    private int mDashboardType;

    public DashboardAdapter(Context context) {
        mContext = context;
    }

    public DashboardPanel swapDashboardPanel(DashboardPanel newPanel) {
        // This method mimics the CursorAdapter.swapCursor() behavior

        if (mPanel == newPanel) {
            return null;
        }

        final DashboardPanel oldPanel = mPanel;

        mPanel = newPanel;
        if (newPanel != null) {
            notifyDataSetChanged();
        } else {
            notifyDataSetInvalidated();
        }

        return oldPanel;
    }

    public void setDashboardType(int dashboardType) {
        mDashboardType = dashboardType;
        if (mPanel == null) {
            notifyDataSetInvalidated();
        } else {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mPanel != null ? mPanel.getAcronymIdCount() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mPanel != null ? mPanel.getDashboardItemForPosition(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            view = newView(parent);
        } else {
            view = convertView;
        }
        bindView(view, position);

        return view;
    }

    public View newView(ViewGroup viewGroup) {
        LayoutInflater inflater =  (LayoutInflater) mContext.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dashboard_list_item, viewGroup, false);

        ViewHolder holder = new ViewHolder((TextView) view.findViewById(R.id.txtAcronymId), (TextView) view.findViewById(R.id.txtDescription), (TextView) view.findViewById(R.id.txtFlags),
                view.findViewById(R.id.layRowZero), view.findViewById(R.id.layRowOne), view.findViewById(R.id.layRowTwo), view.findViewById(R.id.layRowThree),
                (ImageView) view.findViewById(R.id.imgColZero), (TextView) view.findViewById(R.id.txtColZero),
                (ImageView) view.findViewById(R.id.imgColOne), (TextView) view.findViewById(R.id.txtColOne),
                (ImageView) view.findViewById(R.id.imgColTwo), (TextView) view.findViewById(R.id.txtColTwo),
                (ImageView) view.findViewById(R.id.imgColThree), (TextView) view.findViewById(R.id.txtColThree),
                (ImageView) view.findViewById(R.id.imgColFour), (TextView) view.findViewById(R.id.txtColFour),
                (ImageView) view.findViewById(R.id.imgColFive), (TextView) view.findViewById(R.id.txtColFive),
                (ImageView) view.findViewById(R.id.imgColSix), (TextView) view.findViewById(R.id.txtColSix),
                (ImageView) view.findViewById(R.id.imgColSeven), (TextView) view.findViewById(R.id.txtColSeven),
                (ImageView) view.findViewById(R.id.imgColEight), (TextView) view.findViewById(R.id.txtColEight),
                (ImageView) view.findViewById(R.id.imgColNine), (TextView) view.findViewById(R.id.txtColNine),
                (ImageView) view.findViewById(R.id.imgColTen), (TextView) view.findViewById(R.id.txtColTen),
                (ImageView) view.findViewById(R.id.imgColEleven), (TextView) view.findViewById(R.id.txtColEleven),
                (ImageView) view.findViewById(R.id.imgColTwelve), (TextView) view.findViewById(R.id.txtColTwelve),
                (ImageView) view.findViewById(R.id.imgColThirteen), (TextView) view.findViewById(R.id.txtColThirteen),
                (ImageView) view.findViewById(R.id.imgColFourteen), (TextView) view.findViewById(R.id.txtColFourteen),
                (ImageView) view.findViewById(R.id.imgColFifteen), (TextView) view.findViewById(R.id.txtColFifteen),
                (ImageView) view.findViewById(R.id.imgColSixteen), (TextView) view.findViewById(R.id.txtColSixteen));

        view.setTag(holder);

        return view;
    }

    public void bindView(View view, int position) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.acronymId.setText(mPanel != null ? mPanel.getAcronymIdForPosition(position) : null);
        holder.description.setText(mPanel != null ? mPanel.getDescriptionForPosition(position) : null);
        if (DEBUG_MODE) {
            holder.plainText.setVisibility(View.VISIBLE);
            holder.plainText.setText(mPanel != null ? mPanel.getDashboardItemForPosition(position)
                    .getPlainTextForDashboardType(mDashboardType) : null);
        } else {
            holder.plainText.setVisibility(View.GONE);
        }
        if (mPanel != null) {
            holder.setupRowsForDashboardItemAndType(mPanel.getDashboardItemForPosition(position), mDashboardType);
        }
    }

    private static class ViewHolder {

        private static final int TOTAL_ROWS = 4;
        private final int TOTAL_RESOURCES = IssueUtils.TOTAL_FLAGS * IssueUtils.TOTAL_CLOCKS + 1;

        public final TextView acronymId;
        public final TextView description;
        public final ImageView[] imageArray = new ImageView[TOTAL_RESOURCES];
        public final TextView[] totalArray = new TextView[TOTAL_RESOURCES];
        public final TextView plainText;
        public final View[] layoutArray = new View[TOTAL_ROWS];

        public ViewHolder(TextView acronymId, TextView description, TextView modeType,
                          View layoutZero, View layoutOne, View layoutTwo, View layoutThree,
                          ImageView imageZero, TextView totalZero,
                          ImageView imageOne, TextView totalOne,
                          ImageView imageTwo, TextView totalTwo,
                          ImageView imageThree, TextView totalThree,
                          ImageView imageFour, TextView totalFour,
                          ImageView imageFive, TextView totalFive,
                          ImageView imageSix, TextView totalSix,
                          ImageView imageSeven, TextView totalSeven,
                          ImageView imageEight, TextView totalEight,
                          ImageView imageNine, TextView totalNine,
                          ImageView imageTen, TextView totalTen,
                          ImageView imageEleven, TextView totalEleven,
                          ImageView imageTwelve, TextView totalTwelve,
                          ImageView imageThirteen, TextView totalThirteen,
                          ImageView imageFourteen, TextView totalFourteen,
                          ImageView imageFifteen, TextView totalFifteen,
                          ImageView imageSixteen, TextView totalSixteen) {

            this.acronymId = acronymId;
            this.description = description;
            this.plainText = modeType;
            this.layoutArray[0] = layoutZero;
            this.layoutArray[1] = layoutOne;
            this.layoutArray[2] = layoutTwo;
            this.layoutArray[3] = layoutThree;
            this.imageArray[0] = imageZero; this.totalArray[0] = totalZero;
            this.imageArray[1] = imageOne; this.totalArray[1] = totalOne;
            this.imageArray[2] = imageTwo; this.totalArray[2] = totalTwo;
            this.imageArray[3] = imageThree; this.totalArray[3] = totalThree;
            this.imageArray[4] = imageFour; this.totalArray[4] = totalFour;
            this.imageArray[5] = imageFive; this.totalArray[5] = totalFive;
            this.imageArray[6] = imageSix; this.totalArray[6] = totalSix;
            this.imageArray[7] = imageSeven; this.totalArray[7] = totalSeven;
            this.imageArray[8] = imageEight; this.totalArray[8] = totalEight;
            this.imageArray[9] = imageNine; this.totalArray[9] = totalNine;
            this.imageArray[10] = imageTen; this.totalArray[10] = totalTen;
            this.imageArray[11] = imageEleven; this.totalArray[11] = totalEleven;
            this.imageArray[12] = imageTwelve; this.totalArray[12] = totalTwelve;
            this.imageArray[13] = imageThirteen; this.totalArray[13] = totalThirteen;
            this.imageArray[14] = imageFourteen; this.totalArray[14] = totalFourteen;
            this.imageArray[15] = imageFifteen; this.totalArray[15] = totalFifteen;
            this.imageArray[16] = imageSixteen; this.totalArray[16] = totalSixteen;
        }

        public void setupRowsForDashboardItemAndType(DashboardItem dashboardItem, int dashboardType) {
            int currentPosition = 0;

            if (dashboardType == DashboardUtils.DASHBOARD_TYPE_FLAG) {
                for (int flag = 0; flag < IssueUtils.TOTAL_FLAGS; flag++) {
                    final int count = dashboardItem.getFlagTypeCount(flag);
                    if (count > 0) {
                        imageArray[currentPosition].setVisibility(View.VISIBLE);
                        totalArray[currentPosition].setVisibility(View.VISIBLE);
                        imageArray[currentPosition].setImageResource(DashboardUtils.getImageResourceIdForFlagType(flag));
                        totalArray[currentPosition].setText(String.valueOf(count));
                        currentPosition++;
                    }
                }
            } else if (dashboardType == DashboardUtils.DASHBOARD_TYPE_CLOCK) {
                for (int clock = 0; clock < IssueUtils.TOTAL_CLOCKS; clock++) {
                    final int count = dashboardItem.getClockTypeCount(clock);
                    if (count > 0) {
                        imageArray[currentPosition].setVisibility(View.VISIBLE);
                        totalArray[currentPosition].setVisibility(View.VISIBLE);
                        imageArray[currentPosition].setImageResource(DashboardUtils.getImageResourceIdForClockType(clock));
                        totalArray[currentPosition].setText(String.valueOf(count));
                        currentPosition++;
                    }
                }
            } else {
                for (int flag = 0; flag < IssueUtils.TOTAL_FLAGS; flag++) {
                    for (int clock = 0; clock < IssueUtils.TOTAL_CLOCKS; clock++) {
                        final int count = dashboardItem.flagAndClockType[flag][clock];
                        if (count > 0) {
                            imageArray[currentPosition].setVisibility(View.VISIBLE);
                            totalArray[currentPosition].setVisibility(View.VISIBLE);
                            imageArray[currentPosition].setImageResource(DashboardUtils.getImageResourceIdForFlagTypeAndClockType(flag, clock));
                            totalArray[currentPosition].setText(String.valueOf(count));
                            currentPosition++;
                        }
                    }
                }
            }

            if (dashboardItem.closed > 0) {
                imageArray[currentPosition].setVisibility(View.VISIBLE);
                totalArray[currentPosition].setVisibility(View.VISIBLE);
                imageArray[currentPosition].setImageResource(DashboardUtils.getImageResourceIdForClosed());
                totalArray[currentPosition].setText(String.valueOf(dashboardItem.closed));
                currentPosition++;
            }

            // Setting not used views == GONE.
            for (int i = currentPosition; i < TOTAL_RESOURCES; i++) {
                imageArray[i].setVisibility(View.GONE);
                totalArray[i].setVisibility(View.GONE);
            }
            for (int i = 1; i < TOTAL_ROWS; i++) {
                layoutArray[i].setVisibility(View.VISIBLE);
            }
            if (currentPosition <= 5) layoutArray[1].setVisibility(View.GONE);
            if (currentPosition <= 10) layoutArray[2].setVisibility(View.GONE);
            if (currentPosition <= 15) layoutArray[3].setVisibility(View.GONE);
        }
    }
}