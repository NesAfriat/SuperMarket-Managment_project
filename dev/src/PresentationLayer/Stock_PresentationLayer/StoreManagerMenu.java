package PresentationLayer.Stock_PresentationLayer;

import BusinessLayer.FacedeModel.Objects.Response;
import BusinessLayer.FacedeModel.facade;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;

public class StoreManagerMenu {
    private facade Facade;
    private BufferedReader reader;
    private Response res;

    int inputi;
    String inputs;

    public StoreManagerMenu(facade Facade, BufferedReader reader){
        this.Facade = Facade;
        this.reader = reader;
    }

    public void run() throws IOException {
        System.out.println("Hello, and welcome to the 'Super-Li's Stock' interface");
        boolean flag = true;
        while (flag) {
            System.out.println("Please choose an action:");
            System.out.println("0. Exit\n1. Reports \n ");
            inputs = reader.readLine().trim();
            if(inputs.equals("")) {
                inputi = -1;
            }else inputi=Integer.parseInt(inputs);
            switch (inputi) {
                case 0: {
                    flag = false;
                    break;
                    //should terminate the program
                }
                case 1: {
                    report_window();
                    break;
                }
                default: {
                    System.out.println("not valid input, please try again");
                }
            }
        }
    }

    private boolean check_action(Response res) {
        if (res.getErrorOccurred()) {
            System.out.println(res.getErrorMsg());
            System.out.println("Action FAILED!");
            return false;
        } else System.out.println("Action SUCCESS!");
        return true;
    }

    public void report_window() throws IOException {

        boolean flag = true;
        while (flag) {
            System.out.println("What action would u like to do?");
            System.out.println("0. go back");
            System.out.println("1. create a new report");
            System.out.println("2. get report by id");
            System.out.println("3. get report by subject and date");
            System.out.println("4. show all reports");
            inputs = reader.readLine().trim();
            if(inputs.equals("")) {
                inputi = -1;
            }else inputi=Integer.parseInt(inputs);
            switch (inputi) {
                case 0: {
                    flag = false;
                    break;
                }
                case 1: {
                    //create report
                    System.out.println("please type the subject of the report: (Stock, Missing, Defects)");
                    String sub = reader.readLine().trim().toLowerCase();
                    System.out.println("please type the time range: (d-Daily, w-Weekly, m-Monthly)");
                    String time = reader.readLine().trim().toLowerCase();
                    System.out.println("please type the names of the categories: (type q for exit)");
                    inputs = reader.readLine().trim().toLowerCase();
                    LinkedList<String> cat = new LinkedList<>();
                    while (!inputs.equals("q")) {
                        cat.add(inputs);
                        inputs = reader.readLine().trim().toLowerCase();
                    }
                    res = Facade.createReport(sub, time, cat);
                    if (check_action(res))
                        System.out.println(res.print());
                    break;
                }
                case 2: {
                    //get report by id
                    System.out.println("please type the report id");
                    inputi = Integer.parseInt(reader.readLine());
                    res = Facade.get_report_by_id(inputi);
                    if (check_action(res))
                        System.out.println(res.print());
                    break;
                }
                case 3: {
                    //get report by sub and date
                    System.out.println("please type the subject(Missing, Defects, Stock");
                    inputs = reader.readLine().trim().toLowerCase();
                    System.out.println("please type the date (YYYY-MM-DD)");
                    String date = reader.readLine().trim().toLowerCase();
                    res = Facade.get_reports_id(inputs, date);
                    if (check_action(res))
                        System.out.println(res.print());
                    break;
                }
                case 4: {
                    //show all report
                    res = Facade.show_all_reports();
                    if (check_action(res))
                        System.out.println(res.print());
                    break;
                }
                default: {
                    System.out.println("not valid input, please try again");
                }
            }
        }
    }
}

