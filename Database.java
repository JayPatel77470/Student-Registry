/**********************************************
Workshop #10	
Course:JAC444 - 4
Last Name:Patel
First Name:Jay
ID:158741199
Section:ZAA
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature - Jay Girishkumar Patel
Date:06-Dec-2022
**********************************************/

package application;

import java.sql.*;

public class Database {
	Statement statement;

	Database(Statement statement) {
		this.statement = statement;
	}

	public void createTable(Statement statement) throws SQLException {
		statement.execute("IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='student' and xtype='U')\r\n"
				+ "   CREATE TABLE student (\"Student Id\" INT PRIMARY KEY NOT NULL,\r\n"
				+ "   Name VARCHAR(255) NOT NULL,\r\n" + "   Email VARCHAR(255) NOT NULL,\r\n"
				+ "   \"Mailing Address\" VARCHAR(255) NOT NULL,\r\n" + "   \"Enrollment Year\" INT NOT NULL\r\n"
				+ ")");
	}

	public void insertTable(int studentId, String name, String email, String mailAddress, int enrollYear)
			throws SQLException {
		statement.execute("INSERT INTO student (\"Student Id\", Name, Email, \"Mailing Address\", \"Enrollment Year\")"
				+ " VALUES (" + studentId + ", '" + name + "', '" + email + "', '" + mailAddress + "', " + enrollYear
				+ ")");
	}

	public void deleteTable(int studentId) throws SQLException {
		statement.execute("DELETE FROM STUDENT WHERE \"Student Id\"=" + studentId);
	}

	public ResultSet retrieveTable(int studentId) throws SQLException {
		return statement.executeQuery("SELECT * FROM STUDENT WHERE \"Student Id\"=" + studentId);
	}

	public int updateTable(int studentId, String name, String email, String mailAddress, int enrollYear)
			throws SQLException {
		return statement.executeUpdate("UPDATE student \r\n" + "SET Name = '" + name + "', \"Mailing Address\"= '"
				+ mailAddress + "', Email = '" + email + "', \"Enrollment Year\" = " + enrollYear + "\r\n"
				+ "WHERE \"Student Id\" = " + studentId + "");
	}

	public ResultSet nextTable() throws SQLException {
		return statement.executeQuery("SELECT * FROM student");
	}
}
