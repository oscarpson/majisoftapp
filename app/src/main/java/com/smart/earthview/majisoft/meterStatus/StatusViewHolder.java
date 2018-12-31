package com.smart.earthview.majisoft.meterStatus;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.smart.earthview.majisoft.R;

public class StatusViewHolder extends RecyclerView.ViewHolder{
    TextView txtpreading,txtaccno,txtuname,txtcdate;
    public StatusViewHolder(@NonNull View itemView) {
        super(itemView);
        txtpreading=itemView.findViewById(R.id.txtpreading);
        txtaccno=itemView.findViewById(R.id.txtaccno);
        txtuname=itemView.findViewById(R.id.txtcname);
        txtcdate=itemView.findViewById(R.id.txtcdate);

    }
}
