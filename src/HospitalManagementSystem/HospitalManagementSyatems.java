package HospitalManagementSystem;
import java.util.*;
import java.sql.*;
import java.sql.DriverManager;

public class HospitalManagementSyatems {
	
	private static final String url="jdbc:mysql://localhost:3306/hospital";
	
	private static final String username="root";
	
	private static final String password="    ";
	
	

	public static void main(String[] args) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		Scanner sc=new Scanner(System.in);
		
		 try (Connection connection = DriverManager.getConnection(url, username, password)) {
	            Patient patient = new Patient(connection, sc);
	            Doctors doctors = new Doctors(connection);
			while(true) {
				System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patient");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter Your Choice ");
				int choice=sc.nextInt();
				switch(choice) {
				case 1:
					//add patient
					 patient.addPatient();
					System.out.println();
					break;
					
					
				case 2:
					//view patient
					patient.viewPatient();
					System.out.println();
					break;
					
					
				case 3:
					//view doctors
					doctors.viewDoctors();
					System.out.println();
					break;
					
					
				case 4: 
					//book appointment
					bookAppointment(patient,doctors,connection,sc);
					System.out.println();
					break;
					
					
				case 5:
					System.out.println("Exit Successfully");
					return;
					
					default:
						System.out.println("Enter valid choice");
						break;
						
				}
				
				}
			
			
		}
		catch(SQLException e){
			e.printStackTrace();
			
		}
		
		

	}
	public static void bookAppointment(Patient patient,Doctors doctors, Connection connection, Scanner sc) {
		System.out.println("Enter Patient Id: ");
		int patientId=sc.nextInt();
		System.out.println("Enter Doctor Id: ");
		int doctorId=sc.nextInt();
		System.out.println("Enter Appointment Date (YYYY-MM-DD)");
		String appointmentDate=sc.next();
		if(patient.getPatientById(patientId)&&doctors.getDoctorById(doctorId)) {
			if(checkDoctorsAvailability(doctorId,appointmentDate,connection)) {
				String appointmentsQuery="INSERT INTO appointments(patient_id,doctor_id,appointment_date)VALUES(?,?,?)";
				
				try {
					PreparedStatement preparedStatement=connection.prepareStatement(appointmentsQuery);
					preparedStatement.setInt(1, patientId);
					preparedStatement.setInt(2, doctorId);
					preparedStatement.setString(3, appointmentDate);
					int rowsAffected=preparedStatement.executeUpdate();
					if(rowsAffected>0) {
						System.out.println("Appointment Booked");
					}
					else {
						System.out.println("Failed to book appointment ");
					}


					
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				
			}
			else {
				System.out.println("Doctor not available");
			}
			
			
		}
		else {
			System.out.println("Either doctor or Patient does not exist!");
		}
		
		
	}
	public static boolean checkDoctorsAvailability(int doctorId,String appointmentDate,Connection connection) {
		
		String query="SELECT COUNT(*) FROM appointments WHERE doctor_id=? AND appointment_date=?";
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			preparedStatement.setInt(1, doctorId);
			preparedStatement.setString(2, appointmentDate);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				int count=resultSet.getInt(1);
				if(count==0) {
					return true;
				}
				else {
					return false;
				}
			}

			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}

}
