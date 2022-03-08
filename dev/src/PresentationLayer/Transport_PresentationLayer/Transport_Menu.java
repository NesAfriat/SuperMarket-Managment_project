package PresentationLayer.Transport_PresentationLayer;
import BusinessLayer.Transport_BusinessLayer.Cont.Transport_Facade;
import BusinessLayer.Transport_BusinessLayer.etc.Tuple;
import BusinessLayer.Workers_Integration;
import DataLayer.Transport_DAL.Connect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Transport_Menu {
    private Transport_Facade bc;
    private Scanner sc;

    int input;
    boolean firstRun;


    public void addWorkersIntegration (Workers_Integration wk){
        bc.addWorkersInterface(wk);
    }
    public Transport_Menu(Transport_Facade facade)
    {

        bc = facade;
        sc = new Scanner(System.in);

        try {
            Connect.createTablesAndLoadData();

        }catch (Exception e)
        {

        }
        loadData();

    }

    public void mainMenu() {

        print("Please enter your ID\n");
        String str = sc.nextLine();
        boolean run = bc.canUserEnter(str);

        if(run == false)
            System.out.println("User can not enter\n");
        while(run){
            /*HashMap<Integer,Integer>hm = new HashMap<>();
            hm.put(1,3);
            hm.put(2,1);
            hm.put(3,2);
            try {
                bc.addTransportFromSupplier(2,2, hm, "10/05/2021");
            }catch (Exception e)
            {
                System.out.println(e.toString());
            }*/
            bc.sendTransportToStock();

            run = runProgram();
        }
    }

    public int displayMenu(){

        print("Please choose an option:\n\n");


        print ("1) Display Truck List \n");
        print ("2) Display Driver List \n");
        print ("3) Edit Truck \n");
        print ("4) Edit Driver \n");
        print ("5) Edit Truck and Driver \n");
        print ("6) Edit Truck Weight on Departure \n");
        print ("7) Remove Destination \n");
        print ("8) Remove Product By Store \n");
        print ("9) Print Basic Info \n");
        print ("10) Print Stops \n");
        print ("11) Print Products \n");
        print ("12) Show all unapproved transports \n");
        print ("13) Approve all transports \n");
        print ("14) Approve a single transport \n");
        print ("15) SaveDoc \n");
        print ("16) Exit \n");



        input = sc.nextInt();
        return input;
    }

    public boolean runProgram() {
        int a, b, c, d;
        String str;
        switch (displayMenu()) {

            case 1:
                print(bc.getTrucksString());
                break;
            case 2:
                print("will only display drivers in shift, please enter date in format DD/MM/YYYY, 1 for morning shift - 2 for evening shift\n" +
                        "and the truck license plate\n");
                str = sc.nextLine();
                str = sc.nextLine();
                a = sc.nextInt();
                b=sc.nextInt();
                try {
                    print(bc.getDriversString(str,a,b));
                } catch (Exception e) {
                    print(e.getMessage()+"\n");
                }
                break;

            case 3:
                print("please enter your doc ID and new truck License Plate \n");
                a= sc.nextInt();
                b= sc.nextInt();
                try {
                    bc.editDocTruck(a, b);
                }catch (Exception e){
                    print(e.getMessage()+"\n");
                }
                break;
            case 4:
                print("please enter your doc ID  and new truck Driver ID \n");
                a= sc.nextInt();
                b=sc.nextInt();
                try {
                    bc.editDocDriver(a, b);
                }catch (Exception e){
                    print(e.getMessage()+"\n");
                }
                break;
            case 5:
                print("please enter your doc ID, new truck License Plate and new Driver ID \n");
                a= sc.nextInt();
                b=sc.nextInt();
                c=sc.nextInt();
                try {
                    bc.editDocTruck(a, b);
                    bc.editDocDriver(a, c);
                }catch (Exception e){
                    print(e.getMessage()+"\n");
                }
                break;
            case 6:
                print("please enter your doc ID, new truck weight on departure \n");
                a= sc.nextInt();
                b=sc.nextInt();
                try {
                    bc.editTruckWeightDep(a, b);
                }catch (Exception e){
                    print(e.getMessage()+"\n");
                }
                break;

            case 7:
                print("please enter your doc ID, and stop number\n");
                a= sc.nextInt();
                b=sc.nextInt();
                try {
                    bc.removeDestination(a, b);
                }catch (Exception e){
                    print(e.getMessage()+"\n");
                }
                break;
            case 8:
                print("please enter your doc ID, product ID and store ID\n");
                a= sc.nextInt();
                b= sc.nextInt();
                c= sc.nextInt();
                try {
                    bc.removeProduct(a, b,c);
                }catch (Exception e){
                    print(e.getMessage()+"\n");
                }
                break;

            case 9:
                print("please enter your doc ID, will only print initialised variables \n");
                a= sc.nextInt();
                try {
                    print(bc.docInfo(a));
                }
                catch (Exception e)
                {
                    print(e.getMessage()+"\n");
                }
                break;
            case 10:
                print("please enter your doc ID\n");
                a= sc.nextInt();
                print(bc.docDestinations(a));
                break;
            case 11:
                print("please enter your doc ID\n");
                a= sc.nextInt();
                print(bc.docProducts(a));
                break;
            case 12:
                print("Unapproved Transports:\n");
                print(bc.getUnapprovedDocs());

                break;
            case 13:

                try {
                    bc.approveAllTransports();
                    print("great success\n");
                } catch (Exception e) {
                    print("At least one transport isn't full, action failed\n");
                }

                break;
            case 14:
                print("please enter the Transport ID you would like to approve\n");
                a= sc.nextInt();

                try {
                    bc.approveSingleTransports(a);
                    print("great success\n");
                } catch (Exception e) {
                    print("the transport isn't full, action failed\n");
                }
                break;

            case 15:
                print("please enter the doc ID you would like to save, it will only save if all fields are full\n");
                a = sc.nextInt();
                try {
                    bc.saveDoc(a);
                } catch (Exception e) {
                    print(e.toString()+"\n");
                }
                break;
            case 16:
                return false;



        }
        return true;
    }











    public void loadData()  {
        try {
            bc.loadData();
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    static void print(String m) {
        System.out.print(m);
    }

}
