package com.lendingkart.instant.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.lendingkart.instant.R;


public abstract class BaseDialogFragment extends DialogFragment {

    public abstract int getLayoutId();

    public abstract void initViews(Dialog dialog);

    protected IFragmentInteractionListener mFragmentInteractionListener;

    //works well with support fragments
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity;

        if (context instanceof Activity) {
            activity = (Activity) context;
            try {
                mFragmentInteractionListener = (IFragmentInteractionListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = initDialog();
        initViews(dialog);
        return dialog;
    }

    private Dialog initDialog() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
        try {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            dialog.setContentView(getLayoutId());
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (NullPointerException e) {
            Toast.makeText(getActivity(), "No Dialog Found", Toast.LENGTH_SHORT).show();
        }
        return dialog;
    }
}
