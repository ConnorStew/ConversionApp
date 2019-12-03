package uk.ac.stir.cs.yh.cs.database;

import android.content.Context;

import androidx.room.Room;

/**
 * This singleton class allows access to the Room database from other classes.
 * @author Connor Stewart
 */
public class Database {

    /** The instance of the Room database. */
    private static AppDatabase db;

    private static boolean initialised = false;

    /** Singleton class to the constructor is private. */
    private Database() {}

    /**
     * Creates the Room database and inserts test data.
     * @param context the applications context
     */
    public static void initDB(Context context) {
        if (!initialised) {
            db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).allowMainThreadQueries().build();
            initTestData();

            initialised = true;
        }
    }

    /** @return the Room database */
    public static AppDatabase getDB() {
        return db;
    }

    /** Inserts test data into the database. */
    private static void initTestData() {
        db.clearAllTables();

        db.categoryDao().insertAll(
                new Category("Weight"),
                new Category("Liquid")
        );

        int weightID = db.categoryDao().getCategoryByName("Weight").id;
        int liquidID = db.categoryDao().getCategoryByName("Liquid").id;
        db.unitDao().insertAll(
                new Unit("Kilogram", "kg", weightID),
                new Unit("Pound", "lb", weightID),
                new Unit("Gallon", "gallon", liquidID),
                new Unit("Pint", "pint", liquidID),
                new Unit("Quart", "quart", liquidID)
        );

        int kilogramID = db.unitDao().getUnitByName("Kilogram").id;
        int poundID = db.unitDao().getUnitByName("Pound").id;
        int gallonID = db.unitDao().getUnitByName("Gallon").id;
        int pintID = db.unitDao().getUnitByName("Pint").id;
        int quartID = db.unitDao().getUnitByName("Quart").id;
        db.conversionDao().insertAll(
                new Conversion(kilogramID, poundID, 2.2),
                new Conversion(gallonID, pintID, 8),
                new Conversion(gallonID, quartID, 4)
        );
    }
}
