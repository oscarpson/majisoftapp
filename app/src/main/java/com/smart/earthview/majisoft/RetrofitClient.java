package com.smart.earthview.majisoft;

import com.google.gson.Gson;
import com.smart.earthview.majisoft.apiService.ApiService;
import com.smart.earthview.majisoft.constant.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static ApiService service;
    static {setRestClient();}

    private static void setRestClient() {
        Gson gson = new Gson();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.GETSTATUS_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(ApiService.class);
    }

  public static ApiService getRestClient(){
        return  service;
  }
}
