package DataLayer.Mappers;

import BusinessLayer.Agreement;
import BusinessLayer.ProductSupplier;

import java.sql.*;
import java.util.LinkedList;

public class SuppliersProductsMapper extends Mapper {
    public SuppliersProductsMapper() {
        super();
        create_table();
    }

    @Override
    void create_table() {
//        File f = new File(db_name);
        String SPTable = "CREATE TABLE IF NOT EXISTS SuppliersProducts(\n" +
                "\tsupID INTEGER,\n" +
                "\tcatalogID INTEGER,\n" +
                "\tgpID INTEGER,\n" +
                "\tname TEXT,\n" +
                "\tprice REAL,\n" +

                "\tPRIMARY KEY (supID, catalogID),\n" +
                "\tFOREIGN KEY (supID) REFERENCES Suppliers (supID) ON DELETE NO ACTION,\n" +
                "\tFOREIGN KEY (gpID) REFERENCES GeneralProducts (gpID)\n" +
                ");";
//        String sql = "BEGIN TRANSACTION;" + GeneralProductTable + "COMMIT;";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(SPTable);
            //TODO: in DataController - need to activate loadData
//            if (!identityMap.initialized){
//                LoadPreData();
//                identityMap.initialized = true;
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ProductSupplier getProductSupplier(int sup_id, int catalog_id) {
        ProductSupplier obj = null;
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM SuppliersProducts WHERE supID=? AND catalogID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, sup_id);
                pstmt.setInt(2, catalog_id);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int Supplierid = rs.getInt(1);

                    int catalogID = rs.getInt(2);
                    int gpID = rs.getInt(3);
                    String name = rs.getString(4);
                    double price = rs.getDouble(5);

                    obj = new ProductSupplier(price, catalogID, gpID, name, Supplierid);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    public boolean update(ProductSupplier ps, int sup_id) {
        boolean updated = false;
        try (Connection conn = connect()) {
            String statement = "UPDATE SuppliersProducts SET supID=?, catalogID=?, gpID=?, name=?, price=? WHERE supID=? AND catalogID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, sup_id);
                pstmt.setInt(2, ps.getCatalogID());
                pstmt.setInt(3, ps.getId());
                pstmt.setString(4, ps.getName());
                pstmt.setDouble(5, ps.getPrice());
                pstmt.setInt(6, sup_id);
                pstmt.setInt(7, ps.getCatalogID());

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
    public boolean delete(ProductSupplier ps, int sup_id) {
        boolean deleted = false;
        try (Connection conn = connect()) {
            String statement = "DELETE FROM SuppliersProducts WHERE supID=? AND catalogID=?";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, sup_id);
                pstmt.setInt(2, ps.getCatalogID());
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
    public boolean insertProduct(ProductSupplier ps, int sup_id) {
        boolean output = false;
        try (Connection conn = connect()) {
            boolean inserted = false;
            String statement = "INSERT OR IGNORE INTO SuppliersProducts(supID, catalogID, gpID, name, price) " +
                    "VALUES (?,?,?,?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, sup_id);
                pstmt.setInt(2, ps.getCatalogID());
                pstmt.setInt(3, ps.getId());
                pstmt.setString(4, ps.getName());
                pstmt.setDouble(5, ps.getPrice());

                output = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }

//    public LinkedList<ProductSupplier> addPStoProduct(GeneralProduct gp) {
//        LinkedList<ProductSupplier> output = new LinkedList<>();
//        try (Connection conn = connect()) {
//            String statement = "SELECT * FROM SuppliersProducts WHERE gpID=? ";
//
//            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
//                pstmt.setInt(1, gp.getProduct_id());
//
//                ResultSet rs = pstmt.executeQuery();
//                while (rs.next()) {
//                    int Supplierid = rs.getInt(1);
//                    int catalogID = rs.getInt(2);
//                    int gpID = rs.getInt(3);
//                    String name = rs.getString(4);
//                    double price = rs.getDouble(5);
//
//                    ProductSupplier ps = new ProductSupplier(price, catalogID, gpID, name, Supplierid);
//                    gp.addSupplierProduct(ps);
//                    output.add(ps);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return output;
//    }

    public LinkedList<ProductSupplier> addPStoAgreement(Agreement agr){
        LinkedList<ProductSupplier> output = new LinkedList<>();
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM SuppliersProducts WHERE supID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, agr.getSupplierID());

                ResultSet rs = pstmt.executeQuery();
                while(rs.next()) {
                    int supID = rs.getInt(1);
                    int catalogID = rs.getInt(2);
                    int gpID = rs.getInt(3);
                    String name = rs.getString(4);
                    double price = rs.getDouble(5);

                    ProductSupplier ps = new ProductSupplier(price,catalogID,gpID,name,supID);
                    agr.addSupplierProduct(ps);
                    output.add(ps);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }

    public int getMaxPGcounterNumber(){
        int obj = 0;
        try (Connection conn = connect()) {
            String statement = "SELECT MAX (gpID) FROM SuppliersProducts";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                ResultSet rs = pstmt.executeQuery();
                if(rs.next()){
                    obj=rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;

    }

    //written by stock
    public int get_gpId(int catalogID, int supID)
    {
        int gpID = -1;
        try (Connection conn = connect()) {
            String statement = "SELECT (gpID) FROM SuppliersProducts WHERE supID=? AND catalogID=?";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, supID);
                pstmt.setInt(2, catalogID);
                ResultSet rs = pstmt.executeQuery();
                if(rs.next()){
                    gpID=rs.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return gpID;
    }
}
