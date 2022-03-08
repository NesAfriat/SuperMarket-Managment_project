package PresentationLayer.Workers_PresentationLayer;

import BusinessLayer.Workers_BusinessLayer.Pair;
import BusinessLayer.Workers_BusinessLayer.Workers_Facade;
import BusinessLayer.Workers_BusinessLayer.Responses.*;
import java.util.List;

class HRManagerMenu extends Workers_Main_Menu {
    HRManagerMenu(Workers_Facade facade){
        super(facade);
    }
    void run() {
        boolean prev = false;
        while (!prev) {
            printPrettyHeadline("\n\nHR Manager Menu");
            System.out.println("1) Manage Workers");
            System.out.println("2) Manage Shifts");
            System.out.println("3) Logout");
            //System.out.println("4) Exit");
            System.out.print("Option: ");
            int option = getInputInt();
            switch (option) {
                case 1:
                    new WorkersManageMenu(facade).run();
                    break;
                case 2:
                    ShiftsManageMenu();
                    break;
                case 3:
                    LogOut();
                    prev = true;
                    break;
                default:
                    printPrettyError("No such option");
                    run();
            }
        }
    }



    private void ShiftsManageMenu() {
        boolean prev = false;
        while (!prev) {
            printPrettyHeadline("\n\nShifts Manage Menu");
            System.out.println("1) View shift arrangement");
            System.out.println("2) Edit shift");
            System.out.println("3) Add new shifts");
            System.out.println("4) Remove shift");
            System.out.println("5) Edit default shift/workday");
            System.out.println("6) View requests");
            System.out.println("7) Delete request");
            System.out.println("8) Previous");
            System.out.println("9) Exit");
            System.out.println("10) add request test");
            System.out.print("Option: ");
            int option = getInputInt();
            switch (option) {
                case 1:
                    adminViewShiftArrangement();
                    break;
                case 2:
                    EditShift();
                    break;
                case 3:
                    new AddShiftsMenu(facade).run();
                    break;
                case 4:
                    RemoveShift();
                    break;
                case 5:
                    new EditDefaultWorkDayShiftMenu(facade).run();
                    break;
                case 6:
                    viewAllRequests();
                    break;
                case 7:
                    deleteRequest();
                    break;
                case 8:
                    prev = true;
                    break;
                case 9:
                    super.exit();
                default:
                    printPrettyError("No such option");
            }
        }
    }


    private void deleteRequest() {
        System.out.println("Enter Order ID: ");
        int orderID = getInputInt();
        String date = getInputDate();
        Response result = facade.removeRequest(orderID, date);
        if (result.ErrorOccurred()){
            printPrettyError(result.getErrorMessage());
        }
        else {
            printPrettyConfirm("Request removed successfully");
        }
    }

    private void viewAllRequests() {
        ResponseT<List<Pair>> requests = facade.getRequests();
        if (requests.ErrorOccurred()){
            printPrettyError(requests.getErrorMessage());
        }
        else {
            int i = 1;
            if (requests.value.isEmpty()){
                printPrettyConfirm("Woohoo! There's no requests at the moment.");
            }
            for (Pair request : requests.value){
                printPrettyConfirm(i + ")" + " Order ID: " + request.getKey() + "\tDate: " + request.getValue());
                i++;
            }
        }
    }

    private void EditShift() {
        String date = getInputDate();
        String shiftType = getInputShiftType();

        ResponseT<ShiftResponse> shiftResponse = facade.chooseShift(date, shiftType);
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("Shift selected successfully");
            new EditShiftsMenu(facade).run();
        }
    }

    private void adminViewShiftArrangement() {
        String date = getInputDate();
        ResponseT<WorkDayResponse> workDay = facade.viewShiftArrangement(date);
        if (workDay.ErrorOccurred()){
            printPrettyError(workDay.getErrorMessage());
        }
        else {
            printPrettyConfirm(workDay.value.toString());
        }
    }

    private void RemoveShift(){
        String date = getInputDate();
        String shiftType = getInputShiftType();
        ResponseT<ShiftResponse> shiftResponse = facade.removeShift(date, shiftType);
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm(shiftType + " Shift removed successfully at date: " + date);
        }
    }



}
