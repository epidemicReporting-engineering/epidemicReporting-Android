package com.reporting.epidemic.epidemicreporting.Adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationResponseModel;
import com.reporting.epidemic.epidemicreporting.R;
import com.reporting.epidemic.epidemicreporting.Views.IRecyclerViewClick;


import java.util.List;

/**
 * Created by jianyu on 19/10/2018.
 */

public class MyReporterAdapter extends RecyclerView.Adapter<MyReporterAdapter.MyReporterHolder> {


    List<EpidemicSituationResponseModel> dataList;

    IRecyclerViewClick mItemClickListener;

    public MyReporterAdapter(List<EpidemicSituationResponseModel> dataSource, IRecyclerViewClick listener) {
        this.dataList = dataSource;
        this.mItemClickListener = listener;
    }

    @Override
    public MyReporterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_report, parent, false);
        MyReporterHolder holder = new MyReporterHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(view);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyReporterHolder holder, int position) {
        EpidemicSituationResponseModel model = dataList.get(position);
        holder.nameTextView.setText(model.getReporter());
        holder.descTextView.setText(model.getDutyDescription());
        holder.ownerTextView.setText("责任人: " + model.getDutyOwnerName());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyReporterHolder extends RecyclerView.ViewHolder {
        AppCompatTextView nameTextView;
        AppCompatTextView descTextView;
        AppCompatTextView ownerTextView;
        public MyReporterHolder(View itemView) {
            super(itemView);
            nameTextView = (AppCompatTextView)itemView.findViewById(R.id.item_my_report_reporter);
            descTextView = (AppCompatTextView)itemView.findViewById(R.id.item_my_report_report_desc);
            ownerTextView = (AppCompatTextView)itemView.findViewById(R.id.item_my_report_duty_owner);

        }
    }
}


