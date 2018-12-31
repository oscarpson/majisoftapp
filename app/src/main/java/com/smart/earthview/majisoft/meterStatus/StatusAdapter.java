package com.smart.earthview.majisoft.meterStatus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smart.earthview.majisoft.R;

import java.util.Collections;
import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusViewHolder> {
Context context;
List<StatusClass>mstatus;

    public StatusAdapter(Context context, List<StatusClass> mstatus) {
        this.context = context;
        this.mstatus = mstatus;
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.status_viewholder,viewGroup,false);
        StatusViewHolder holder=new StatusViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder statusViewHolder, int i) {
        StatusClass statusClass=mstatus.get(i);
        statusViewHolder.txtpreading.setText(statusClass.CurrentReading);
        statusViewHolder.txtcdate.setText(statusClass.Rdate);
        statusViewHolder.txtaccno.setText(statusClass.AccNumber);
        statusViewHolder.txtuname.setText(statusClass.MtrReader);

        Log.e("mstatus",statusClass.ActiveStatus+"\t"+statusClass.CurrentReading);
        if (statusClass.ActiveStatus.equals("0")){
            statusViewHolder.txtpreading.setText("Inactive");
            statusViewHolder.txtpreading.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
            Log.e("mstatusx",statusClass.ActiveStatus);
        }
        else {
            statusViewHolder.txtpreading.setText(statusClass.CurrentReading);
        }

    }

    @Override
    public int getItemCount() {
        return (mstatus!=null)?mstatus.size():0;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
