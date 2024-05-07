package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * @author Kamil Krzemiński 
 */

public class JDBCconn {
	// tu jest dobrze, tutaj trzymamy ile nowych baz danych zostało stworzonych i jak się nazywają
	private static final String ADD_NEW_STORAGE = "INSERT INTO storages (storage_name) VALUES (?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM storages";
    private static final String DELETE_ROW = "DELETE FROM storages WHERE storage_name = ?";
    private static final String CHANGE_NAME = "UPDATE storages SET storage_name = ? WHERE storage_name = ?";

    
    
	
	// ok
	void changeTableName(Connection conn, String oldTableName, String newTableName) {
	    try {
	        
	        String alterTableSQL = "ALTER TABLE " + oldTableName + " RENAME TO " + newTableName;
	        
	        
	        PreparedStatement prepStatement = conn.prepareStatement(alterTableSQL);
	        
	        
	        prepStatement.executeUpdate();
	        
	        
	        prepStatement.close();
	        
	        
	        System.out.println("Table " + oldTableName + " renamed to " + newTableName + " successfully.");
	        
	    } catch (SQLException e) {
	        
	        e.printStackTrace();
	    }
	}
	void deleteTable(Connection conn, String tableName) {
	    try {
	        
	        String dropTableSQL = "DROP TABLE IF EXISTS " + tableName;
	        
	        
	        PreparedStatement prepStatement = conn.prepareStatement(dropTableSQL);
	        
	       
	        prepStatement.executeUpdate();
	        
	       
	        prepStatement.close();
	        
	       
	        System.out.println("Table " + tableName + " deleted successfully.");
	        
	    } catch (SQLException e) {
	        
	        e.printStackTrace();
	    }
	}
	
	
	// ok 
	void createTable(Connection conn, String tableName) {
	    try {
	        String createTableSQL = "CREATE TABLE " + tableName+ " ("
	                                + "file_data_id INT AUTO_INCREMENT PRIMARY KEY, "
	                                + "file_data_name VARCHAR(20), "
	                                + "file_content TEXT"
	                                + ")";
	        
	        Statement statement = conn.createStatement();
	        statement.executeUpdate(createTableSQL);
	        
	        System.out.println("Table " + tableName + " created successfully.");
	        
	        statement.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	
	
	
	
	
	void addNewDataFile(Connection conn, String tableName, String dataName, String dataContent) {
	    try {
	        
	        String insertElementSQL = "INSERT INTO " + tableName + " (file_data_name, file_content) VALUES (?, ?)";
	        
	        
	        PreparedStatement prepStatement = conn.prepareStatement(insertElementSQL);
	        
	        
	        prepStatement.setString(1, dataName);
	        prepStatement.setString(2, dataContent);
	        
	        
	        prepStatement.executeUpdate();
	       
	        prepStatement.close();
	        
	        
	        System.out.println("New element added to table " + tableName + " successfully.");
	        
	    } catch (SQLException e) {
	        
	        e.printStackTrace();
	    }
	}
	void changeDataFileName(Connection conn, String tableName, String newDataName, String oldFileName) {
	    try {
	        
	        String updateFileNameSQL = "UPDATE " + tableName + " SET file_data_name = ? WHERE file_data_name = ?";
	        
	        
	        PreparedStatement prepStatement = conn.prepareStatement(updateFileNameSQL);
	        
	        
	        prepStatement.setString(1, newDataName);
	        prepStatement.setString(2, oldFileName);
	        
	        
	        int rowsAffected = prepStatement.executeUpdate();
	        
	        prepStatement.close();
	        
	       
	        if (rowsAffected > 0) {
	            System.out.println("File data name updated successfully.");
	        } else {
	            System.out.println("No rows updated. File data ID may not exist in the table.");
	        }
	        
	    } catch (SQLException e) {
	        
	        e.printStackTrace();
	    }
	}
	void deleteDataFile(Connection conn, String tableName, String fileName) {
	    try {
	        
	        String deleteElementSQL = "DELETE FROM " + tableName + " WHERE file_data_name = ?";
	        
	        
	        PreparedStatement prepStatement = conn.prepareStatement(deleteElementSQL);
	        
	        
	        prepStatement.setString(1, fileName);
	        
	        
	        int rowsAffected = prepStatement.executeUpdate();
	        
	        
	        prepStatement.close();
	        
	        
	        if (rowsAffected > 0) {
	            System.out.println("Element with file_data_name " + fileName + " deleted successfully.");
	        } else {
	            System.out.println("No rows deleted. File data ID may not exist in the table.");
	        }
	        
	    } catch (SQLException e) {
	        // Handle any SQL exceptions
	        e.printStackTrace();
	    }
	}
	
	
	
	
	
	// ok
	void changeStorageName(Connection conn, String storage_name, String oldStorageName) {
		try {
	        PreparedStatement prepStatement = conn.prepareStatement(CHANGE_NAME);
	        prepStatement.setString(1, storage_name);
	        prepStatement.setString(2, oldStorageName);
	        
	        int rowsAffected = prepStatement.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Successfully deleted row with storage_id: " + oldStorageName);
	        } else {
	            System.out.println("No row found with storage_id: " + oldStorageName);
	        }
	        
	        prepStatement.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	// ok 
	void deleteRow(Connection conn, String storageName) {
	    try {
	        PreparedStatement prepStatement = conn.prepareStatement(DELETE_ROW);
	        prepStatement.setString(1, storageName);
	        
	        int rowsAffected = prepStatement.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Successfully deleted row with storage_id: " + storageName);
	        } else {
	            System.out.println("No row found with storage_id: " + storageName);
	        }
	        
	        prepStatement.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	// ok
	void addNewRow(Connection conn, String storage_name) {
		try {
			PreparedStatement prepStatement = conn.prepareStatement(ADD_NEW_STORAGE);
			prepStatement.setString(1, storage_name);
			
			int rowsAffected = prepStatement.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Succesfully added new row to database");
			} else {
				System.out.println("An error occured");
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			}
		
	}
	// ok 
	void queryStorages(Connection conn) {
	    try {
	        PreparedStatement prepStatement = conn.prepareStatement(SELECT_ALL_SQL);
	        
	        ResultSet resultSet = prepStatement.executeQuery();
	        
	        while (resultSet.next()) {
	            System.out.println("Storage ID: " + resultSet.getInt("storage_id") + "   Storage Name: " + resultSet.getString("storage_name"));
	            
	        }
	        
	        resultSet.close();
	        prepStatement.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	
	
	
	
	
}
