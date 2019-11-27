package uk.ac.stir.cs.yh.cs.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        indices = {@Index("id")},
        foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "category_id")
)
public class Unit {

    public Unit(String unitName, String unitSuffix, int categoryId) {
        this.unitName = unitName;
        this.unitSuffix = unitSuffix;
        this.categoryId = categoryId;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "unit_name")
    public String unitName;

    @ColumnInfo(name = "unit_suffix")
    public String unitSuffix;

    @ColumnInfo(name = "category_id")
    public int categoryId;

    @Override
    public String toString() {
        return "Unit{" +
                "id=" + id +
                ", unitName='" + unitName + '\'' +
                ", unitSuffix='" + unitSuffix + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
