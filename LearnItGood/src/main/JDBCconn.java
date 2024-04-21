package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCconn {
	private static final String DB_URL = "jdbc:mysql://127.0.0.1/test";
	private static final String DB_USR = "root";
	private static final String DB_PASS = "Fc(UQVJZa4";
	
	private static final String ADD_NEW_STORAGE = "INSERT INTO storages (storage_name) VALUES (?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM storages";
    private static final String DELETE_ROW = "DELETE FROM storages WHERE storage_id = ?";
    private static final String CHANGE_NAME = "UPDATES storages SET storage_name = ? WHERE storage_id + ?";
    
    
	public static void main (String[] args) {
		
		try {
			Connection connection = DriverManager.getConnection(
					DB_URL,
					DB_USR,
					DB_PASS
					);
			
			addNewRow(connection, "third storage");
			
			queryStorages(connection);
            
            // Closing connection
            connection.close();
			
            
			} catch (SQLException e) {
			
			e.printStackTrace();
			}
		
		
	}
	
	
	
	private static void changeStorageName(Connection conn, String storage_name, int storageID) {
		try {
	        PreparedStatement prepStatement = conn.prepareStatement(DELETE_ROW);
	        prepStatement.setString(1, storage_name);
	        prepStatement.setInt(2, storageID);
	        
	        int rowsAffected = prepStatement.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Successfully deleted row with storage_id: " + storageID);
	        } else {
	            System.out.println("No row found with storage_id: " + storageID);
	        }
	        
	        prepStatement.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	private static void deleteRow(Connection conn, int storageId) {
	    try {
	        PreparedStatement prepStatement = conn.prepareStatement(DELETE_ROW);
	        prepStatement.setInt(1, storageId);
	        
	        int rowsAffected = prepStatement.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Successfully deleted row with storage_id: " + storageId);
	        } else {
	            System.out.println("No row found with storage_id: " + storageId);
	        }
	        
	        prepStatement.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private static void addNewRow(Connection conn, String storage_name) {
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
	
	private static void queryStorages(Connection conn) {
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
	private void closeConnection(Connection conn) {
		try {
		conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
