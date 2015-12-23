package at.tuwien.telemedizin.dermadoc.desktop.gui.main.controls.casedata.edit.lists;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

import java.util.List;

/**
 * Created by Lucas on 17.12.2015.
 */
public class GCSearchBar<T> extends TextField {

    public GCSearchBar(final List<T> sourceList, final ObservableList<T> selectedList, final ObservableList<T> filteredList) {

        super();

        filteredList.addAll(sourceList);

        this.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                filteredList.clear();

                if(newValue.length() == 0 || (newValue.length() > oldValue.length() && newValue.length() < 3)) {
                    filteredList.addAll(sourceList);
                }
                else {
                    for (T t : sourceList) {
                        if (t.toString().toLowerCase().contains(newValue.toLowerCase())) {
                            filteredList.add(t);
                        }
                    }
                }

                filteredList.removeAll(selectedList);
            }
        });
    }
}
