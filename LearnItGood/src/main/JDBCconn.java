package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCconn {
	public static void main (String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		try {
			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1/test",
					"root",
					"Fc(UQVJZa4"
					);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM data");
			
			while (resultSet.next()) {
				System.out.println(resultSet.getString("user_name"));
				System.out.println(resultSet.getInt("user_id"));
			}
			statement.close();
            connection.close();
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		
	}
}
