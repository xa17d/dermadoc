package at.tuwien.telemedizin.dermadoc.desktop.util;

import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseInfo;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Lucas on 17.12.2015.
 */
public class UtilSet<T> {

    public T getLastElement(Set<T> items) {

        Iterator<T> it = items.iterator();
        T lastElement = null;

        while(it.hasNext()) {
            lastElement = it.next();
        }

        return lastElement;
    }

    public T getPreviousElement(Set<T> items, T element) {

        Iterator<T> it = items.iterator();
        T previousElement = null;

        while(it.hasNext()) {
            T t = it.next();
            if(element.equals(t)) {
                return previousElement;
            }
            previousElement = t;
        }

        return null;
    }

    public T getNextElement(Set<T> items, T element) {

        Iterator<T> it = items.iterator();

        while(it.hasNext()) {
            T t = it.next();
            if(element.equals(t)) {
                if(it.hasNext()) {
                    return it.next();
                }
            }
        }

        return null;
    }
}
