package BusinessLayer.Transport_BusinessLayer.Document;
import BusinessLayer.Transport_BusinessLayer.Drives.Driver;
import BusinessLayer.Transport_BusinessLayer.Drives.License;
import BusinessLayer.Transport_BusinessLayer.Drives.Truck;
import BusinessLayer.Transport_BusinessLayer.Shops.*;

import java.util.*;

import BusinessLayer.Transport_BusinessLayer.etc.Tuple;

public class TransportDoc {
    int id;
    String TransDate=null;
    String LeftOrigin=null;
    Truck truck=null;
    Driver driver=null;
    boolean approved= false;
    public int getVersion() {
        return version;
    }

    int version;

    int origin=-2147483648;
    // store,int
    HashMap<Integer, Integer/*Store*/> destinationStore;
    HashMap<Integer, Integer/*Supplier*/> destinationSupplier;
    ArrayList<Integer> allStops = new ArrayList<>();
    Area area=Area.A;
    double truckWeightDep=-1;
    List<Triple<Integer/*Product*/, Integer/*amount*/, Integer/*Store*/>> productList;
    public TransportDoc upDates=null;
    public TransportDoc(int id) {
        this.id = id;
        destinationStore = new HashMap<>();
        destinationSupplier = new HashMap<>();
        productList = new LinkedList<>();
        upDates=null;
        version=0;
    }
    public int getId() {
        return id;
    }



    public TransportDoc(int id, String transDate, String leftOrigin, Truck truck, Driver driver,
                        int origin, HashMap<Integer, Integer> destinationStore, HashMap<Integer,
                        Integer> destinationSupplier, Area area, double truckWeightDep,
                        List<Triple<Integer, Integer, Integer>> productList, ArrayList<Integer >allStops, int version) {
        this.id = id;
        TransDate = transDate;
        LeftOrigin = leftOrigin;
        this.truck = truck;
        this.driver = driver;
        this.origin = origin;
        this.destinationStore = destinationStore;
        this.destinationSupplier = destinationSupplier;
        this.area = area;
        this.truckWeightDep = truckWeightDep;
        this.productList = productList;
        this.upDates = null;
        this.allStops=allStops;
        this.version =version+1;
    }
    public String toString(){
        String acc="";
        acc = "id "+ id  +(TransDate!=null? ", Trasnport Date "+ TransDate.toString():"") + (LeftOrigin!=null? ", Time Left Origin "+ LeftOrigin.toString():"") + (truck!=null?", Truck: "+ truck.getName():"")
                +"\n" + (driver!=null?"Driver "+ driver.getName():"") + (origin!=-2147483648?", Origin "+ origin:"") + (area!=null?", Area "+ area:"") + (truckWeightDep!=-1?", Truck weight "+ truckWeightDep:" " )+ ", Approved: "+ approved+ "\n ";
        return acc;
    }
    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) throws Exception {
        if (truck!=null&&driver!=null&&origin!=-2147483648&&TransDate!=null&&LeftOrigin!=null) {
            this.approved = approved;
        }
        else{
            throw new Exception("all fileds must be filled before the doc is approved");
        }
    }
    public String destinationsString(){
        String acc="";
        Collections.sort(allStops);

        for (Integer i : allStops)
        {
            if(destinationStore.containsKey(i))
                 acc=acc+ "Stop<"+i+"> "+"id: "+ destinationStore.get(i).toString()+ " name: "+ destinationStore.get(i).toString() +"\n"  ;
            else
                acc=acc+ "Stop<"+i+"> "+"id: "+ destinationSupplier.get(i).toString()+ " name: "+ destinationSupplier.get(i).toString()+"\n" ;
        }
        return acc+"\n";
    }
    public String productsString(){
        String acc="";
        for (Triple<Integer/*Product*/, Integer, Integer/*Store*/>  trip : productList)
        {
            acc=acc+ "Product: " + trip.getFirst().toString() + ", Amount: " +trip.getSecond() + ", To Store:" + trip.getThird().toString() +"\n";
        }
        return acc;
    }
    public Driver getDriver(){
        return driver;
    }
    public Area getArea(){
        return area;
    }
    public double getTruckWeightDep() {
        return truckWeightDep;
    }

    public void setTruckWeightDep(double truckWeightDep) throws Exception {
        if(truckWeightDep>truck.getTruckType().getMaxWeight())
            throw new Exception("exceeded max weight for truck");
        this.truckWeightDep = truckWeightDep;

    }
    public void addVersionCount(){
        this.version++;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) throws Exception {
        if (this.origin != -2147483648)
            throw new Exception("cant set origin twice\n");
        this.origin = origin;
    }

    public String getTransDate() {
        return TransDate;
    }

    public void setTransDate(String transDate) {
        TransDate = transDate;
    }

    public String getLeftOrigin() {
        return LeftOrigin;
    }

    public void setLeftOrigin(String leftOrigin) {
        LeftOrigin = leftOrigin;
    }



    public List<Integer> getStores() {
        List<Integer> storeLinked = new LinkedList<>();
        Iterator StoreIterator = destinationStore.entrySet().iterator();
        while (StoreIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) StoreIterator.next();
            storeLinked.add((Integer) mapElement.getValue());

        }
        return storeLinked;
    }

    public void addStore(int store, int place) throws Exception {
        if (destinationStore.isEmpty()) {
            destinationStore.put(place, store);

        } else {

                if(!destinationStore.containsKey(place)) {
                    destinationStore.put(place, store);
                }
                else
                    throw new Exception("someone already in this place ");

        }
        allStops.add(place);

    }

    public void addSupplier(Integer supplier, int place) throws Exception {
        if (destinationSupplier.isEmpty()) {
            destinationSupplier.put(place, supplier);
            //this.area = supplier.getArea();
        } else {
            //if (this.area == supplier.getArea())
                if(!destinationSupplier.containsKey(place)) {
                    destinationSupplier.put(place, supplier);
                }
            else{
                throw new Exception("someone already in this place ");
                }
            //else
            //    throw new Exception("wrong area\n");
        }
        allStops.add(place);
    }
    public void setVersion(int version) {
        this.version = version;
    }

    public void addProduct(Triple<Integer, Integer, Integer> trip) {
        productList.add(trip);
    }

    public Tuple<List<Integer>, Area> retAvaliableSupp() {
        List<Integer> pd = new LinkedList<>();
        for (Triple<Integer, Integer, Integer> t : productList) {
            if (!pd.contains(t.getFirst()))
                pd.add(t.getFirst());
        }
        Tuple<List<Integer>, Area> tuple = new Tuple<>(pd, area);
        return tuple;
    }

    public void addTruck(Truck tk) {
        this.truck = tk;
    }

    public Truck getTruck(){
        return this.truck;
    }

    public void addDriver(Driver dr) throws Exception {
        boolean correctLicense = false;
        if(truck!=null) {
            for (License ls : truck.getTruckType().getLicensesForTruck()) {
                if (ls.compareTo(dr.getLicense()) == 0)
                    correctLicense = true;
            }
        }
        else
            correctLicense=true;
        if (correctLicense)
            this.driver = dr;
        else
            throw new Exception("the driver does not have the proper license\n");
    }

    public void addWeightWhenLeaving(double weight) {
        truckWeightDep = weight;
    }

    public void change(TransportDoc doc) {
        this.upDates = doc;
    }

    public void removeDestination(int place) throws Exception {
        boolean removed =false;
        if(destinationStore.containsKey(place))
        {
            destinationStore.remove(place);
            removed=true;
            Integer i = place;
            allStops.remove(i);

        }
        if(destinationSupplier.containsKey(place))
        {
            destinationSupplier.remove(place);
            removed=true;
            allStops.remove(place);

        }
            if(!removed)
                throw new Exception("destination does not exist");
    }
    public void removeProduct(int prod,int store) throws Exception {
        boolean removed =false;

        for (Triple<Integer,Integer,Integer> trip: productList)
        {
            if(trip.getFirst()==prod && trip.getThird()==store)
            {
                productList.remove(trip);
                removed=true;
            }
        }
        if(!removed)
            throw new Exception("destination does not exist");
    }
    public TransportDoc copyDeep(){

        HashMap<Integer, Integer> destinationStoreCopy = new HashMap<>();
        HashMap<Integer, Integer> destinationSupplierCopy = new HashMap<>();
        destinationStoreCopy.putAll(destinationStore);
        destinationSupplierCopy.putAll(destinationSupplier);
        List<Triple<Integer, Integer, Integer>> productListCopy = new LinkedList<>();
        for(Triple<Integer,Integer,Integer> trip : productList)
        {
            Triple<Integer,Integer,Integer> tr = new Triple<Integer, Integer, Integer>(trip.getFirst(),trip.getSecond(),trip.getThird());
            productListCopy.add(tr);

        }
        ArrayList<Integer> stopL= (ArrayList<Integer>) allStops.clone();

        return new TransportDoc(id,TransDate,LeftOrigin,truck,driver,origin,destinationStoreCopy,destinationSupplierCopy,area,truckWeightDep,productListCopy,stopL,version);
    }

    public HashMap<Integer, Integer> getDestinationStore() {
        return destinationStore;
    }

    public HashMap<Integer, Integer> getDestinationSupplier() {
        return destinationSupplier;
    }

    public List<Triple<Integer, Integer, Integer>> getProductList() {
        return productList;
    }

    public TransportDoc getUpDates() {
        return upDates;
    }
}
