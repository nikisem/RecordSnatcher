package fi.nikisem.recordsnatcher;

import java.util.Comparator;

public class ItemComparator  implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        return o2.getTimeStamp().compareTo(o1.getTimeStamp());
    }
}