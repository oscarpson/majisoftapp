package com.smart.earthview.majisoft.apiService;

import com.smart.earthview.majisoft.MainSeedData;
import com.smart.earthview.majisoft.NewRecordResults;
import com.smart.earthview.majisoft.ResponseRetro;
import com.smart.earthview.majisoft.Users;
import com.smart.earthview.majisoft.meterStatus.StatusClass;
import com.smart.earthview.majisoft.model.SurveyReading;
import com.smart.earthview.majisoft.model.Zones;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("addreading") //endpoint
    Call<StatusClass> status(

    );
    @FormUrlEncoded
    @POST("addrecords")
    Call<NewRecordResults> createUser(
            @Field(" EntryId") String EntryId,
            @Field("AccNumber") String AccNumber,
            @Field("CurrentReading") String CurrentReading,
            @Field("MtrReader") String MtrReader,
            @Field("MtrStatus") String  MtrStatus,
            @Field("Rdate") String Rdate,
            @Field("Location") String Location,
            @Field("ActiveStatus") String ActiveStatus);



    @GET("allusers")
    Call<Users> getUsers();

    @GET("seedmain")
    Call<MainSeedData>getSeedMain();

    @FormUrlEncoded
    @POST("surveyform")//endpoints
    Call<ResponseRetro>surveyForm(
            @Field("zone") String zone,
            @Field("mdate") String mdate,
            @Field("connection") String connection,
            @Field("latslongs") String latslongs,
            @Field("mastermeter") String mastermeter,
            @Field("serviceLine") String serviceLine);

    //disconnection
    @FormUrlEncoded
    @POST("disconnect")
    Call<ResponseRetro>disconnect(
            @Field("meterNo")String meterNo,
            @Field("accNo")String accNo,
            @Field("discReason")String discReason,
            @Field("discBy")String discBy,
            @Field("message")String message);
    @FormUrlEncoded
    @POST("reconnect")
    Call<ResponseRetro>reconnect(
            @Field("meterNo")String meterNo,
            @Field("accNo")String accNo,
            @Field("recBy")String recBy,
            @Field("message")String message);

    @FormUrlEncoded
    @POST("offlinesvy")
    Call<ResponseRetro>offlinesvy(@Field("svy[]") ArrayList<SurveyReading> svy);

    @FormUrlEncoded
    @POST("login")
    Call<ResponseRetro>login(
            @Field("userName")String userName,
            @Field("password")String password
           );






    //Call<StatusClass> createUser(String accNumber, String currentReading, String mtrReader,
                                 //String mtrStatus, String rdate, String location, String activeStatus);
}
