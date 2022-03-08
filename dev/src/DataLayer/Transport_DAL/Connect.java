package DataLayer.Transport_DAL;

import java.io.File;
import java.sql.*;


public class Connect {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:database.db";;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public static void createDatabase (){
        String db_name= "Transport.db";
        File f = new File(db_name);
        if(!f.exists() && !f.isDirectory()) {
            try {
                Connect.createTablesAndLoadData();
            }
            catch(Exception e) {
                System.out.println("dan");
            }
        }


        }
    public static void createTablesAndLoadData () throws SQLException {
        Connection conn = Connect.getConnection();
        Statement st = conn.createStatement();
        String delete = "\n" +
                "CREATE TABLE IF NOT EXISTS \"TransportStopSupplier\" (\n" +
                "\t\"ID\"\tINTEGER,\n" +
                "\t\"StopNumber\"\tINTEGER,\n" +
                "\t\"SupplierID\"\tINTEGER,\n" +
                "\t\"Version\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"SupplierID\",\"ID\",\"Version\")\n" +
                ");\n" +
                "CREATE TABLE IF NOT EXISTS \"TransportDocument\" (\n" +
                "\t\"Id\"\tINTEGER,\n" +
                "\t\"Version\"\tINTEGER,\n" +
                "\t\"TransDate\"\tTEXT,\n" +
                "\t\"LeftOrigin\"\tTEXT,\n" +
                "\t\"DriverName\"\tTEXT,\n" +
                "\t\"DriverID\"\tINTEGER,\n" +
                "\t\"DriverLicense\"\tTEXT,\n" +
                "\t\"TruckID\"\tINTEGER,\n" +
                "\t\"OriginStoreID\"\tINTEGER,\n" +
                "\t\"Area\"\tTEXT,\n" +
                "\t\"truckWeightDep\"\tINTEGER,\n" +
                "\t\"approved\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"Id\",\"Version\")\n" +
                ");\n" +
                "CREATE TABLE IF NOT EXISTS \"TransportDocStoreProduct\" (\n" +
                "\t\"ProductID\"\tINTEGER,\n" +
                "\t\"StoreID\"\tINTEGER,\n" +
                "\t\"DocumentID\"\tINTEGER,\n" +
                "\t\"Amount\"\tINTEGER,\n" +
                "\t\"Version\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"ProductID\",\"StoreID\",\"DocumentID\",\"Version\")\n" +
                ");\n" +
                "CREATE TABLE IF NOT EXISTS \"TransportStopStores\" (\n" +
                "\t\"ID\"\tINTEGER,\n" +
                "\t\"StopNumber\"\tINTEGER,\n" +
                "\t\"StoreID\"\tINTEGER,\n" +
                "\t\"Version\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"ID\",\"StoreID\",\"Version\")\n" +
                ");\n" +
                "CREATE TABLE IF NOT EXISTS \"Truck\" (\n" +
                "\t\"Name\"\tTEXT,\n" +
                "\t\"LicensePlate\"\tINTEGER,\n" +
                "\t\"TruckType\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"LicensePlate\"),\n" +
                "\tFOREIGN KEY(\"TruckType\") REFERENCES \"TruckType\"(\"Name\")\n" +
                ");\n" +
                "CREATE TABLE IF NOT EXISTS \"TruckType\" (\n" +
                "\t\"Name\"\tTEXT,\n" +
                "\t\"MaxWeight\"\tINTEGER,\n" +
                "\t\"FreeWeight\"\tINTEGER,\n" +
                "\t\"LicenseForTruck\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"Name\")\n" +
                ");\n" +
                "CREATE TABLE IF NOT EXISTS \"Area\" (\n" +
                "\t\"Name\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"Name\")\n" +
                ");\n" +
                "INSERT INTO \"TransportDocument\" (\"Id\",\"Version\",\"TransDate\",\"LeftOrigin\",\"DriverName\",\"DriverID\",\"DriverLicense\",\"TruckID\",\"OriginStoreID\",\"Area\",\"truckWeightDep\",\"approved\") VALUES (0,0,'03/11/1998','08:32:24','Dan',209889510,'typeA',1223199,2,'A',1000,0);\n" +
                "INSERT INTO \"Truck\" (\"Name\",\"LicensePlate\",\"TruckType\") VALUES ('CyberTruck',1223123,'Lamborghini'),\n" +
                " ('Mad Max',1223199,'Ferrari'),\n" +
                " ('Rolling',1223211,'Mercedes'),\n" +
                " ('The Tank',1223333,'Honda'),\n" +
                " ('The Tank',1223777,'Honda'),\n" +
                " ('BackToTheFuture',1223889,'Mercedes');\n" +
                "INSERT INTO \"TruckType\" (\"Name\",\"MaxWeight\",\"FreeWeight\",\"LicenseForTruck\") VALUES ('Honda',2000,1200,3),\n" +
                " ('Mercedes',2000,1000,3),\n" +
                " ('Lamborghini',300,1500,2),\n" +
                " ('Ferrari',200,100,1);\n" +
                "INSERT INTO \"Area\" (\"Name\") VALUES ('A\n" +
                "'),\n" +
                " ('B'),\n" +
                " ('C');\n";
        st.executeUpdate(delete);

    }

//    public static void connect() {
//        Connection conn = null;
//        try {
//            // db parameters
            /** WATCH OUT URL IS NOT GENERIC**/
//            String url = "jdbc:sqlite:C:\\Users\\guyne\\Documents\\BGU\\semester C\\HomeAssinments\\system architecture\\gitRipo\\ADSS_Group_K\\dev";
//            // create a connection to the database
//            conn = DriverManager.getConnection(url);
//            System.out.println("Connection to SQLite has been established.");
//            Statement st = conn.createStatement();
//            ResultSet results = st.executeQuery("SELECT * FROM area");
//            while(results.next()){
//
//                System.out.println("entered");
//                String m = results.getString(1);
//                System.out.println("the area is: "+ m);
//            }
//
//
//
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
//        }
//    }
}
