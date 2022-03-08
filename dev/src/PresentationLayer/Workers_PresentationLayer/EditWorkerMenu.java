package PresentationLayer.Workers_PresentationLayer;

import BusinessLayer.Workers_BusinessLayer.Workers_Facade;
import BusinessLayer.Workers_BusinessLayer.Responses.ResponseT;
import BusinessLayer.Workers_BusinessLayer.Responses.WorkerResponse;

class EditWorkerMenu extends WorkerMenu {
    EditWorkerMenu(Workers_Facade facade){
        super(facade);
    }
    void run() {
        boolean prev = false;
        while (!prev) {
            printPrettyHeadline("\n\n Edit Worker Menu");
            System.out.println("1) Edit id");
            System.out.println("2) Edit name");
            System.out.println("3) Edit salary");
            System.out.println("4) Previous");
            System.out.print("Option: ");
            int option = getInputInt();
            switch (option) {
                case 1:
                    editWorkerId();
                    break;
                case 2:
                    editWorkerName();
                    break;
                case 3:
                    editWorkerSalary();
                    break;
                case 4:
                    prev = true;
                    break;
                default:
                    System.out.println("No such option");
            }
        }
    }

    private void editWorkerId() {
        String ID = getInputWorkerID();
        System.out.println("Enter new worker id: ");
        String newID = getInputString();
        ResponseT<WorkerResponse> workerResponse = facade.setWorkerID(ID, newID);
        if (workerResponse.ErrorOccurred())
            printPrettyError(workerResponse.getErrorMessage());
        else{
            printPrettyConfirm("Edited ID successfully");
        }
    }

    private void editWorkerName() {
        String ID = getInputWorkerID();
        System.out.println("Enter new worker name: ");
        String name = getInputString();
        ResponseT<WorkerResponse> workerResponse = facade.setWorkerName(ID, name);
        if (workerResponse.ErrorOccurred())
            printPrettyError(workerResponse.getErrorMessage());
        else{
            printPrettyConfirm("Edited name successfully for worker " + workerResponse.value.getNameID());
        }
    }

    private void editWorkerSalary() {
        String ID = getInputWorkerID();
        System.out.println("Enter new worker id: ");
        double salary = getInputDouble();
        ResponseT<WorkerResponse> workerResponse = facade.setWorkerSalary(ID, salary);
        if (workerResponse.ErrorOccurred())
            printPrettyError(workerResponse.getErrorMessage());
        else{
            printPrettyConfirm("Edited salary successfully for worker " + workerResponse.value.getNameID());
        }
    }


}