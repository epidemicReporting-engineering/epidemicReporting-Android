package com.reporting.epidemic.epidemicreporting.Adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationResponseModel;
import com.reporting.epidemic.epidemicreporting.R;


import java.util.List;

/**
 * Created by jianyu on 19/10/2018.
 */

public class MyReporterAdapter extends RecyclerView.Adapter<MyReporterAdapter.MyReporterHolder> {


    List<EpidemicSituationResponseModel> dataList;


    public MyReporterAdapter(List<EpidemicSituationResponseModel> dataSource) {
        this.dataList = dataSource;
    }

    @Override
    public MyReporterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_report, parent, false);
        MyReporterHolder holder = new MyReporterHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyReporterHolder holder, int position) {
        EpidemicSituationResponseModel model = dataList.get(position);
        holder.nameTextView.setText(model.getReporter());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyReporterHolder extends RecyclerView.ViewHolder {
        AppCompatTextView nameTextView;

        public MyReporterHolder(View itemView) {
            super(itemView);
            nameTextView = (AppCompatTextView)itemView.findViewById(R.id.item_my_report_reporter);
        }
    }
}

