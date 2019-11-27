package uk.ac.stir.cs.yh.cs.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UnitDao {
    @Query("SELECT * FROM unit")
    List<Unit> getAll();

    @Insert
    void insertAll(Unit... units);

    @Delete
    void delete(Unit unit);
}
