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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Urkom
 */
public class KorisnikStage extends Stage{
    public KorisnikStage() throws SQLException, NeValidnaCenaException, PraznoPoljeException {

        Button logout = new Button("Log Out");
        Button btnKupi = new Button("Kupi");

        TableView<Automobil> table = new TableView<>();

        logout.setPrefHeight(45);
        logout.setPrefWidth(170);
        btnKupi.setPrefHeight(45);
        btnKupi.setPrefWidth(150);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setPrefSize(750, 500);
        table.setStyle("-fx-border-color: #476b6b;"
                + "-fx-background-color: #a3c2c2;");

        BorderPane pane = new BorderPane();

        //Postavljanje gornjeg dela GUI-a ********************************
        HBox topRight = new HBox(btnKupi, logout);
        topRight.setPadding(new Insets(32, 80, 10, 10));
        topRight.setAlignment(Pos.CENTER_RIGHT);
        topRight.setSpacing(10);
        pane.setTop(topRight);

        //Kreiranje centralnog dela GUI-a ************************
        HBox mid = new HBox(table);
        mid.setPadding(new Insets(10, 50, 10, 10));
        mid.setAlignment(Pos.CENTER_RIGHT);
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
                return new SimpleStringProperty(e.getValue().getCena());
            });
            TableColumn<Automobil, String> column3 = new TableColumn<>("Id");
            column3.setCellValueFactory(e -> {
                return new SimpleStringProperty(e.getValue().getId() + "");
            });
            TableColumn<Automobil, String> column4 = new TableColumn<>("Rezervisan");
            column4.setCellValueFactory(e -> {
                if(e.getValue().isReserved()){
                    return new SimpleStringProperty("Da");
                }
                else{
                    return new SimpleStringProperty("Ne");
                }
            });
            table.getColumns().addAll(column1, column2, column3, column4);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //handlovanje dugmeta za logout
        logout.setOnAction(e -> {
            this.hide();
            try {
                new Main().start(this);
            } catch (SQLException ex) {
                Logger.getLogger(KorisnikStage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NeValidnaCenaException ex) {
                Logger.getLogger(KorisnikStage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PraznoPoljeException ex) {
                Logger.getLogger(KorisnikStage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //handlovanje dugmeta za kupovinu
        btnKupi.setOnAction(e -> {
            if (table.getSelectionModel().getSelectedItem() != null) {
                try {
                    Automobil auto = new Automobil();
                    auto = table.getSelectionModel().getSelectedItem();
                    DB.db.kupiAutomobil2(auto);
                    table.setItems(FXCollections.observableList(DB.db.getAutomobil2()));
                    ut.Ut.showInfoAlert("Uspesno ste kupili Automobil");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (NumberFormatException ex) {
                    ut.Ut.showErrorAlert(ex.getMessage());
                }
            } else {
                ut.Ut.showErrorAlert("Morate izabrati koji automobil zelite da kupite");
            }
        });
        //StackPane stackPane = new StackPane(imgBcg, pane);
        Scene scene = new Scene(pane, 1100, 950);

        this.setTitle("Polovni Automobili");
        this.setScene(scene);
        this.show();
    }
}
