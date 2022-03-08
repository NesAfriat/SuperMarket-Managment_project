package BusinessLayer.Transport_BusinessLayer.Cont;


import BusinessLayer.Transport_BusinessLayer.Document.Triple;
import BusinessLayer.Transport_BusinessLayer.Drives.Driver;
import BusinessLayer.Transport_BusinessLayer.Drives.License;
import BusinessLayer.Transport_BusinessLayer.Drives.Truck;
import BusinessLayer.Transport_BusinessLayer.Drives.TruckType;
import BusinessLayer.Transport_BusinessLayer.Shops.*;
import BusinessLayer.Transport_BusinessLayer.Transport_Integration;
import BusinessLayer.Transport_BusinessLayer.etc.Tuple;
import BusinessLayer.Workers_BusinessLayer.Responses.ResponseT;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;
import BusinessLayer.Workers_Integration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Transport_Facade implements Transport_Integration {
    static int theOneStoreId=1;
    private DriversController driversController;
    private DocCont docCont;
    private Workers_Integration wk;
    //private ControllerShops controllerShops;

    public Transport_Facade() {
        this.driversController = new DriversController();
        this.docCont = new DocCont();
        //this.controllerShops = new ControllerShops();
        // init data base
    }
    public void addWorkersInterface(Workers_Integration wk ){
        this.wk=wk;
        driversController.addWorkersInterface(wk);

    }
    public void saveDoc(int docId) throws Exception {
        docCont.save(docId);
    }
    public Transport_Facade(DriversController driversController, DocCont docCont, ControllerShops controllerShops) {
        this.driversController = driversController;
        this.docCont = docCont;
       // this.controllerShops = controllerShops;
    }
    public boolean canUserEnter(String str) {
        ResponseT<List<Job>> ans=  wk.getWorkerOccupations(str);

        if(ans.ErrorOccurred()){
            System.out.println(ans.getErrorMessage());
            return false;
        }
        for (Job b : ans.value)
        {
            if(b==Job.Transport_Manager)
                return true;
        }
        return false;
    }


    public String getUnapprovedDocs(){
       return buildListToString(docCont.getUnapprovedDocs());
    }

    public void approveAllTransports() throws Exception {
        docCont.approveAllTransports();
    }

    public void approveSingleTransports(int id) throws Exception {
        docCont.approveSingleTranposrt(id);
    }

    public int createNewDelivery() {
        return docCont.newDelivery();
    }

    /*public String getAvaliableStoresString(int docNum) {
        Area area=docCont.getArea(docNum);
        return controllerShops.getStoreList().stream().filter(x->x.getArea()==area).collect(Collectors.toList()).toString();
    }*/



    public void addStore(int doc, int StoreNum, int place) throws Exception {
        Area area = docCont.getArea(doc);
        /*Optional<Store> s = controllerShops.getStoreList().stream().filter(r -> r.getId() == StoreNum).findFirst();
        if (!s.isPresent()) {
            throw new Exception("No store with this number");
        }
        if (!s.get().getArea().equals(area) & area !=null) {
            throw new Exception("store not in the same area");
        }*/
        docCont.addStore(doc, StoreNum, place);
    }
    public void removeDestination(int doc, int place) throws Exception {
        docCont.removeDestinations(doc,place);

    }
    public void removeProduct(int doc, int prodId,int storeId) throws Exception {
        docCont.removeProducts(doc,prodId,storeId);
    }
    public String docProducts(int doc){
       return docCont.docProdString(doc);
    }
    public String docInfo(int doc){
        return docCont.docInfo(doc);
    }
    public String docDestinations(int doc){
        return docCont.docDestinations(doc);
    }
    public void addSupplier(int doc, int SupplierNum, int place) throws Exception {
        Area area = docCont.getArea(doc);
       /* Optional<Supplier> s = controllerShops.getSupplierList().stream().filter(r -> r.getId() == SupplierNum).findFirst();
        if (!s.isPresent()) {
            throw new Exception("No supplier with this number");
        }
        if (!s.get().getArea().equals(area)& area !=null) {
            throw new Exception("Supplier not in the same area");
        }*/
        docCont.addSupplier(doc, SupplierNum, place);
    }

    public void addProductsToDoc(int doc, Tuple<Integer, Integer> productAndAmounts, int storeId) throws Exception {
        /*Optional<Product> product = controllerShops.getProductList().stream().filter(x -> x.getId() == productAndAmounts.x).findFirst();
        if (!product.isPresent()) {
            throw new Exception("no product with this id");
        }
        Optional<Store> Store = controllerShops.getStoreList().stream().filter(x -> x.getId() == storeId).findFirst();
        if (!Store.isPresent()) {
            throw new Exception("no Store with this id");
        }*/
        docCont.addProducts(doc, new Triple<>(productAndAmounts.x, productAndAmounts.y, storeId));
    }


    /*private List<Tuple<List<Supplier>, Product>> returnAvaliableSuppliersP(int doc) {
        List<Tuple<List<Supplier>, Product>> output = new LinkedList<>();
        Area area = docCont.getArea(doc);
        List<Product> allproductsFromDoc = docCont.getProducts(doc);//get all products from document

        for (Product p : allproductsFromDoc) {

            List<Supplier> supplierList = controllerShops.returnAvaliableSupplier(p, area);
            Tuple<List<Supplier>, Product> supAndPro = new Tuple<>(supplierList, p);
            output.add(supAndPro);

        }
        return output;
    }*/

    /*public  String returnAvaliableSupplierString(int doc) {
        List<Tuple<List<Supplier>, Product>> listTuple = returnAvaliableSuppliersP(doc);
        String acc = "";

        for (Tuple<List<Supplier>, Product> tup : listTuple) {

            acc = acc + tup.y.getId() + ", " + tup.y.getName() + ":\n";
            if (tup.x == null)
                acc = acc + " there are no supplier for this product in that area\n";
            else {
                for (Supplier sp : tup.x) {
                    acc = acc + "    " + sp.toString();
                }
            }
        }
        return acc;
    }*/

    private List<Truck> getAvaliableTrucksP() {
        return driversController.getTrucks();
    }
    public String getTrucksString(){
        return buildListToString(getAvaliableTrucksP());
    }
    public void addTruck(int doc, int truckLicensePlate) throws Exception{

        Optional<Truck> truck = driversController.getTrucks().stream().filter(x -> x.getLicensePlate() == truckLicensePlate).findFirst();
        if (!truck.isPresent()) {
            throw new Exception("truck not found");
        }
        if(docCont.getTruck(doc)!=null){
            throw new Exception("document already contain truck");
        }
        if(docCont.getDriver(doc)!=null){
            driversController.connectDriverAndTruck( docCont.getDriver(doc),truck.get());
        }
        docCont.addTruck(doc,truck.get());
    }
    public void addDriver(int doc, int driverId) throws Exception {
        if(docCont.getDriver(doc)!=null){
            throw new Exception("document already contain Driver");
        }


        Driver driver = driversController.getDriverWithID(driverId);


        if(docCont.getTruck(doc)!=null){
            driversController.connectDriverAndTruck(driver, docCont.getTruck(doc));
        }

        docCont.addDriver(doc,driver);
    }


    public String getDriversString(String date, int shift, int truckLicensePlate) throws Exception {
        Optional<Truck> truckOp=driversController.getTrucks().stream().filter(x -> x.getLicensePlate() == truckLicensePlate).findFirst();
        if(!truckOp.isPresent()){
            throw new Exception("no truck with this plate");
        }
        return buildListToString( driversController.getDrivers(date,shift,truckOp.get().getTruckType().getLicensesForTruck()));
    }
    public void addWeightWhenLeaving(int doc, double truckDepartureWeight) throws Exception {
        int truckMaxWeight = docCont.getTruck(doc).getTruckType().getMaxWeight();
        if (truckMaxWeight < truckDepartureWeight) {
            throw new Exception("truck overweight");
        }
        docCont.addWeightWhenLeaving(doc,  truckDepartureWeight);
    }
    public void editTransDate(int doc, String transDate) {
        docCont.editTransDate(doc, transDate);
    }
    public void editLeftOrigin(int doc, String LeftOrigin) {
        docCont.editLeftOrigin(doc, LeftOrigin);
    }
    public void setOrigin (int doc, int store) throws Exception {
       // Optional<Store> s = controllerShops.getStoreList().stream().filter(r -> r.getId() == store).findFirst();
        docCont.setOrigin(doc,store);

    }

    public void setTransportDate(int doc, String str) throws Exception {
        if(!driversController.isStoreKeeper(str))
            throw new Exception("no storekeepers in this date");
        docCont.setTranportDate(doc,str);

    }
    public void setDepartureTime(int doc, String date){
        docCont.setDepartureTime(doc,date);
    }
    public void editDocTruck(int doc, int tkId) throws Exception {
        Optional<Truck> newTruck = driversController.getTrucks().stream().filter(c -> c.getLicensePlate() == tkId).findFirst();
        if (!newTruck.isPresent()) {
            throw new Exception("no truck with this id");
        }
        Driver Driver = docCont.getDriver(doc);
        if(Driver!=null){
            License DriverLicense = Driver.getLicense();
            if (!newTruck.get().getTruckType().getLicensesForTruck().contains(DriverLicense)) {
                throw new Exception("new truck not have a compatible license to Driver");
            }
           //driversController.changeTruck(newTruck.get(), docCont.getTruck(doc));
        }

        docCont.editTruck(doc, newTruck.get());
    }
    public void editDocDriver(int doc, int drId) throws Exception {

        Driver dr = driversController.getDriverWithID(drId);
        if (!docCont.getTruck(doc).getTruckType().getLicensesForTruck().contains(dr.getLicense())) {
            throw new Exception("new driver not have a compatible license to Truck");
        }
       // driversController.changeDriver(newDriver.get(),docCont.getDriver(doc) );
        docCont.editDriver(doc, dr);
    }
    public void editOrigin(int doc, int origenStoreId) throws Exception {
        /*Optional<Store> newStore = controllerShops.getStoreList().stream().filter(x -> x.getId() == origenStoreId).findFirst();
        if (!newStore.isPresent()) {
            throw new Exception("no store with this Id");
        }
        if (!(newStore.get().getArea() == docCont.getArea(doc))) {
            throw new Exception("new store outside of delivery area");
        }*/

        docCont.editOrigin(doc, origenStoreId);
    }
    public void editTruckWeightDep(int doc, double trWeight) throws Exception {
        int truckMaxWeight = docCont.getTruck(doc).getTruckType().getMaxWeight();
        if (truckMaxWeight < trWeight) {
            throw new Exception("new Truck Weight is over Truck Max");
        }
        docCont.editTruckWeightDep(doc, trWeight);

    }
    public static <T> String buildListToString(List<T> lt) {
        String acc="";
        for(T var:lt){
            acc=acc+var.toString();
        }
        return acc;
    }
    public void ApproveDoc(int doc) throws Exception {
        docCont.approved(doc,true);
    }
    public String addTransportFromSupplierConstant(int orderID,int supplierId, HashMap<Integer,Integer> productAndAmount) throws Exception {

        Date date = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //add 3 days
        cal.add(Calendar.DATE,3);
        date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        return addTransportFromSupplier(orderID,supplierId,productAndAmount,strDate);

    }





    public String   addTransportFromSupplier(int orderID, int supplierId, HashMap<Integer,Integer> productAndAmount, String date) throws Exception {
        List<License> a = new LinkedList();
        a.add(License.typeA);
        a.add(License.typeB);
        a.add(License.typeC);
        Tuple<String, List<Driver>> dateWithDriverList = driversController.getDateWithDriver(date);
        if(dateWithDriverList==null){
            //tell workers to add a driver + storekeeper;
           
            wk.addRequest(orderID,date);
            return null;
        }

        int docId=docCont.addTranportFromSupplier(supplierId, productAndAmount,dateWithDriverList.x);//add TranportFromSupplier Without DriversAndTrucks and dates return the doc id
        for (Driver d : dateWithDriverList.y) {
            List <Truck> truckList= driversController.getCompatibleTrucks(d);
            if(!truckList.isEmpty()) {
                Truck t = truckList.get(0);
                docCont.addDriver(docId, d);
                docCont.addTruck(docId, t);
                break;
            }
        }
        saveDoc(docId);



        return dateWithDriverList.x;
    }
    public void sendTransportToStock() {
        docCont.sendTransportToStock();
    }


    /*public String getAllStores(){

        return buildListToString(controllerShops.getStoreList());
    }*/
    /*public String getAllProducts(){
        return buildListToString(controllerShops.getProductList());
    }*/
    /*public String getAllSuppliers(){
        return controllerShops.getSupplierList().toString();
    }*/
    public void loadData() throws Exception {
        //ControllerShops cs = this.controllerShops;
        //controllerShops.load();
        DriversController drivers = this.driversController;
        drivers.load();
        DocCont docContro = this.docCont;
        docCont.load();


    }


    public void loadDataNoDatabase()  {

try {
    /*ControllerShops cs = this.controllerShops;
    Supplier sp1 = new Supplier("Rami   Levi", 2, "0507133213", "Rami Ach", Area.B);
    Supplier sp2 = new Supplier("Shufersal", 3, "050713123213", "Shuf Ach", Area.A);
    Supplier sp3 = new Supplier("Mega", 4, "0501231231", "meg Ach", Area.A);
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

    cs.addProductToSupp(sp1, pr1);
    cs.addProductToSupp(sp1, pr2);
    cs.addProductToSupp(sp1, pr3);
    cs.addProductToSupp(sp1, pr4);
    cs.addProductToSupp(sp2, pr1);
    cs.addProductToSupp(sp2, pr2);
    cs.addProductToSupp(sp2, pr3);
    cs.addProductToSupp(sp3, pr4);
    cs.addProductToSupp(sp3, pr1);
    cs.addProductToSupp(sp3, pr2);
    cs.addProductToSupp(sp4, pr3);
    cs.addProductToSupp(sp4, pr4);
    cs.addProductToSupp(sp4, pr1);
    cs.addProductToSupp(sp4, pr2);

*//*
    cs.addProduct(pr1);
    cs.addProduct(pr2);
    cs.addProduct(pr3);
    cs.addProduct(pr4);


    Store st1 = new Store("shufersal 1", 2, "0501231213", "mr man", Area.A);
    Store st2 = new Store("shufersal 2", 3, "0501465213", "mr man", Area.B);
    Store st3 = new Store("shufersal 3", 4, "0507553113", "mr man", Area.B);
    Store st4 = new Store("shufersal 4", 5, "0507993113", "mr man", Area.B);
    Store st5 = new Store("shufersal 5", 6, "0501299913", "mr man", Area.C);

    cs.addStore(st1);
    cs.addStore(st2);
    cs.addStore(st3);
    cs.addStore(st4);

*/
    DriversController drivers = this.driversController;
    Driver dr = new Driver("Dan", 209889510, License.typeA);
    /*drivers.AddNewDriver("Guy", 208750760, License.typeA);
    drivers.AddNewDriver("Dan", 209889510, License.typeA);
    drivers.AddNewDriver("Lebron James", 986750760, License.typeB);
    drivers.AddNewDriver("Stephen Curry", 308450560, License.typeB);
    drivers.AddNewDriver("Omri Caspi", 208750760, License.typeC);
    drivers.AddNewDriver("Deni Avdija", 208750760, License.typeC);*/

    List<License> licenseList1 = new LinkedList<License>();
    licenseList1.add(License.typeA);
    licenseList1.add(License.typeB);
    List<License> licenseList2 = new LinkedList<License>();
    licenseList2.add(License.typeA);
    licenseList2.add(License.typeB);
    licenseList2.add(License.typeC);
    List<License> licenseList3 = new LinkedList<License>();
    licenseList3.add(License.typeA);

    Truck tk = new Truck("The Tank", 421652160, new TruckType("honda", 2000, 700, licenseList3));
    drivers.AddNewTruck("The Tank", 421652160, new TruckType("honda", 2000, 700, licenseList3));
    drivers.AddNewTruck("The Hunk", 421652160, new TruckType("honda", 2000, 700, licenseList3));
    drivers.AddNewTruck("Mad Max", 421652160, new TruckType("mazda", 3000, 1000, licenseList1));
    drivers.AddNewTruck("Big Daddy", 421652160, new TruckType("honda", 4000, 1200, licenseList2));

    DocCont docContro = this.docCont;

    String date = "11/03/1998";


    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    String date2 = "03-11-1998 16:32:08";
    docCont.newDelivery();
    docCont.setDepartureTime(0, date2);
    docCont.setTranportDate(0, date);
    addDriver(0, 208750760);
    addTruck(0, 421652160);

    setOrigin(0, 2);
    addStore(0, 3, 1);
    addStore(0, 4, 2);
    addSupplier(0, 1, 3);
    addWeightWhenLeaving(0, 1500);
    addProductsToDoc(0, new Tuple<>(2, 3), 3);
    addProductsToDoc(0, new Tuple<>(3, 1), 4);




}catch(Exception e){

}
    }



}
