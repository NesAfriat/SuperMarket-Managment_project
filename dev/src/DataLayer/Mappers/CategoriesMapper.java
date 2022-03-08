package DataLayer.Mappers;

import BusinessLayer.Category;

import java.sql.*;
import java.util.LinkedList;

public class CategoriesMapper extends Mapper {

    public CategoriesMapper() {
        super();
        create_table();
    }

    @Override
    void create_table() {
        String CategoriesTable = "CREATE TABLE IF NOT EXISTS Categories(\n" +
                "\tcatName TEXT PRIMARY KEY,\n" +
                "\tfather_Category TEXT,\n" +
                "\tFOREIGN KEY (father_Category) REFERENCES Categories (catName)\n" +
                ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(CategoriesTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Category getCategory(String cat_name) {
        Category category =null;
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM Categories WHERE catName=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setString(1, cat_name);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    String catName = rs.getString(1);
                    String father_Category= rs.getString(2);
                    category = new Category(cat_name);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return category;
    }

    public String getFatherCategory(Category cat) {
        String father =null;
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM Categories WHERE catName=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setString(1, cat.getCategory_name());

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    father= rs.getString(2);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return father;
    }

    public LinkedList<String> getChildrenCategories(Category cat) {
        LinkedList<String> children= new LinkedList<>();
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM Categories WHERE father_Category=? ";
            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setString(1, cat.getCategory_name());

                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    String child= rs.getString(1);
                    children.add(child);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return children;
    }

    public boolean update(Category obj) {
        boolean updated = false;
        try (Connection conn = connect()) {
            String statement = "UPDATE Categories SET catName=?, father_Category=? WHERE catName=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setString(1, obj.getCategory_name());
                Category father_Cat= obj.getFather_Category();
                if(father_Cat!=null) {
                    pstmt.setString(2, father_Cat.getCategory_name());
                    update(father_Cat);
                }
                else
                    pstmt.setString(2, null);
                updated = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updated;
    }

    public boolean delete(Category obj) {
        boolean deleted = false;
        try (Connection conn = connect()) {
            String statement = "DELETE FROM Categories WHERE catName=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setString(1, obj.getCategory_name());
                deleted = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return deleted;
    }

    public boolean insertCategory(Category category) {
        boolean output = false;
        try (Connection conn = connect()) {
            boolean inserted = false;
            String statement = "INSERT OR IGNORE INTO Categories (catName, father_Category) " +
                    "VALUES (?,?)";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setString(1, category.getCategory_name());
                if(category.getFather_Category()!=null)
                pstmt.setString(2, category.getFather_Category().getCategory_name());
                else
                    pstmt.setString(2, null);
                output = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return output;
    }

    public boolean setFather(Category cat,Category father_cat) {
        boolean updated = false;
        try (Connection conn = connect()) {
            String statement = "UPDATE Categories SET father_Category=? WHERE catName=? ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                pstmt.setString(1, father_cat.getCategory_name());
                pstmt.setString(2, cat.getCategory_name());
                updated = pstmt.executeUpdate() != 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return updated;
    }

    public LinkedList<Category> loadAllCategories() {
        LinkedList<Category> categories=new LinkedList<>();
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM Categories  ";

            try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next())  {
                    String catName = rs.getString(1);
                    Category newCat= new Category(catName);
                    categories.add(newCat);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        setFathers(categories);
        return categories;
    }
    private void setFathers(LinkedList<Category> categories) {
        try (Connection conn = connect()) {
            String statement = "SELECT * FROM Categories WHERE catName=? ";
            for (Category category : categories) {
                try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        String fathersCatString = rs.getString(2);
                        for(Category fc: categories)
                        if(fc.getCategory_name().equals(fathersCatString))
                        category.setFather_Category(fc);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
             catch(SQLException throwables){
                throwables.printStackTrace();
            }
        }

        }

