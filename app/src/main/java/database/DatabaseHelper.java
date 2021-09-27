package database;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.covidtestreport.CovidMasterData;


@Database(entities = {CovidMasterData.class}, version = 1, exportSchema = false)

public abstract class DatabaseHelper extends RoomDatabase
{
    public abstract DatabaseAccess daoAccess();
}