package uk.ac.stir.cs.yh.cs.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("id"), @Index(value = "category_name", unique = true)})
public class Category {

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "category_name")
    public String categoryName;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
