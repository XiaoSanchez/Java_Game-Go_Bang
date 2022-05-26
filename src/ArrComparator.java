

import java.util.Comparator;

/**
 * ≈≈–Ú Comparator
 */
class ArrComparator implements Comparator<Object> {
    int column = 2;

    int sortOrder = -1; 

    public ArrComparator() {
    }
    public int compare(Object a, Object b) {
        if (a instanceof int[]) {
            return sortOrder * (((int[]) a)[column] - ((int[]) b)[column]);
        }
        throw new IllegalArgumentException("param a,b must int[].");
    }
}