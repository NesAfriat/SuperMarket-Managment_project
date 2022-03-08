package DataLayer.Mappers;

import BusinessLayer.Sales.Sale;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AffectedCategoriesMapper extends Mapper{
    public AffectedCategoriesMapper(){
        super();
        create_table();
    }
    @Override
    void create_table() {
        String AffectedCategoriesTable = "CREATE TABLE IF NOT EXISTS AffectedCategories(\n" +
                "\tsaleID INTEGER,\n"+
                "\tcatName TEXT,\n"+
                "\tPRIMARY KEY (saleID,catName),\n"+
                "\tFOREIGN KEY (saleID) REFERENCES Sales(saleID),\n"+
                "\tFOREIGN KEY (catName) REFERENCES Categories(catName)\n"+
                ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(AffectedCategoriesTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public LinkedList<Integer> getSalesID(String catName) {
        LinkedList<Integer> sales= new LinkedList<>();
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM AffectedCategories WHERE catName=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setString(1,catName);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int saleID = rs.getInt(1);
                    sales.add(saleID);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sales;
    }

    public LinkedList<String> getAffectedCategories(int saleID) {
        LinkedList<String> afftectedCategories= new LinkedList<>();
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM AffectedCategories WHERE saleID=? ";
            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, saleID);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    String catName = rs.getString(2);
                    afftectedCategories.add(catName);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return afftectedCategories;
    }
    public void insertAffected(Sale sale) { //by Product
        List<String> affected= sale.getAffected();
        try (Connection conn = connect()) {
            for(String category:affected)
            {
                String statement = "INSERT OR IGNORE INTO AffectedCategories(saleID, catName) " +
                        "VALUES (?,?)";

                try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                    pstmt.setInt(1, sale.getSale_id());
                    pstmt.setString(2,category);
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }} catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public boolean deleteAffected(Sale sale) {
        boolean deleted = false;
        try (Connection conn = connect()) {
            String statement = "DELETE FROM AffectedCategories WHERE saleID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, sale.getSale_id());
                deleted = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return deleted;
    }
}
