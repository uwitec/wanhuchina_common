package com.wanhuchina.common.util.collection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CollectionUtil {

    /**
     * Removes the duplicates from a list based on a comparator
     * 
     * @param list
     *            - the list
     * @param comparator
     *            - the comparator
     * 
     * @return a list with no duplicates
     */
    public static <T> List<T> removeDuplicates(List<T> list, Comparator<T> comparator) {
        List<T> listNoDups = new ArrayList<T>();

        for (T listElem : list) {
            boolean exists = false;
            for (T listNoDupsElem : listNoDups) {
                if (comparator.compare(listElem, listNoDupsElem) == 0) {
                    exists = true;
                }
            }

            if (!exists) {
                listNoDups.add(listElem);
            }
        }

        return listNoDups;
    }

}