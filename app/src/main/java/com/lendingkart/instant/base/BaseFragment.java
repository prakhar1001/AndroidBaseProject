package com.lendingkart.instant.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.lendingkart.instant.R;


public abstract class BaseFragment extends Fragment {

    public enum FragmentTransactionType {
        ADD,
        REPLACE,
        ADD_TO_BACK_STACK_AND_REPLACE,
        POP_BACK_STACK_AND_REPLACE,
        CLEAR_BACK_STACK_AND_REPLACE
    }

    protected IFragmentInteractionListener mFragmentInteractionListener;
    protected IToolbarInteractionListener mToolbarInteractionListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFragmentInteractionListener = (IFragmentInteractionListener) context;
            mFragmentInteractionListener.isToolBarRequired(true);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        try {
            mToolbarInteractionListener = (IToolbarInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement mToolbarInteractionListener");
        }
    }

    final protected void hide_keyboard(@NonNull Context context, @NonNull View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected void closeFragment() {
        getActivity().onBackPressed();
    }


    protected void showAlert(String alertMessage) {
        mFragmentInteractionListener.showAlert(null, alertMessage, getString(R.string.ok), null, new AlertDialogFragment.AlertPositiveActionListener() {
            @Override
            public void onPositiveAction() {

            }
        }, null, true);
    }
}
