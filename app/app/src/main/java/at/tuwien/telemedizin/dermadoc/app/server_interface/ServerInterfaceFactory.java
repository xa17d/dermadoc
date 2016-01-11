package at.tuwien.telemedizin.dermadoc.app.server_interface;

/**
 * Created by FAUser on 10.12.2015.
 */
public class ServerInterfaceFactory {

    public static boolean testMode = false;

    private static ServerInterface instance;

    public static synchronized ServerInterface getInstance() {
        if (instance == null) {

            if (testMode) {
                instance = new TestServerInterface();
            } else {
                instance = new RestServerInterface("http://dermadoc.xa1.at:82");
            }
        }

        return instance;
    }
}
