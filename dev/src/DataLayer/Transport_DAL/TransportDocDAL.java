package DataLayer.Transport_DAL;

import BusinessLayer.Transport_BusinessLayer.Cont.DocCont;
import BusinessLayer.Transport_BusinessLayer.Document.TransportDoc;
import BusinessLayer.Transport_BusinessLayer.Document.Triple;
import BusinessLayer.Transport_BusinessLayer.Drives.License;
import BusinessLayer.Transport_BusinessLayer.Drives.Truck;
import BusinessLayer.Transport_BusinessLayer.Shops.Area;
import BusinessLayer.Transport_BusinessLayer.Shops.Product;
import BusinessLayer.Transport_BusinessLayer.Shops.Store;
import BusinessLayer.Transport_BusinessLayer.Shops.Supplier;


import java.sql.*;
import java.sql.Date;
import java.util.*;

public class TransportDocDAL {

    public HashMap<Integer, TransportDoc> LoadProducts () throws SQLException {
        HashMap<Integer, TransportDoc> theTransportBible = new HashMap<>();

        List<Product> stList = null;
        Connection conn = null;
        try{
            stList = new LinkedList<>();


            conn = Connect.getConnection();
            Statement st = conn.createStatement();
            Statement st1 = conn.createStatement();
            Statement st2 = conn.createStatement();
            boolean atLeastOne=true;
            int index=0;
          while(atLeastOne) {
              atLeastOne = false;
              int loops = 0;
              ResultSet results = st.executeQuery("SELECT * FROM TransportDocument where version = " + index);

              while (results.next()) {
                  ArrayList<Integer > allStops = new ArrayList<>();
                  atLeastOne=true;
                  String driverName, area,driverLicense;
                  int id, version, driverID, truckID, originStoreID,approve;
                  boolean approvedOrder;
                  double truckWeightDep;
                  String TransDate, LeftOrigin;

                  //transport doc primitive values
                  id = results.getInt(1);
                  version = results.getInt(2);
                  TransDate = results.getString(3);
                  LeftOrigin = results.getString(4);
                  driverName = results.getString(5);
                  driverID = results.getInt(6);
                  driverLicense = results.getString(7);
                  truckID = results.getInt(8);
                  originStoreID = results.getInt(9);
                  area = results.getString(10);
                  truckWeightDep = results.getDouble(11);
                  approvedOrder = (1== results.getInt(12));

                  //transport doc objects
                  Truck trk = TruckDAL.findTruck(truckID);
                  License lcs =null;
                  switch(driverLicense){
                      case "typeA":
                          lcs=License.typeA;
                          break;
                      case "typeB":
                          lcs=License.typeB;
                          break;
                      case "typeC":
                          lcs=License.typeC;
                          break;
                      case "typeD":
                          lcs=License.typeD;
                          break;
                          }
                  BusinessLayer.Transport_BusinessLayer.Drives.Driver drv = new BusinessLayer.Transport_BusinessLayer.Drives.Driver(driverName,driverID,lcs);

                    //store and supplier hashmap
                  HashMap<Integer, Integer> destinationStore = new HashMap<>();
                  ResultSet resultsHash = st1.executeQuery("SELECT * FROM TransportStopStores where version = " + index+ " and id = "+ id);
                  while (resultsHash.next()) {
                      int stopNumber, storeIDHash;

                      stopNumber = resultsHash.getInt(2) ;
                      storeIDHash = resultsHash.getInt(3);
                      allStops.add(stopNumber);
                      destinationStore.put(stopNumber,storeIDHash);
                  }
                  HashMap<Integer, Integer> destinationSupplier = new HashMap<>();
                  ResultSet resultsHash2 = st1.executeQuery("SELECT * FROM TransportStopSupplier where version = " + index+ " and ID = "+ id);
                  while (resultsHash2.next()) {
                      int stopNumber, supplierIDHash;
                      stopNumber = resultsHash2.getInt(2) ;
                      supplierIDHash = resultsHash2.getInt(3);
                      allStops.add(stopNumber);
                      destinationSupplier.put(stopNumber,supplierIDHash);
                  }


                  // create triple list of store product and amount
                  resultsHash = st1.executeQuery("SELECT * FROM TransportDocStoreProduct where Version = " + index+ " and DocumentID = "+ id);
                  List<Triple<Integer, Integer, Integer>> productList = new LinkedList<>();
                  while(resultsHash.next()) {
                        int prodID, StoreID, Amount;
                        prodID = resultsHash.getInt(1);
                        StoreID = resultsHash.getInt(2);
                        Amount = resultsHash.getInt(4);



                        Triple<Integer, Integer, Integer> trp = new Triple<>(prodID,Amount,StoreID);
                        productList.add(trp);
                    }
                           Collections.sort(allStops);
                          TransportDoc td = new TransportDoc(id,TransDate, LeftOrigin,trk,drv,originStoreID,destinationStore,destinationSupplier, Area.A,truckWeightDep,productList, allStops,index);

                          td.setApproved(approvedOrder);

                          td.setVersion(index);
                          if(index==0)
                              theTransportBible.put(td.getId(),td);

                          else
                              DocCont.getUpToDateDoc(theTransportBible.get(td.getId())).upDates = td;



              }
              index++;
          }

        } catch(Exception e) {

            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return theTransportBible;
    }

    public void saveDoc(TransportDoc doc)  {

        Connection conn = null;
        try {
            if(doc !=null) {
                conn = Connect.getConnection();
                Statement st = conn.createStatement();
                //delete the not up to date records
                String delete = "DELETE FROM TransportDocument WHERE Id='" + doc.getId() + "' AND Version='" + doc.getVersion() + "'";
                st.executeUpdate(delete);
                String delete2 = "DELETE FROM TransportDocStoreProduct WHERE DocumentId='" + doc.getId() + "' AND Version='" + doc.getVersion() + "'";
                st.executeUpdate(delete2);
                String delete3 = "DELETE FROM TransportStopStores WHERE Id='" + doc.getId() + "' AND Version='" + doc.getVersion() + "'";
                st.executeUpdate(delete3);
                String delete4 = "DELETE FROM TransportStopSupplier WHERE Id='" + doc.getId() + "' AND Version='" + doc.getVersion() + "'";
                st.executeUpdate(delete4);




                //now save to all tables for id
                String insert1 = "INSERT INTO TransportDocument " + "VALUES ("+ doc.getId() + "," + doc.getVersion() + ",'" + doc.getTransDate() +
                        "','" + doc.getLeftOrigin() + "','" + doc.getDriver().getName() + "'," + doc.getDriver().getId() + ",'" + LicenseToString(doc.getDriver().getLicense()) +
                        "'," + doc.getTruck().getLicensePlate() + "," + doc.getOrigin() + ",'" + doc.getArea().toString() + "'," + doc.getTruckWeightDep() + "," + (doc.isApproved()?1:0)+");";
                st.executeUpdate(insert1);

                Iterator itStore=doc.getDestinationStore().entrySet().iterator();
                while(itStore.hasNext()) {
                    Map.Entry pair =(Map.Entry)itStore.next();
                    String insert2 = "INSERT INTO TransportStopStores " + "VALUES (" + doc.getId() + "," + pair.getKey()+ "," + (int)pair.getValue()  +
                            "," + doc.getVersion() + ");";
                    st.executeUpdate(insert2);
                }

                Iterator itSupplier=doc.getDestinationSupplier().entrySet().iterator();
                while(itSupplier.hasNext()) {
                    Map.Entry pair =(Map.Entry)itSupplier.next();
                    String insert2 = "INSERT INTO TransportStopSupplier " + "VALUES (" + doc.getId() + "," + pair.getKey()+ "," + (int)pair.getValue()  +
                            "," + doc.getVersion() + ");";
                    st.executeUpdate(insert2);
                }

                List<Triple<Integer/*Product*/, Integer,Integer/* Store*/>> l=doc.getProductList();
                for (Triple<Integer,Integer,Integer> t: l) {
                    String insert2 = "INSERT INTO TransportDocStoreProduct " + "VALUES (" + t.getFirst() + "," + t.getThird()+ "," + doc.getId()  +
                            "," + t.getSecond() +  "," + doc.getVersion() +" );";
                    st.executeUpdate(insert2);
                }
                saveDoc(doc.upDates);
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    private String LicenseToString(License license) throws Exception {
        String output="error" ;

        switch (license) {
            case typeA:
                output = "typeA";
                break;
            case typeB:
                output = "typeB";
                break;
            case typeC:
                output = "typeC";
                break;
            case typeD:
                output = "typeD";
                break;
        }
        if(output.equals("error")){
            throw new Exception("cant convert License to string ");
        }
        return output;

    }

    private  java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }


}
