package uk.ac.stir.cs.yh.cs.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

public class Database {
    private static final Database ourInstance = new Database();

    private static AppDatabase db;

    private Database() {}

    public static void initDB(Context context) {
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
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
        db.unitDao().insertAll(
                new Unit("Kilogram", "kg", weightID),
                new Unit("Pound", "lb", weightID)
        );

        for (Unit unit : db.unitDao().getAll())
            Log.d("Units", unit.toString());

        db.conversionDao().insertAll(
                new Conversion(0, 1, 2.2)
        );

        for (Conversion conversion : db.conversionDao().getAll())
            Log.d("Conversions", conversion.toString());
    }
}
