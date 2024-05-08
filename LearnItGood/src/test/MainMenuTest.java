package test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.JDBCconn;
import main.MenuBar;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MainMenuTest {

	private static final String URL = "jdbc:mysql://127.0.0.1/test";
    private static final String USER = "root";
    private static final String PASSWORD = "Fc(UQVJZa4";
    private static Connection conn;

    @BeforeAll
    static void setUp() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Failed to connect to the database.");
        }
    }

    @AfterAll
    static void tearDown() {
        if (conn != null) {
            try {
            	dropTable(conn, "baza_test");
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void testChangeTableName() {
        
        
    }

    
    void createNewTable(String tablename) {
    	JDBCconn jdbc = new JDBCconn();
    	jdbc.createTable(conn, tablename);
    	
    }
    void deleteTable(String tableName) {
    	
    }
    @Test
    void testAddNewDataFile() {
    	JDBCconn jdbc = new JDBCconn();
        
    	String tableName = "baza_test";
    	
    	String dataName1 = "test data";
    	String data1 = "data testowe";
    	
    	String dataName2 = "";
    	String data2 = "data testowe";
    	
    	String dataName3 = "test data2";
    	String data3 = "";
    	
    	
    	
    	createNewTable(tableName);
    	
    	jdbc.addNewDataFile(conn, tableName, dataName1, data1);
    	jdbc.addNewDataFile(conn, tableName, dataName2, data2);
    	jdbc.addNewDataFile(conn, tableName, dataName3, data3);
    	
    	
    	int rowCount = getRowCount(conn, tableName);
    	
    	assertEquals(rowCount, 3);
    	
    	
    	
    }
    int getRowCount(Connection conn, String tableName) {
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) AS row_count FROM " + tableName);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int rowCount = resultSet.getInt("row_count");
            resultSet.close();
            statement.close();
            return rowCount;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; 
        }
    }

    @Test
    void testChangeDataFileName() {
    	JDBCconn jdbc = new JDBCconn();
        
    	String tableName = "baza_test";
    	
    	String dataName1 = "test data";
    	String data1 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    	
    	String dataName2 = "";
    	String data2 = "--__1";
    	
    	String dataName3 = "test data2";
    	String data3 = "    ";
    	
    	
    	
    	jdbc.changeDataFileName(conn, tableName, data1, dataName1);
    	jdbc.changeDataFileName(conn, tableName, data2, dataName2);
    	jdbc.changeDataFileName(conn, tableName, data3, dataName3);
    	
    	
    	List<String> dataFileNames = getAllDataFileNames(conn, tableName);
        
        
        assertNotNull(dataFileNames);
        assertEquals(3, dataFileNames.size());

        
        
        assertTrue(dataFileNames.contains("    "));
        assertTrue(dataFileNames.contains("--__1"));
        assertTrue(dataFileNames.contains("aaaaaaaaaaaaaaaaaaaa"));
    	
    	
    }
    List<String> getAllDataFileNames(Connection conn, String tableName) {
        List<String> dataFileNames = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement("SELECT file_data_name FROM " + tableName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String dataFileName = resultSet.getString("file_data_name");
                dataFileNames.add(dataFileName);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataFileNames;
    }
    @Test
    void testDeleteDataFile() {
    	JDBCconn jdbc = new JDBCconn();
        
    	String tableName = "baza_test";
    	
    	
    	jdbc.deleteDataFile(conn, tableName, "    ");
    	jdbc.deleteDataFile(conn, tableName, "nie istnieje");
    	
    	List<String> dataFileNames = getAllDataFileNames(conn, tableName);
    	
    	assertNotNull(dataFileNames);
        assertEquals(2, dataFileNames.size());
        
        assertTrue(dataFileNames.contains("--__1"));
        assertTrue(dataFileNames.contains("aaaaaaaaaaaaaaaaaaaa"));
        
    	
    }

    @Test
    void testChangeStorageName() {
        
    }

    @Test
    void testDeleteRow() {
        
    }

    @Test
    void testAddNewRow() {
        
    }

    @Test
    void testQueryStorages() {
        
    }
    private static void dropTable(Connection conn, String tableName) {
        try {
            String dropTableSQL = "DROP TABLE IF EXISTS " + tableName;
            conn.prepareStatement(dropTableSQL).executeUpdate();
            System.out.println("Table " + tableName + " dropped successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
