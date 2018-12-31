package com.smart.earthview.majisoft.presenter;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.smart.earthview.majisoft.CustomerClass;
import com.smart.earthview.majisoft.model.LoginClass;
import com.smart.earthview.majisoft.model.MeterReading;
import com.smart.earthview.majisoft.model.SurveyReading;
import com.smart.earthview.majisoft.model.Zone;

import java.util.List;

@Dao
public interface MajiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertReading(MeterReading meterReading);

    @Query("SELECT * FROM meterreading")
    LiveData<List<MeterReading>> allReadings();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSurvey(SurveyReading surveyReading);

    @Query("SELECT * FROM surveyreading")
    LiveData<List<SurveyReading>> allSurvey();

    @Query("DELETE  FROM  surveyreading")
    void deleteSurvey();

     @Query("DELETE  FROM  meterreading")
    void deleteMeterReadings();

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     void  insertCustomer(CustomerClass customerClass);
     @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertZone(Zone zone);
     @Query("SELECT * FROM CustomerClass")
    LiveData<List<CustomerClass>> allCustomers();
     @Query("SELECT * FROM zone")
    LiveData<List<Zone>> allZone();
     @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLogin(LoginClass loginClass);
     @Query("SELECT * FROM loginclass")
    LiveData<List<LoginClass>> loginDetails();





}
