package DataLayer.Mappers;

import BusinessLayer.SupplierBuissness.Contact;
import BusinessLayer.SupplierBuissness.Supplier;

import java.sql.*;

public class SuppliersContactsMapper extends Mapper {
    public SuppliersContactsMapper() {
        super();
        create_table();
    }

    @Override
    void create_table() {
        String ContactsTable = "CREATE TABLE IF NOT EXISTS SuppliersContacts(\n" +
                "\tsupID INTEGER,\n" +
                "\tcntcID INTEGER,\n" +
                "\tcntcName TEXT,\n" +
                "\tphoneNumber TEXT,\n" +
                "\temail TEXT,\n" +

                "\tPRIMARY KEY (supID, cntcID),\n" +
                "\tFOREIGN KEY (supID) REFERENCES Suppliers (supID)\n" +
                " );";

//        String sql = "BEGIN TRANSACTION;" + GeneralProductTable + "COMMIT;";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(ContactsTable);
            //TODO: in DataController - need to activate loadData
//            if (!identityMap.initialized){
//                LoadPreData();
//                identityMap.initialized = true;
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Contact getContact(int supplier_id, int contact_id) {
        Contact obj = null;
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM SuppliersContacts WHERE supID=? AND cntcID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, supplier_id);
                pstmt.setInt(2, contact_id);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int supid = rs.getInt(1);
                    int id = rs.getInt(2);
                    String name = rs.getString(3);
                    String phone = rs.getString(4);
                    String email = rs.getString(5);
                    obj = new Contact(id, name, phone, email, supid);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public boolean update(Contact con, int supplier_id) {
        boolean updated = false;
        try (Connection conn = connect()) {
            String statement = "UPDATE SuppliersContacts SET supID=?, cntcID=?, cntcName=?, phoneNumber=? email=? WHERE supID=? AND cntcID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, supplier_id);
                pstmt.setInt(2, con.getId());
                pstmt.setString(3, con.getContactName());
                pstmt.setString(4, con.getPhoneNumber());
                pstmt.setString(5, con.getEmail());
                pstmt.setInt(6, supplier_id);
                pstmt.setInt(7, con.getId());

                updated = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updated;
    }

    //TODO: not sure if it will be used
    public boolean delete(Contact con, int supplier_id) {
        boolean deleted = false;
        try (Connection conn = connect()) {
            String statement = "DELETE FROM SuppliersContacts WHERE supID=? AND cntcID=?";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, supplier_id);
                pstmt.setInt(2, con.getId());

                deleted = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return deleted;
    }

    //TODO: make sure the dates are added properly!
    public boolean insertContact(Contact con, int supplier_id) {
        boolean output = false;
        try (Connection conn = connect()) {
            boolean inserted = false;
            String statement = "INSERT OR IGNORE INTO SuppliersContacts(supID,cntcID, cntcName, phoneNumber, email) " +
                    "VALUES (?,?,?,?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, supplier_id);
                pstmt.setInt(2, con.getId());
                pstmt.setString(3, con.getContactName());
                pstmt.setString(4, con.getPhoneNumber());
                pstmt.setString(5, con.getEmail()); //TODO - better change it to String and not enum
                output = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }


    public Contact addAllContactsToSupplier(Supplier supplier) {
        Contact obj = null;
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM SuppliersContacts WHERE supID=?";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, supplier.getId());

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int supId = rs.getInt(1);
                    int id = rs.getInt(2);
                    String name = rs.getString(3);
                    String phone = rs.getString(4);
                    String email = rs.getString(5);
                    obj = new Contact(id, name, phone, email, supId);
                    supplier.addContactFromDal(obj);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public int getBigestId(int SupID) {

        int obj = 0;
        try (Connection conn = connect()) {
            String statement = "SELECT MAX (cntcID) FROM SuppliersContacts WHERE supID=?";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, SupID);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    obj = rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;

    }
}
