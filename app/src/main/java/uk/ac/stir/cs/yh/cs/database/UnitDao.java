package uk.ac.stir.cs.yh.cs.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UnitDao {

    @Insert
    void insertAll(Unit... units);

    @Query("SELECT * FROM unit WHERE category_id = :category_id")
    List<Unit> getUnitsByCategory(int category_id);

    @Query("SELECT * FROM unit WHERE unit_name = :unitName")
    Unit getUnitByName(String unitName);
}
