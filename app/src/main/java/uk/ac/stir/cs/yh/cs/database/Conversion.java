package uk.ac.stir.cs.yh.cs.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"unit_1_id", "unit_2_id"})
public class Conversion {

    public Conversion(int unit1Id, int unit2Id, double conversionFactor) {
        this.unit1Id = unit1Id;
        this.unit2Id = unit2Id;
        this.conversionFactor = conversionFactor;
    }

    @ColumnInfo(name = "unit_1_id")
    public int unit1Id;

    @ColumnInfo(name = "unit_2_id")
    public int unit2Id;

    @ColumnInfo(name = "conversion_factor")
    public double conversionFactor;

    @Override
    public String toString() {
        return "Conversion{" +
                "unit1Id=" + unit1Id +
                ", unit2Id=" + unit2Id +
                ", conversionFactor=" + conversionFactor +
                '}';
    }
}
