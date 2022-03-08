package DataLayer.Mappers;

import BusinessLayer.Reports.Report;

import java.sql.*;
import java.util.LinkedList;

public class Reports_CategoriesMapper extends Mapper{
    public Reports_CategoriesMapper(){
        super();
        create_table();
    }
    @Override
    void create_table() {
        String ReportsCategoriesTable = "CREATE TABLE IF NOT EXISTS ReportCategories(\n" +
                "\trepID INTEGER,\n" +
                "\tcatName TEXT,\n" +
                "\tPRIMARY KEY (repID, catName),\n" +
                "\tFOREIGN KEY (repID) REFERENCES Reports(repID),\n" +
                "\tFOREIGN KEY (catName) REFERENCES Categories(catName)\n" +
                ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(ReportsCategoriesTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public LinkedList<String> getCategories(int repID) {
        LinkedList<String> categories = new LinkedList<>();
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM ReportCategories WHERE repID=? ";
            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setInt(1, repID);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    String category = rs.getString(2);
                    categories.add(category);
                }
            }
         catch (SQLException e) {
            e.printStackTrace();
        }
    }
    catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return categories;
    }

    public void insertCategories(Report report) {
        LinkedList<String> categories = report.getCategories();
        try (Connection conn = connect()) {
            for (String cat : categories) {
                String statement = "INSERT OR IGNORE INTO ReportCategories(repID, catName) " +
                        "VALUES (?,?)";

                try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                    pstmt.setInt(1, report.getReportID());
                    pstmt.setString(2, cat);
                    if (pstmt.executeUpdate() == 0)
                        throw new Exception("failed inserting report category");
                } catch (Exception e) {
                    e.printStackTrace();
                } }}catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
        }
    }

