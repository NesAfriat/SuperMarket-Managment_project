//package PresentationLayer;
//import BusinessLayer.FacedeModel.facade;
//import BusinessLayer.Transport_BusinessLayer.Cont.Transport_Facade;
//import BusinessLayer.Transport_BusinessLayer.Transport_Integration;
//import BusinessLayer.Workers_BusinessLayer.Workers_Facade;
//import BusinessLayer.Workers_Integration;
//import PresentationLayer.Transport_PresentationLayer.Transport_Menu;
//import PresentationLayer.Workers_PresentationLayer.Workers_Main_Menu;
//
//import java.util.*;
//
//public class IO {
//    private static IO single_instance = null;
//    private SupplierFunctionality supplierFunctionality;
//    private  OrderFunctionality orderFunctionality;
//    private  inventPresentation InventPresentation;
//
//    private static final Workers_Facade workers_facade = new Workers_Facade();
//    private static final Transport_Facade transport_facade = new Transport_Facade();
//
//    facade Facade;
//
//    private IO(){
//        Transport_Integration transport_integration=transport_facade;
//        InventPresentation=new inventPresentation(transport_integration);
//        Facade=facade.getInstance(transport_integration);
//        Workers_Main_Menu workers_menu = new Workers_Main_Menu(workers_facade);
//        Transport_Menu transport_menu = new Transport_Menu(transport_facade);
//        Workers_Integration wk = workers_facade;
//        transport_menu.addWorkersIntegration(wk);
//
//        supplierFunctionality=new SupplierFunctionality(Facade);
//        orderFunctionality=new OrderFunctionality(Facade);
//
//    }
//    public static IO getInstance()
//    {
//
//        if (single_instance == null)
//            single_instance = new IO();
//
//
//
//        return single_instance;
//    }
//
//    public void Start_Menu() throws Exception {
//        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
//        String[] arrFunctionality=new String[]{"Supplier Functionality","order Functionality","inventory Functionality","Load Data","exit"};
////        System.out.println("__  _  __ ____ |  |   ____  ____   _____   ____  \n" +
////                "\\ \\/ \\/ // __ \\|  | _/ ___\\/  _ \\ /     \\_/ __ \\ \n" +
////                " \\     /\\  ___/|  |_\\  \\__(  <_> )  Y Y  \\  ___/ \n" +
////                "  \\/\\_/  \\___  >____/\\___  >____/|__|_|  /\\___  >\n" +
////                "             \\/          \\/            \\/     \\/ ");
////        System.out.println("\n");
//
//        while(true) {
//            System.out.println("please choose one option : ");
//            System.out.println("\n");
//
//            for (int i = 0; i < arrFunctionality.length; i++) {
//                System.out.println(i + 1 + ")" + " " + arrFunctionality[i]);
//            }
//            String Option = myObj.nextLine();  // Read user input
//            switch (Option) {
//                case "1":
//                    supplierFunctionality.SupplierFunctionalityMenu();
//                    break;
//                case "2":
//                    orderFunctionality.OrderFunctionalityMenu();
//                    break;
//                case "3":
//                    InventPresentation.main_window();
//                    break;
//
//                case "4":
//
//                   break;
//
//                case "5":
//                    return;
//                default:
//                    System.out.println("Not within bounds");
//            }
//        }
//
//
//    }//    public Response addNewSupplier(int id, String name, String bankAccount, paymentMethods paymentMethods,DeliveryMode deliveryMode,List<Integer> daysOfDelivery,int NumOfDaysFromDelivery)
//
//
//
//
//
//
//
//}
