package translate;

/**
 *
 * @author Dang Minh Tuan SE150430
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static TranslateManagement tm;

    public static ArrayList generate(int number) {

        //Declare intermediate components
        ArrayList tmp = new ArrayList();
        int count = 0;
        int value;
        boolean flag;
        while (count < number) {
            flag = false;
            value = (int) (Math.random() * (tm.TranslateBank.size()));//Assuming the values range from 1 to size of vocabulary's bank
            for (int i = 0; i < tmp.size(); i++) {

                if (((Integer) tmp.get(i)).intValue() == value) {

                    flag = true;
                    break;

                }
            }

            if (!flag) {
                tmp.add(value);
                count++;
            }
        }
        return tmp;
    }

    public static void main(String[] args) {
        try {
            tm = new TranslateManagement("src/data/TranslateEV.txt");
            tm.uploadFile();
            Scanner sc = new Scanner(System.in);
            int chose = 0;
            String check = "";
            boolean isVaild = true;
            do {
                try {
                    System.out.println("----------Translate English - Vietnamese----------");
                    System.out.println("1.Convert English to Vietnamese");
                    System.out.println("2.Convert Vietnamese to English");
                    System.out.println("3.Add new vocabulary");
                    System.out.println("4.Vocabulary test");
                    System.out.println("5.Quit.");
                    System.out.print("\tPlease select a function: ");
                    chose = sc.nextInt();
                    sc.nextLine();
                    switch (chose) {
                        case 1:
                            do {
                                System.out.println("----------Convert English to Vietnamese----------");
                                System.out.print("Please enter the word English that you want to translate to Vietnamese: ");
                                String english = sc.nextLine().trim();
                                english = english.replaceAll("\\s\\s+", " ").trim();
                                tm.FindEnglish(english);
                                do {
                                    isVaild = true;
                                    System.out.println("Do you want to continue?");
                                    System.out.print("Please enter Y/N: ");
                                    check = sc.nextLine().trim().toLowerCase();
                                    if (check.equals("n") || check.equals("y")) {
                                        isVaild = true;
                                    } else {
                                        System.out.println("Error: you must enter Y/N!");
                                        isVaild = false;
                                    }
                                } while (isVaild == false);
                            } while (check.equals("y"));
                            break;
                        case 2:
                            do {
                                System.out.println("----------Convert Vietnamese to English----------");
                                System.out.print("Please enter the word Vietnamese that you want to translate to English: ");
                                String vietnamese = sc.nextLine().trim();
                                vietnamese = vietnamese.replaceAll("\\s\\s+", " ").trim();
                                tm.FindVietName(vietnamese);
                                do {
                                    isVaild = true;
                                    System.out.println("Do you want to continue?");
                                    System.out.print("Please enter Y/N: ");
                                    check = sc.nextLine().trim().toLowerCase();
                                    if (check.equals("n") || check.equals("y")) {
                                        isVaild = true;
                                    } else {
                                        System.out.println("Error: you must enter Y/N!");
                                        isVaild = false;
                                    }
                                } while (isVaild == false);
                            } while (check.equals("y"));
                            break;
                        case 3:
                            String english = "";
                            String vietnamese = "";
                            do {
                                isVaild = true;
                                System.out.println("----------Add new vocabulary----------");
                                System.out.print("Please enter new english: ");
                                english = sc.nextLine().trim();
                                if (english.equals("")) {
                                    System.out.println("The english can't be empty");
                                    isVaild = false;
                                } else if (tm.checkEnglish(english) == true) {
                                    System.out.println(english + " is exits");
                                    isVaild = false;
                                }
                            } while (isVaild == false);
                            do {
                                System.out.print("Please enter vietnamese of word " + english + ": ");
                                vietnamese = sc.nextLine().trim();
                                if (vietnamese.equals("")) {
                                    System.out.println("The vietnamese can't be empty");
                                    isVaild = false;
                                }
                            } while (isVaild == false);
                            tm.addVocabulary(english, vietnamese);
                            tm.showTableFull();
                            sc.nextLine();
                            break;
                        case 4:

                            int mark = 0;
                            int qNo = 1;
                            String ans;
                            int totalMark = 0;
                            System.out.println("----------Vocabulary test----------");
                            System.out.println("Quiz: ");
                            ArrayList arrInt = Main.generate(10);
                            for (int i = 0; i < 10; i++) {
                                System.out.println("#######################");
                                int randomInt = (int) arrInt.get(i);
                                System.out.println(qNo + ". " + tm.TranslateBank.get(tm.shuffer().get(randomInt)));
                                qNo++;
                                do {
                                    isVaild = true;
                                    System.out.print("    >>> Please select answer: ");
                                    ans = sc.nextLine();
                                    if (ans.equals("")) {
                                        System.out.println("The answer can't be empty");
                                        isVaild = false;
                                    }
                                } while (isVaild == false);
                                totalMark += qNo;
                                if (tm.checkAns(ans, tm.shuffer().get(randomInt))) {
                                    mark++;
                                    System.out.println("    +++ Congratulation! You answer is CORRECT");
                                } else {
                                    System.out.println("    --- So sad! Your answer is WRONG!!!");
                                    tm.wrongAns(tm.shuffer().get(i), ans);
                                }
                            }
                            System.out.println("++++++++++++++++++++++++++++++");
                            System.out.println("You are FINISH!!!");
                            System.out.println("Correct rate is " + mark + "/" + 10 + " ("
                                    + String.format("%.2f", ((double) mark * 100 / 10)) + "%)");
                            System.out.println("Wrong answer ....");
                            tm.showTableFull(tm.wrongAns, tm.ans);
                            sc.nextLine();
                            break;
                        case 5:
                            System.out.println("\n--------------------------------------");
                            System.out.println("Thank for using our software!\n"
                                    + "See you again!");
                            break;
                        default:
                            System.out.println("Error: The function must be a integer number from 1 to 5!");
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Error: The function must be a integer number from 1 to 5!");
                    isVaild = false;
                    sc.nextLine();
                }
            } while (isVaild = false || chose != 5);
        } catch (IOException | TranslateException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                tm.saveFile();
            } catch (IOException e) {
                System.out.println("Error: Can't save vocabulary");
            }
        }
    }
}
