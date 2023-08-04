/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ut;

import javafx.scene.control.Alert;

/**
 *
 * @author Urkom
 */
public class Ut {
    public static void showErrorAlert(String poruka) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(poruka);
        alert.show();
    }
    public static void showInfoAlert(String poruka) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(poruka);
        alert.show();
    }
}
