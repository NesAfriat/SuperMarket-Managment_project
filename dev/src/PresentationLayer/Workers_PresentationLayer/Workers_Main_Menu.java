package PresentationLayer.Workers_PresentationLayer;
import BusinessLayer.Workers_BusinessLayer.Workers.Worker;
import BusinessLayer.Workers_BusinessLayer.Workers_Facade;
import BusinessLayer.Workers_BusinessLayer.Responses.*;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Workers_Main_Menu {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    protected static final Scanner scanner = new Scanner(System.in);
    protected Workers_Facade facade;

    public Workers_Main_Menu(Workers_Facade facade){
        this.facade = facade;
    }


    public void start() {
        boolean run = true;
        while (run){
            System.out.println("1) Enter ID number for login");
            System.out.println("2) Previous");
            System.out.print("Option: ");
            int option = getInputInt();
            if (option == 1){
                System.out.println("ID: ");
                String ID = getInputString();
                ResponseT<WorkerResponse> worker = facade.login(ID);
                if (worker.ErrorOccurred()) {
                    System.out.println(ANSI_RED + worker.getErrorMessage() + ANSI_RESET);
                }
                else {
                    printPrettyConfirm("Hello, " + worker.value.getName() + "!");
                    run = false;
                    if (worker.value.getIsAdmin()) {
                        new HRManagerMenu(facade).run();
                    } else {
                        new WorkerMenu(facade).run();
                    }
                }
            }
            else if (option == 2){
                run = false;
                printPrettyConfirm("Exited workers manage system");
            }
            else {
                printPrettyError("There's no such option");
            }

        }

    }




    protected void LogOut() {
        Response logout = facade.logout();
        if (logout.ErrorOccurred())
            printPrettyError(logout.getErrorMessage());
        else {
            printPrettyConfirm("Logout succeed");
        }
    }


    protected void getDefaultWorkDay() {
        int day = getInputDay();
        ResponseT<WorkDayResponse> workDayResponse = facade.getDefaultShiftInDay(day);
        if (workDayResponse.ErrorOccurred()){
            printPrettyError(workDayResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm(workDayResponse.value.Settings());
        }
    }


    protected void getDefaultShift(){
        int day = getInputDayType();
        String shiftType = getInputShiftType();
        ResponseT<ShiftResponse> response = facade.getDefaultJobsInShift(day, shiftType);
        if (response.ErrorOccurred()){
            printPrettyError(response.getErrorMessage());
        }
        else {
            printPrettyConfirm(response.value.Settings());
        }
    }


    // can't print in colors in cmd terminal :(
    protected void printPrettyHeadline(String s) {
        System.out.println(s);
    }
    protected void printPrettyConfirm(String message) {
        System.out.println(message);
    }
    protected void printPrettyError(String errorMessage) {
        System.out.println(errorMessage);
    }


    protected String getInputDate(){
        System.out.println("Enter Date <DD/MM/YYYY>: ");
        return getInputString();
    }

    protected String getInputShiftType(){
        System.out.println("Choose shift:");
        System.out.println("1) Morning");
        System.out.println("2) Evening");
        int option = getInputInt();
        if (option == 1)
            return "Morning";
        else if (option == 2)
            return "Evening";
        else {
            printPrettyError("There's no such option.");
            return getInputShiftType();
        }
    }

    protected String getInputJob() {
        System.out.print("Enter job name: ");
        return getInputString();
    }


    protected String getInputWorkerID(){
        System.out.print("Enter worker id: ");
        return getInputString();
    }


    protected int getInputDayType() {
        System.out.println("Choose day type:");
        System.out.println("1) Weekday");
        System.out.println("2) Friday");
        System.out.println("3) Saturday");
        int day = getInputInt();
        if (day == 2) day = 6;
        else if (day == 3) day = 7;
        else if (day != 1){
            printPrettyError("There's no such option");
            return getInputDayType();
        }
        return day;
    }
    protected int getInputDay() {
        System.out.println("Choose day:");
        System.out.println("1) Sunday");
        System.out.println("2) Monday");
        System.out.println("3) Tuesday");
        System.out.println("4) Wednesday");
        System.out.println("5) Thursday");
        System.out.println("6) Friday");
        System.out.println("7) Saturday");
        int day = getInputInt();
        if (day < 1 | day > 7){
            printPrettyError("There's no such option");
            return getInputDay();
        }
        return day;
    }

    protected boolean getInputYesNo() {
        System.out.println("1) Yes");
        System.out.println("2) No");
        System.out.print("Option: ");
        int option = getInputInt();
        if (option == 1)
            return true;
        else if (option == 2)
            return false;
        else{
            printPrettyError("There's no such option");
            return getInputYesNo();
        }
    }

    protected int getInputInt() {
        while (!scanner.hasNextInt()){
            printPrettyError("Please enter a number");
            scanner.next();
        }
        return scanner.nextInt();
    }

    protected double getInputDouble() {
        while (!scanner.hasNextDouble()){
            printPrettyError("Please enter a number");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    protected String getInputString() {
        String output = scanner.next();
        output += scanner.nextLine();
        return output;
    }

    protected String getInputConstraintType() {
        System.out.println("Choose constraint type: ");
        System.out.println("1) Can't");
        System.out.println("2) Want");
        int option = getInputInt();
        if (option == 1)
            return "Cant";
        else if (option == 2)
            return "Want";
        else {
            printPrettyError("There's no such option");
            return getInputConstraintType();
        }
    }

    protected void exit() {
        LogOut();
        printPrettyConfirm("Goodbye!");
        System.exit(0);
    }
}
