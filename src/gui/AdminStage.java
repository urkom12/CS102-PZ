/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import glavno.Automobil;
import db.DB;
import exceptions.NeValidnaCenaException;
import exceptions.PraznoPoljeException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

/**
 *
 * @author Aleksa
 */
public class AdminStage extends Stage {

    Button btnLogout = new Button("Log Out");

    TextField modelTxt = new TextField();
    TextField cenaTxt = new TextField();
    Button dodajBtn = new Button("Dodaj");
    Button izbrisiBtn = new Button("Obrisi");
    Button izmeniBtn = new Button("Izmeni");
    Button scrapujBtn = new Button("Scrapuj");

    TableView<Automobil> table = new TableView<>();
    Automobil automobilIzmena = new Automobil();

    public AdminStage() throws SQLException, NeValidnaCenaException, PraznoPoljeException {
        btnLogout.setPrefHeight(45);
        btnLogout.setPrefWidth(170);
        modelTxt.setPromptText("Model...");
        cenaTxt.setPromptText("Cena...");
        dodajBtn.setPrefWidth(120);
        izbrisiBtn.setPrefWidth(120);
        izmeniBtn.setPrefWidth(120);
        scrapujBtn.setPrefWidth(120);
        table.setPrefSize(750, 500);
        table.setStyle("-fx-border-color: #476b6b;"
                + "-fx-background-color: #a3c2c2;");

        BorderPane pane = new BorderPane();
        HBox topRight = new HBox(btnLogout);
        topRight.setPadding(new Insets(32, 150, 10, 10));
        topRight.setAlignment(Pos.TOP_RIGHT);

        pane.setTop(topRight);

        VBox boxBot = new VBox(dodajBtn, izbrisiBtn, izmeniBtn);
        boxBot.setSpacing(20);
        boxBot.setAlignment(Pos.CENTER_LEFT);
        VBox boxTop = new VBox(modelTxt, cenaTxt);
        boxTop.setSpacing(20);
        VBox box = new VBox(boxTop, boxBot);
        box.setPadding(new Insets(50, 20, 10, 0));
        box.setSpacing(120);
        box.setPrefWidth(250);

        HBox dugme= new HBox(scrapujBtn);
        dugme.setAlignment(Pos.BOTTOM_RIGHT);
        HBox mid = new HBox(box, table, dugme);
        mid.setPadding(new Insets(10, 0, 10, 10));
        mid.setAlignment(Pos.CENTER);
        mid.setSpacing(7);
        
        pane.setCenter(mid);

        //dodavanje kolona u tabelu
        table.setColumnResizePolicy(table.CONSTRAINED_RESIZE_POLICY);
        try {
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
            table.getColumns().addAll(column1, column2, column3);
            table.setItems(FXCollections.observableList(DB.db.getAutomobil2()));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        Scene scene = new Scene(pane, 1220, 950);

        /**
         * handlovanje dugmeta za dodavanje automobila
         */
        dodajBtn.setOnAction(e -> {
            try {

                Automobil automobil = new Automobil();
                automobil.setModel(modelTxt.getText());
                automobil.setCena(cenaTxt.getText() + " €");
                DB.db.addAutomobil2(automobil);
                modelTxt.clear();
                cenaTxt.clear();
                table.setItems(FXCollections.observableList(DB.db.getAutomobil2()));
                ut.Ut.showInfoAlert("Uspesno ste dodali Automobil");

            } catch (SQLException ex) {
            } catch (PraznoPoljeException | NumberFormatException ex) {
                ut.Ut.showErrorAlert(ex.getMessage());
            }
        });
        /**
         * handlovanje dugmeta za izmenu automobila
         */
        izmeniBtn.setOnAction(e -> {
            if (table.getSelectionModel().getSelectedItem() != null) {
                try {
                    automobilIzmena = table.getSelectionModel().getSelectedItem();
                    automobilIzmena.setModel(modelTxt.getText());
                    automobilIzmena.setCena(cenaTxt.getText() + " €");
                    DB.db.updateAutomobil2(automobilIzmena);
                    modelTxt.clear();
                    cenaTxt.clear();
                    table.setItems(FXCollections.observableList(DB.db.getAutomobil2()));
                    ut.Ut.showInfoAlert("Uspesno ste izmenili Automobil");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (PraznoPoljeException | NumberFormatException ex) {
                    ut.Ut.showErrorAlert(ex.getMessage());
                }
            } else {
                ut.Ut.showErrorAlert("Morate izabrati automobil za izmenu");
            }
        });
        /**
         * handlovanje dugmeta za brisanje automobila
         */
        izbrisiBtn.setOnAction(e -> {
            if (table.getSelectionModel().getSelectedItem() != null) {
                try {
                    DB.db.deleteAutomobil2(table.getSelectionModel().getSelectedItem());
                    table.setItems(FXCollections.observableList(DB.db.getAutomobil2()));
                    ut.Ut.showInfoAlert("Uspesno ste obrisali Automobil");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                ut.Ut.showErrorAlert("Morate izabrati Automobil koji zelite da izbrisete");
            }
        });
        //handlovanje logout dugmeta
        btnLogout.setOnAction(e -> {
            this.hide();
            try {
                new Main().start(this);
            } catch (SQLException ex) {
                Logger.getLogger(AdminStage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NeValidnaCenaException ex) {
                Logger.getLogger(AdminStage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PraznoPoljeException ex) {
                Logger.getLogger(AdminStage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //handlovanje scrape dugmeta
        scrapujBtn.setOnAction(e -> {
            try {
                ut.jsoupAuto.prikazPocetni();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (PraznoPoljeException | NumberFormatException ex) {
                ut.Ut.showErrorAlert(ex.getMessage());
            } catch (NeValidnaCenaException ex) {
                Logger.getLogger(AdminStage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.setTitle("Polovni Automobili - Admin");
        this.setScene(scene);
        this.show();
    }

}
