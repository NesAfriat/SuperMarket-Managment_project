package PresentationLayer;

import BusinessLayer.DeliveryMode;
import BusinessLayer.Workers_BusinessLayer.Responses.ResponseT;

import BusinessLayer.FacedeModel.Objects.*;
import BusinessLayer.FacedeModel.facade;
import BusinessLayer.GetOccupations_Integration;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;
import BusinessLayer.paymentMethods;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SupplierFunctionality {
    facade facade;
    public  SupplierFunctionality(facade facade){
        this.facade=facade;
    }
    //        String[] SupplierFunctionality=new String[]{"Print All Supplier cards","print specific Supplier card","Add new Supplier","add New Product To Supplier",
//        "remove Product From Supplier","add New Discount By Quantity To Product","Remove Supplier","set Extra Discount To Supplier","add New Contact Member",
//        "print supplier agreement","set Product Price","set Supplier Payment","Remove Discount by Quantity of Product","Remove Contact","Set Delivery Mode","go back"};
    public void SupplierFunctionalityMenu(GetOccupations_Integration getOccupations_integration){
        String[] SupplierFunctionality=new String[]{"Add new Supplier","Remove Supplier","print specific Supplier card","Print All Supplier cards","add New Product To Supplier","remove Product From Supplier","add New Discount By Quantity To Product","Remove Discount by Quantity of Product","add New Contact Member","Remove Contact","set Product Price","set Supplier Payment","Set Delivery Mode","set Extra Discount To Supplier","print supplier agreement","go back"};
        System.out.println("\n" +
                " __                   _ _               ___                 _   _                   _ _ _         \n" +
                "/ _\\_   _ _ __  _ __ | (_) ___ _ __    / __\\   _ _ __   ___| |_(_) ___  _ __   __ _| (_) |_ _   _ \n" +
                "\\ \\| | | | '_ \\| '_ \\| | |/ _ \\ '__|  / _\\| | | | '_ \\ / __| __| |/ _ \\| '_ \\ / _` | | | __| | | |\n" +
                "_\\ \\ |_| | |_) | |_) | | |  __/ |    / /  | |_| | | | | (__| |_| | (_) | | | | (_| | | | |_| |_| |\n" +
                "\\__/\\__,_| .__/| .__/|_|_|\\___|_|    \\/    \\__,_|_| |_|\\___|\\__|_|\\___/|_| |_|\\__,_|_|_|\\__|\\__, |\n" +
                "         |_|   |_|                                                                          |___/ \n" +
                "\n" +
                "\n");

        while(true){
            Scanner myObj1 = new Scanner(System.in);
            System.out.println("please type your id");
            String id=myObj1.nextLine();
            ResponseT< List<Job> > jobs =facade.getOccupations_integration().getWorkerOccupations(id);
            if(jobs.ErrorOccurred()){
                System.out.println(jobs.getErrorMessage());
                return;
            }
            else{
                if (!jobs.value.contains(Job.Storekeeper) && !jobs.value.contains(Job.Store_Manager)) {
                    System.out.println("Only a storekeeper or Branch_Manager is allowed to manage Supplier");
                    return;
                }
            }

            System.out.println("please choose one option : ");
            System.out.println("\n");
            for(int i=0;i<SupplierFunctionality.length;i++){
                System.out.println(i + 1 + ")" + " " + SupplierFunctionality[i]);

            }
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            String Option = myObj.nextLine();
            switch (Option){
                case "1":
                    addNewSupplier();
                    break;
                case "2":
                    removeSupplier();
                    break;
                case "3":
                    printSupplier();
                    break;
                case "4":
                    printAllSuppliers();
                    break;
                case "5":
                    addNewProductToAgreement();
                    break;
                case "6":
                    removeProductFromSupplier();
                    break;
                case "7":
                    addNewDiscountByQuantitiyToProduct();
                    break;
                case "8":
                    RemveDiscountByQuantity();
                    break;
                case "9":
                    addNewContactMember();
                    break;
                case "10":
                    ReomoveContact();
                    break;
                case "11":
                    setProductPrice();
                    break;
                case "12":
                    SetSupplierPayment();
                    break;
                case "13":
                    SetDeliveryMode();
                    break;
                case "14":
                    setExtraDiscountToSupplier();
                    break;
                case "15":
                    printAgreement();
                    break;
                case "16":
                    return;
                default:
                    System.out.println("Not within bounds");

            }

        }
    }

    public void ReomoveContact(){
        System.out.println("please type the supplier id : ");
        int id=NumberType();
        System.out.println("please type the Contact id : ");
        int contactId=NumberType();
        Response response=facade.RemoveContact(id,contactId);
        if(response.getErrorOccurred()){
            System.out.println(response.getErrorMsg());
        }

    }

    public void RemveDiscountByQuantity(){
        System.out.println("\n");
        System.out.println("please type the supplier id : ");
        int id=NumberType();
        System.out.println("please type the product CatalogID : ");
        int catalogid=NumberType();
        System.out.println("please type product quantity : ");
        int quantity=NumberType();
        Response r=facade.RemovDiscountByQuantitiyToProduct(id,catalogid,quantity);
        if(r.getErrorOccurred()){
            System.out.println(r.getErrorMsg());
        }

    }


    //public Response addNewSupplier(int id, String name, String bankAccount, paymentMethods paymentMethods);
    public void addNewSupplier(){
        System.out.println("\n");

        boolean isOk=false;
        int id=0;
        String name="";
        String bankAccount="";
        paymentMethods paymentMethods;
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        System.out.println("please type the supplier id : ");
        id=NumberType();
        System.out.println("please type the supplier name : ");
        name=myObj.nextLine();
        System.out.println("please type the supplier bankAccount : ");
        bankAccount=myObj.nextLine();
        //choose the supplier paymentmethod
        paymentMethods=SupplierPaymentMethodsSellect();
        DeliveryMode deliveryMode=DeliveryModeSellect();
        Response response;
        int daysF=-1;
        List<Integer> days=null;
        if(deliveryMode==DeliveryMode.DeliveryByDay){
            days=selectDaysOfDelivey();
        }else if(deliveryMode==DeliveryMode.DeliveryAfterOrder){
            System.out.println("please type how much days after the order the delivery will arrive :");
            daysF=NumberType();
        }//pickup
        else{
        }
        System.out.println("now we will add first Contact");
        System.out.println("please type the contactName : ");
        String contactName=myObj.nextLine();
        System.out.println("please type the contactEmail : ");
        String email=myObj.nextLine();
        System.out.println("please type the contact Phone Number : ");
        String phoneNumber=myObj.nextLine();
        //check if response have an eror message
        response=facade.addNewSupplier(id,name,bankAccount,paymentMethods,deliveryMode,days,daysF,contactName,email,phoneNumber);
        if(response.getErrorOccurred()){
            System.out.println(response.getErrorMsg());
        }
        else {
            System.out.println("the supplier add sucssefuly");
        }


    }

    private List<Integer> selectDaysOfDelivey(){
        System.out.println("\n");

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        List<Integer> days=new LinkedList<>();
        System.out.println("1) sunday");
        System.out.println("2) Monday");
        System.out.println("3) Thuesday");
        System.out.println("4) Wenesday");
        System.out.println("5) Thursday");
        System.out.println("6) Friday");
        System.out.println("7) Saturday");
        while(true){
            System.out.println("please type a day to delivery : ");
            System.out.println("if You finish please type the number 0 : ");
            int day=NumberType();
            if(day==0){
                return days;
            }
            else if(day<=7&&day>=1){
                if(days.contains(day)){
                    System.out.println("the day is already add");
                }
                else {
                    days.add(day);
                }
            }
            else{
                System.out.println("try again!");
            }
        }

    }

    //public Response removeSupplier(int SupId);
    public void removeSupplier(){
        System.out.println("\n");

        System.out.println("please type the supplier id : ");
        int id =NumberType();
        Response response=facade.removeSupplier(id);
        if(response.getErrorOccurred()){
            System.out.println(response.getErrorMsg());
        }
        else   {
            System.out.println("Supplier removed successfully\n");

        }

    }


    //public Response setSupplierPayment(paymentMethods paymentMethods, int SupplierId);
    public void SetSupplierPayment(){
        System.out.println("\n");

        System.out.println("please type the supplier id : ");
        int id=NumberType();
        paymentMethods paymentMethods=SupplierPaymentMethodsSellect();
        Response response=facade.setSupplierPayment(paymentMethods,id);
        if(response.getErrorOccurred()){
            System.out.println(response.getErrorMsg());
        }
        else   System.out.println("Supplier Payment Set successfully\n");

    }


    //public Response addNewProductToAgreement(int SupplierId,int Price, int CatalogID, String manfucator, String name);
    public void addNewProductToAgreement(){
        System.out.println("\n");

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("please type the supplier id : ");
        int id=NumberType();
        System.out.println("please type the item price : ");
        double price=doubleType();
        System.out.println("please type item CatalogId : ");
        int catalogId=NumberType();
        System.out.println("is product already exsist in the stock ? : ");
        boolean isExsist=trueOrFlae();
        String manfucator="Def";
        String name="DEF";
        String category="Def";
        if(!isExsist) {
            System.out.println("please type item manfucator : ");
            manfucator = myObj.nextLine();
            System.out.println("please type item name : ");
            name = myObj.nextLine();
            System.out.println("please type the product category : ");
            category = myObj.nextLine();
        }
        if(isExsist){
            System.out.println("please type the id of the product in stock");
            int general_product_id=NumberType();
            Response response= facade.addNewProductToAgreement(id,price,catalogId,manfucator,name,category,general_product_id,isExsist);
            if(response.getErrorOccurred()){
                System.out.println((response.getErrorMsg()));
            }
            else  System.out.println("Product added successfully\n");

        }
        else
        {
            Response response= facade.addNewProductToAgreement(id,price,catalogId,manfucator,name,category,-1,isExsist);
            if(response.getErrorOccurred()){
                System.out.println((response.getErrorMsg()));
            }
            else   System.out.println("Product added successfully\n");

        }
    }


    private boolean trueOrFlae(){
        while(true) {
            System.out.println("1. yes ");
            System.out.println("2. no ");
            int x = NumberType();
            if (x == 1) {
                return true;
            }
            if (x == 2)
                return false;
        }
    }

    //public Response removeProductFromSupplier(int SupId, int CatalogID);
    public void removeProductFromSupplier(){
        System.out.println("\n");

        System.out.println("please type the supplier id : ");
        int id=NumberType();
        System.out.println("please type item CatalogId : ");
        int catalogId=NumberType();
        Response response=facade.removeProductFromSupplier(id,catalogId);

        if(response.getErrorOccurred()){
            System.out.println((response.getErrorMsg()));
        }
        else
        {
            System.out.println("Product removed  successfully\n");
            Response response2=facade.RemovePrudactFromOrders(id,catalogId);
            if(response2.getErrorOccurred()){
                System.out.println((response2.getErrorMsg()));
            }
            else
                System.out.println("Product removed from all the Constant orders successfully\n");
        }
    }

    //    public Response setProductPrice(int SupId, int CatalogID, double price);
    public void setProductPrice(){
        System.out.println("\n");

        System.out.println("please type the supplier id : ");
        int id=NumberType();
        System.out.println("please type item CatalogId : ");
        int catalogId=NumberType();
        System.out.println("please type new price: ");
        double price=doubleType();
        Response response=facade.setProductPrice(id,catalogId,price);
        if(response.getErrorOccurred()){
            System.out.println((response.getErrorMsg()));
        }
        else   System.out.println("Product Price set successfully\n");

    }
    //    public Response addNewDiscountByQuantitiyToProduct(int SupId, int CatalogID, int Quantitiy, double newPrice);
    public void addNewDiscountByQuantitiyToProduct()
    {        System.out.println("\n");


        System.out.println("please type the supplier id : ");
        int id=NumberType();
        System.out.println("please type item CatalogId : ");
        int catalogId=NumberType();
        System.out.println("please type item Quantity : ");
        int Quantity=NumberType();
        System.out.println("please type price after discount: ");
        double price=doubleType();
        Response response=facade.addNewDiscountByQuantitiyToProduct(id,catalogId,Quantity,price);
        if(response.getErrorOccurred()){
            System.out.println((response.getErrorMsg()));
        }
        else   System.out.println("Discount added successfully\n");

    }

    public void setExtraDiscountToSupplier(){
        System.out.println("\n");

        System.out.println("please type the supplier id : ");
        int id=NumberType();
        System.out.println("please type Extra Discount (%) : ");
        int dis=NumberType();
        Response response=facade.setExtraDiscountToSupplier(id,dis);
        if(response.getErrorOccurred()){
            System.out.println((response.getErrorMsg()));
        }
        else   System.out.println("Extra Discount set successfully\n");

    }
    //public Response addNewContactMember(int SupId, String contactName, String contactEmail, String phoneNumber);
    public void addNewContactMember(){
        System.out.println("\n");

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("please type the supplier id : ");
        int id=NumberType();
        addNewContactMemberHelper(id);
    }
    //SetDeliveryMode
    public void SetDeliveryMode(){
        System.out.println("please type the supplier id : ");
        int Supid=NumberType();
        DeliveryMode deliveryMode=DeliveryModeSellect();
        Response response;
        int daysF=-1;
        List<Integer> days=null;
        if(deliveryMode==DeliveryMode.DeliveryByDay){
            days=selectDaysOfDelivey();
            response=facade.SetDeliveryMode(Supid,deliveryMode,days,daysF);
        }else if(deliveryMode==DeliveryMode.DeliveryAfterOrder){
            System.out.println("please type how much days after the order the delivery will arrive :");
            daysF=NumberType();
            response=facade.SetDeliveryMode(Supid,deliveryMode,days,daysF);
        }//pickup
        else{
            response=facade.SetDeliveryMode(Supid,deliveryMode,days,daysF);
        }
    }
    private void addNewContactMemberHelper(int id){
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object

        System.out.println("please type the contactName : ");
        String name=myObj.nextLine();
        System.out.println("please type the contactEmail : ");
        String email=myObj.nextLine();
        System.out.println("please type the contact Phone Number : ");
        String phoneNumber=myObj.nextLine();
        Response response=facade.addNewContactMember(id,name,email,phoneNumber);
        if(response.getErrorOccurred()){
            System.out.println((response.getErrorMsg()));
            System.out.println("The Contact not added, please try again");
        }
        else{System.out.println("Contact added successfully\n");}
    }

    //public ResponseT getSupplier(int SupId);
    public void printSupplier(){
        System.out.println("\n");

        System.out.println("please type the supplier id : ");
        int id=NumberType();
        BusinessLayer.FacedeModel.Objects.ResponseT<SupplierResponse> responseT=facade.getSupplier(id);
        if(responseT.getErrorOccurred()){
            System.out.println((responseT.getErrorMsg()));
        }
        else{
            System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","id","name","bank Account","payment method","Contacts");
            System.out.printf("%-30s%-30s%-30s%-30s\n",responseT.getValue().id,responseT.getValue().supplierName,responseT.getValue().bankAcount,responseT.getValue().paymentMethods);
            List<contactResponse> cr=responseT.getValue().contacts;
            System.out.printf("%-120s%-30s%-30s%-30s%-30s\n","","id","name","Email","Phone Number");
            for(int i=0;i<responseT.getValue().contacts.size();i++){
                System.out.printf("%-120s%-30s%-30s%-30s%-30s\n","",cr.get(i).id,cr.get(i).name,cr.get(i).Email,cr.get(i).PhoneNumber);
            }
        }
    }
    //
//public ResponseT getAllSuppliers();
    public void printAllSuppliers() {
        System.out.println("\n");

        BusinessLayer.FacedeModel.Objects.ResponseT<List<SupplierResponse>> responseT=facade.getAllSuppliers();
        if(responseT.getErrorOccurred()){
            System.out.println((responseT.getErrorMsg()));
        }
        else{
            List<SupplierResponse> lrs=responseT.getValue();


            for(int i=0;i<lrs.size();i++){
                System.out.printf("%-30s%-30s%-30s%-30s%-30s\n","id","name","bank Account","payment method","Contacts");
                SupplierResponse supplierResponse=lrs.get(i);

                System.out.printf("%-30s%-30s%-30s%-30s\n",supplierResponse.id,supplierResponse.supplierName,supplierResponse.bankAcount,supplierResponse.paymentMethods.toString());
                List<contactResponse> cr=supplierResponse.contacts;
                System.out.printf("%-120s%-30s%-30s%-30s%-30s\n","","id","name","Email","Phone Number");
                for(int j=0;j<supplierResponse.contacts.size();j++){
                    System.out.printf("%-120s%-30s%-30s%-30s%-30s\n","",cr.get(j).id,cr.get(j).name,cr.get(j).Email,cr.get(j).PhoneNumber);
                }
                System.out.println("\n");

            }
        }
    }


    //public ResponseT getAgreement(int SupId);
    public void printAgreement(){
        System.out.println("\n");

        System.out.println("please type the supplier id : ");
        int id=NumberType();
        BusinessLayer.FacedeModel.Objects.ResponseT<AgreementResponse> responseT=facade.getAgreement(id);
        if(responseT.getErrorOccurred()){
            System.out.println((responseT.getErrorMsg()));
        }
        else {
            AgreementResponse agreementResponse = responseT.getValue();
            System.out.println("The delivery Mode is: "+agreementResponse.deliveryModes);
            if(agreementResponse.deliveryModes==DeliveryMode.DeliveryAfterOrder)
            {
                System.out.println("The delivery will arrive "+ agreementResponse.numOfDaysFromOrder+" days after the order");

            }else if(agreementResponse.deliveryModes==DeliveryMode.DeliveryByDay) {
                System.out.println("Delivery days: ");
                for(int i=0;i<agreementResponse.daysOfDelivery.size();i++){
                    System.out.print(" "+agreementResponse.daysOfDelivery.get(i)+" ");
                }
                System.out.println();
            }
            System.out.println("The products in the agreement: ");
            List<productSupplierResponse> productResponseList =agreementResponse.products;
            System.out.printf("%-22s%-22s%-22s\n","Catalog ID","produc name","produc price");
            for(int i=0;i<productResponseList.size();i++){
                System.out.printf("%-22d%-22s%-22s\n",productResponseList.get(i).CatalogID,productResponseList.get(i).name,productResponseList.get(i).price);
            }
            System.out.println("The Discounts in the agreement: ");
            if(agreementResponse.ExtaraDiscount!=-1){
                System.out.println("ExtraDiscount:  "+agreementResponse.ExtaraDiscount+" %");
            }
            System.out.println("Discount of products: ");
            System.out.printf("%-22s%-22s%-22s\n","CatalogID","Quantity","new price per product");
            HashMap<Integer,HashMap<Integer,Double>> discounts=agreementResponse.discounts;
            for (Integer key:discounts.keySet()
            ) {
                HashMap<Integer,Double> quantityDis=discounts.get(key);
                for (Integer QUANT: quantityDis.keySet()
                ) {
                    double discount=quantityDis.get(QUANT);
                    System.out.printf("%-22d%-22d%-22f\n",key,QUANT,discount);
                }
            }
        }
    }
    /*
      Pickup,
        DeliveryByDay,
        DeliveryAfterOrder
     */
    private DeliveryMode DeliveryModeSellect(){
        System.out.println("\n");

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String DeliveryMethode;
        while (true){
            System.out.println("please choose the supplier Delivery Methode : ");
            DeliveryMode[] deliveryMethodes=DeliveryMode.values();
            for(int i=0;i<deliveryMethodes.length;i++){
                System.out.println(i+1+" ) "+deliveryMethodes[i]);
            }
            DeliveryMethode=myObj.nextLine();
            switch (DeliveryMethode){
                case "1":
                    return DeliveryMode.Pickup;
                case "2":
                    return DeliveryMode.DeliveryByDay;
                case "3":
                    return DeliveryMode.DeliveryAfterOrder;
                default:
                    System.out.println("Not within bounds! , try again");
                    break;
            }

        }

    }


    private paymentMethods SupplierPaymentMethodsSellect() {
        System.out.println("\n");

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String PaymentMethod;
        while (true) {
            System.out.println("please choose the supplier paymentMethods : ");
            paymentMethods[] paymentMethodsArr = paymentMethods.values();
            for (int i = 0; i < paymentMethodsArr.length; i++) {
                System.out.println(i+1 + ")" + " " + paymentMethodsArr[i]);
            }
            PaymentMethod = myObj.nextLine();
            switch (PaymentMethod) {
                case "1":
                    return paymentMethods.paypal;
                case "2":
                    return paymentMethods.creditcard;
                case "3":
                    return paymentMethods.Cash;
                case "4":
                    return paymentMethods.BankTransfers;
                default:
                    System.out.println("Not within bounds! , try again");
                    break;
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

    private double doubleType() {

        double numb = 0;
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        boolean isOk = false;
        while (!isOk) {
            try {
                return Double.parseDouble(myObj.nextLine());
            }catch (Exception e) {
                System.out.println("Input is not a valid Double, try again");
            }
        }
        return numb;
    }
}
