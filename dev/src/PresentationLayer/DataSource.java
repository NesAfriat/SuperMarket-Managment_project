package PresentationLayer;//package PresentationLayer;
//
//import BuisnnesLayer.Controlls.Sales_Controller;
//import BuisnnesLayer.Controlls.Stock_Controller;
//import BuisnnesLayer.FacedeModel.facade;
////import Inventory.BusinessLayer.Controlls.Sales_Controller;
////import Inventory.BusinessLayer.Controlls.Stock_Controller;
////import Inventory.BusinessLayer.Facade;
//
//import java.util.Date;
//import java.util.LinkedList;
//
//public class DataSource {
//    private static DataSource ds = null;
//    private Stock_Controller stockC;
//    private Sales_Controller salesC;
//
//    private DataSource(facade Facade) throws Exception {
//        stockC = Facade.getStockC();
//        salesC = Facade.getSalesC();
//        setStock();
//        setSales();
//    }
//
//    private void setSales() throws Exception {
//        LinkedList<String> categories1 = new LinkedList<>();
//        LinkedList<String> categories2= new LinkedList<>();
//        LinkedList<String> products1 = new LinkedList<>();
//        LinkedList<String> products2 = new LinkedList<>();
//        categories1.add("snacks");
//        salesC.createSaleByCategory(10.0, "snacks for all", Facade.getDate("2021-03-12"), Facade.getDate("2021-06-01"), categories1);
//        categories2.add("dairies");
//        salesC.createSaleByCategory(25.0, "Dare with dairies", Facade.getDate("2021-03-12"), Facade.getDate("2021-06-07"), categories2);
//        products1.add("milk");
//        salesC.createSaleByProduct(10.0, "the milky day", new Date(), Facade.getDate("2021-05-01"), products1);
//        products2.add("arak");
//        products2.add("wine");
//        salesC.createSaleByCategory(20.0, "drinks on me", Facade.getDate("2021-01-12"), Facade.getDate("2021-05-15"), products2);
//    }
//
//    private void setStock() throws Exception {
//        try {
//            stockC.createNewStock(77);
//            stockC.connect_stock(77);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        setCategories();
//        setProducts();
//        setItems();
//
//    }
//    private void setCategories() throws Exception {
//        stockC.createNewCategory("meat");
//        stockC.createNewCategory("food");
//        stockC.createNewCategory("dairies");
//        stockC.createNewCategory("snacks");
//        stockC.createNewCategory("drinks");
//        stockC.createNewCategory("alcohol");
//        stockC.createNewCategory("gummy-bears");
//        stockC.createNewCategory("chocolates");
//        stockC.set_father("meat", "food");
//        stockC.set_father("dairies", "food");
//        stockC.set_father("snacks", "food");
//        stockC.set_father("drinks", "food");
//        stockC.set_father("alcohol", "drinks");
//        stockC.set_father("gummy-bears", "snacks");
//        stockC.set_father("chocolates", "snacks");
//    }
//    private void setProducts() throws Exception {
//        stockC.addProduct(1, "Milk", "Tara", 2, 6.90, "dairies", 8.95);
//        stockC.addProduct(2, "Milk", "Tnuva", 2, 6.87, "dairies", 8.90);
//        stockC.addProduct(3, "Sussages", "Meato", 3, 7.87, "meat", 11.80);
//        stockC.addProduct(4, "Gummy-Snakes", "yummy", 3, 5.9, "gummy-bears", 6.90);
//        stockC.addProduct(5, "ChocolateBalls", "yummy", 3, 7.9, "chocolates", 8.90);
//        stockC.addProduct(6, "Arak", "Halili", 2, 65.0, "alcohol", 74.90);
//        stockC.addProduct(7, "Vodka", "Respect", 2, 69.99, "alcohol", 81.0);
//        stockC.addProduct(8, "Wine", "Galili", 2, 32.0, "alcohol", 37.50);
//        stockC.addProduct(9, "greekCheese", "Tara", 3, 10.90, "dairies", 13.90);
//        stockC.addProduct(10, "yellowCheese", "Tara", 2, 11.90, "dairies", 12.9);
//        stockC.addProduct(11, "bamba", "Osem", 3, 3.90, "snacks", 4.4);
//    }
//
//    private void setItems() throws Exception {
//        stockC.addItems(1, 3, "store_1_c", Facade.getDate("2021-03-12"), Facade.getDate("2021-03-10"), Facade.getDate("2021-05-01"));
//        stockC.addItems(2, 2, "store_1_c", Facade.getDate("2021-03-12"), Facade.getDate("2021-03-09"), Facade.getDate("2021-05-01"));
//        stockC.addItems(3, 3, "store_1_b", Facade.getDate("2021-03-12"), Facade.getDate("2021-03-02"), Facade.getDate("2021-05-01"));
//        stockC.addItems(4, 1, "store_2_a", Facade.getDate("2021-03-12"), Facade.getDate("2021-02-20"), Facade.getDate("2021-05-03"));
//        stockC.addItems(4, 1, "storage", Facade.getDate("2021-03-12"), Facade.getDate("2021-02-20"), Facade.getDate("2021-05-03"));
//        stockC.addItems(5, 1, "storage", Facade.getDate("2021-03-12"), Facade.getDate("2021-03-04"), Facade.getDate("2021-05-02"));
//        stockC.addItems(6, 1, "store_5_a", Facade.getDate("2021-03-12"), Facade.getDate("2021-03-07"), Facade.getDate("2021-05-01"));
//        stockC.addItems(7, 1, "store_5_b", Facade.getDate("2021-03-12"), Facade.getDate("2021-03-04"), Facade.getDate("2021-05-17"));
//        stockC.addItems(8, 1, "store_5_c", Facade.getDate("2021-03-12"), Facade.getDate("2021-03-01"), Facade.getDate("2021-05-21"));
//        stockC.addItems(8, 1, "storage", Facade.getDate("2021-03-12"), Facade.getDate("2021-03-01"), Facade.getDate("2021-05-21"));
//        stockC.addItems(9, 2, "store_1_d", Facade.getDate("2021-03-12"), Facade.getDate("2021-02-25"), Facade.getDate("2021-05-30"));
//        stockC.addItems(10, 1, "store_1_d", Facade.getDate("2021-03-12"), Facade.getDate("2021-02-28"), Facade.getDate("2021-05-01"));
//        stockC.addItems(11, 2, "store_2_c", Facade.getDate("2021-03-12"), Facade.getDate("2021-03-05"), Facade.getDate("2021-05-01"));
//        stockC.addItems(11, 1, "storage", Facade.getDate("2021-03-12"), Facade.getDate("2021-03-05"), Facade.getDate("2021-05-01"));
//    }
//
//
//
//    // static method to create instance of Singleton class
//    public static void LoadData(Facade facade) {
//        try {
//            if (ds == null)
//                ds = new DataSource(facade);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//}
