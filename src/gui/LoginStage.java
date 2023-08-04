/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import exceptions.NeValidnaCenaException;
import exceptions.PraznoPoljeException;
import glavno.Role;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Urkom
 */
public class LoginStage extends Stage {
    
    TextField email = new TextField();
    PasswordField password = new PasswordField();
    Button register = new Button("Register");
    Button login = new Button("Login");
    Button nazad = new Button("Povratak");
    Label lbl = new Label("Nemate akaunt? Registrujte se ovde");

    public LoginStage() {
        email.setPromptText("Email..");
        password.setPromptText("Password..");
        //ubacivanje textFildova za registraciju u vertikalni box 
        VBox box = new VBox(email, password, login, lbl, register, nazad);
        box.setSpacing(20);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(10));

        Scene scene = new Scene(box, 300, 350);
        
        //handlovanje dugmeta za registraciju->salje nas na registerStage panel
        register.setOnAction(e -> {
            this.hide();
            new RegisterStage();
        });
        
        //handlovanje dugmeta za login gde se proverava da li korisnik koji zeli da se uloguje postoji u bazi
        login.setOnAction(e -> {
            try {
                Role role = db.DB.db.checkUser(email.getText(), password.getText());
                if (role != null && role.equals(Role.ADMIN)) {
                    this.hide();
                    new AdminStage();
                } else if (role != null && role.equals(Role.USER)) {
                    this.hide();
                    new KorisnikStage();
                } else {
                    ut.Ut.showErrorAlert("Korisnicko ime i/ili sifra nisu validni");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (NeValidnaCenaException ex) {
                Logger.getLogger(LoginStage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PraznoPoljeException ex) {
                Logger.getLogger(LoginStage.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

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
        
        this.setScene(scene);
        this.setTitle("Polovni Automobili - Login");
        this.show();
        box.requestFocus();
    }

    }
    
