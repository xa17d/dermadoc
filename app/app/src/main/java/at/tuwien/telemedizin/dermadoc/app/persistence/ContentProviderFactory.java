package at.tuwien.telemedizin.dermadoc.app.persistence;

/**
 * Created by FAUser on 02.12.2015.
 */
public class ContentProviderFactory {

    private static ContentProvider instance;

    public static synchronized ContentProvider getContentProvider() {
        if (instance == null) {
            instance = new TestContentProvider();
        }
        return instance;
    }
}
