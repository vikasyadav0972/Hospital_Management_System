package HospitalManagementSystem;
import java.util.*;
import java.sql.*;

public class Patient {
	private Connection connection;
	private Scanner sc;
	
	public Patient(Connection connection, Scanner sc) {
		this.connection=connection;
		this.sc=sc;
		
	}
		
		
		public void addPatient() {
			System.out.println("Enter Patient Name: ");
			sc.nextLine();
			String name=sc.nextLine();
			sc.nextLine();
			
			System.out.println("Enter Patient Age: ");
			int age=sc.nextInt();
			
			sc.nextLine();
			
			System.out.println("Enter Patient Gender: ");
			String gender=sc.nextLine();
			
			try {
				String query="INSERT INTO patients(name, age, gender) VALUES (?,?,?)";
				PreparedStatement preparedStatement=connection.prepareStatement(query);
				preparedStatement.setString(1,name);
				preparedStatement.setInt(2,age);
				preparedStatement.setString(3, gender);
				int affectedRows=preparedStatement.executeUpdate();
				if(affectedRows>0) {
					System.out.println("Patient Added Successfully!! ");
				}
				else {
					System.out.println("Failed to add Patient");
				}
				
			}
			catch(SQLException e ) {
				e.printStackTrace();
			}	
			
			}
		public void viewPatient() {
			String query="SELECT * From patients";
			try {
				PreparedStatement preparedStatement=connection.prepareStatement(query);
				ResultSet resultset= preparedStatement.executeQuery();
				System.out.println("Patient");
				System.out.println("+------------+------------------+---------+--------------+");
				System.out.println("| Patient Id | Name             | Age     | Gender        ");
				System.out.println("+------------+------------------+---------+--------------+");
				while(resultset.next()) {
					int id=resultset.getInt("id");///: some error
					String name=resultset.getString("name");
					int age=resultset.getInt("age");
					String gender=resultset.getString("gender");
					System.out.printf("| %13s|%-19s|%-10s|%-14s|\n",id,name,age,gender);
					System.out.println("+------------+------------------+---------+--------------+");

				}
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		public boolean getPatientById(int id) {
			String query="SELECT * FROM patients WHERE id =? ";
			
			try {
				PreparedStatement prepareStatement=connection.prepareStatement(query);
				prepareStatement.setInt(1,id);
				ResultSet resultSet=prepareStatement.executeQuery();
				if(resultSet.next()) {
					return true;
				}
				else {
					return false;
				}
				
				
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		
		return false;
		

	}

}
