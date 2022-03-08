package PresentationLayer.Workers_PresentationLayer;

import BusinessLayer.Workers_BusinessLayer.Workers_Facade;
import BusinessLayer.Workers_BusinessLayer.Responses.ConstraintResponse;
import BusinessLayer.Workers_BusinessLayer.Responses.ResponseT;
import BusinessLayer.Workers_BusinessLayer.Responses.WorkDayResponse;
import BusinessLayer.Workers_BusinessLayer.Responses.WorkerResponse;

import java.util.List;

class WorkerMenu extends Workers_Main_Menu {
    WorkerMenu(Workers_Facade facade){
        super(facade);
    }
     void run() {
         boolean prev = false;
         while (!prev) {
             printPrettyHeadline("\n\nWorker Menu");
             System.out.println("1) View shift arrangement");
             System.out.println("2) View constraints");
             System.out.println("3) Add constraint");
             System.out.println("4) Remove constraint");
             System.out.println("5) Logout");
             //System.out.println("6) Exit");
             System.out.print("Option: ");
             int option = getInputInt();
             switch (option) {
                 case 1:
                     nonAdminViewShiftArrangement();
                     break;
                 case 2:
                     viewLoggedWorkerConstraints();
                     break;
                 case 3:
                     addConstraint();
                     break;
                 case 4:
                     removeConstraint();
                     break;
                 case 5:
                     LogOut();
                     prev = true;
                     break;
//                 case 6:
//                     super.exit();
                 default:
                     printPrettyError("No such option");
             }
         }
    }

    private void nonAdminViewShiftArrangement() {
        String date = getInputDate();
        ResponseT<WorkDayResponse> workDay = facade.viewShiftArrangement(date);
        if (workDay.ErrorOccurred()){
            printPrettyError(workDay.getErrorMessage());
        }
        else {
            printPrettyConfirm(workDay.value.approvedToString());
        }
    }


    private void removeConstraint() {
        String date = getInputDate();
        String shiftType = getInputShiftType();
        ResponseT<ConstraintResponse> constraint = facade.removeConstraint(date, shiftType);
        if (constraint.ErrorOccurred()){
            printPrettyError(constraint.getErrorMessage());
        }
        else {
            printPrettyConfirm("Constraint removed successfully, details: ");
            printPrettyConfirm(constraint.value.toString());
        }
    }


    private void addConstraint() {
        String date = getInputDate();
        String shiftType = getInputShiftType();
        String constraintType = getInputConstraintType();
        ResponseT<ConstraintResponse> constraint = facade.addConstraint(date, shiftType, constraintType);
        if (constraint.ErrorOccurred()){
            printPrettyError(constraint.getErrorMessage());
        }
        else {
            printPrettyConfirm("Constraint added successfully, details: ");
            printPrettyConfirm(constraint.value.toString());
        }
    }



    private void viewLoggedWorkerConstraints() {
        ResponseT<WorkerResponse> worker = facade.getLoggedWorker();
        if (worker.ErrorOccurred()){
            printPrettyError(worker.getErrorMessage());
        }
        else {
            List<ConstraintResponse> constraintResponseList = worker.value.getConstraints();
            for (ConstraintResponse constraint: constraintResponseList){
                printPrettyConfirm(constraint.toString());
            }
        }
    }

}
