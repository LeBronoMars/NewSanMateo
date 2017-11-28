package sanmateo.com.profileapp.news.usecase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by rsbulanon on 28/11/2017.
 */

@Entity
public class News {

    public String body;

    public String createdAt;

    public String deletedAt;

    @PrimaryKey
    public int id;

    public String imageUrl;

    public String reportedBy;

    public String sourceUrl;

    public String status;

    public String title;

    public String updatedAt;

    public News() {
        this.body = "";
        this.createdAt = "";
        this.deletedAt = "";
        this.imageUrl = "";
        this.reportedBy = "";
        this.sourceUrl = "";
        this.status = "";
        this.title = "";
        this.updatedAt = "";
    }
}
