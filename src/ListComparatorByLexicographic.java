//Topaz Avraham 206842627

import java.util.Comparator;

/**
 * This class is used to sort a list by lexicographic order.
 */
public class ListComparatorByLexicographic implements Comparator<SingleHyper> {
    @Override
    public int compare(SingleHyper h1, SingleHyper h2) {
        String firstHyperName = h1.getHyperWord().toLowerCase();
        String secondHyperName = h2.getHyperWord().toLowerCase();
        int res = firstHyperName.compareTo(secondHyperName);
        return res;
    }
}
