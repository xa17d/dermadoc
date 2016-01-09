package at.tuwien.telemedizin.dermadoc.app.general_entities.casedata;

import java.util.Calendar;


import at.tuwien.telemedizin.dermadoc.app.general_entities.User;

/**
 * Created by daniel on 11.11.2015.
 */

public class PhotoMessage extends CaseData {

    public PhotoMessage(Long id, Calendar created, User author, String mime, byte[] photoData) {
        super(id, created, author);

        this.mime = mime;
        this.photoData = photoData;
    }

    public PhotoMessage() { }

    private String mime;
    public String getMime() { return mime; }

    private byte[] photoData;
    public byte[] getPhotoData() { return photoData; }
 }
