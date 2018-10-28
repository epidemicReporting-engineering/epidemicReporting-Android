package com.reporting.epidemic.epidemicreporting.Adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reporting.epidemic.epidemicreporting.App.EpidemicApplication;
import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationAllStatusModel;
import com.reporting.epidemic.epidemicreporting.Model.ReportStatusChangeDetailModel;
import com.reporting.epidemic.epidemicreporting.R;

/**
 * Created by eleven on 28/10/2018.
 */

public class ReporterAllStatusAdapter extends RecyclerView.Adapter<ReporterAllStatusAdapter.ReporterAllStatusHolder> {


    EpidemicSituationAllStatusModel allStatus;


    public ReporterAllStatusAdapter(EpidemicSituationAllStatusModel dataSource) {
        this.allStatus = dataSource;
    }

    @Override
    public ReporterAllStatusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_report_all_status, parent, false);
        ReporterAllStatusHolder holder = new ReporterAllStatusHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ReporterAllStatusHolder holder, int position) {
        ReportStatusChangeDetailModel model = allStatus.getDutyProcessRes().get(position);

        if (position == 0) {
            holder.nameTextView.setText("疫情上报人：" + allStatus.getReporterName());
            holder.descTextView.setText(allStatus.getDescription());
            holder.imageView.setImageDrawable(EpidemicApplication.getInstance().getAppContext().getResources().getDrawable((R.drawable.start_report)));
            return;
        } else if (model.getDutyStatus().equals("3")){ // suspend
            holder.imageView.setImageDrawable(EpidemicApplication.getInstance().getAppContext().getResources().getDrawable((R.drawable.circle_down_red)));
        } else if (model.getDutyStatus().equals("5")) { // leader confirm
            holder.imageView.setImageDrawable(EpidemicApplication.getInstance().getAppContext().getResources().getDrawable((R.drawable.success)));
        } else {
            holder.imageView.setImageDrawable(EpidemicApplication.getInstance().getAppContext().getResources().getDrawable((R.drawable.circle_down_blue)));
        }
        holder.nameTextView.setText("处理人：" + model.getDutyOwnerName());
        holder.descTextView.setText(model.getDutyDescription());
    }

    @Override
    public int getItemCount() {
        return allStatus.getDutyProcessRes().size();
    }

    class ReporterAllStatusHolder extends RecyclerView.ViewHolder {
        AppCompatTextView nameTextView;
        AppCompatTextView descTextView;
        AppCompatImageView imageView;

        public ReporterAllStatusHolder(View itemView) {
            super(itemView);
            imageView = (AppCompatImageView)itemView.findViewById(R.id.item_status_report_image);
            descTextView = (AppCompatTextView)itemView.findViewById(R.id.item_status_report_report_desc);
            nameTextView = (AppCompatTextView)itemView.findViewById(R.id.item_status_report_reporter);
        }
    }

}


