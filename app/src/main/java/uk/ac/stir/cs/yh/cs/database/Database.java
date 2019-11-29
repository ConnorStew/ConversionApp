package uk.ac.stir.cs.yh.cs.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

public class Database {
    private static final Database ourInstance = new Database();

    private static AppDatabase db;

    private Database() {}

    public static void initDB(Context context) {
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).allowMainThreadQueries().build();
        initTestData();
    }

    public static AppDatabase getDB() {
        return db;
    }

    private static void initTestData() {
        db.clearAllTables();
        db.categoryDao().insertAll(
                new Category("Weight"),
                new Category("Liquid")
        );

        for (Category category : db.categoryDao().getAll())
            Log.d("Categories", category.toString());

        int weightID = db.categoryDao().getCategoryByName("Weight").id;
        int liquidID = db.categoryDao().getCategoryByName("Liquid").id;
        db.unitDao().insertAll(
                new Unit("Kilogram", "kg", weightID),
                new Unit("Pound", "lb", weightID),
                new Unit("Gallon", "gallon", liquidID),
                new Unit("Pint", "pint", liquidID),
                new Unit("Quart", "quart", liquidID)
        );

        for (Unit unit : db.unitDao().getAll())
            Log.d("Units", unit.toString());

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

        for (Conversion conversion : db.conversionDao().getAll())
            Log.d("Conversions", conversion.toString());
    }
}
