package uk.ac.stir.cs.yh.cs.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Unit.class, Category.class, Conversion.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UnitDao unitDao();
    public abstract CategoryDao categoryDao();
    public abstract ConversionDao conversionDao();
}
