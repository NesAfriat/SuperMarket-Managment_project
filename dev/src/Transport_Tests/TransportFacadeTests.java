/*package Transport_Tests;

import BusinessLayer.Transport_BusinessLayer.Cont.Transport_Facade;
import BusinessLayer.Transport_BusinessLayer.Cont.ControllerShops;
import BusinessLayer.Transport_BusinessLayer.Cont.DocCont;
import BusinessLayer.Transport_BusinessLayer.Cont.DriversController;
import BusinessLayer.Transport_BusinessLayer.Drives.License;
import BusinessLayer.Transport_BusinessLayer.Drives.TruckType;
import BusinessLayer.Transport_BusinessLayer.Shops.Area;
import BusinessLayer.Transport_BusinessLayer.Shops.Product;
import BusinessLayer.Transport_BusinessLayer.Shops.Store;
import BusinessLayer.Transport_BusinessLayer.Shops.Supplier;

import java.util.LinkedList;
import java.util.List;

class TransportFacadeTests {



     private Transport_Facade LoadBusinessController(){
        ControllerShops cs=LoadControllerShops();
        DriversController dc=LoadDriverController();
        return new Transport_Facade(dc,new DocCont(),cs);
    }
    private ControllerShops LoadControllerShops(){
        ControllerShops cs = new ControllerShops();
        Supplier sp1 = new Supplier("Rami Levi", 2, "0507133213", "Rami Ach", Area.B);
        Supplier sp2 = new Supplier("Shufersal", 3, "050713123213", "Shuf Ach", Area.C);
        Supplier sp3 = new Supplier("Mega", 4, "0501231231", "meg Ach", Area.C);
        Supplier sp4 = new Supplier("Tiv Taam", 5, "052312312", "Taami Ach", Area.A);

        cs.addSupplier(sp1);
        cs.addSupplier(sp2);
        cs.addSupplier(sp3);
        cs.addSupplier(sp4);
        //cs.ToStringSuppliers();

        Product pr1 = new Product("Cheese", 2);
        Product pr2 = new Product("Milk", 3);
        Product pr3 = new Product("Chocolate", 4);
        Product pr4 = new Product("Water", 5);


        cs.addProduct(pr1);
        cs.addProduct(pr2);
        cs.addProduct(pr3);
        cs.addProduct(pr4);
        //cs.ToStringProducts();

        Store st1 = new Store("shufersal 1", 2, "0501231213", "mr man", Area.A);
        Store st2 = new Store("shufersal 2", 3, "0501465213", "mr man", Area.B);
        Store st3 = new Store("shufersal 3", 4, "0507553113", "mr man", Area.B);
        Store st4 = new Store("shufersal 4", 5, "0501299913", "mr man", Area.C);

        cs.addStore(st1);
        cs.addStore(st2);
        cs.addStore(st3);
        cs.addStore(st4);
        //cs.ToStringStores();

        return cs;
    }
    private DriversController LoadDriverController(){
        DriversController drivers=new DriversController();

        List<License> licenseList1=new LinkedList<License>();licenseList1.add(License.typeA);licenseList1.add(License.typeB);
        List<License> licenseList2=new LinkedList<License>();licenseList2.add(License.typeA);licenseList2.add(License.typeB);licenseList2.add(License.typeC);
        List<License> licenseList3=new LinkedList<License>();licenseList3.add(License.typeA);

        drivers.AddNewTruck("The Tank", 421652160, new TruckType("honda", 2000, 700,licenseList3));
        drivers.AddNewTruck("The Hunk", 421652161, new TruckType("honda", 2000, 700,licenseList3));
        drivers.AddNewTruck("Mad Max", 421652162, new TruckType("mazda", 3000, 1000,licenseList1));
        drivers.AddNewTruck("Big Daddy", 421652163, new TruckType("honda", 4000, 1200,licenseList2));

        return drivers;

    }
}
*/