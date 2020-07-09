package com.mohammedibrahim.covid_19.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.mohammedibrahim.covid_19.R;
import com.mohammedibrahim.covid_19.model.ByStateDataModel;

import java.util.List;

public class ByStateAdapter extends ArrayAdapter<ByStateDataModel> {

    private Context context;
    private List<ByStateDataModel> byStateList;
    private int resourse;

    public ByStateAdapter(@NonNull Context context, int resource, @NonNull List<ByStateDataModel> byStateList) {
        super(context, resource, byStateList);
        this.context = context;
        this.byStateList = byStateList;
        this.resourse = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resourse, null, false);

        TextView region = view.findViewById(R.id.region);
        TextView infected = view.findViewById(R.id.infected);
        final ByStateDataModel byStateDataModel = byStateList.get(position);

        region.setText(byStateDataModel.getRegion() + "");
        infected.setText(byStateDataModel.getInfected() + "");


        //moreInfoView
        CardView byStateCard = view.findViewById(R.id.byStateListCard);
        byStateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMoreInfo(position, view, byStateDataModel);
            }
        });


        return view;


    }

    private void showMoreInfo(int position, View view, ByStateDataModel byStateDataModel) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View moreInfoDailog = LayoutInflater.from(view.getContext()).inflate(R.layout.more_info_dialog, viewGroup, false);
        TextView region = moreInfoDailog.findViewById(R.id.popUpRegion);
        TextView activeCase = moreInfoDailog.findViewById(R.id.popUpactiveCases);
        TextView recoverd = moreInfoDailog.findViewById(R.id.popUprecovered);
        TextView death = moreInfoDailog.findViewById(R.id.popUpdeaths);
        TextView totalcase = moreInfoDailog.findViewById(R.id.popUptotalCases);
        TextView ok = moreInfoDailog.findViewById(R.id.okBtn);

        builder.setView(moreInfoDailog);

        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

        alertDialog.show();
        region.setText(byStateDataModel.getRegion());
        activeCase.setText(String.valueOf(byStateDataModel.getInfected()));
        recoverd.setText(String.valueOf(byStateDataModel.getRecovered()));
        death.setText(String.valueOf(byStateDataModel.getDeaths()));
        totalcase.setText(String.valueOf(byStateDataModel.getTotalCases()));

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}
