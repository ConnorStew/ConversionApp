package uk.ac.stir.cs.yh.cs.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * This interface allows the app to access conversions from the database.
 * @author Connor Stewart
 */
@Dao
public interface ConversionDao {

    /**
     * Gets all conversions in the database.
     * @return all conversions in the database
     */
    @Query("SELECT * FROM conversion")
    List<Conversion> getAll();

    /**
     * Inserts given conversions into the database.
     * @param conversions the conversions to insert
     */
    @Insert
    void insertAll(Conversion... conversions);

    @Query("SELECT * FROM conversion WHERE unit_1_id = :unitOneId AND unit_2_id = :unitTwoId")
    Conversion getConversion(int unitOneId, int unitTwoId);
}
