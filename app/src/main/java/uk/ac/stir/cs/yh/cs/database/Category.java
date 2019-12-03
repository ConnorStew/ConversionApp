package uk.ac.stir.cs.yh.cs.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * This class represents a category of conversion.<br>
 * For example, Weight or Mass.
 * @author Connor Stewart
 */
@Entity(indices = {@Index("id"), @Index(value = "category_name", unique = true)})
public class Category implements Serializable {

    /**
     * Creates a new category with a given name.
     * @param categoryName the name of this category
     */
    public Category(String categoryName) {
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

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean equal = false;
        if (obj instanceof Category) {
            Category otherCategory = (Category) obj;

            if (otherCategory.categoryName.equals(this.categoryName)) {
                equal = true;
            }
        }

        return equal;
    }
}
