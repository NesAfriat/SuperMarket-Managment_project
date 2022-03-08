package DataLayer.Mappers;

import BusinessLayer.SupplierBuissness.Supplier;
import BusinessLayer.paymentMethods;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SuppliersMapper extends Mapper{
    public SuppliersMapper() {
        super();
        create_table();
    }
    @Override
    void create_table() {
        String SuppliersTable = "CREATE TABLE IF NOT EXISTS Suppliers(\n" +
                                "\tsupID INTEGER PRIMARY KEY,\n" +
                                "\tsupName TEXT,\n" +
                                "\tbankAccount TEXT,\n" +
                                "\tpaymentMethod TEXT\n" +
                                ");";
//        String sql = "BEGIN TRANSACTION;" + GeneralProductTable + "COMMIT;";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(SuppliersTable);
            //TODO: in DataController - need to activate loadData
//            if (!identityMap.initialized){
//                LoadPreData();
//                identityMap.initialized = true;
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Supplier getSupplier(int supplier_id) {
        Supplier obj = null;
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM Suppliers WHERE supID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, supplier_id);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int supID = rs.getInt(1);
                    String supName = rs.getString(2);
                    String bank = rs.getString(3);
                    String payment = rs.getString(4);

                    if(payment.equals(paymentMethods.Cash.toString())){
                        obj=new Supplier(supID,supName, BusinessLayer.paymentMethods.creditcard , bank);

                    }else if(payment.equals(paymentMethods.paypal.toString())){
                        obj=new Supplier(supID,supName, paymentMethods.paypal , bank);

                    }else if(payment.equals(paymentMethods.creditcard.toString())){
                        obj=new Supplier(supID,supName, paymentMethods.paypal , bank);

                    }else {
                        obj=new Supplier(supID,supName, BusinessLayer.paymentMethods.BankTransfers , bank);

                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public boolean update(Supplier sup) {
        boolean updated = false;
        try (Connection conn = connect()) {
            String statement = "UPDATE Suppliers SET supID=?, supName=?, bankAccount=?, paymentMethod=? WHERE supID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, sup.getId());
                pstmt.setString(2, sup.getSupplierName());
                pstmt.setString(3, sup.getBankAccount());
                pstmt.setString(4, sup.getPayment().toString()); //TODO - better change it to String and not enum
                pstmt.setInt(5, sup.getId());
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
    public boolean delete(Supplier sup) {
        boolean deleted = false;
        try (Connection conn = connect()) {
            String statement = "DELETE FROM Suppliers WHERE supID=?";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, sup.getId());
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
    public boolean insertSupplier(Supplier sup) {
        boolean output = false;
        try (Connection conn = connect()) {
            boolean inserted = false;
            String statement = "INSERT OR IGNORE INTO Suppliers(supID,supName,bankAccount,paymentMethod) " +
                    "VALUES (?,?,?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, sup.getId());
                pstmt.setString(2, sup.getSupplierName());
                pstmt.setString(3, sup.getBankAccount());
                pstmt.setString(4, sup.getPayment().toString()); //TODO - better change it to String and not enum
                output = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }


    public List<Supplier> getALLSupplier() {
        List<Supplier> obj = new LinkedList<>();
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM Suppliers ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int supID = rs.getInt(1);
                    String supName = rs.getString(2);
                    String bank = rs.getString(3);
                    String payment = rs.getString(4);

                    if(payment.equals(paymentMethods.Cash.toString())){
                        obj.add(new Supplier(supID,supName, BusinessLayer.paymentMethods.creditcard , bank));

                    }else if(payment.equals( paymentMethods.paypal.toString())){
                        obj.add(new Supplier(supID,supName, BusinessLayer.paymentMethods.Cash , bank));

                    }else if(payment.equals(paymentMethods.creditcard.toString())){
                        obj.add(new Supplier(supID,supName, paymentMethods.paypal , bank));

                    }else {
                        obj.add(new Supplier(supID,supName, BusinessLayer.paymentMethods.BankTransfers , bank));
                    }
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
