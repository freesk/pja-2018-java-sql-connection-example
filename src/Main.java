import java.sql.*;

// The Employees table 

//CREATE TABLE IF NOT EXISTS `mydb`.`Employees` (
//  `ID` INT NOT NULL AUTO_INCREMENT,
//  `first_name` VARCHAR(45) NULL,
//  `last_name` VARCHAR(45) NULL,
//  `phone_no` INT NULL,
//  `job` VARCHAR(45) NULL,
//  PRIMARY KEY (`ID`))
//ENGINE = InnoDB

public class Main {	
	public static void main(String[] args) {
		try {
			// get the connector 
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// here mydb is database name, root is username and password			
			String URL = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			// connect to the db 
			Connection con = DriverManager.getConnection(URL, "root", "root");
			
			// CONCUR_UPDATABLE stands for updating the db by non primary keys (I think so) 
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
			
			// get all the records from Employees table 
			ResultSet rs = stmt.executeQuery("select * from Employees");			
			
			// print all the matches 
			while(rs.next())  
				System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + " " + rs.getInt(4) + " " + rs.getString(5));			
			
			// add a new record 
			rs.moveToInsertRow();
			rs.updateString("first_name","jack");
			rs.updateString("last_name", "black");
			rs.insertRow();
			
			// get the new record by null in the job column
			rs = stmt.executeQuery("select * from Employees where job is null");
			
			System.out.println("");
			
			// print all the matches and update null job to "undefined" 
			while(rs.next()) {
				System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + " " + rs.getInt(4) + " " + rs.getString(5));
				rs.updateString("job", "undefined");
				rs.updateRow();
			}
				
			// get the new record by "undefined" in the job column
			rs = stmt.executeQuery("select * from Employees where job = 'undefined'");
			
			System.out.println("");
			
			// print all the matches and delete the record by "undefined" job column
			while(rs.next()) {
				System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + " " + rs.getInt(4) + " " + rs.getString(5));
				rs.deleteRow();
			}
			
			// close the connection 
			con.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
}
