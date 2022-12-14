
package application;

import java.sql.*;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;

public class Main extends Application {
	GUI gui = new GUI();
	Database db;
	Statement statement;
	String CONNECTION_STRING = "Your connection string for JDBC";
	String username = "Your user name";
	String password = "Your password";
	int studentId;
	String name;
	String email;
	String mailAddress;
	int enrollYear;

	@Override
	public void start(Stage primaryStage) {
		Stage stage = new Stage();
		stage.setTitle("Student Information");
		stage.setWidth(450);
		stage.setHeight(250);
		Scene scene = new Scene(gui);
		stage.setScene(scene);
		stage.show();

		Connection connection;

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(CONNECTION_STRING, username, password);
			this.statement = connection.createStatement();

			this.db = new Database(statement);

			db.createTable(statement);

			gui.insertButton.setOnAction(e -> {
				try {
					insertTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			});

			gui.deleteButton.setOnAction(e -> {
				try {
					deleteTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			});

			gui.retrieveButton.setOnAction(e -> {
				try {
					retrieveTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			});

			gui.updateButton.setOnAction(e -> {
				try {
					updateTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			});

			gui.nextButton.setOnAction(e -> {
				try {
					nextTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			});

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public NumberFormatException readGUI() {
		try {
			if (gui.studentIdField.getText() != "")
				this.studentId = Integer.parseInt(gui.studentIdField.getText());
			this.name = gui.nameField.getText();
			this.email = gui.emailField.getText();
			this.mailAddress = gui.mailAddressField.getText();
			if (gui.enrollYearField.getText() != "")
				this.enrollYear = Integer.parseInt(gui.enrollYearField.getText());
			return null;
		} catch (NumberFormatException e) {
			return e;
		}
	}

	public void resetGUI() {
		this.studentId = 0;
		this.name = "";
		this.email = "";
		this.mailAddress = "";
		this.enrollYear = 0;
		gui.studentIdField.setText("");
		gui.nameField.setText("");
		gui.emailField.setText("");
		gui.mailAddressField.setText("");
		gui.enrollYearField.setText("");
	}

	public void insertTable() throws SQLException {
		if (readGUI() != null) {
			Dialog("Student Id and Enrollment Year cannot contain letters.");
		} else if (studentId == 0 || name == "" || email == "" || mailAddress == "" || enrollYear == 0) {
			Dialog("Please fill up all the fields");
		} else {
			try {
				db.insertTable(studentId, name, email, mailAddress, enrollYear);
				Dialog("A record of a student is added into a database.");
				resetGUI();
			} catch (SQLServerException e) {
				Dialog("Record with entered Student Id already exists.");
				resetGUI();
			}
		}
	}

	public void deleteTable() throws SQLException {
		if (readGUI() != null) {
			Dialog("Student Id cannot contain letters.");
		} else if (studentId == 0) {
			Dialog("Please enter Student Id");
		} else {
			if (retrieveTable()) {
				Dialog<String> dialog = new Dialog<String>();
				dialog.setTitle("Dialog");
				dialog.setContentText("Do you want to delete?");
				ButtonType type = new ButtonType("Ok", ButtonData.CANCEL_CLOSE);
				dialog.getDialogPane().getButtonTypes().add(type);
				dialog.show();
				dialog.setOnCloseRequest(e -> {
					try {
						db.deleteTable(studentId);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				});
			}
		}
	}

	public boolean retrieveTable() throws SQLException {
		if (readGUI() != null) {
			Dialog("Student Id cannot contain letters.");
			return false;
		} else if (studentId == 0) {
			Dialog("Please enter Student Id");
			return false;
		} else {
			ResultSet rs = db.retrieveTable(studentId);
			if (rs.next()) {
				this.name = rs.getString("Name");
				this.email = rs.getString("Email");
				this.mailAddress = rs.getString("Mailing Address");
				this.enrollYear = rs.getInt("Enrollment Year");
				gui.nameField.setText(name);
				gui.emailField.setText(email);
				gui.mailAddressField.setText(mailAddress);
				gui.enrollYearField.setText(Integer.toString(enrollYear));
				return true;
			} else {
				Dialog("Student with entered Id does not exist.");
				return false;
			}
		}

	}

	public void updateTable() throws SQLException {
		if (readGUI() != null) {
			Dialog("Student Id cannot contain letters.");
		} else if (studentId == 0 || name == "" || email == "" || mailAddress == "" || enrollYear == 0) {
			Dialog("Please fill up all the fields.");
		} else {
			if (db.updateTable(studentId, name, email, mailAddress, enrollYear) == 1) {
				Dialog("Record updated.");
				resetGUI();
			} else {
				Dialog("Student with entered Id does not exist.");
				resetGUI();
			}
		}
	}

	public void nextTable() throws SQLException {
		resetGUI();
		ResultSet rs = db.nextTable();
		if (rs.next()) {
			this.studentId = rs.getInt("Student Id");
			this.name = rs.getString("Name");
			this.email = rs.getString("Email");
			this.mailAddress = rs.getString("Mailing Address");
			this.enrollYear = rs.getInt("Enrollment Year");
			gui.studentIdField.setText(Integer.toString(studentId));
			gui.nameField.setText(name);
			gui.emailField.setText(email);
			gui.mailAddressField.setText(mailAddress);
			gui.enrollYearField.setText(Integer.toString(enrollYear));
			gui.nextButton.setOnAction(e1 -> {
				try {
					if (rs.next()) {
						this.studentId = rs.getInt("Student Id");
						this.name = rs.getString("Name");
						this.email = rs.getString("Email");
						this.mailAddress = rs.getString("Mailing Address");
						this.enrollYear = rs.getInt("Enrollment Year");
						gui.studentIdField.setText(Integer.toString(studentId));
						gui.nameField.setText(name);
						gui.emailField.setText(email);
						gui.mailAddressField.setText(mailAddress);
						gui.enrollYearField.setText(Integer.toString(enrollYear));
					} else {
						Dialog("No more records");
						resetGUI();
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			});
		} else {
			Dialog("No more records");
			resetGUI();
		}
	}

	public void Dialog(String context) {
		Dialog<String> dialog = new Dialog<String>();
		dialog.setTitle("Dialog");
		dialog.setContentText(context);
		ButtonType type = new ButtonType("Ok", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().add(type);
		dialog.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}