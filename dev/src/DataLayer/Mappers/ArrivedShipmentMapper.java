package DataLayer.Mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import BusinessLayer.Item;
import DataLayer.DataController;

import java.sql.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;


public class ArrivedShipmentMapper extends Mapper{
    public ArrivedShipmentMapper(){
        super();
        create_table();
    }
    @Override
    void create_table() {
        String ArrivedShippmentTable = "CREATE TABLE IF NOT EXISTS ArrivedShipment(\n" +
                "\tgpID INTEGER PRIMARY KEY,\n" +
                "\tquantity INTEGER " +
                ");";
//        String sql = "BEGIN TRANSACTION;" + GeneralProductTable + "COMMIT;";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(ArrivedShippmentTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(int gpID, int quantity) throws SQLException {
        boolean output = false;
        try (Connection conn = connect()) {
            boolean inserted = false;
            String statement = "INSERT INTO ArrivedShipment(gpID, quantity) " +
                    "VALUES (?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1,gpID);
                pstmt.setInt(2, quantity);

                output = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                throw new SQLException();
            }
        } catch (SQLException throwables) {
            throw new SQLException();
        }
    }
    public void addQuantity(int gpID, int quantity){
        boolean updated = false;
        try (Connection conn = connect()) {
            String statement = "UPDATE ArrivedShipment SET gpID=?, quantity=?+quantity WHERE gpID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1,gpID);
                pstmt.setInt(2, quantity);
                pstmt.setInt(3,gpID);

                updated = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public HashMap<Integer, Integer> getLastShipment() {
        HashMap<Integer,Integer> supply = new HashMap<>();
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM ArrivedShipment ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int gpID = rs.getInt(1);
                    int quantity = rs.getInt(2);
                    supply.put(gpID, quantity);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return supply;
    }

    public void cleanShipment()
    {
        try (Connection conn = connect()) {
            String statement = "DELETE FROM ArrivedShipment";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


}
