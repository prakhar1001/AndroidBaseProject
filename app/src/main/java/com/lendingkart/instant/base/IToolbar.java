package com.lendingkart.instant.base;

import android.view.View;

public interface IToolbar {

    int getHeaderLeftIconId();

    int getHeaderRightIconId();

    View.OnClickListener getHeaderViewClickListener();

    String getToolbarTitleId();

    int getToolBarBackgroundColor();

}
