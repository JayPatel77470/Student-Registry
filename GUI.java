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

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class GUI extends BorderPane {
	
	GridPane grid = new GridPane();
	Insets insets = new Insets(10);
	Button insertButton = new Button("Insert");
	Button nextButton = new Button("Next");
	Button updateButton = new Button("Update");
	Button deleteButton = new Button("Delete");
	Button retrieveButton = new Button("View");
	HBox btnPane = new HBox();
	TextField studentIdField = new TextField();
	TextField nameField = new TextField();
	TextField emailField = new TextField();
	TextField mailAddressField = new TextField();
	TextField enrollYearField = new TextField();
	
	@SuppressWarnings("static-access")
	public GUI() {
		
		grid.setVgap(5);
		grid.setHgap(5);
		grid.setAlignment(Pos.CENTER);
		
		Label studentIdLabel = new Label("Student ID:");
		grid.add(studentIdLabel, 0, 0);
		grid.add(studentIdField, 1, 0);
 
		Label nameLabel = new Label("Name:");
		grid.add(nameLabel, 0, 1);
		grid.add(nameField, 1, 1);

		Label emailLabel = new Label("Email:");
		grid.add(emailLabel, 0, 2);
		grid.add(emailField, 1, 2);

		Label mailAddressLabel = new Label("Mailing Address:");
		grid.add(mailAddressLabel, 0, 3);
		grid.add(mailAddressField, 1, 3);

		Label enrollYearLabel = new Label("Enrollment Year:");
		grid.add(enrollYearLabel, 0, 4);
		grid.add(enrollYearField, 1, 4);
		
		btnPane.getChildren().addAll(insertButton, nextButton, updateButton, deleteButton, retrieveButton);
		btnPane.setAlignment(Pos.CENTER);
		btnPane.setMargin(insertButton, new Insets(5));
		btnPane.setMargin(nextButton, new Insets(5));
		btnPane.setMargin(updateButton, new Insets(5));
		btnPane.setMargin(deleteButton, new Insets(5));
		btnPane.setMargin(retrieveButton, new Insets(5));
		
		setMargin(grid, insets);
		setMargin(btnPane, insets);
		setTop(grid);
		setBottom(btnPane);
	}
}
