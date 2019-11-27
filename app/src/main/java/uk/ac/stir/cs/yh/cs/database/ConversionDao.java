package uk.ac.stir.cs.yh.cs.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ConversionDao {
    @Query("SELECT * FROM conversion")
    List<Conversion> getAll();

    @Insert
    void insertAll(Conversion... conversions);

    @Delete
    void delete(Conversion conversion);
}
