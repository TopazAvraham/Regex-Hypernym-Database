//Topaz Avraham 206842627

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.io.FileReader;

/**
 * This class is used to read from files and write it to a data-base.
 */
public class ReaderToDataBase {
    private File[] files;
    private List<String> exps;

    /**
     * Constructor.
     * @param files - the files to read from
     * @param exps - the list of regex expressions
     */
    public ReaderToDataBase(File[] files, List<String> exps) {
        this.files = files;
        this.exps = exps;
    }

    /**
     * This method iterates through all the files and for each file it reads it and writes it to the database.
     */
    public void readFromFileToDataBase() {
        try {
            for (File file : this.files) {
                if (file.isFile()) {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String checkLine = bufferedReader.readLine();

                        while (checkLine != null) {
                            if (!(checkLine.contains("such") || checkLine.contains("which")
                                    || checkLine.contains("expecially") || checkLine.contains("including"))) {
                                checkLine = bufferedReader.readLine();
                                continue;
                            }
                            CreateHypernymDatabase.addExpToRegexlist(checkLine, this.exps);
                            checkLine = bufferedReader.readLine();
                        }
                        bufferedReader.close();
                    }
                }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
