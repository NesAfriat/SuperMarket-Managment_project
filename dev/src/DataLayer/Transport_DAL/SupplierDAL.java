package DataLayer.Transport_DAL;

import BusinessLayer.Transport_BusinessLayer.Shops.Area;
import BusinessLayer.Transport_BusinessLayer.Shops.Supplier;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SupplierDAL {
/*
    public static Supplier findSupplier(int supplierID) {

        Connection conn = null;
        try {


            conn = Connect.getConnection();
            Statement st = conn.createStatement();
            ResultSet results = st.executeQuery("SELECT * FROM Supplier where id =  " + supplierID);
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

                return new Supplier(name, id, phoneNumber, contactName, a);
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

    public List<Integer> LoadSupplierProductList (int SupplierID) throws SQLException {
        List<Integer> stList = null;
        Connection conn = null;
        try {
            stList = new LinkedList<>();

            conn = Connect.getConnection();
            Statement st = conn.createStatement();
            ResultSet results = st.executeQuery("SELECT * FROM SupplierProducts where SupplierID =" + SupplierID);
            int id = 0;
            while (results.next()) {
                id = results.getInt(2);
                stList.add(id);
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


    public List<Supplier> LoadEmptySuppliers () throws SQLException {
        List<Supplier> stList = null;
        Connection conn = null;
        try{
            stList = new LinkedList<>();

            conn = Connect.getConnection();
            Statement st = conn.createStatement();
            ResultSet results = st.executeQuery("SELECT * FROM Supplier");
            while(results.next()){

                String name, contactName,phoneNumber,area;
                Area a;
                int id;
                name= results.getString(1);
                id= results.getInt(2);
                phoneNumber= results.getString(3);
                contactName= results.getString(4);
                area= results.getString(5);
                switch (area){
                    case "A":
                        a=Area.A;
                        break;
                    case "B":
                        a=Area.B;
                        break;
                    case "C":
                        a=Area.C;
                        break;
                    default:
                        a=null;
                        System.out.println("wrong area");

                }

                stList.add(new Supplier(name,id,phoneNumber,contactName,a));
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

*/
}

