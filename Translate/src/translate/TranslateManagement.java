package translate;

import java.io.*;
import java.util.*;

public class TranslateManagement {

    private int numberOfVocabulary;
    Hashtable<String, String> TranslateBank;
    private String T_FILE;
    Set<String> stringSetKey = new HashSet<>();
    ArrayList<String> stringArrayListValue = new ArrayList<>();
    private int widthVietnamese = 10;
    private int widthNo = 3;
    private String template = "";
    private String barLine = "";
    private String template1 = "";

    public void updateWidth(String vietnamese) {
        widthVietnamese = Math.max(widthVietnamese, vietnamese.length());

    }

    public void updateTemplate() {
        String col1 = "";
        for (int i = 0; i < widthVietnamese + 4; i++) {
            col1 += "-";
        }
        String col2 = "";
        for (int i = 0; i < widthNo + 3; i++) {
            col2 += "-";
        }
        barLine = "+" + col2 + "+" + col1 + "+" + col1 + "+" + col1 + "+";
        template = "| %" + (widthNo + 1) + "s | %-" + (widthVietnamese + 2) + "s | %-" + (widthVietnamese + 2) + "s |\n";
        template1 = "| %" + (widthNo + 1) + "s | %-" + (widthVietnamese + 2) + "s | %-" + (widthVietnamese + 2) + "s | %-" + (widthVietnamese + 2) + "s |\n";
    }

    public void showTableFull() {
        int No = 0;
        updateTemplate();
        System.out.println(barLine);
        System.out.printf(template, "No.", "English", "Vietnamese");
        System.out.println(barLine);
        Set<String> keySet = this.TranslateBank.keySet();
        for (String key : keySet) {
            No++;
            System.out.printf(template, No + "", key, this.TranslateBank.get(key));
        }
        System.out.println(barLine);

    }

    public void showTableFull(Hashtable<String, String> hashtable) {
        int No = 0;
        updateTemplate();
        System.out.println(barLine);
        System.out.printf(template, "No.", "English", "Vietnamese");
        System.out.println(barLine);
        Set<String> keySet = hashtable.keySet();
        for (String key : keySet) {
            No++;
            System.out.printf(template, No + "", key, hashtable.get(key));
        }
        System.out.println(barLine);

    }

    public void showTableFull(Set<String> stringSet, ArrayList<String> test) {
        int No = 0;
        updateTemplate();
        System.out.println(barLine);
        System.out.printf(template1, "No.", "English", "Vietnamese", "Wrong Answer");
        System.out.println(barLine);
        int i = 0;
        for (String key : stringSet) {
            No++;
            System.out.printf(template1, No + "", key, this.TranslateBank.get(key), test.get(i));
            i++;
        }
        System.out.println(barLine);

    }

    public TranslateManagement(String T_FILE) {
        this.T_FILE = T_FILE;
        this.numberOfVocabulary = 0;
        this.TranslateBank = new Hashtable<String, String>();
    }

    public void uploadFile() throws IOException {
        File ft = new File(T_FILE);
        if (!ft.exists()) {
            ft.createNewFile();
            System.out.println("The data file TranslateEV.txt is not exits! "
                    + "Creating new data file TranslateEV.txt... Done.");
            this.numberOfVocabulary = 0;
        } else {
            System.out.print("The data file " + T_FILE + " is found. "
                    + "Data of vocabulary loading...");
            try (BufferedReader br = new BufferedReader(new FileReader(T_FILE))) {
                String vietNamese, english;
                this.numberOfVocabulary = Integer.valueOf(br.readLine());
                for (int i = 0; i < this.numberOfVocabulary; i++) {
                    english = br.readLine().trim();
                    vietNamese = br.readLine().trim();
                    this.stringSetKey.add(english);
                    TranslateBank.put(english, vietNamese);
                }
            }
            System.out.println(" Done! [" + this.numberOfVocabulary + " vocabulary]");
        }
    }

    public void saveFile() throws IOException {
        FileWriter fileWriter = new FileWriter(new File(T_FILE));
        try {
            System.out.print("\n Vocabulary is saving into data file " + T_FILE + "...");
            fileWriter.append(String.valueOf(this.numberOfVocabulary) + "\n");
            Set<String> keySet = this.TranslateBank.keySet();
            for (String key : keySet) {
                fileWriter.append(key + "\n");
                fileWriter.append(this.TranslateBank.get(key) + "\n");
            }
        } finally {
            fileWriter.close();
            System.out.println(" Done! [" + this.numberOfVocabulary + " vocabulary]");
        }
    }

    public void FindEnglish(String english) {
        Set<String> keySet = this.TranslateBank.keySet();
        Hashtable<String, String> result = new Hashtable<>();
        for (String key : keySet) {
            if (key.toLowerCase().contains(english.toLowerCase())) {
                result.put(key, this.TranslateBank.get(key));
            }
        }
        if (result.size() == 0) {
            System.out.println("Can't found" + english);
        } else {
            showTableFull(result);
        }
    }

    public void FindVietName(String vietNamese) {
        Set<String> keySet = this.TranslateBank.keySet();
        Hashtable<String, String> result = new Hashtable<>();
        for (String key : keySet) {
            if (this.TranslateBank.get(key).toLowerCase().contains(vietNamese.toLowerCase())) {
                result.put(key, this.TranslateBank.get(key));
            }
        }
        if (result.size() == 0) {
            System.out.println("Can't found " + vietNamese);
        } else {
            showTableFull(result);
        }
    }

    public boolean checkEnglish(String english) {
        Set<String> keySet = this.TranslateBank.keySet();
        for (String key : keySet) {
            if (key.equalsIgnoreCase(english)) {
                return true;
            }
        }
        return false;
    }

    public int addVocabulary(String english, String vietNamese) throws TranslateException {
        this.stringSetKey.add(english);
        Translate translate = new Translate(vietNamese, english);
        this.TranslateBank.put(translate.getEnglish(), translate.getVietNamese());
        return ++this.numberOfVocabulary;
    }

    public ArrayList<String> shuffer() {

        for (String a : this.stringSetKey) {
            this.stringArrayListValue.add(a);
        }
        return this.stringArrayListValue;
    }

    public boolean checkAns(String ans, String test) {
        if (ans.equalsIgnoreCase(test)) {
            return true;
        }
        return false;
    }
    Set<String> wrongAns = new HashSet<>();
    ArrayList<String> ans = new ArrayList<>();

    public void wrongAns(String wrong, String answerWrong) {
        this.wrongAns.add(wrong);
        this.ans.add(answerWrong);
    }

}
