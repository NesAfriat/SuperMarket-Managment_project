package PresentationLayer.Workers_PresentationLayer;

import BusinessLayer.Workers_BusinessLayer.Workers_Facade;
import BusinessLayer.Workers_BusinessLayer.Responses.ResponseT;
import BusinessLayer.Workers_BusinessLayer.Responses.WorkDayResponse;

class AddShiftsMenu extends HRManagerMenu {
    AddShiftsMenu(Workers_Facade facade){
        super(facade);
    }

    void run(){
        boolean prev = false;
        while (!prev) {
            printPrettyHeadline("\n\nAdd Shifts Menu");
            System.out.println("1) Add default shift");
            System.out.println("2) Add default workday");
            System.out.println("3) View default shift settings");
            System.out.println("4) View default workday settings");
            System.out.println("5) Previous");
           // System.out.println("6) Exit");
            System.out.print("Option: ");
            int option = getInputInt();
            switch (option) {
                case 1:
                    addDefaultShift();
                    break;
                case 2:
                    addDefaultWorkDay();
                    break;
                case 3:
                    getDefaultShift();
                    break;
                case 4:
                    getDefaultWorkDay();
                    break;
                case 5:
                    prev = true;
                    break;
//                case 6:
//                    super.exit();
                default:
                    System.out.println("No such option");
            }
        }
    }

    private void addDefaultShift() {
        String date = getInputDate();
        String shiftType = getInputShiftType();
        ResponseT<WorkDayResponse> workDayResponse = facade.addDefaultShift(date, shiftType);
        if (workDayResponse.ErrorOccurred()){
            printPrettyError(workDayResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm(shiftType + " shift added successfully to workday at " + workDayResponse.value.getDate());
        }
    }


    private void addDefaultWorkDay() {
        String date = getInputDate();
        ResponseT<WorkDayResponse> workDayResponse = facade.addDefaultWorkDay(date);
        if (workDayResponse.ErrorOccurred()){
            printPrettyError(workDayResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("A workday added successfully at " + workDayResponse.value.getDate());
        }
    }
}
