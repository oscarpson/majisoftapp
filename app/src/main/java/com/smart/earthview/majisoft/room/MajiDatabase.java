package com.smart.earthview.majisoft.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.smart.earthview.majisoft.CustomerClass;
import com.smart.earthview.majisoft.model.LoginClass;
import com.smart.earthview.majisoft.model.MeterReading;
import com.smart.earthview.majisoft.model.SurveyReading;
import com.smart.earthview.majisoft.model.Zone;
import com.smart.earthview.majisoft.presenter.MajiDao;

@Database(entities = {MeterReading.class,SurveyReading.class,CustomerClass.class,Zone.class,LoginClass.class}, version = 6)
public abstract class MajiDatabase extends RoomDatabase {
    public abstract MajiDao majiDao();
}
