package DataLayer.Mappers;

import BusinessLayer.Sales.Sale;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AffectedProductsMapper extends Mapper{
    public AffectedProductsMapper(){
        super();
        create_table();
    }

    @Override
    void create_table() {
            String AffectedProductsTable = "CREATE TABLE IF NOT EXISTS AffectedProducts(\n" +
                    "\tsaleID INTEGER,\n"+
                    "\tgpName TEXT NOT NULL,\n"+
                    "\tPRIMARY KEY (saleID,gpName),\n"+
                    "\tFOREIGN KEY (saleID) REFERENCES Sales(saleID)\n"+
                    ");";
            try (Connection conn = connect();
                 Statement stmt = conn.createStatement()) {
                // create a new tables
                stmt.execute(AffectedProductsTable);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public LinkedList<Integer> getSalesID(String productName) {
            LinkedList<Integer> sales= new LinkedList<>();
                try (Connection conn = connect()) {
                    String statement = "SELECT * FROM AffectedProducts WHERE gpName=? ";

                    try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                        pstmt.setString(1, productName);
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
    public LinkedList<String> getAffectedProucts(int saleID) {
        LinkedList<String> afftectedProducts= new LinkedList<>();
            try (Connection conn = connect()) {
                String statement = "SELECT * FROM AffectedProducts WHERE saleID=? ";
                try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                    pstmt.setInt(1, saleID);
                    ResultSet rs = pstmt.executeQuery();

                    if (rs.next()) {
                        String prodName = rs.getString(2);
                        afftectedProducts.add(prodName);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        return afftectedProducts;
    }

    public void insertAffected(Sale sale) { //by Product
        List<String> affected= sale.getAffected();
        try (Connection conn = connect()) {
            for(String product:affected)
            {
            String statement = "INSERT OR IGNORE INTO AffectedProducts(saleID, gpName) " +
                    "VALUES (?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, sale.getSale_id());
                pstmt.setString(2,product);
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
            String statement = "DELETE FROM AffectedProducts WHERE saleID=? ";

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
