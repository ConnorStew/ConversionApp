package uk.ac.stir.cs.yh.cs.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * This class represents a conversion between two Units.<br>
 * This entity in the database has a composite key of the two Unit's ids.
 * @author Connor Stewart
 */
@Entity(primaryKeys = {"unit_1_id", "unit_2_id"})
public class Conversion {

    public Conversion(int unit1Id, int unit2Id, double conversionFactor) {
        this.unit1Id = unit1Id;
        this.unit2Id = unit2Id;
        this.conversionFactor = conversionFactor;
    }

    /** Unit ID of the left hand side of the conversion. */
    @ColumnInfo(name = "unit_1_id")
    int unit1Id;

    /** Unit ID of the right hand side of the conversion. */
    @ColumnInfo(name = "unit_2_id")
    int unit2Id;

    /** The amount to multiply the first Unit by to get the second Unit. */
    @ColumnInfo(name = "conversion_factor")
    public double conversionFactor;

    @Override
    @NonNull
    public String toString() {
        return "Conversion{" +
                "unit1Id=" + unit1Id +
                ", unit2Id=" + unit2Id +
                ", conversionFactor=" + conversionFactor +
                '}';
    }
}
