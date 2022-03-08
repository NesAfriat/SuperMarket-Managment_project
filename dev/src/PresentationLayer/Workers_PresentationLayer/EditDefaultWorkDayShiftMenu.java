package PresentationLayer.Workers_PresentationLayer;

import BusinessLayer.Workers_BusinessLayer.Workers_Facade;
import BusinessLayer.Workers_BusinessLayer.Responses.Response;

class EditDefaultWorkDayShiftMenu extends HRManagerMenu {
    EditDefaultWorkDayShiftMenu(Workers_Facade facade){
        super(facade);
    }
    void run(){
        boolean prev = false;
        while (!prev) {
            printPrettyHeadline("\n\nEdit Default Settings Menu");
            System.out.println("1) Get shift default settings");
            System.out.println("2) Edit shift default");
            System.out.println("3) Get workday default settings");
            System.out.println("4) Edit workday default");
            System.out.println("5) Previous");
           // System.out.println("6) Exit");
            System.out.print("Option: ");
            int option = getInputInt();
            switch (option) {
                case 1:
                    getDefaultShift();
                    break;
                case 2:
                    setDefaultShift();
                    break;
                case 3:
                    getDefaultWorkDay();
                    break;
                case 4:
                    setDefaultWorkDay();
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

    private void setDefaultShift() {
        int day = getInputDayType();
        String shiftType = getInputShiftType();
        String role = getInputJob();
        System.out.println("Enter new amount required: ");
        int amountRequired = getInputInt();
        Response response = facade.setDefaultJobsInShift(day, shiftType, role, amountRequired);
        if (response.ErrorOccurred()){
            printPrettyError(response.getErrorMessage());
        }
        else {
            printPrettyConfirm("New shift default updated successfully");
        }
    }

    private void setDefaultWorkDay() {
        int day = getInputDay();
        System.out.println("Do you want morning shift?");
        boolean hasMorning = getInputYesNo();
        System.out.println("Do you want evening shift? ");
        boolean hasEvening = getInputYesNo();
        Response m_response = facade.setDefaultShiftInDay(day, "Morning", hasMorning);
        Response e_response = facade.setDefaultShiftInDay(day, "Evening", hasEvening);
        if (m_response.ErrorOccurred()){
            printPrettyError(m_response.getErrorMessage());
        }
        if (e_response.ErrorOccurred())
            printPrettyError(e_response.getErrorMessage());
        if (!m_response.ErrorOccurred() & !e_response.ErrorOccurred()){
            printPrettyConfirm("New workday setting updated successfully");
        }
    }

}
