package PresentationLayer.Workers_PresentationLayer;

import BusinessLayer.Workers_BusinessLayer.Workers_Facade;
import BusinessLayer.Workers_BusinessLayer.Responses.Response;
import BusinessLayer.Workers_BusinessLayer.Responses.ResponseT;
import BusinessLayer.Workers_BusinessLayer.Responses.ShiftResponse;
import BusinessLayer.Workers_BusinessLayer.Responses.WorkerResponse;

import java.util.List;

class EditShiftsMenu extends HRManagerMenu {
    EditShiftsMenu(Workers_Facade facade){
        super(facade);
    }
    void run() {
        boolean prev = false;
        while (!prev) {
            printPrettyHeadline("\n\n Edit Shift Menu");
            System.out.println("1) View current arrangement");
            System.out.println("2) Add worker to shift");
            System.out.println("3) Remove worker from shift");
            System.out.println("4) Add required job");
            System.out.println("5) Edit job required amount");
            System.out.println("6) Get available workers");
            System.out.println("7) Approve shift");
            System.out.println("8) Previous");
            System.out.print("Option: ");
            int option = getInputInt();
            switch (option) {
                case 1:
                    viewCurrentArrangement();
                    break;
                case 2:
                    assignWorker();
                    break;
                case 3:
                    removeWorker();
                    break;
                case 4:
                    AddRequiredJob();
                    break;
                case 5:
                    SetRequiredAmount();
                    break;
                case 6:
                    GetAvailableWorkers();
                    break;
                case 7:
                    ApproveShift();
                    break;
                case 8:
                    ExitEditShiftMenu();
                    prev = true;
                    break;
                default:
                    System.out.println("No such option");
            }
        }
    }

    private void viewCurrentArrangement() {
        ResponseT<ShiftResponse> shiftResponse = facade.viewCurrentArrangement();
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm(shiftResponse.value.toString());
        }
    }

    private void assignWorker() {
        String ID = getInputWorkerID();
        String role = getInputJob();
        ResponseT<ShiftResponse> shiftResponse = facade.addWorkerToCurrentShift(ID, role);
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("Worker added successfully");
        }
    }

    private void removeWorker() {
        String ID = getInputWorkerID();
        String role = getInputJob();
        ResponseT<ShiftResponse> shiftResponse = facade.removeWorkerFromCurrentShift(ID, role);
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("Worker removed successfully from the shift.");
        }
    }

    private void AddRequiredJob() {
        String role = getInputJob();
        System.out.print("Amount required: ");
        int required = getInputInt();
        ResponseT<ShiftResponse> shiftResponse = facade.addRequiredJob(role, required);
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("Job "+ role + " added successfully to the shift.");
        }
    }

    private void SetRequiredAmount() {
        String role = getInputJob();
        System.out.print("Amount required: ");
        int required = getInputInt();
        ResponseT<ShiftResponse> shiftResponse = facade.setAmountRequired(role, required);
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else {
            printPrettyConfirm("The amount of workers required for role " + role + " has updated successfully to "+ required);
        }
    }

    private void GetAvailableWorkers() {
        String role = getInputJob();
        ResponseT<List<WorkerResponse>> availableWorkers = facade.getAvailableWorkers(role);
        if (availableWorkers.ErrorOccurred()){
            printPrettyError(availableWorkers.getErrorMessage());
        }
        else {
            if (availableWorkers.value.isEmpty()){
                printPrettyConfirm("No available workers to work as " + role +" at current shift.");
            }
            else {
                for (WorkerResponse workerRes : availableWorkers.value) {
                    printPrettyConfirm(workerRes.getNameID());
                }
            }
        }
    }

    private void ApproveShift() {
        ResponseT<ShiftResponse> shiftResponse = facade.approveShift();
        if (shiftResponse.ErrorOccurred()){
            printPrettyError(shiftResponse.getErrorMessage());
        }
        else{
            if (shiftResponse.value.isFullyOccupied())
                printPrettyConfirm("Shift approved successfully");
            else{
                printPrettyConfirm("Shift approved successfully.");
                printPrettyError("BEWARE: Not all roles are fully occupied.");
            }
        }

    }

    private void ExitEditShiftMenu() {
        Response response = facade.exitShift();
        if (response.ErrorOccurred())
            printPrettyError(response.getErrorMessage());
        else {
            printPrettyConfirm("Exited from shift successfully");
        }
    }


}