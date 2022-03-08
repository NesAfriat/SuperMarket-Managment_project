package DataLayer.Transport_DAL;

import BusinessLayer.Transport_BusinessLayer.Shops.Product;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProductDAL {

/*
    public static Product findProduct (int prodId) throws SQLException {
        Connection conn = null;
        try{

            conn = Connect.getConnection();
            Statement st = conn.createStatement();
            ResultSet results = st.executeQuery("SELECT * FROM Product where Id = " + prodId);
            while(results.next()){

                String name;
                int id;
                id= results.getInt(1);
                name= results.getString(2);


                return new Product(name,id);
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

    public List<Product> LoadProducts () throws SQLException {
        List<Product> stList = null;
        Connection conn = null;
        try{
            stList = new LinkedList<>();

            conn = Connect.getConnection();
            Statement st = conn.createStatement();
            ResultSet results = st.executeQuery("SELECT * FROM Product");
            while(results.next()){

                String name;
                int id;
                id= results.getInt(1);
                name= results.getString(2);


                stList.add(new Product(name,id));
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



    public static void saveProduct(Product product) {
        Connection conn = null;
        try {

            conn = Connect.getConnection();
            Statement st = conn.createStatement();
            //saveTruckType(truck.getTruckType());
            String insert = "INSERT INTO Product " + "VALUES (" + product.getId() + ",'" + product.getName() + "');";
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

