//Topaz Avraham 206842627

import java.util.Comparator;
import java.util.Map;

/**
 * This class is used to sort a map by the values order.
 */
public class MapComparatorByValue implements Comparator<Map.Entry<String, Integer>> {
    @Override
    public int compare(Map.Entry<String, Integer> firstHypoCounter, Map.Entry<String, Integer> secondHypoCounter) {
        int hypoCounter1 = firstHypoCounter.getValue();
        int hypoCounter2 = secondHypoCounter.getValue();
        if (hypoCounter1 > hypoCounter2) {
            return 1;
        } else if (hypoCounter1 == hypoCounter2) {
           int res = firstHypoCounter.getKey().compareTo(secondHypoCounter.getKey());
           if (res > 0) {
               return -1;
           }
           if (res < 0) {
               return 1;
           }
           return 0;
        } else {
            return -1;
        }
    }
}