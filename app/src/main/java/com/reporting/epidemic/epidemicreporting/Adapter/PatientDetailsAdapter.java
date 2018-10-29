package com.reporting.epidemic.epidemicreporting.Adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.reporting.epidemic.epidemicreporting.Model.EpidemicSituationResponseModel;
import com.reporting.epidemic.epidemicreporting.Model.PatientResponseModel;
import com.reporting.epidemic.epidemicreporting.R;

import java.util.List;

/**
 * Created by jianyu on 29/10/2018.
 */

public class PatientDetailsAdapter extends RecyclerView.Adapter<PatientDetailsAdapter.PatientDetailsHolder> {

    List<PatientResponseModel> dataList;

    public PatientDetailsAdapter(List<PatientResponseModel> dataSource) {
        this.dataList = dataSource;
    }

    @Override
    public PatientDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient_details, parent, false);
        PatientDetailsAdapter.PatientDetailsHolder holder = new PatientDetailsAdapter.PatientDetailsHolder(view);
        return holder;    }

    @Override
    public void onBindViewHolder(PatientDetailsHolder holder, int position) {
        PatientResponseModel model = dataList.get(position);
//        holder.nameTextView.setText(model.getName());
        holder.infoTv.setText(model.getName() + "  " + model.getSex() + model.getAge() + "岁");
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class PatientDetailsHolder extends RecyclerView.ViewHolder {
        AppCompatTextView infoTv;
        AppCompatTextView careerTv;
        AppCompatTextView methodTv;
        AppCompatTextView pyTv;
        AppCompatTextView fbTv;


        public PatientDetailsHolder(View itemView) {
            super(itemView);

            infoTv = (AppCompatTextView)itemView.findViewById(R.id.item_patient_details_info);
            careerTv = (AppCompatTextView)itemView.findViewById(R.id.item_patient_details_career);
            methodTv = (AppCompatTextView)itemView.findViewById(R.id.item_patient_details_method);
            pyTv = (AppCompatTextView)itemView.findViewById(R.id.item_patient_details_py);
            fbTv = (AppCompatTextView)itemView.findViewById(R.id.item_patient_details_fabing);

        }
    }
}
