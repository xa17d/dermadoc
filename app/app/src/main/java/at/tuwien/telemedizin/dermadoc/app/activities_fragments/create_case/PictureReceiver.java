package at.tuwien.telemedizin.dermadoc.app.activities_fragments.create_case;

import at.tuwien.telemedizin.dermadoc.app.entities.PictureHelperEntity;

/**
 * Created by FAUser on 22.11.2015.
 */
public interface PictureReceiver {

    public void receiveNewPicture(PictureHelperEntity pictureHE);

    public void modifyDescriptionOfPicture(PictureHelperEntity pictureToBeModified, String description);
}
