package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata;

import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.view.AGCCaseDataView;
import at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.view.GCTextMessageView;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;
import at.tuwien.telemedizin.dermadoc.entities.casedata.TextMessage;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lucas on 20.11.2015.
 */
public class GCCaseDataFactory {

    private static Map<Class<? extends CaseData>, Class<? extends AGCCaseDataView>> mappingView = new HashMap<>();

    protected GCCaseDataFactory() {

        mappingView.put(TextMessage.class, GCTextMessageView.class);
        //TODO add other casedataviews
    }

    protected AGCCaseData getGC(CaseData caseData) {

        //TODO handle exceptions
        AGCCaseData result = null;
        try {
            result = mappingView.get(caseData.getClass()).getConstructor(CaseData.class).newInstance(caseData);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
}
