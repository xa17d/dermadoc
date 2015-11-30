package at.tuwien.telemedizin.dermadoc.app.adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by FAUser on 22.11.2015.
 *
 * As android does not support GridViews inside ScrollLayouts/Views this view tries to make it possible
 *
 * !!!BUT: The view-recycling is disabled - therefore only a few items should be placed in this view!!!
 *
 * see: http://stackoverflow.com/questions/12859250/how-to-put-gridview-inside-scrollview
 * and http://stackoverflow.com/questions/8481844/gridview-height-gets-cut
 */
public class EmbedableSGridView extends GridView {

    boolean expanded = false;

    public EmbedableSGridView(Context context)
    {
        super(context);
    }

    public EmbedableSGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public EmbedableSGridView(Context context, AttributeSet attrs, int defaultStyle) {
        super(context, attrs, defaultStyle);
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // HACK!  TAKE THAT ANDROID!
        if (isExpanded()) {
            // Calculate entire height by providing a very large height hint.
            // View.MEASURED_SIZE_MASK represents the largest height possible.
            int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);

            ViewGroup.LayoutParams params = this.getLayoutParams();
            params.height = getMeasuredHeight();
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
