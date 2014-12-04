package br.com.arndroid.monitormobile.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import br.com.arndroid.monitormobile.R;

public class TextDialog extends DialogFragment {
    /**
     * The callback used to indicate the user is done filling in the text.
     */
    public interface OnTextSetListener {
        void onTextSet(String tag, String actualText);
    }

    private static final String TITLE_KEY = "TITLE_KEY";
    private static final String INITIAL_KEY = "INITIAL_KEY";
    private static final String ACTUAL_KEY = "ACTUAL_KEY";
    private static final String HINT_KEY = "HINT_KEY";

    private String mTitle;
    private String mInitialText;
    private EditText mEdtText;
    private String mHint;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // We don't have access to root ViewGroup here:
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.text_dialog, null);
        builder.setView(view);

        bindScreen(view);

        String actualText = getInitialText();
        if (savedInstanceState != null) {
            setTitle(savedInstanceState.getString(TITLE_KEY));
            setInitialText(savedInstanceState.getString(INITIAL_KEY));
            actualText = savedInstanceState.getString(ACTUAL_KEY);
            mHint = savedInstanceState.getString(HINT_KEY);
        }

        refreshScreen(actualText);

        builder.setTitle(getTitle());
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                final String userText = mEdtText.getText().toString().trim();
                if (!TextUtils.isEmpty(userText)) {
                    ((OnTextSetListener)getActivity()).onTextSet(TextDialog.this.getTag(),
                            mEdtText.getText().toString());
                    dialog.dismiss();
                } else {
                    dialog.cancel();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        return builder.create();
    }

    private void refreshScreen(String actualText) {
        mEdtText.setHint(mHint);
        mEdtText.setText(actualText);
    }

    private void bindScreen(View rootView) {
        mEdtText = (EditText) rootView.findViewById(R.id.edtText);
    }

    @Override
    public void onSaveInstanceState(@SuppressWarnings("NullableProblems") Bundle outState) {
        outState.putString(TITLE_KEY, getTitle());
        outState.putString(INITIAL_KEY, getInitialText());
        outState.putString(ACTUAL_KEY, mEdtText.getText().toString());
        outState.putString(HINT_KEY, mHint);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Activity activity) {
        /* We didn't use a callback like:
             OnPointListener mListener
           because we couldn't save the listener with Bundle.setXxx to restore the dialog after,
           for example, a screen rotation.
           Due to it, the attached activity must implement the interface.
         */
        super.onAttach(activity);
        if (!(activity instanceof OnTextSetListener)) {
            throw new ClassCastException(activity.toString() +
                    " must implement TextDialog.OnTextSetListener");
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getInitialText() {
        return mInitialText;
    }

    public void setInitialText(String currentText) {
        mInitialText = currentText;
    }

    public void setHint(String hint) {
        mHint = hint;
    }
}