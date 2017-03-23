package com.lendingkart.instant.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lendingkart.instant.R;
import com.lendingkart.instant.utils.Utils;


public abstract class BaseActivity extends AppCompatActivity implements IFragmentInteractionListener,
        IToolbarInteractionListener {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private boolean mIsPaused = false;

    private LinearLayout mHeaderLayoutContainer;

    private LinearLayout mBlockingProgressBar;

    private ImageView mHeaderLeftImageView;
    private TextView mHeaderTextView;
    private ImageView mHeaderRightImageView;



    private View.OnTouchListener mProgressbarTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    };

    protected abstract int getHeaderLayoutId();

    protected abstract void initHeaderView(View view);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lendingkart_base_layout);
        initViews();
    }

    protected void addContentView(int layoutId) {
        FrameLayout layoutContainer = (FrameLayout) findViewById(R.id.layout_container);
        View view = LayoutInflater.from(this).inflate(layoutId, layoutContainer, false);
        initAddedContentView(view);
        layoutContainer.addView(view);
    }

    protected void initAddedContentView(View view) {

    }

    private void initViews() {
        mHeaderLayoutContainer = (LinearLayout) findViewById(R.id.header_layout_container);
        if (getHeaderLayoutId() != 0) {
            View view = LayoutInflater.from(this).inflate(getHeaderLayoutId(), mHeaderLayoutContainer, false);
            initHeaderView(view);
            mHeaderLayoutContainer.addView(view);
        }

        mBlockingProgressBar = (LinearLayout) findViewById(R.id.progressbar_layout);
    }

    // A method to find height of the status bar
    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void updateToolbar(IToolbar toolbar) {
        setHeadToolBarValues(toolbar.getHeaderLeftIconId(), toolbar.getToolbarTitleId(), toolbar.getHeaderRightIconId(), toolbar.getToolBarBackgroundColor());
        setHeaderToolBarViewClickListeners(toolbar.getHeaderViewClickListener());
    }


    protected void setHeaderToolBarViewClickListeners(View.OnClickListener headerViewClickListener) {
        if (headerViewClickListener != null) {
            mHeaderRightImageView.setOnClickListener(headerViewClickListener);
            mHeaderLeftImageView.setOnClickListener(headerViewClickListener);
        }
    }

    protected final void setHeadToolBarValues(int headerLeftIconId, String toolbarTitle, int headerRightIconId, int backgroundColor) {
        if (mHeaderLayoutContainer != null) {
            if (mHeaderLeftImageView != null) {
                if (headerLeftIconId != 0) {
                    mHeaderLeftImageView.setVisibility(View.VISIBLE);
                    mHeaderLeftImageView.setImageResource(headerLeftIconId);
                } else {
                    mHeaderLeftImageView.setVisibility(View.GONE);
                }
            }

            if (mHeaderTextView != null) {
                if (TextUtils.isEmpty(toolbarTitle)) {
                    mHeaderTextView.setText(R.string.app_name);
                } else {
                    mHeaderTextView.setText(toolbarTitle);
                }
            }

            if (mHeaderRightImageView != null) {
                if (headerRightIconId != 0) {
                    mHeaderRightImageView.setVisibility(View.VISIBLE);
                    mHeaderRightImageView.setImageResource(headerRightIconId);
                } else {
                    mHeaderRightImageView.setVisibility(View.GONE);
                }
            }

            if (backgroundColor != 0) {
                mHeaderLayoutContainer.setBackgroundColor(backgroundColor);
                mHeaderTextView.setTextColor(Color.BLACK);
            } else {
                if (mHeaderTextView != null) {
                    mHeaderTextView.setTextColor(Color.WHITE);
                }
                mHeaderLayoutContainer.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            }
        }
    }

    protected int getFragmentContainerId() {
        return R.id.layout_container;
    }

    @Override
    public void loadFragment(int fragmentContainerId, BaseFragment fragment, @Nullable String tag,
                             int enterAnimId, int exitAnimId,
                             BaseFragment.FragmentTransactionType fragmentTransactionType) {

        performFragmentTransaction(fragmentContainerId, fragment, tag,
                (enterAnimId == 0) ? 0 : enterAnimId,
                (exitAnimId == 0) ? 0 : exitAnimId,
                fragmentTransactionType);
    }

    @Override
    public void loadDialogFragment(DialogFragment fragment, @Nullable String tag) {
        fragment.show(getSupportFragmentManager(), tag);
    }

    @Override
    public void showBlockingProgressBar() {
        if (mBlockingProgressBar != null) {
            mBlockingProgressBar.setOnTouchListener(mProgressbarTouchListener);
            mBlockingProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideBlockingProgressBar() {
        if (mBlockingProgressBar != null) {
            mBlockingProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showAlert(String title, String message, String positveBtnText,
                          String negativeBtnText,
                          AlertDialogFragment.AlertPositiveActionListener alertPositiveActionListener,
                          AlertDialogFragment.AlertNegativeActionListener alertNegativeActionListener,
                          boolean isCancelable) {
        if (mIsPaused) return;
        AlertDialogFragment alertDialogFragment = AlertDialogFragment.create(title, message,
                positveBtnText, negativeBtnText,
                alertPositiveActionListener, alertNegativeActionListener);
        alertDialogFragment.setCancelable(isCancelable);
        alertDialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsPaused = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsPaused = true;
    }


    private void performFragmentTransaction(int fragmentContainerId,
                                            Fragment fragment, String tag,
                                            int enterAnimId, int exitAnimId,
                                            BaseFragment.FragmentTransactionType fragmentTransactionType) {
        switch (fragmentTransactionType) {
            case ADD:
                addFragment(fragmentContainerId, fragment, tag, enterAnimId, exitAnimId);
                break;
            case REPLACE:
                replaceFragment(fragmentContainerId, fragment, tag, enterAnimId, exitAnimId);
                break;
            case ADD_TO_BACK_STACK_AND_REPLACE:
                addToBackStackAndReplace(fragmentContainerId, fragment, tag, enterAnimId, exitAnimId);
                break;
            case POP_BACK_STACK_AND_REPLACE:
                popBackStackAndReplace(fragmentContainerId, fragment, tag, enterAnimId, exitAnimId);
                break;
            case CLEAR_BACK_STACK_AND_REPLACE:
                clearBackStackAndReplace(fragmentContainerId, fragment, tag, enterAnimId, exitAnimId);
                break;
            default:
                replaceFragment(fragmentContainerId, fragment, tag, enterAnimId, exitAnimId);
        }

    }


    protected void addFragment(int fragmentContainerId, Fragment fragment, String tag, int enterAnimId, int exitAnimId) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(enterAnimId, exitAnimId)
                .add(fragmentContainerId, fragment, tag)
                .commit();
    }

    private void replaceFragment(int fragmentContainerId, Fragment fragment, @Nullable String tag,
                                 int enterAnimId, int exitAnimId) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(enterAnimId, exitAnimId)
                .replace(fragmentContainerId, fragment, tag).commit();
    }

    private void popBackStackAndReplace(int fragmentContainerId, Fragment fragment,
                                        @Nullable String tag, int enterAnimId, int exitAnimId) {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(enterAnimId, exitAnimId)
                .replace(fragmentContainerId, fragment, tag).commit();
    }

    private void addToBackStackAndReplace(int fragmentContainerId, Fragment fragment,
                                          @Nullable String tag, int enterAnimId, int exitAnimId) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(enterAnimId, 0, 0, exitAnimId)
                .replace(fragmentContainerId, fragment, tag)
                .commit();
    }

    private void clearBackStackAndReplace(int fragmentContainerId, Fragment fragment,
                                          @Nullable String tag, int enterAnimId, int exitAnimId) {
        clearBackStack(FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(enterAnimId, exitAnimId)
                .replace(fragmentContainerId, fragment, tag).commit();
    }

    private void clearBackStack(int flag) {
        FragmentManager fm = getSupportFragmentManager();

        if (fm.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = fm.getBackStackEntryAt(0);
            fm.popBackStack(first.getId(), flag);
            fm.executePendingTransactions();
        }
    }

    protected void hide_keyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected String getFragmentTag() {
        return this.getClass().getName();
    }

    protected final int getCommonHeaderLayoutId() {
        return R.layout.common_header_layout;
    }

    protected final void initCommonHeaderViews(View view) {
        mHeaderLeftImageView = (ImageView) view.findViewById(R.id.header_left_img);
        mHeaderTextView = (TextView) view.findViewById(R.id.header_title_tv);
        mHeaderRightImageView = (ImageView) view.findViewById(R.id.header_right_img);
    }

    @Override
    public void hideKeyboard() {
        Utils.hideKeyboard(this);
    }

    @Override
    public void isToolBarRequired(boolean isRequired) {
        if (isRequired) {
            mHeaderLayoutContainer.setVisibility(View.VISIBLE);
        } else {
            mHeaderLayoutContainer.setVisibility(View.GONE);
        }
    }
}
