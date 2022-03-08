package PresentationLayer;

import BusinessLayer.FacedeModel.Objects.Response;
import BusinessLayer.FacedeModel.Objects.ResponseT;
import BusinessLayer.FacedeModel.Objects.orderResponse;
import BusinessLayer.FacedeModel.facade;
import BusinessLayer.GetOccupations_Integration;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class OrderFunctionality {
    facade facade;
    public OrderFunctionality(facade facade){
        this.facade=facade;
    }
    public void OrderFunctionalityMenu(GetOccupations_Integration getOccupations_integration) {
        System.out.printf("  ____          _           ______                _   _                   _ _ _         \n" +
                " / __ \\        | |         |  ____|              | | (_)                 | (_) |        \n" +
                "| |  | |_ __ __| | ___ _ __| |__ _   _ _ __   ___| |_ _  ___  _ __   __ _| |_| |_ _   _ \n" +
                "| |  | | '__/ _` |/ _ \\ '__|  __| | | | '_ \\ / __| __| |/ _ \\| '_ \\ / _` | | | __| | | |\n" +
                "| |__| | | | (_| |  __/ |  | |  | |_| | | | | (__| |_| | (_) | | | | (_| | | | |_| |_| |\n" +
                " \\____/|_|  \\__,_|\\___|_|  |_|   \\__,_|_| |_|\\___|\\__|_|\\___/|_| |_|\\__,_|_|_|\\__|\\__, |\n" +
                "                                                                                   __/ |\n" +
                "                                                                                  |___/ \n");
        String[] SupplierFunctionality = new String[]{"add New Order", "remove Order", "Print Specific Order", "print All Orders", "add Product To Order","change Product Quantity From Order","create order Due to lack","go back"};
        while (true) {
            System.out.println("please choose one option : ");
            System.out.println("\n");
            for (int i = 0; i < SupplierFunctionality.length; i++) {
                System.out.println(i + 1 + ")" + " " + SupplierFunctionality[i]);

            }
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            String Option = myObj.nextLine();
            switch (Option) {
                case "1":
                    addNewOrder();
                    break;
                case "2":
                    removeOrder();
                    break;
                case "3":
                    PrintSpecificOrder();
                    break;
                case "4":
                    printAllOrders();
                    break;
                case "5":
                    addProductToOrder();
                    break;
                case "6":
                    changeProductQuantityFromOrder();
                    break;
                case "7":
                    create_order_Due_to_lack();
                    break;
                case "8":
                    return;
                default:
                    System.out.println("Not within bounds");



            }

        }}

    //this function remove an order from the system
    //public Response removeOrder(int SupId)
    public void removeOrder () {

        Scanner myObj1 = new Scanner(System.in);
        System.out.println("please type your id");
        String id=myObj1.nextLine();
        BusinessLayer.Workers_BusinessLayer.Responses.ResponseT< List<Job> > jobs =facade.getOccupations_integration().getWorkerOccupations(id);
        if(jobs.ErrorOccurred()){
            System.out.println(jobs.getErrorMessage());
            return;
        }
        else{
            if (!jobs.value.contains(Job.Storekeeper)||!jobs.value.contains(Job.HR_Manager)||!jobs.value.contains(Job.Store_Manager)) {
                System.out.println("Only a Store_Manager or hr manager or storkeeper is allowed to manage Order");
                return;
            }
        }


        System.out.println("please type the order id");
        int orderId = NumberType();
        System.out.println("please type the sup id");
        int supId = NumberType();
        Response r = facade.removeOrder(orderId, supId);
        if (r.getErrorOccurred()) {
            System.out.println(r.getErrorMsg());
        }
    }

    public void create_order_Due_to_lack () {
        Scanner myObj1 = new Scanner(System.in);
        System.out.println("please type your id");
        String id=myObj1.nextLine();
        BusinessLayer.Workers_BusinessLayer.Responses.ResponseT< List<Job> > jobs =facade.getOccupations_integration().getWorkerOccupations(id);
        if(jobs.ErrorOccurred()){
            System.out.println(jobs.getErrorMessage());
            return;
        }
        else{
            if (!jobs.value.contains(Job.Store_Manager)) {
                System.out.println("Only a Store_Manager is allowed to manage Order");
                return;
            }
        }


        Response r = facade.create_order_Due_to_lack();
        if (r.getErrorOccurred()) {
            System.out.println(r.getErrorMsg());
        }
        else System.out.println("order completed");
    }


    //public Response addProductToOrder(int SupId,int OrderId, int CatalogID, int quantity);
    public void addProductToOrder(){
        Scanner myObj1 = new Scanner(System.in);
        System.out.println("please type your id");
        String id=myObj1.nextLine();
        BusinessLayer.Workers_BusinessLayer.Responses.ResponseT< List<Job> > jobs =facade.getOccupations_integration().getWorkerOccupations(id);
        if(jobs.ErrorOccurred()){
            System.out.println(jobs.getErrorMessage());
            return;
        }
        else{
            if (!jobs.value.contains(Job.Store_Manager)) {
                System.out.println("Only a Store_Manager is allowed to manage Order");
                return;
            }
        }


        System.out.println("please type the supplier id");
        int Supid=NumberType();
        System.out.println("please type the Order id");
        int OrderId=NumberType();
        System.out.println("please type the Catalog id of the product");
        int CatalogId=NumberType();
        System.out.println("please type the quantity of the product");
        int quantity=NumberType();
        Response r=facade.addProductToOrder(Supid,OrderId,CatalogId,quantity);
        if(r.getErrorOccurred()){
            System.out.println(r.getErrorMsg());
        }
        else{System.out.println("Product added successfully\n");}

    }
    public void changeProductQuantityFromOrder(){
        Scanner myObj1 = new Scanner(System.in);
        System.out.println("please type your id");
        String id=myObj1.nextLine();
        BusinessLayer.Workers_BusinessLayer.Responses.ResponseT< List<Job> > jobs =facade.getOccupations_integration().getWorkerOccupations(id);
        if(jobs.ErrorOccurred()){
            System.out.println(jobs.getErrorMessage());
            return;
        }
        else{
            if (!jobs.value.contains(Job.Store_Manager)) {
                System.out.println("Only a Store_Manager is allowed to manage Order");
                return;
            }
        }


        System.out.println("please type the supplier id");
        int Supid=NumberType();
        System.out.println("please type the Order id");
        int OrderId=NumberType();
        System.out.println("please type the Catalog id of the product");
        int CatalogId=NumberType();
        System.out.println("please type the new quantity of the product");
        int quantity=NumberType();
        Response r=facade.changeProductQuantityFromOrder(Supid,OrderId,CatalogId,quantity);
        if(r.getErrorOccurred()){
            System.out.println(r.getErrorMsg());
        }
        else{System.out.println("Product Quantity changed successfully\n");}

    }
    //int id, int supplierid, LocalDate date, HashMap<Integer, Integer> quantity, double totalPayment,boolean isConstant
    //this function return a response of specific order
    public void PrintSpecificOrder(){
        Scanner myObj1 = new Scanner(System.in);
        System.out.println("please type your id");
        String id_=myObj1.nextLine();
        BusinessLayer.Workers_BusinessLayer.Responses.ResponseT< List<Job> > jobs =facade.getOccupations_integration().getWorkerOccupations(id_);
        if(jobs.ErrorOccurred()){
            System.out.println(jobs.getErrorMessage());
            return;
        }
        else{
            if (!jobs.value.contains(Job.Store_Manager)) {
                System.out.println("Only a Store_Manager is allowed to manage Order");
                return;
            }
        }


        System.out.println("please type the Order id");
        int OrderId=NumberType();
        System.out.println("please type the sup id");
        int supId=NumberType();

        ResponseT<orderResponse> response=facade.getOrder(OrderId,supId );

        if(response.getErrorOccurred()){
            System.out.println(response.getErrorMsg());
        }else {
            orderResponse orderResponse=response.getValue();
            int id=orderResponse.id;
            int SupplierId=orderResponse.Supplierid;
            HashMap<Integer,Integer> products=orderResponse.quantity;
            LocalDate date=orderResponse.date;
            double Totalpayment=orderResponse.TotalPayment;
            boolean isConstant=orderResponse.isConstant;
            System.out.printf("%-22s%-22s%-22s%-22s%-22s\n","id","SupplierId","date","Totalpayment","IFConstant");
            System.out.printf("%-22d%-22d%-22s%-22s%-22b\n",id,SupplierId,date.toString(),Totalpayment,isConstant);
            //PRINT ALL PRODUCR IN THE ORDER
            System.out.printf("%-22s%-22s\n","productId","product Quantity");

            for (Integer p:products.keySet()) {
                System.out.printf("%-22d%-22d\n",p,products.get(p));
            }

        }
    }


    //this function return a response of all the orders in the system
    //public ResponseT getAllOrders();
    public void printAllOrders(){
        Scanner myObj1 = new Scanner(System.in);
        System.out.println("please type your id");
        String id_=myObj1.nextLine();
        BusinessLayer.Workers_BusinessLayer.Responses.ResponseT< List<Job> > jobs =facade.getOccupations_integration().getWorkerOccupations(id_);
        if(jobs.ErrorOccurred()){
            System.out.println(jobs.getErrorMessage());
            return;
        }
        else{
            if (!jobs.value.contains(Job.Store_Manager)) {
                System.out.println("Only a Store_Manager is allowed to manage Order");
                return;
            }
        }


        ResponseT<List<orderResponse>> response=facade.getAllOrders();
        if(response.getErrorOccurred()){
            System.out.print(response.getErrorMsg());
        }
        else {
            List<orderResponse> orderResponseList=response.getValue();
            for (orderResponse orderResponse:orderResponseList) {
                int id=orderResponse.id;
                int SupplierId=orderResponse.Supplierid;
                HashMap<Integer,Integer> products=orderResponse.quantity;
                LocalDate date=orderResponse.date;
                double Totalpayment=orderResponse.TotalPayment;
                boolean isConstant=orderResponse.isConstant;
                System.out.printf("%-22s%-22s%-22s%-22s%-22s\n","order id","Supplier Id","date","Total payment","Constant");
                System.out.printf("%-22d%-22d%-22s%-22s%-22b\n",id,SupplierId,date.toString(),Totalpayment,isConstant);
                //PRINT ALL PRODUCR IN THE ORDER
                System.out.printf("%-22s%-22s\n","product id","product Quantity");

                for (Integer p:products.keySet()
                ) {
                    System.out.printf("%-22d%-22d\n",p,products.get(p));
                }

            }
        }
    }

    //    public Response addNewOrder(int SupId,HashMap<Integer,Integer> productQuantity);
    public void addNewOrder(){
        Scanner myObj1 = new Scanner(System.in);
        System.out.println("please type your id");
        String id_=myObj1.nextLine();
        BusinessLayer.Workers_BusinessLayer.Responses.ResponseT< List<Job> > jobs =facade.getOccupations_integration().getWorkerOccupations(id_);
        if(jobs.ErrorOccurred()){
            System.out.println(jobs.getErrorMessage());
            return;
        }
        else{
            if (!jobs.value.contains(Job.Store_Manager)) {
                System.out.println("Only a Store_Manager is allowed to manage Order");
                return;
            }
        }



        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("please type the supplier id");
        int id=NumberType();
        HashMap<Integer,Integer> productQuantity=new HashMap<>();
        System.out.println("now please type the products in the order.");
        int catalogId=-1;
        int start=1;
        int quantity=-1;
        boolean tocontinue=true;
        Response response;
        while(tocontinue){
            //if(start!=1){
            System.out.println("please type the product catalog Id:");
            catalogId=NumberType();
            System.out.println("please type the product quantity in the order:");
            quantity=NumberType();
            productQuantity.put(catalogId,quantity);

            // now we want to check if the use want to add another product, if not we get out off the loop
            while (true) {
                System.out.println("do you want to insert another product to the order?");
                System.out.println("1) yes");
                System.out.println("2) no");
                int cont = NumberType();
                if (cont != 1 && cont != 2) {
                    System.out.println("please try again! ");
                }
                if(cont==2){
                    tocontinue=false;
                    break;
                }
                else break;
            }

            //}
        }
        System.out.println("is the order constant?");
        System.out.println("1) yes");
        System.out.println("2) no");
        int isConstat=1;
         while (true) {
             isConstat = NumberType();
             if (isConstat != 1 && isConstat != 2) {
                 System.out.println("try again!");
             }
             break;
         }

            if(isConstat==1){
                System.out.println("please type the constant day for the order");
                int day=0;
                while (true) {
                    day = NumberType();
                    if (isConstat>= 1 && isConstat <=7 ) {
                        System.out.println("try again!");
                    }
                    break;
                }
                Response r=facade.addNewOrder(id,productQuantity,true,day);
                if(r.getErrorOccurred()) System.out.println(r.getErrorMsg());
                else{System.out.println("order add changed successfully\n");
                    return;
                }
            }
            else {//==2
                Response r=facade.addNewOrder(id,productQuantity,false,-1);
                if(r.getErrorOccurred()) System.out.println(r.getErrorMsg());
                else{System.out.println("order add changed successfully\n");
                    return;
                }
            }



    }

    private int NumberType() {
        int numb = 0;
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        boolean isOk = false;
        while (!isOk) {
            try {
                return Integer.parseInt(myObj.nextLine());
            }catch (Exception e) {
                System.out.println("Input is not a valid integer, try again");
            }
        }
        return numb;
    }
}
 /*






    //this function return a response of all the orders in the system
    public ResponseT getAllOrders();

  */