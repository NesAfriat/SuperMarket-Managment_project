package DataLayer.Mappers;

import BusinessLayer.Agreement;

import java.sql.*;

public class AgreementProductDiscMapper extends Mapper{
    public AgreementProductDiscMapper() {
        super();
        create_table();
    }


    @Override
    void create_table() {
        String AgreementProductsDiscTable = "CREATE TABLE IF NOT EXISTS AgreementProductDisc(\n" +
                                            "\tsupID INTEGER,\n" +
                                            "\tcatalogID INTEGER,\n" +
                                            "\tquantity INTEGER,\n" +
                                            "\tprice REAL,\n" +
                                            "\tPRIMARY KEY (supID, catalogID, quantity),\n" +
                                            "\tFOREIGN KEY (supID) REFERENCES Agreement(supID),\n" +
                                            "\tFOREIGN KEY (catalogID) REFERENCES SuppliersProducts(catalogID)\n" +
                                            ");";
        //        String sql = "BEGIN TRANSACTION;" + itemTable + "COMMIT;";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(AgreementProductsDiscTable);
            //TODO: in DataController - need to activate loadData
//                      if (!identityMap.initialized){
//                                LoadPreData();
//                                identityMap.initialized = true;
//                            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



           public boolean addQuantityDiscAgreement(int SupId,int catalogId,int quantity,double Price) {
               boolean output = false;
               try (Connection conn = connect()) {//String statement = "UPDATE OrderProducts SET oID=?, catalogID=?, quantity=?";
                   boolean inserted = false;
                   String statement = "INSERT OR IGNORE INTO AgreementProductDisc(supID, catalogID, quantity, price) " +
                           "VALUES (?,?,?,?)";

                   try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                       pstmt.setInt(1, SupId);
                       pstmt.setInt(2, catalogId);
                       pstmt.setInt(3, quantity);
                       pstmt.setDouble(4, Price);

                       output = pstmt.executeUpdate() != 0;
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               } catch (SQLException throwables) {
                   throwables.printStackTrace();
               }
               return output;
           }



           public boolean RemoveQuantityDiscAgreement(int SupId,int catalogId,int quantity) {
               boolean deleted = false;
               try (Connection conn = connect()) {
                   String statement = "DELETE FROM AgreementProductDisc WHERE supID=? AND catalogID=? AND quantity=?";
                   try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                       pstmt.setInt(1, SupId);
                       pstmt.setInt(2, catalogId);
                       pstmt.setInt(3, quantity);

                       deleted = pstmt.executeUpdate() != 0;
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               } catch (SQLException throwables) {
                   throwables.printStackTrace();
               }
               return deleted;
           }


           public boolean UpdateQuantityDiscAgreement(int SupId,int catalogId,int quantity,int price) {
               boolean updated = false;
               try (Connection conn = connect()) {
                   String statement = "UPDATE AgreementProductDisc SET price=? WHERE supID=? AND catalogID=? AND quantity=?";

                   try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                       pstmt.setInt(1, price);
                       pstmt.setInt(2, SupId);
                       pstmt.setInt(3, catalogId);
                       pstmt.setInt(4,quantity );
                       updated = pstmt.executeUpdate() != 0;
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               } catch (SQLException throwables) {
                   throwables.printStackTrace();
               }
               return updated;
           }





           public void addQuantityDiscAgreement(Agreement agr){
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM AgreementProductDisc WHERE supID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, agr.getSupplierID());

                ResultSet rs = pstmt.executeQuery();
                while(rs.next()) {
                    int catalogID = rs.getInt(2);
                    int amount = rs.getInt(3);
                    double price = rs.getDouble(4);
                    agr.addDiscountByProductQuantity(catalogID,amount,price);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
