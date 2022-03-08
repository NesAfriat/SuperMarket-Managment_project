package DataLayer.Transport_DAL;

import BusinessLayer.Transport_BusinessLayer.Shops.Area;
import BusinessLayer.Transport_BusinessLayer.Shops.Store;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class StoreDAL {
/*
    public static Store findStore(int storeID) {

        Connection conn = null;
        try {


            conn = Connect.getConnection();
            Statement st = conn.createStatement();
            ResultSet results = st.executeQuery("SELECT * FROM Store where id =  " + storeID);
            while (results.next()) {

                String name, contactName, phoneNumber, area;
                Area a;
                int id;
                name = results.getString(1);
                id = results.getInt(2);
                phoneNumber = results.getString(3);
                contactName = results.getString(4);
                area = results.getString(5);
                switch (area) {
                    case "A":
                        a = Area.A;
                        break;
                    case "B":
                        a = Area.B;
                        break;
                    case "C":
                        a = Area.C;
                        break;
                    default:
                        a = null;
                        System.out.println("wrong area");

                }

                return new Store(name, id, phoneNumber, contactName, a);
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
    public List<Store> LoadStores() throws SQLException {
        List<Store> stList = null;
        Connection conn = null;
        try {
            stList = new LinkedList<>();

            conn = Connect.getConnection();
            Statement st = conn.createStatement();
            ResultSet results = st.executeQuery("SELECT * FROM Store");
            while (results.next()) {

                String name, contactName, phoneNumber, area;
                Area a;
                int id;
                name = results.getString(1);
                id = results.getInt(2);
                phoneNumber = results.getString(3);
                contactName = results.getString(4);
                area = results.getString(5);
                switch (area) {
                    case "A":
                        a = Area.A;
                        break;
                    case "B":
                        a = Area.B;
                        break;
                    case "C":
                        a = Area.C;
                        break;
                    default:
                        a = null;
                        System.out.println("wrong area");

                }

                stList.add(new Store(name, id, phoneNumber, contactName, a));
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
    public static void saveStore(Store store) {
        Connection conn = null;
        try {

            conn = Connect.getConnection();
            Statement st = conn.createStatement();
            //saveTruckType(truck.getTruckType());
            String insert = "INSERT INTO Store " + "VALUES ('" + store.getName() + "'," + store.getId() + ","+store.getPhoneNumber()+","+store.getContact()+","+store.getArea()+");";
            st.executeUpdate(insert);


        } catch (SQLException e) {
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
    }
*/
}

