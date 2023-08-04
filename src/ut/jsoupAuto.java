/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ut;

import exceptions.NeValidnaCenaException;
import exceptions.PraznoPoljeException;
import glavno.Automobil;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 *
 * @author Urkom
 */
public class jsoupAuto {
    public static void prikazPocetni() throws SQLException, NeValidnaCenaException, PraznoPoljeException {
        try {
            //konekcija na nas sajt
            Document document = Jsoup.connect("https://www.polovniautomobili.com/auto-oglasi/pretraga?brand=mercedes-benz&price_to=&year_from=&year_to=&showOldNew=all&submit_1=&without_price=1").get();
            Elements body=document.select("div.table div#search-results div.js-hide-on-filter article div.textContentHolder div.textContent div.price");
            Elements body2=document.select("div.table div#search-results div.js-hide-on-filter article div.textContentHolder div.textContent h2 a");
            /*
                petlja gde ucitavamo sve informacije o nasem automobilu i zatim izvlacimo samo potrebne ifnormacije
                split metodom, i smestamo ih u nas automoivil koji zatim dodajemo u bazu.
                Petlja se izvrsava za sve automobile prikazanih na pocetnoj strani polovniautomobili.com sajta
            */
            List<String> lista = new ArrayList();
            for(Element korak : body){
                lista.add(korak.ownText());
            }
            List<String> lista2 = new ArrayList();
            for(Element korak : body2){
                lista2.add(korak.html());
            }
            for(int i=0;i<lista.size();i++){
                Automobil auto=new Automobil();
                auto.setCena(lista.get(i));
                auto.setModel(lista2.get(i));
                db.DB.db.addAutomobil2(auto);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            ut.Ut.showErrorAlert(ex.getMessage());
        }
    }
}
