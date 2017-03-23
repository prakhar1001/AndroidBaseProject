package com.lendingkart.instant.base;

import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public interface IFragmentInteractionListener {

    void loadFragment(int fragmentContainerId, BaseFragment fragment,
                      @Nullable String tag, int enterAnimId, int exitAnimId,
                      BaseFragment.FragmentTransactionType fragmentTransactionType);

    void loadDialogFragment(DialogFragment fragment,
                            @Nullable String tag);

    void showAlert(String title, String message, String positveBtnText,
                   String negativeBtnText,
                   AlertDialogFragment.AlertPositiveActionListener alertPositiveActionListener,
                   AlertDialogFragment.AlertNegativeActionListener alertNegativeActionListener,
                   boolean isCancelable);

    void showBlockingProgressBar();

    void hideBlockingProgressBar();

    void hideKeyboard();

    void isToolBarRequired(boolean isRequired);

}
