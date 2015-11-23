package at.tuwien.telemedizin.dermadoc.app.entities;

import android.graphics.Bitmap;

/**
 * Created by FAUser on 22.11.2015.
 *
 * this class is preliminary used until a entity is specified in entities-project!
 */
public class PictureHelperEntity {

    private Bitmap picture;
    private Bitmap thumbnail;
    private String description;

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
