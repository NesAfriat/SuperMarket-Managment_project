package DataLayer.Mappers;

import BusinessLayer.Agreement;

import java.sql.*;

public class AgreementDeliveryDaysMapper extends Mapper{
    public AgreementDeliveryDaysMapper() {
        super();
        create_table();
    }


    public boolean addAgreementDeliveryDaysAgreement (int SupID,int Day) {
        boolean output = false;
        try (Connection conn = connect()) {//String statement = "UPDATE OrderProducts SET oID=?, catalogID=?, quantity=?";
            boolean inserted = false;
            String statement = "INSERT OR IGNORE INTO AgreementDeliveryDays(deliveryDay, supID) " +
                    "VALUES (?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, Day);
                pstmt.setInt(2, SupID);

                output = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;

    }

    public boolean RemoveAgreementDeliveryDays(int SupId,int Day) {


        boolean deleted = false;
        try (Connection conn = connect()) {
            String statement = "DELETE FROM AgreementDeliveryDays WHERE supID=? AND deliveryDay=?";
            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, SupId);
                pstmt.setInt(2, Day);
                deleted = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return deleted;
  }


    @Override
    void create_table() {
        String Table = "CREATE TABLE IF NOT EXISTS AgreementDeliveryDays(\n" +
                        "\tdeliveryDay INTEGER,\n" +
                        "\tsupID INTEGER,\n" +
                        "\tPRIMARY KEY(deliveryDay, supID),\n" +
                        "\tFOREIGN KEY(supID) REFERENCES Agreement(supID)\n" +
                        ");";
        //        String sql = "BEGIN TRANSACTION;" + itemTable + "COMMIT;";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(Table);
            //TODO: in DataController - need to activate loadData
//                      if (!identityMap.initialized){
//                                LoadPreData();
//                                identityMap.initialized = true;
//                            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDaysDelivery(Agreement agr){
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM AgreementDeliveryDays WHERE supID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, agr.getSupplierID());

                ResultSet rs = pstmt.executeQuery();
                while(rs.next()) {
                    String day = rs.getString(1);

                    agr.addDeliveryDay(Integer.parseInt(day));

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
