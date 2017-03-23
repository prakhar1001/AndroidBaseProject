package com.lendingkart.instant.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;

public abstract class BaseRecylerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    interface IViewType {
        int getViewType();
    }

    interface RecyclerViewDataBinder<G extends RecyclerView.ViewHolder, T extends IViewType> extends IViewType {
        G getViewHolder(ViewGroup parent);
        void bindDataToViewHolder(G viewHolder, T data, int position, RecyclerItemClickListener recyclerViewClickListener);
    }

    interface OnPageEndListener {
        void onPageEnd(int position);
    }

    interface RecyclerItemClickListener {
        void onRecyclerItemClick(int position, View view, IViewType object);
    }

    abstract
    @NonNull
    List<RecyclerViewDataBinder> getViewDataBinders();

    private HashMap<Integer, RecyclerViewDataBinder> mRecyclerViewTypes;
    private List<IViewType> mDataList;
    private RecyclerItemClickListener mRecyclerItemClickListener;
    private OnPageEndListener mOnPageEndListener;

    public BaseRecylerAdapter(@NonNull List<IViewType> dataList) {
        mDataList = dataList;
        List<RecyclerViewDataBinder> recyclerViewHolderTypes = getViewDataBinders();
        mRecyclerViewTypes = new HashMap<>(recyclerViewHolderTypes.size());

        for (RecyclerViewDataBinder recyclerViewHolderType : recyclerViewHolderTypes) {
            mRecyclerViewTypes.put(recyclerViewHolderType.getViewType(), recyclerViewHolderType);
        }
    }

    public void setRecyclerItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        mRecyclerItemClickListener = recyclerItemClickListener;
    }

    public void setRecyclerOnPageEndListener(OnPageEndListener onPageEndListener) {
        mOnPageEndListener = onPageEndListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewDataBinder recyclerViewType = mRecyclerViewTypes.get(viewType);

        return recyclerViewType.getViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount() - 1 && mOnPageEndListener != null) {
            mOnPageEndListener.onPageEnd(position + 1);
        }
        RecyclerViewDataBinder recyclerViewType = mRecyclerViewTypes.get(getItemViewType(position));
        recyclerViewType.bindDataToViewHolder(holder, mDataList.get(position), position, mRecyclerItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).getViewType();
    }

}
