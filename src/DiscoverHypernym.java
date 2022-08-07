//Topaz Avraham 206842627


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class is used do discover hypernyms in the corpus.
 */
public class DiscoverHypernym {

    /**
     * this method will search all the possible hypernyms of the input lemma and print them to the console.
     * @param args - the absolute path to the directory of the corpus, and a lemma
     */
    public static void main(String[] args) {

        List<String> exps = new ArrayList<>();
        File fileInput = new File(args[0]);
        String lemma = args[1];
        Map<String, Integer> lemmaHypers = new TreeMap<>();

        File[] files = fileInput.listFiles();
        readFromFileToDataBase(files, exps);
        List<SingleHyper> finalHypers = CreateHypernymDatabase.seperateHyperAndHypo(exps);

        int counter = 0;
        for (int i = 0; i < finalHypers.size(); i++) {
            Map<String, Integer> currentHyperMap = finalHypers.get(i).getHyposMap();
            if (currentHyperMap.size() == 0) {
                counter = 1;
                continue;
            } else {
                for (Map.Entry<String, Integer> currentHyper : currentHyperMap.entrySet()) {
                    if ((currentHyper.getKey().compareTo(lemma) == 0)
                           && (lemmaHypers.containsKey(finalHypers.get(i).getHyperWord()))) {
                        lemmaHypers.put(finalHypers.get(i).getHyperWord(),
                                lemmaHypers.get(finalHypers.get(i).getHyperWord()) + currentHyper.getValue());
                        counter++;
                    } else if ((currentHyper.getKey().compareTo(lemma) == 0)
                          &&  !(lemmaHypers.containsKey(finalHypers.get(i).getHyperWord()))) {
                        lemmaHypers.put(finalHypers.get(i).getHyperWord(), currentHyper.getValue());
                        counter++;
                    }
                }
            }
        }

        if (counter == 0 || counter == 1) {
        lemmaHypers = SingleHyper.valueSort(lemmaHypers);
        }

        for (Map.Entry<String, Integer> hyper : lemmaHypers.entrySet()) {
            System.out.printf(hyper.getKey() + ":");
            System.out.println(hyper.getValue());
        }

        if (lemmaHypers.entrySet().size() == 0) {
            System.out.println("The lemma doesn't appear in the corpus.");
        }
    }

    /**
     * This method iterates through all the files and for each file it reads it and writes it to the database.
     * @param files - the files to read from
     * @param exps - the regex list
     */
    public static void readFromFileToDataBase(File[] files, List<String> exps) {
        try {
            for (File file : files) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String checkLine = bufferedReader.readLine();

                while (checkLine != null) {
                    if (!(checkLine.contains("such") || checkLine.contains("which")
                            || checkLine.contains("expecially") || checkLine.contains("including"))) {
                        checkLine = bufferedReader.readLine();
                        continue;
                    }
                    CreateHypernymDatabase.addExpToRegexlist(checkLine, exps);
                    checkLine = bufferedReader.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}