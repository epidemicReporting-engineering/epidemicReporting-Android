package com.reporting.epidemic.epidemicreporting.Adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationResponseModel;
import com.reporting.epidemic.epidemicreporting.R;
import com.reporting.epidemic.epidemicreporting.Views.IRecyclerViewClick;

import java.util.List;

/**
 * Created by jianyu on 28/10/2018.
 */

public class MyMessageAdapter extends RecyclerView.Adapter<MyMessageAdapter.MyMessageHolder> {

    List<EpidemicSituationResponseModel> dataList;

    IRecyclerViewClick mItemClickListener;

    public MyMessageAdapter(List<EpidemicSituationResponseModel> dataSource, IRecyclerViewClick listener) {
        this.dataList = dataSource;
        this.mItemClickListener = listener;
    }

    @Override
    public MyMessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_message, parent, false);
        MyMessageAdapter.MyMessageHolder holder = new MyMessageAdapter.MyMessageHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyMessageHolder holder, int position) {
        EpidemicSituationResponseModel model = dataList.get(position);
        String status = model.getDutyStatus();
        if (status.equals("0")) {
            holder.mMessageStatusCover.setBackgroundResource(R.color.colorThemeBlue);
            holder.mStatusTextView.setText("未分配");
        } else if (status.equals("1")) {
            holder.mMessageStatusCover.setBackgroundResource(R.color.colorThemeBlue);
            holder.mStatusTextView.setText("新任务");
        } else if (status.equals("2")) {
            holder.mMessageStatusCover.setBackgroundResource(R.color.colorStart);
            holder.mStatusTextView.setText("开始处理");
        } else if (status.equals("3")) {
            holder.mMessageStatusCover.setBackgroundResource(R.color.colorBlock);
            holder.mStatusTextView.setText("有困难");
        }else if (status.equals("4")) {
            holder.mMessageStatusCover.setBackgroundResource(R.color.colorWaiting);
            holder.mStatusTextView.setText("等待审阅");
        }else if (status.equals("5")) {
            holder.mMessageStatusCover.setBackgroundResource(R.color.colorFinish);
            holder.mStatusTextView.setText("完成");
        }else if (status.equals("6")) {
            holder.mMessageStatusCover.setBackgroundResource(R.color.colorCantdo);
            holder.mStatusTextView.setText("无法做");
        } else {
            holder.mMessageStatusCover.setBackgroundResource(R.color.colorThemeBlue);
            holder.mStatusTextView.setText("未知状态");
        }
        holder.mReporterTv.setText("疫情上报者: " + model.getReporterName());
        holder.mDescTv.setText(model.getDescription());
        holder.mOwnerTv.setText("责任人: " + model.getDutyOwnerName());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyMessageHolder extends RecyclerView.ViewHolder {

        AppCompatTextView mStatusTextView;
        RelativeLayout mMessageStatusCover;
        AppCompatTextView mReporterTv;
        AppCompatTextView mDescTv;
        AppCompatTextView mOwnerTv;

        public MyMessageHolder(View itemView) {
            super(itemView);

            mStatusTextView = (AppCompatTextView)itemView.findViewById(R.id.item_my_message_status);
            mMessageStatusCover = (RelativeLayout)itemView.findViewById(R.id.item_my_message_status_cover);
            mReporterTv = (AppCompatTextView)itemView.findViewById(R.id.item_my_message_reporter);
            mDescTv = (AppCompatTextView)itemView.findViewById(R.id.item_my_message_desc);
            mOwnerTv = (AppCompatTextView)itemView.findViewById(R.id.item_my_message_owner);

        }
    }
}
