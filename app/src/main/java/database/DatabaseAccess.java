package database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.covidtestreport.CovidMasterData;

import java.util.List;


@Dao
public interface DatabaseAccess {


    @Query("SELECT COUNT(id) FROM CovidMasterData")
    int getNumberOfCovidDataRows();

    @Query("DELETE FROM covidmasterdata")
    int getdeletedata();

    @Insert
    Long[] insertCovidMasterData(List<CovidMasterData> Covid_Master_Data);



    @Query("SELECT *  FROM CovidMasterData ")
    List<CovidMasterData> getCovidDetails();


}
