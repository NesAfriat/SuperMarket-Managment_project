package BusinessLayer.Transport_BusinessLayer.Cont;

import BusinessLayer.Transport_BusinessLayer.DriversFactory;
import BusinessLayer.Transport_BusinessLayer.Drives.Driver;
import BusinessLayer.Transport_BusinessLayer.Drives.License;
import BusinessLayer.Transport_BusinessLayer.Drives.Truck;
import BusinessLayer.Transport_BusinessLayer.Drives.TruckType;
import BusinessLayer.Transport_BusinessLayer.etc.Tuple;
import BusinessLayer.Workers_Integration;
import DataLayer.Transport_DAL.DALController;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DriversController {
    private List<Driver> Drivers;
    private List<Truck> Trucks;
   // private List<Tuple<Truck,Driver>> Occupied;
    private DriversFactory DriversFac;

    public List<Driver> getDrivers(String date,int shift,List<License> license) {

        Drivers=DriversFac.getDrivers(date,shift,license);
        return Drivers;
    }
    public boolean isStoreKeeper(String date) {

        return DriversFac.isStoreKeeper(date);

    }
    public Tuple<String, List<Driver>> getDateWithDriver(String date) throws ParseException {
        List<License> licenses=new ArrayList<>();
        licenses.add(License.typeA);
        licenses.add(License.typeB);
        licenses.add(License.typeC);

        return DriversFac.getDriversWeekly(date,licenses);
    }

    /*public DriversController(List<Driver> drivers, List<Truck> trucks, List<Tuple<Truck,Driver>> occupied) {
        Drivers = drivers;
        Trucks = trucks;
        this.Occupied = occupied;
    }*/
    /*public DriversController(List<Driver> drivers, List<Truck> trucks) {
        Drivers = drivers;
        Trucks = trucks;
        this.Occupied = new LinkedList<>();
    }*/
    public DriversController() {
       // Drivers = new LinkedList<>();
        Trucks = new LinkedList<>();
       // this.Occupied = new LinkedList<>();
    }

    private <T>List<T> CopyList(List<T> original){//return copy list with final objects
        final List<T> finalCopy=new LinkedList<>();
        for (T el:original) {
            final T clel=el;
            finalCopy.add(clel);
        }
        return finalCopy;
    }



    public List<Truck> getTrucks() {
        return Trucks;
    }


  /*  public void AddNewDriver(String name,int id,License license) {
        Driver newDriver = new Driver(name, id, license);
        Drivers.add(newDriver);
    }*/
    public void AddNewTruck(String name,int licensePlate,TruckType type) {
        Truck newTruck = new Truck(name, licensePlate, type);
        Trucks.add(newTruck);
    }
    public void AddNewTruckAndBuildNewTruckType(String name,int licensePlate, String typeName,int typeMaxWeight,int typeFreeWeight,List<License> licenseList) {
        Truck newTruck = new Truck(name, licensePlate, new TruckType(typeName,typeMaxWeight,typeFreeWeight,licenseList));
        Trucks.add(newTruck);
    }

    public List<Truck> getCompatibleTrucks(Driver driver){

        List<Truck> conTrucks= Trucks.stream().filter(tr->
        {
            /*for (Tuple<Truck,Driver> y:Occupied) {
                if(tr.getLicensePlate()==y.x.getLicensePlate()){
                    return false;
                }
            }*/
            return tr.getTruckType().getLicensesForTruck().contains(driver.getLicense());
        }).collect(Collectors.toList());
       return conTrucks;

    }

   /*public List<Driver> getCompatibleDrivers(Truck truck,Date date ){

        List<Driver> d=DriversFac.getDrivers(date, truck.getTruckType().getLicensesForTruck());
        List<Driver> conDrivers= d.stream().filter(Dr->
        {
           if(!Occupied.isEmpty()) {
                for (Tuple<Truck, Driver> y : Occupied) {
                    if (Dr.getLicense() == y.y.getLicense()) {
                        return false;
                    }
                }
            }
            return truck.getTruckType().getLicensesForTruck().contains(Dr.getLicense());
        }).collect(Collectors.toList());;

        return conDrivers;

    }*/

//todo create new func taht return all druvers in date

    public void connectDriverAndTruck(Driver driver,Truck truck) throws Exception{//create reserve a truck and a driver

        /*boolean alreadyOccupied=Occupied.stream().allMatch(tup->{
            if(tup.x.getLicensePlate()==truck.getLicensePlate()){
                throw new RuntimeException("truck already occupied");
                //return false;
            }
            else if(driver.getId()==tup.y.getId()){
                throw new RuntimeException("driver  already occupied");
                //return false;
            }
               return true;
            });*/
        if(/*alreadyOccupied &&*/ truck.getTruckType().getLicensesForTruck().contains(driver.getLicense())){
            //Occupied.add(new Tuple<>(truck,driver));
        }
        else
            throw new RuntimeException("driver and truck not have the same licence");
    }
   /* public void changeTruck(Truck newTruck,Truck oldTruck) throws Exception {
        Optional<Tuple<Truck,Driver>> newTruckOccupied  =Occupied.stream().filter(tuple->tuple.x.getLicensePlate()== newTruck.getLicensePlate()).findFirst();
        if(!newTruckOccupied.isEmpty()){
            throw new Exception("new truck already Occupied");
        }
        Optional<Tuple<Truck,Driver>> oldTruckTuple  =Occupied.stream().filter(tuple->tuple.x.getLicensePlate()== oldTruck.getLicensePlate()).findFirst();
        if(oldTruckTuple.isEmpty()){
            throw new Exception("old truck not in delivery so cant change trucks");
        }

        if(Occupied.remove(oldTruckTuple.get())){
            throw new Exception("change failed");
        }
        connectDriverAndTruck(oldTruckTuple.get().y,newTruck);

    }*/
    /*public void changeDriver(Driver newDriver,Driver oldDriver) throws Exception {
        Optional<Tuple<Truck,Driver>> newDriverOccupied  =Occupied.stream().filter(tuple->tuple.y.getId()== newDriver.getId()).findFirst();
        if(!newDriverOccupied.isEmpty()){
            throw new Exception("new driver already Occupied");
        }
        Optional<Tuple<Truck,Driver>> oldDriverTuple  =Occupied.stream().filter(tuple->tuple.y.getId()== oldDriver.getId()).findFirst();
        if(oldDriverTuple.isEmpty()){
            throw new Exception("old Driver not in delivery so cant change Driver");
        }



        if(Occupied.remove(oldDriverTuple.get())){
            throw new Exception("change failed");
        }
        connectDriverAndTruck( newDriver,oldDriverTuple.get().x);
    }*/
    /*public void FreeDriverAndTruck(Driver driver) throws Exception {
        Optional<Tuple<Truck,Driver>> driverTup =Occupied.stream().filter(tuple->tuple.y.getId()== driver.getId()).findFirst();
        if(driverTup.isEmpty()){
            throw new Exception("driver not in occupied list");
        }
        if(Occupied.remove(driverTup.get())){
            throw new Exception("failed to free");
        }
    }
    public void FreeDriverAndTruck(Truck truck) throws Exception {
        Optional<Tuple<Truck,Driver>> truckTup =Occupied.stream().filter(tuple->tuple.x.getLicensePlate()== truck.getLicensePlate()).findFirst();
        if(truckTup.isEmpty()){
            throw new Exception("truck not in occupied list");
        }
        if(Occupied.remove(truckTup.get())){
            throw new Exception("failed to free");
        }
    }
*/
    public Truck getTruck(int idTruck) throws Exception {
        for(Truck tk:Trucks){
            if(tk.getLicensePlate() == idTruck)
                return tk;
        }
        throw new Exception("truck not found");
    }

    public void load() throws Exception {
        DALController con=DALController.getInstance();
        try {
            Trucks=con.tru.LoadTrucks();
        } catch (SQLException throwables) {
            throw new Exception("Error Loading Documents");
        }
    }

    public Driver getDriverWithID(int driverId) throws Exception {
        for (Driver d :Drivers){
            if (d.getId()==driverId)
                return d;
        }

        throw new Exception("could not find driver");

    }

    public void addWorkersInterface(Workers_Integration wk) {
        DriversFac = new DriversFactory(wk);
    }
}
