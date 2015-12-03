package at.tuwien.telemedizin.dermadoc.app.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.R;
import at.tuwien.telemedizin.dermadoc.app.entities.PictureHelperEntity;

/**
 * Created by FAUser on 18.11.2015.
 * Adapter to fill the MyCases-List
 *
 * following http://www.vogella.com/tutorials/AndroidListView/article.html
 */
public class CasePictureListAdapter extends ArrayAdapter<PictureHelperEntity> {
    public static final String LOG_TAG = CasePictureListAdapter.class.getSimpleName();

    private final Context context;
    private CasePictureClickedHandler itemClickHandler;
    private final List<PictureHelperEntity> values;


    public CasePictureListAdapter(Context context, List<PictureHelperEntity> values, CasePictureClickedHandler handler) {
        super(context, R.layout.edit_picture_list_item, values);
        this.context = context;
        this.values = values;
        this.itemClickHandler = handler;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;
        final ViewGroup parentListView = parent;

        // save resources: only inflate when == null
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.edit_picture_list_item, null);

        }

        final PictureHelperEntity pictureItem = getItem(position);
        // check if the case_item exists
        if (pictureItem != null) {
            ImageView imageView = (ImageView) v.findViewById(R.id.picture_image_view);
            ImageButton deleteButton = (ImageButton) v.findViewById(R.id.picture_delete_button);
            TextView imageText = (TextView) v.findViewById(R.id.picture_text_view);

            imageView.setImageBitmap(pictureItem.getThumbnail());

            String picDescription = pictureItem.getDescription();
            if (picDescription == null || picDescription.trim().length() == 0) {
                picDescription = context.getString(R.string.label_no_description_touch_here);
            }
            imageText.setText(picDescription);

            final Bitmap thumbnail = pictureItem.getThumbnail();
            final String currentDescription = pictureItem.getDescription();

            View.OnClickListener editDescriptionListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // show dialog, edit Description
//                    AddPictureDescriptionDialog dialog =
//                            new AddPictureDescriptionDialog(context, thumbnail, currentDescription);
//                    dialog.show();

                    CasePictureListAdapter.this.itemClickHandler.casePictureClicked(pictureItem);
                }
            };

            // the listener is added to the frame-layout and the imageView separatly, to keep the
            // are on the right side for the "delete/cancel" button and avoid pressing "edit" by mistake
            FrameLayout textContainer = (FrameLayout) v.findViewById(R.id.picture_text_container_layout);

            textContainer.setOnClickListener(editDescriptionListener);
            imageView.setOnClickListener(editDescriptionListener);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // delete this row
                    showDeleteAlertDialog(position, (ListView) parentListView);
                }
            });

        }


        return v;
    }

    private void showDeleteAlertDialog(final int rowID, ListView parent) {
        final ListView parentListView = parent;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.msg_confirm_picture_deletion_dialog);
        builder.setTitle(R.string.title_picture_deletion_dialog);
        builder.setPositiveButton(R.string.label_picture_deletion_dialog_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // delete picture
                removeListItem(rowID, parentListView);

            }
        });
        builder.setNegativeButton(R.string.label_picture_deletion_dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing, close dialog
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void removeListItem(int index, ListView parent) {
        values.remove(index);
        EmbedableListViewUtility.setListViewHeightBasedOnChildren(parent);
        this.notifyDataSetChanged();
    }

    public interface CasePictureClickedHandler {
        public void casePictureClicked(PictureHelperEntity picture);
    }

}


