package DataLayer.Mappers;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Mapper {
    protected final String connectionPath = "jdbc:sqlite:database.db";
    protected final static String db_name = "database.db";
    public Mapper(){

    }

    protected Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(connectionPath);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    abstract void create_table();


}
