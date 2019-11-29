package uk.ac.stir.cs.yh.cs.database;

import androidx.annotation.Nullable;
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
        return unitName;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean equal = false;
        if (obj instanceof Unit) {
            Unit otherUnit = (Unit) obj;

            if (otherUnit.unitName.equals(this.unitName)) {
                equal = true;
            }
        }

        return equal;
    }
}
