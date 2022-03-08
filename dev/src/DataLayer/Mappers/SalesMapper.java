package DataLayer.Mappers;


import BusinessLayer.Sales.Sale;
import BusinessLayer.Sales.SaleByProduct;
import BusinessLayer.Sales.Sale_Category;
import DataLayer.DataController;

import java.sql.*;
import java.text.ParseException;
import java.util.LinkedList;


public class SalesMapper extends Mapper{
    private AffectedCategoriesMapper acm;
    private AffectedProductsMapper apm;
    public SalesMapper() {
        super();
        create_table();
    }

    public void setAcm(AffectedCategoriesMapper acm){
        this.acm = acm;
    }
    public void setApm(AffectedProductsMapper apm){
        this.apm = apm;
    }
    @Override
    void create_table() {
//        File f = new File(db_name);
        String SaleTable = "CREATE TABLE IF NOT EXISTS Sales(\n" +
                            "\tsaleID INTEGER,\n"+
                            "\tdiscount_percent DOUBLE ,\n"+
                            "\tdescription TEXT,\n"+
                            "\tstart_date TEXT,\n"+
                            "\tend_date TEXT,\n"+
                            "\tbyCategory INTEGER,\n"+
                            "\tPRIMARY KEY (saleID)\n"+
                            ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(SaleTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Sale getSaleByID(int sID) {
        Sale s=null;
            try (Connection conn = connect()) {
                String statement = "SELECT * FROM Sales WHERE saleID=? ";

                try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                    pstmt.setInt(1, sID);

                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        int saleID = rs.getInt(1);
                        Double discount = rs.getDouble(2);
                        String description = rs.getString(3);
                        String startDate = rs.getString(4);
                        String endDate = rs.getString(5);
                        LinkedList<String> affected;
                        int byCategory = rs.getInt(6);
                        if(byCategory==1) {
                            affected = acm.getAffectedCategories(saleID);
                            s= new Sale_Category(saleID,discount,description,DataController.getDate(startDate),DataController.getDate(endDate),affected);
                        }
                        else {
                            affected = apm.getAffectedProucts(saleID);
                            s= new SaleByProduct(saleID,discount,description,DataController.getDate(startDate),DataController.getDate(endDate),affected);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        return s;
    }
    public int getMaxSaleID() {
        int output=0;
        try (Connection conn = connect()) {
            String statement = "SELECT max(saleID) FROM Sales";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int saleID = rs.getInt(1);
                    output=saleID;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }
    public LinkedList<Sale> getSaleByProduct(String product) {
        LinkedList<Sale> sales= new LinkedList<>();
        LinkedList<Integer> salesID= apm.getSalesID(product);
        for(Integer sID: salesID)
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM Sales WHERE saleID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, sID);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int saleID = rs.getInt(1);
                    Double discount_percent = rs.getDouble(2);
                    String description = rs.getString(3);
                    String start_date = rs.getString(4);
                    String end_date = rs.getString(5);
                    LinkedList<String> affected= apm.getAffectedProucts(saleID);
                    Sale newSale = new SaleByProduct(saleID, discount_percent,description, DataController.getDate(start_date), DataController.getDate(end_date),  affected);
                    sales.add(newSale);
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

    public LinkedList<Sale> getSaleByCategory(String category) {
        LinkedList<Sale> sales= new LinkedList<>();
        LinkedList<Integer> salesID= acm.getSalesID(category);
        for(Integer sID: salesID)
            try (Connection conn = connect()) {
                String statement = "SELECT * FROM Sales WHERE saleID=? ";

                try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                    pstmt.setInt(1, sID);

                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        int saleID = rs.getInt(1);
                        Double discount_percent = rs.getDouble(2);
                        String description = rs.getString(3);
                        String start_date = rs.getString(4);
                        String end_date = rs.getString(5);
                        LinkedList<String> affected= acm.getAffectedCategories(saleID);
                        Sale newSale = new SaleByProduct(saleID, discount_percent,description,  DataController.getDate(start_date), DataController.getDate(end_date),  affected);
                        sales.add(newSale);
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

    public boolean insertSaleByProduct(Sale sale) { //by Product
        boolean output = false;
        try (Connection conn = connect()) {
            boolean inserted = false;
            String statement = "INSERT OR IGNORE INTO Sales(saleID, discount_percent, description, start_date, end_date,byCategory) " +
                    "VALUES (?,?,?,?,?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, sale.getSale_id());
                pstmt.setDouble(2, sale.getDiscount_percent());
                pstmt.setString(3, sale.getSale_description());
                pstmt.setString(4, DataController.getDate(sale.getStart_date()));
                pstmt.setString(5, DataController.getDate(sale.getEnd_date()));
                pstmt.setInt(6,0);
                output = pstmt.executeUpdate() != 0;
                apm.insertAffected(sale);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }
    public boolean insertSaleByCategory(Sale sale) { //byCategory
        boolean output = false;
        try (Connection conn = connect()) {
            boolean inserted = false;
            String statement = "INSERT OR IGNORE INTO Sales(saleID, discount_percent, description, start_date, end_date,byCategory) " +
                    "VALUES (?,?,?,?,?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, sale.getSale_id());
                pstmt.setDouble(2, sale.getDiscount_percent());
                pstmt.setString(3, sale.getSale_description());
                pstmt.setString(4, DataController.getDate(sale.getStart_date()));
                pstmt.setString(5, DataController.getDate(sale.getEnd_date()));
                pstmt.setInt(6, 1);
                output = pstmt.executeUpdate() != 0;
                acm.insertAffected(sale);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }

    public boolean update(Sale sale) {
        boolean updated = false;
        try (Connection conn = connect()) {
            String statement = "UPDATE Sales SET saleID=?, discount_percent=?, description=?, start_date=?, end_date=? WHERE saleID=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, sale.getSale_id());
                pstmt.setDouble(2, sale.getDiscount_percent());
                pstmt.setString(3, sale.getSale_description());
                pstmt.setString(4, DataController.getDate(sale.getStart_date()));
                pstmt.setString(5, DataController.getDate(sale.getEnd_date()));
                pstmt.setInt(6, sale.getSale_id());
                updated = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updated;
    }

    public boolean delete(Sale sale) {
        boolean deleted = false;
        try (Connection conn = connect()) {
            String statement = "DELETE FROM Sales WHERE saleID=?";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, sale.getSale_id());
                if(isByCategory(sale))
                acm.deleteAffected(sale);
                else
                apm.deleteAffected(sale);
                deleted = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return deleted;
    }

    private boolean isByCategory(Sale s) {
                boolean byCategory=false;
                try (Connection conn = connect()) {
                    String statement = "SELECT * FROM Sales WHERE saleID=? ";
                    try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                        pstmt.setInt(1, s.getSale_id());
                        ResultSet rs = pstmt.executeQuery();
                        if (rs.next()) {
                            int byCat = rs.getInt(6);
                        if(byCat==1)
                            byCategory=true;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            return byCategory;
        }

    public LinkedList<Sale> loadAllSales() {
        LinkedList<Sale> sales = new LinkedList<>();
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM Sales ";
            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int saleID = rs.getInt(1);
                    Double discount = rs.getDouble(2);
                    String description = rs.getString(3);
                    String startDate = rs.getString(4);
                    String endDate = rs.getString(5);
                    LinkedList<String> affected;
                    int byCategory = rs.getInt(6);
                    if(byCategory==1) {
                        affected = acm.getAffectedCategories(saleID);
                        Sale s= new Sale_Category(saleID,discount,description,DataController.getDate(startDate),DataController.getDate(endDate),affected);
                        sales.add(s);
                    }
                    else {
                        affected = apm.getAffectedProucts(saleID);
                        Sale s= new SaleByProduct(saleID,discount,description,DataController.getDate(startDate),DataController.getDate(endDate),affected);
                        sales.add(s);
                    }

                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sales;
    }

}
