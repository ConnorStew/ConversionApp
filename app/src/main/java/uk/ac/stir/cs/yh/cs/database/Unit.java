package uk.ac.stir.cs.yh.cs.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * This class represents a Unit to be converted between.
 * This class references a foreign key in the Conversion class.
 * @author Connor Stewart
 */
@Entity(indices = {@Index("id")},
        foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "id", childColumns = "category_id"))
public class Unit {

    Unit(String unitName, String unitSuffix, int categoryId) {
        this.unitName = unitName;
        this.unitSuffix = unitSuffix;
        this.categoryId = categoryId;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "unit_name")
    public String unitName;

    @ColumnInfo(name = "unit_suffix")
    String unitSuffix;

    @ColumnInfo(name = "category_id")
    public int categoryId;

    @Override
    @NonNull
    public String toString() {
        return unitName;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        //units are equal if they have the same name
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
