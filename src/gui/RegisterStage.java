/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import exceptions.BrojTelefonaNijeValidanException;
import exceptions.KorisnikNijeValidanException;
import exceptions.NeValidnaCenaException;
import exceptions.PraznoPoljeException;
import glavno.Korisnik;
import glavno.Role;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Urkom
 */
public class RegisterStage extends Stage{
    TextField ime = new TextField();
    TextField prezime = new TextField();
    TextField adresa = new TextField();
    TextField brojTelefona = new TextField();
    TextField email = new TextField();
    PasswordField sifra = new PasswordField();
    Button register = new Button("Registruj se");
    Button nazad = new Button("Povratak");

    public RegisterStage() {
        ime.setPromptText("Ime..");
        prezime.setPromptText("Prezime..");
        email.setPromptText("Email..");
        adresa.setPromptText("Adresa..");
        brojTelefona.setPromptText("Broj Telefona..");
        sifra.setPromptText("Sifra..");

        /**
         * dodavanje input fieldova na nasu scenu
         */
        VBox box = new VBox(ime, prezime, email, adresa, brojTelefona, sifra, register, nazad);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setSpacing(20);
        box.setPadding(new Insets(20));

        Scene scene = new Scene(box, 350, 450);

        this.setScene(scene);
        this.setTitle("Polovni Automobili - Register");
        this.show();
        box.requestFocus();
        /**
         * klikom na ovo dugme vracamo se na pocetnu stranu
         */
        nazad.setOnAction(e -> {
            this.hide();
            try {
                new Main().start(this);
            } catch (SQLException ex) {
                Logger.getLogger(RegisterStage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NeValidnaCenaException ex) {
                Logger.getLogger(RegisterStage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PraznoPoljeException ex) {
                Logger.getLogger(RegisterStage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        /**
         * dodavanje informacija sa inputa u korisnika, zatim takvog korisnika
         * dodajemo u bazu pod uslovom da vec ne postoji takav korisnik u bazi
         */
        register.setOnAction(e -> {
            try {
                if (!db.DB.db.existsUser(email.getText())) {
                    Korisnik korisnik = new Korisnik();
                    korisnik.setIme(ime.getText());
                    korisnik.setPrezime(prezime.getText());
                    korisnik.setEmail(email.getText());
                    korisnik.setAdresa(adresa.getText());
                    korisnik.setBrojTelefona(brojTelefona.getText());
                    korisnik.setSifra(sifra.getText());
                    korisnik.setRole(Role.USER);
                    db.DB.db.registerUser(korisnik);
                    this.hide();
                    new KorisnikStage();
                    ut.Ut.showInfoAlert("Uspesno ste se registrovali");
                } else {
                    ut.Ut.showErrorAlert("Korisnik vec postoji");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (PraznoPoljeException ex) {
                ut.Ut.showErrorAlert(ex.getMessage());
            } catch (KorisnikNijeValidanException | BrojTelefonaNijeValidanException ex) {
                ut.Ut.showErrorAlert(ex.getMessage());
            } catch (NeValidnaCenaException ex) {
                Logger.getLogger(RegisterStage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
