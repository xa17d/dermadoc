package at.tuwien.telemedizin.dermadoc.entities.casedata;

import at.tuwien.telemedizin.dermadoc.entities.User;

import java.util.Calendar;

/**
 * Created by daniel on 11.11.2015.
 */
public class PhotoMessage extends CaseData {

    public PhotoMessage(long id, Calendar created, User author, String mime, byte[] photoData) {
        super(id, created, author);

        this.mime = mime;
        this.photoData = photoData;
    }

    private String mime;
    public String getMime() { return mime; }

    private byte[] photoData;
    public byte[] getPhotoData() { return photoData; }
 }
