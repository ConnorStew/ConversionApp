package uk.ac.stir.cs.yh.cs.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Insert
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);

    @Query("SELECT * FROM category WHERE category_name = :name")
    Category getCategoryByName(String name);
}
