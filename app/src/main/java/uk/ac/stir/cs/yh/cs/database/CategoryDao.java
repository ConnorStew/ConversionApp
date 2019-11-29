package uk.ac.stir.cs.yh.cs.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * This interface allows the app to access categories from the database.
 * @author Connor Stewart
 */
@Dao
public interface CategoryDao {

    /**
     * Gets all categories in the database.
     * @return all categories in the database
     */
    @Query("SELECT * FROM category")
    List<Category> getAll();

    /**
     * Inserts given categories into the database.
     * @param categories the categories to insert
     */
    @Insert
    void insertAll(Category... categories);

    /**
     * Gets a category that has the given name.
     * @param name the name of the category
     * @return the category with the given name
     */
    @Query("SELECT * FROM category WHERE category_name = :name")
    Category getCategoryByName(String name);
}
