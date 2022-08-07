//Topaz Avraham 206842627
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to represent an hypernym's database.
 */
public class CreateHypernymDatabase {
    public static final String NP = "[ ,]*<np>[^<]+</np>[ ,]*";

    /**
     *The main method will read all the files in the directory, find and aggregate hypernym
     *relations that match the Hearst patterns using regular expressions, and save them in a txt file.
     * @param args - (1) the path to the directory of the corpus and (2) the path to the output file.
     * @throws IOException - if there was a problem reading the corpus.
     */
    public static void main(String[] args) throws IOException {

        File fIn = new File("corpus/mbta.com_mtu.pages_0.possf2");
        File fOut = new File(args[1]);
        File[] files = fIn.listFiles();
        List<String> exps = new ArrayList<>();

        ReaderToDataBase rd = new ReaderToDataBase(files, exps);
        rd.readFromFileToDataBase();

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fOut));
        List<SingleHyper> hypers = seperateHyperAndHypo(exps);
        ListComparatorByLexicographic ls = new ListComparatorByLexicographic();

        Collections.sort(hypers, ls);

        for (int i = 0; i < hypers.size(); i++) {
            if (hypers.get(i).getNumberOfHypos() >= 3) {
                bufferedWriter.write(hypers.get(i).toString() + "\n");
            }
        }
        bufferedWriter.close();
    }

    /**
     *this method is used to add to the list of regex the standards rules as shown in the instructions.
     * @param list - the list of the regexes
     */
    public static void addStandardRulesToList(List<Pattern> list) {
        Pattern reg1 = Pattern.compile("such" + NP + "as" + NP + "(" + NP + ")*(or|and)" + NP);
        list.add(reg1);
        Pattern reg2 = Pattern.compile(NP + "which is" + "[ ,]*(a kind|a class|an example)[, ]*of" + NP);
        list.add(reg2);
        Pattern reg3 = Pattern.compile(NP + "(which is|expecially|such as|including)" + NP
                + "(" + NP + ")*((or|and)( )*" + NP + ")?");
        list.add(reg3);
    }


    /**
     *this method is used to add to the list of regex strings that contain regex.
     * @param doesContainsRegex
     * @param list
     */
    public static void addExpToRegexlist(String doesContainsRegex, List<String> list) {

        List<Pattern> pattList = new ArrayList<Pattern>();
        addStandardRulesToList(pattList);

        for (int i = 0; i < pattList.size(); i++) {
            Matcher matcher = pattList.get(i).matcher(doesContainsRegex);
            while (matcher.find()) {
                list.add(matcher.group());
            }
        }
    }

    /**
     *this method receives a list of strings which includes both hypernyms and hyponyms,
     * and returns only the hypernyms.
     * @param list - the list combined with the hypernyms and hyponyms
     * @return - the list contains only hypernyms
     */
    public static List<SingleHyper> seperateHyperAndHypo(List<String> list) {
        List<SingleHyper> hyperList = new ArrayList<>();
        String adjustedReg = NP.substring(5, NP.length() - 5);
        Pattern patt = Pattern.compile(adjustedReg);
        List<String> hypoList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Matcher matcher = patt.matcher(list.get(i));
            SingleHyper hyper = null;
            int times = 0;
            while (matcher.find()) {
                String allMatchRange = matcher.group();
                String regexMatchRange = allMatchRange.substring(4, allMatchRange.length() - 5);

                if (times != 0) {
                    hyper.addHypo(regexMatchRange);
                } else if (!hypoList.contains(regexMatchRange.toLowerCase())) {
                    hyper = new SingleHyper(regexMatchRange);
                    hyperList.add(hyper);
                    hypoList.add(hyper.getHyperWord().toLowerCase());
                } else {
                    int index = hypoList.indexOf(regexMatchRange.toLowerCase());
                    hyper = hyperList.get(index);
                }
                times++;
            }
        }
        return hyperList;
    }
}