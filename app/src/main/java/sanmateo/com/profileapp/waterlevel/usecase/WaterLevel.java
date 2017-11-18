package sanmateo.com.profileapp.waterlevel.usecase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class WaterLevel {

    public String alert;

    public String area;

    public String createdAt;

    public String deletedAt;

    @PrimaryKey
    public int id;

    public String updatedAt;

    public float waterLevel;

    public WaterLevel() {
        this.alert = "";
        this.area = "";
        this.createdAt = "";
        this.deletedAt = "";
        this.updatedAt = "";
    }
}
