package PresentationLayer;

import BusinessLayer.FacedeModel.facade;
import BusinessLayer.GetOccupations_Integration;
import BusinessLayer.Transport_BusinessLayer.Cont.Transport_Facade;
import BusinessLayer.Transport_BusinessLayer.Transport_Integration;
import BusinessLayer.Workers_BusinessLayer.Workers_Facade;
import BusinessLayer.Workers_Integration;
import PresentationLayer.Stock_PresentationLayer.Stock_Main_Menu;
import PresentationLayer.Stock_PresentationLayer.inventPresentation;
import PresentationLayer.Transport_PresentationLayer.Transport_Menu;
import PresentationLayer.Workers_PresentationLayer.Workers_Main_Menu;

import java.io.IOException;
import java.util.*;

public class Main_Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Workers_Facade workers_facade = new Workers_Facade();
    private static final Transport_Facade transport_facade = new Transport_Facade();
    GetOccupations_Integration getOccupations_integration;


    //------------------------------------------------------
    private SupplierFunctionality supplierFunctionality;
    private  OrderFunctionality orderFunctionality;
    private Stock_Main_Menu InventPresentation;
    private  Workers_Main_Menu workers_menu;
    private  Transport_Menu transport_menu;


    facade Facade;

    //------------------------------------------------------

    public Main_Menu(){
        //-------------------------------
        Workers_Integration wk = workers_facade;
        getOccupations_integration= workers_facade;

        Transport_Integration transport_integration=transport_facade;
        InventPresentation=new Stock_Main_Menu(transport_integration,getOccupations_integration);

         workers_menu = new Workers_Main_Menu(workers_facade);
         transport_menu = new Transport_Menu(transport_facade);

        transport_menu.addWorkersIntegration(wk);

        transport_menu.addWorkersIntegration(wk);
        Facade=facade.getInstance(transport_integration,getOccupations_integration);

        //-------------------------------

        supplierFunctionality=new SupplierFunctionality(Facade);
        orderFunctionality=new OrderFunctionality(Facade);

    }
    public void start() throws IOException {
        System.out.println('\n' + "----------------------------------------------------------");
        System.out.println('\n' +
                "0000  0  0  0000  0000  0000              0    0000  0000" + '\n' +
                "0     0  0  0  0  0     0  0              0    0     0   "+ '\n' +
                "0000  0  0  0000  0000  000     000000    0    0000  0000"+ '\n' +
                "   0  0  0  0     0     0 0               0    0     0   "+ '\n' +
                "0000  0000  0     0000  0  0              0000 0000  0000" + '\n');
        System.out.println("----------------------------------------------------------" + '\n');
        System.out.println("Welcome to Super-Lee system!");
        boolean run = true;
        while (run) {
            System.out.println("1) Enter workers manage system");
            System.out.println("2) Enter transport manage system");
            System.out.println("3) Enter Supplier manage system");
            System.out.println("4) Enter Order manage system");
            System.out.println("5) Enter Invent manage system");
            System.out.println("6) Exit");
            System.out.print("Option: ");
            int option = getInputInt();
            switch (option) {
                case 1:
                    workers_menu.start();
                    break;
                case 2:
                    transport_menu.mainMenu();
                    break;
                case  3:
                    supplierFunctionality.SupplierFunctionalityMenu(getOccupations_integration);
                    break;
                case 4:
                    orderFunctionality.OrderFunctionalityMenu(getOccupations_integration);
                    break;
                case 5:
                    InventPresentation.start(getOccupations_integration);
                    break;
                case 6:
                    run = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("No such option");
            }
        }

    }


    private int getInputInt() {
        while (!scanner.hasNextInt()){
            System.out.println("Please enter a number");
            scanner.next();
        }
        return scanner.nextInt();
    }
}
