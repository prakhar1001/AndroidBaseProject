package com.lendingkart.instant.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lendingkart.instant.R;
import com.lendingkart.instant.utils.Utils;


public class AlertDialogFragment extends BaseDialogFragment {

    private static final String TITLE = "title";
    private static final String MESSAGE = "message";
    private static final String POSITIVE_ACTION_BTN_TEXT = "positive";
    private static final String NEGATIVE_ACTION_BTN_TEXT = "negative";

    private LinearLayout mAlertTitleLayout;
    private TextView mAlertTitle;
    private TextView mAlertMessage;
    private TextView mAlertAbovePositiveAction;
    private TextView mAlertNegativeAction;
    private TextView mAlertPositiveAction;
    private View mAlertActionBtnVerticalDivider;
    private View mAlertAtionBtnHorizontalDivider;

    private AlertPositiveActionListener mAlertPositiveActionListener;
    private AlertNegativeActionListener mAlertNegativeActionListener;

    public interface AlertPositiveActionListener {
        void onPositiveAction();
    }

    public interface AlertNegativeActionListener {
        void onNegativeAction();
    }

    public static AlertDialogFragment create(String title, String message, String positveBtnText,
                                             String negativeBtnText,
                                             AlertPositiveActionListener alertPositiveActionListener,
                                             AlertNegativeActionListener alertNegativeActionListener) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(MESSAGE, message);
        bundle.putString(POSITIVE_ACTION_BTN_TEXT, positveBtnText);
        bundle.putString(NEGATIVE_ACTION_BTN_TEXT, negativeBtnText);

        AlertDialogFragment alertFragment = new AlertDialogFragment();
        alertFragment.setArguments(bundle);

        alertFragment.setPositiveActionListener(alertPositiveActionListener);
        alertFragment.setNegaticeActionListener(alertNegativeActionListener);

        return alertFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_alert_dialog;
    }

    @Override
    public void initViews(Dialog view) {
        mAlertTitleLayout = (LinearLayout) view.findViewById(R.id.alert_title_layout);
        mAlertTitle = (TextView) view.findViewById(R.id.alert_title);
        mAlertMessage = (TextView) view.findViewById(R.id.alert_message);
        mAlertAbovePositiveAction = (TextView) view.findViewById(R.id.alert_above_positive_action);
        mAlertNegativeAction = (TextView) view.findViewById(R.id.alert_negative_action);
        mAlertPositiveAction = (TextView) view.findViewById(R.id.alert_positive_action);
        mAlertActionBtnVerticalDivider = view.findViewById(R.id.alert_btn_verical_divider);
        mAlertAtionBtnHorizontalDivider = view.findViewById(R.id.alert_btn_horizontal_divider);
        setCardViewProperty(view);
        setValues();
    }

    private void setCardViewProperty(Dialog view) {
        CardView cardView = (CardView) view.findViewById(R.id.alert_layout_card);
        if (Utils.isAPILevel21AndAbove()) {
            cardView.setPreventCornerOverlap(false);
        } else {
            cardView.setPreventCornerOverlap(true);
        }
    }

    private void setValues() {
        Bundle dataBundle = getArguments();
        if (dataBundle != null) {
            setTitle(dataBundle.getString(TITLE));
            setMessage(dataBundle.getString(MESSAGE));
            setActions(dataBundle.getString(POSITIVE_ACTION_BTN_TEXT), dataBundle.getString(NEGATIVE_ACTION_BTN_TEXT));
        } else {
            dismiss();
        }

    }

    public void setPositiveActionListener(AlertPositiveActionListener alertActionListener) {
        mAlertPositiveActionListener = alertActionListener;
    }

    public void setNegaticeActionListener(AlertNegativeActionListener alertActionListener) {
        mAlertNegativeActionListener = alertActionListener;
    }

    private void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mAlertTitleLayout.setVisibility(View.VISIBLE);
            mAlertTitle.setText(title);
        }
    }

    private void setMessage(String message) {
        mAlertMessage.setText(message);
    }

    private void setActions(String positivetext, String negetiveText) {
        if (!TextUtils.isEmpty(positivetext) && !TextUtils.isEmpty(negetiveText)) {
            if (positivetext.length() > 10) {
                mAlertAbovePositiveAction.setVisibility(View.VISIBLE);
                mAlertAbovePositiveAction.setText(positivetext);
                mAlertAbovePositiveAction.setOnClickListener(getPositiveOnclickListener());
                mAlertActionBtnVerticalDivider.setVisibility(View.VISIBLE);
            } else {
                mAlertPositiveAction.setVisibility(View.VISIBLE);
                mAlertPositiveAction.setText(positivetext);
                mAlertPositiveAction.setOnClickListener(getPositiveOnclickListener());
                mAlertAtionBtnHorizontalDivider.setVisibility(View.VISIBLE);
            }

            mAlertNegativeAction.setVisibility(View.VISIBLE);
            mAlertNegativeAction.setText(negetiveText);
            mAlertNegativeAction.setOnClickListener(getNegativeOnClickListener());

        } else if (!TextUtils.isEmpty(positivetext)) {
            mAlertPositiveAction.setVisibility(View.VISIBLE);
            mAlertPositiveAction.setText(positivetext);
            mAlertPositiveAction.setOnClickListener(getPositiveOnclickListener());
        } else {
            mAlertNegativeAction.setVisibility(View.VISIBLE);
            mAlertNegativeAction.setText(negetiveText);
            mAlertNegativeAction.setOnClickListener(getNegativeOnClickListener());
        }
    }

    private View.OnClickListener getNegativeOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mAlertNegativeActionListener != null) {
                    mAlertNegativeActionListener.onNegativeAction();
                }
            }
        };
    }

    private View.OnClickListener getPositiveOnclickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mAlertPositiveActionListener != null) {
                    mAlertPositiveActionListener.onPositiveAction();
                }
            }
        };
    }
}
