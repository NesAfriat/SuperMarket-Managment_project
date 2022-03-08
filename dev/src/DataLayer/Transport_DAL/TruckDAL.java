package DataLayer.Transport_DAL;

import BusinessLayer.Transport_BusinessLayer.Drives.License;
import BusinessLayer.Transport_BusinessLayer.Drives.Truck;
import BusinessLayer.Transport_BusinessLayer.Drives.TruckType;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TruckDAL {

    public static Truck findTruck(int licensePlateID) {
        Connection conn = null;

        // first we will create a hashmap of truck types, then add new trucks with truck type.
        List<License> licenseList2 = new LinkedList<License>();
        licenseList2.add(License.typeA);
        licenseList2.add(License.typeB);
        List<License> licenseList3 = new LinkedList<License>();
        licenseList3.add(License.typeA);
        licenseList3.add(License.typeB);
        licenseList3.add(License.typeC);
        List<License> licenseList1 = new LinkedList<License>();
        licenseList1.add(License.typeA);
        try {
            HashMap<String, TruckType> hmTruckType = new HashMap<String, TruckType>();

            
            conn = Connect.getConnection();
            Statement st = conn.createStatement();
            ResultSet results = st.executeQuery("SELECT * FROM TruckType");

            while (results.next()) {

                String name;
                int maxWeight, freeWeight, LicenseForTruck;
                name = results.getString(1);
                maxWeight = results.getInt(2);
                freeWeight = results.getInt(3);
                LicenseForTruck = results.getInt(4);
                List<License> theTrueLicense = null;
                switch (LicenseForTruck) {
                    case 1:
                        theTrueLicense = licenseList1;
                        break;
                    case 2:
                        theTrueLicense = licenseList2;
                        break;
                    case 3:
                        theTrueLicense = licenseList3;

                }

                hmTruckType.put(name, new TruckType(name, maxWeight, freeWeight, theTrueLicense));

            }
            ResultSet results2 = st.executeQuery("SELECT * FROM Truck where LicensePlate =  " + licensePlateID);
            while (results2.next()) {
                String name, truckType;
                int licensePlate;
                name = results.getString(1);
                licensePlate = results.getInt(2);
                truckType = results.getString(3);

                return new Truck(name, licensePlate, hmTruckType.get(truckType));
            }


        } catch (
                SQLException e) {

            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }





    public List<Truck> LoadTrucks () throws SQLException {
        List<Truck> stList = null;
        Connection conn = null;

        // first we will create a hashmap of truck types, then add new trucks with truck type.
        List<License> licenseList2 = new LinkedList<License>();
        licenseList2.add(License.typeA);
        licenseList2.add(License.typeB);
        List<License> licenseList3 = new LinkedList<License>();
        licenseList3.add(License.typeA);
        licenseList3.add(License.typeB);
        licenseList3.add(License.typeC);
        List<License> licenseList1 = new LinkedList<License>();
        licenseList1.add(License.typeA);
        try{
            HashMap <String, TruckType> hmTruckType = new HashMap<String, TruckType>();
            stList = new LinkedList<>();

            conn = Connect.getConnection();

            //System.out.println("Connection to SQLite has been established.");

            Statement st = conn.createStatement();
            ResultSet results = st.executeQuery("SELECT * FROM TruckType");

            while(results.next()){

                String name;
                int maxWeight,freeWeight,LicenseForTruck;
                name= results.getString(1);
                maxWeight= results.getInt(2);
                freeWeight= results.getInt(3);
                LicenseForTruck= results.getInt(4);
                List<License> theTrueLicense =null;
                switch(LicenseForTruck){
                    case 1:
                        theTrueLicense=licenseList1;
                        break;
                    case 2:
                        theTrueLicense = licenseList2;
                        break;
                    case 3:
                        theTrueLicense = licenseList3;

                }

                hmTruckType.put(name,new TruckType(name,maxWeight,freeWeight,theTrueLicense));

            }
            ResultSet results2 = st.executeQuery("SELECT * FROM Truck");
            while(results2.next()) {
                String name, truckType;
                int licensePlate;
                name = results.getString(1);
                licensePlate = results.getInt(2);
                truckType = results.getString(3);

                stList.add(new Truck(name,licensePlate,hmTruckType.get(truckType) ));
            }


        } catch (
                SQLException e) {

            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return stList;
    }
}
