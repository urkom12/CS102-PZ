/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import db.DB;
import exceptions.NeValidnaCenaException;
import exceptions.PraznoPoljeException;
import glavno.Automobil;
import java.sql.SQLException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Urkom
 */
public class Main extends Application {
    

    TableView<Automobil> table = new TableView<>();

    Button btnLogin = new Button("Login");
    Button btnRegister = new Button("Register");
    
    @Override
    public void start(Stage primaryStage) throws SQLException, NeValidnaCenaException, PraznoPoljeException {

        table.setPrefSize(750, 500);
        table.setStyle("-fx-border-color: #476b6b;"
                + "-fx-background-color: #a3c2c2;");

        BorderPane pane = new BorderPane();

        //Postavljanje gornjeg dela GUI-a ************************
        HBox topRight = new HBox(btnLogin, btnRegister);
        topRight.setPadding(new Insets(32, 253, 10, 10));
        topRight.setAlignment(Pos.CENTER_RIGHT);
        topRight.setSpacing(10);

        topRight.setSpacing(35);
        pane.setTop(topRight);


        //Centar********************************************************
        HBox mid = new HBox(table);
        mid.setPadding(new Insets(10, 50, 10, 10));
        mid.setAlignment(Pos.CENTER);
        mid.setSpacing(7);

        pane.setCenter(mid);

        //dodavanje kolona u tabelu **************************
        table.setColumnResizePolicy(table.CONSTRAINED_RESIZE_POLICY);
        try {
            table.setItems(FXCollections.observableList(DB.db.getAutomobil2()));
            TableColumn<Automobil, String> column1 = new TableColumn<>("Model");
            column1.setCellValueFactory(e -> {
                return new SimpleStringProperty(e.getValue().getModel());
            });
            TableColumn<Automobil, String> column2 = new TableColumn<>("Cena");
            column2.setCellValueFactory(e -> {
                return new SimpleStringProperty(e.getValue().getCena() + "");
            });
            TableColumn<Automobil, String> column3 = new TableColumn<>("Id");
            column3.setCellValueFactory(e -> {
                return new SimpleStringProperty(e.getValue().getId() + "");
            });
            table.getColumns().addAll(column1, column2, column3);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //StackPane stackPane = new StackPane(imgBcg, pane);
        Scene scene = new Scene(pane, 1100, 950);

        //handlovanje login dugmeta
        btnLogin.setOnAction(e -> {
            primaryStage.hide();
            new LoginStage();
        });
        //hendlovanje registracionog dugmeta
        btnRegister.setOnAction(e -> {
            primaryStage.hide();
            new RegisterStage();
        });

        primaryStage.setTitle("Polovni Automobili");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
