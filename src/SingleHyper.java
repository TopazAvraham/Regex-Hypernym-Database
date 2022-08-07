//Topaz Avraham 206842627


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.Collections;


/**
 * This class is used to represent an hypernym.
 */
public class SingleHyper {

    private Map<String, Integer> hyposMap = new HashMap<>();
    private int numberOfHypos = 0;
    private String hyperWord;

    /**
     * constructor.
     * @param hyperWord - the name/meaning of the Hypernym
     */
    public SingleHyper(String hyperWord) {
            this.hyperWord = hyperWord;
        }

    /**
     * This method is used to sort the map of the hypernyms according to the value of each of them.
     * @param map - the map of hypernyms to sort
     * @return - the map organized by values in order
     */
    public static TreeMap<String, Integer> valueSort(Map<String, Integer> map) {
            MapComparatorByValue comp = new MapComparatorByValue();
        TreeMap<String, Integer> newMap = new TreeMap<>();

            List<Map.Entry<String, Integer>> mapList = new ArrayList<>(map.entrySet());
            mapList.sort(comp);
            Collections.reverse(mapList);

            for (int i = 0; i < mapList.size(); i++) {
                newMap.put(mapList.get(i).getKey(), mapList.get(i).getValue());
            }
        TreeMap<String, Integer> finalMap = newMap;
            return finalMap;
        }

    /**
     * this method is used to add a hyponym to the map of hyponyms of the hypernym.
     * @param hypoWord - the word to add to the map
     */
    public void addHypo(String hypoWord) {

            if (this.hyperWord.compareTo(hypoWord) == 0) {
                return;
            }

            if (!this.hyposMap.containsKey(hypoWord)) {
                hyposMap.put(hypoWord, 1);
                this.numberOfHypos = this.numberOfHypos + 1;
            } else {
                hyposMap.put(hypoWord, hyposMap.get(hypoWord) + 1);
                this.numberOfHypos = this.numberOfHypos + 1;
            }
        }

    @Override
    public String toString() {
            String str = this.hyperWord + ": ";
           this.hyposMap = valueSort(hyposMap);
            for (Map.Entry<String, Integer> hypo : hyposMap.entrySet()) {
                str = str.concat(hypo.getKey() + " (" + hypo.getValue() + "), ");
            }
            StringBuffer sb = new StringBuffer(str);
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }


    /**
     * this method is used to receive the map of hyponyms.
     * @return the map
     */
    public Map<String, Integer> getHyposMap() {
            return this.hyposMap;
        }

    /**
     * this method is used to receive the number of hyponyms there for this hypernym.
     * @return the number of hyponyms
     */
    public int getNumberOfHypos() {
              return this.numberOfHypos;
        }

    /**
     * this method is used to receive the name of the hypernym.
     * @return the name of the hypernym
     */
    public String getHyperWord() {
        return hyperWord;
    }

    /**
     * This method is used to set a map of hyponyms to the hypernym.
     * @param hyposMap
     */
    public void setHyposMap(Map<String, Integer> hyposMap) {
        this.hyposMap = hyposMap;
    }
}
