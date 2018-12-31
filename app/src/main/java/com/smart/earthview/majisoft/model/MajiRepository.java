package com.smart.earthview.majisoft.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;

import com.smart.earthview.majisoft.CustomerClass;
import com.smart.earthview.majisoft.room.MajiDatabase;

import java.util.List;

public class MajiRepository {
    private String DB_NAME="maji-db";
    private MajiDatabase majidb;
     public MajiRepository(Context context){
         majidb= Room.databaseBuilder(context,MajiDatabase.class,DB_NAME).addMigrations(MIGRATION_1_2).fallbackToDestructiveMigration().build();//
     }
    static final Migration MIGRATION_1_2 = new Migration(5, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
// Since we didn't alter the table, there's nothing else to do here.
        }
    };
     public void insertMeterReading(final MeterReading meterReading){
         new AsyncTask<Void,Void,Void>(){

             @Override
             protected Void doInBackground(Void... voids) {
                 majidb.majiDao().insertReading(meterReading);
                 return null;
             }
         }.execute();
     }

     public LiveData<List<MeterReading>> getMeterReading(){
         return majidb.majiDao().allReadings();
     }

     public void insertSurveyReading(final SurveyReading surveyReading) {

         new AsyncTask<Void, Void, Void>() {

             @Override
             protected Void doInBackground(Void... voids) {
                 majidb.majiDao().insertSurvey(surveyReading);
                 //final String size = majidb.majiDao().allSurvey().getValue().size() + "";
                 return null;
             }


         }.execute();

         //return size;
     }

     public LiveData<List<SurveyReading>>getSurveyReading(){

         return majidb.majiDao().allSurvey();
     }

     public void deleteOfflineSurvey(){
         new AsyncTask<Void,Void,Void>(){

             @Override
             protected Void doInBackground(Void... voids) {
                 majidb.majiDao().deleteSurvey();
                 return null;
             }
         }.execute();
     }

     public void deleteOfflineMeterReadings(){
         new AsyncTask<Void,Void,Void>(){

             @Override
             protected Void doInBackground(Void... voids) {
                 majidb.majiDao().deleteMeterReadings();
                 return null;
             }
         }.execute();
     }

     public void insertCustomer(final CustomerClass customerClass){
         new AsyncTask<Void,Void,Void>(){

             @Override
             protected Void doInBackground(Void... voids) {
                 majidb.majiDao().insertCustomer(customerClass);
                 return null;
             }
         }.execute();
     }

     public LiveData<List<CustomerClass>> getCustomers(){
        return  majidb.majiDao().allCustomers();
     }

     public void insertZone(final Zone zone){
         new AsyncTask<Void,Void,Void>(){

             @Override
             protected Void doInBackground(Void... voids) {
                 majidb.majiDao().insertZone(zone);
                 return null;
             }
         }.execute();
     }

     public LiveData<List<Zone>> getZones(){
         return majidb.majiDao().allZone();
     }

     public void insertLogin(final LoginClass loginClass){
         new AsyncTask<Void,Void,Void>(){

             @Override
             protected Void doInBackground(Void... voids) {
                 majidb.majiDao().insertLogin(loginClass);
                 return null;
             }
         }.execute();
     }

     public  LiveData<List<LoginClass>>getLogin(){
         return  majidb.majiDao().loginDetails();
     }
}
