package com.smart.earthview.majisoft.meterStatus;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.smart.earthview.majisoft.MainActivity;
import com.smart.earthview.majisoft.R;
import com.smart.earthview.majisoft.Users;
import com.smart.earthview.majisoft.apiService.ApiService;
import com.smart.earthview.majisoft.apiService.ApiUrl;
import com.smart.earthview.majisoft.constant.Constants;
import com.smart.earthview.majisoft.constant.SnackClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.smart.earthview.majisoft.constant.SnackClass.setErrorSnackbar;
import static com.smart.earthview.majisoft.constant.SnackClass.setSnackBar;

public class MeterStatus extends AppCompatActivity {
    ArrayList<String> zones,statusArray;
    ArrayAdapter adapter;
    Spinner zoneSpinner,mstatus;
    RecyclerView rcv_mstatus;
    StatusAdapter madapter,searchAdapter;
    StatusClass mstatusClass;
    List<StatusClass>statusList,statusSearchList;
    Button btnsearch;
    String zonesearch;
    CoordinatorLayout relativemap;
    int responseSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meter_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        zoneSpinner=findViewById(R.id.zonespinner);
        btnsearch=findViewById(R.id.btnsearch);
        mstatus=findViewById(R.id.mstatus);
        relativemap =  findViewById(R.id.relativemap);
        zones=new ArrayList<>();
        zones.add("Kavuu");
        zones.add("Kithyka");
        zones.add("Mutheu");
        zones.add("Musya");
        zones.add("Masaku");

        adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,zones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zoneSpinner.setAdapter(adapter);
        rcv_mstatus=findViewById(R.id.rcv_status);
        rcv_mstatus.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rcv_mstatus.setAdapter(null);
        statusList=new ArrayList<>();
        //populateList();
        retrotest();
       /* statusList.add(new StatusClass("0001","5858","Kitwasco","12/12/2018","1","Kavuu"));
        statusList.add(new StatusClass("0002","5858","Park side villa","12/12/2018","0","Kithyka"));
        statusList.add(new StatusClass("0003","5858","Kitui mtc","12/12/2018","1","Mutheu"));
        statusList.add(new StatusClass("0004","5858","Kitui water institute","12/12/2018","0","Musya"));
        statusList.add(new StatusClass("0005","5858","Kunda Kindu","12/12/2018","1","Masaku"));

        madapter=new StatusAdapter(getApplicationContext(),statusList);
        rcv_mstatus.setAdapter(madapter);*/
        zoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                zonesearch=parent.getItemAtPosition(position).toString();
                Log.e("spinnerx",position+"\t"+id+"\t"+parent.getSelectedItem());
                String xx=String.valueOf(position);
                statusSearchList=new ArrayList<>();
                statusSearchList.clear();
                for (int x=0;x<responseSize;x++)
                {
                    if(statusList.get(x).EntryId.contains(xx)){

                        statusSearchList.add(statusList.get(x));
                    }
                    searchAdapter=new StatusAdapter(getApplicationContext(),statusSearchList);
                    rcv_mstatus.setAdapter(searchAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private void populateList() {
        final ProgressDialog loading = ProgressDialog.show(MeterStatus.this, "Requesting...", "Please wait...", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.GETSTATUS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("responceserver",response);

                    JSONArray jsonArray = jsonObject.getJSONArray("myorders");
                    Log.e("dataserver",jsonArray.toString());

                    //for (int i=0;i<jsonArray.length();i++) {

                    //JSONObject jor=jsonArray.getJSONObject(i);



                } catch (JSONException e) {
                    Log.e("exception ","Exception encoutered ");
                    //  Toast.makeText(getApplicationContext(),"Exception encoutered ",Toast.LENGTH_LONG);
                    e.printStackTrace();

                }
                //JsonArrayRequest jsonArrayRequest=
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                if (error instanceof NetworkError) {
                    loading.dismiss();
                    //setSnackBar(relativelayer, "Internet connection error...connect to wifi or enable network and retry");
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                System.out.print("params sent");
                Log.e("paramx",params.toString() );
                return params;
            }
        };

        int x = 2;// retry count
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*48,
                x, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplicationContext()).

                add(stringRequest);
    }
    public void retrotest(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.GETSTATUS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
try {
    final ProgressDialog loading = ProgressDialog.show(MeterStatus.this, "Fetching...", "Please wait...", false, false);
    Call<Users> call = service.getUsers();
    call.enqueue(new Callback<Users>() {
        @Override
        public void onResponse(Call<Users> call, retrofit2.Response<Users> response) {
              Log.e("retrodata",response.body().getUsers().get(0).AccNumber+""+response.body().getUsers().size());
            loading.dismiss();
            responseSize = response.body().getUsers().size();
            statusList = response.body().getUsers();
            madapter = new StatusAdapter(getApplicationContext(), response.body().getUsers());
            rcv_mstatus.setAdapter(madapter);
            //setSnackBar(relativemap, "Connected ");
        }

        @Override
        public void onFailure(Call<Users> call, Throwable t) {
            loading.dismiss();
            //SnackClass.setSnackBar(relativemap,"");
            setErrorSnackbar(relativemap, "Your request failed please try later");
        }
    });

} catch (Exception e){
    setErrorSnackbar(relativemap, "Your request failed please try later");
}
    }

}
