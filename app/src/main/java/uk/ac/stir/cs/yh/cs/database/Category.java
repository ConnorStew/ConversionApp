package uk.ac.stir.cs.yh.cs.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * This class represents a category of conversion.<br>
 * For example, Weight or Mass.
 * @author Connor Stewart
 */
@Entity(indices = {@Index("id"), @Index(value = "category_name", unique = true)})
public class Category {

    /**
     * Creates a new category with a given name.
     * @param categoryName the name of this category
     */
    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    /** The id for this entity. */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /** The name of this category. */
    @ColumnInfo(name = "category_name")
    public String categoryName;

    @Override
    @NonNull
    public String toString() {
        return categoryName;
    }
}
