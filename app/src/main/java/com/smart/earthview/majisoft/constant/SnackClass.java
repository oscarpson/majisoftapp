package com.smart.earthview.majisoft.constant;

import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smart.earthview.majisoft.R;

public class SnackClass {

    public static void setSnackBar(View coordinatorLayout, String snackTitle) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, snackTitle, 4000);//TODO check snack duration
        ViewGroup viewGroup= (ViewGroup) snackbar.getView();
        viewGroup.setBackgroundResource(R.color.green);

        TextView txtv = (TextView) viewGroup.findViewById(android.support.design.R.id.snackbar_text);
        txtv.setGravity(Gravity.FILL_HORIZONTAL);

        snackbar.show();
    }

    public static  void setErrorSnackbar(View coordinatorLayout, String snackTitle) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, snackTitle, 5000);
        ViewGroup viewGroup= (ViewGroup) snackbar.getView();
        viewGroup.setBackgroundResource(R.color.red);

        TextView txtv = (TextView) viewGroup.findViewById(android.support.design.R.id.snackbar_text);
        txtv.setGravity(Gravity.FILL_HORIZONTAL);

        snackbar.show();
    }
}
