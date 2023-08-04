/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import glavno.Automobil;
import glavno.Korisnik;
import glavno.Role;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Urkom
 */
public class DB {

    private static final String url = "jdbc:mysql://localhost:3306/automobil";
    private static final String username = "root";
    private static final String password = "";
    private Connection connection;

    public static DB db = new DB();

    public DB() {
        
    }

    //metoda za kreiranje tabela ako ne postoje u bazi
    /**
     *
     */
    

    /**
     * metoda za otvaranje konekcije na bazu
     *
     * @throws SQLException
     */
    public void openConnection() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    /**
     * metoda za zatvaranje konekcije na bazu
     *
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        connection.close();
    }

    /**
     * metoda za kreiranje tabela
     *
     * @param sql
     * @throws SQLException
     */
    public void createTables(String sql) throws SQLException {
        openConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.execute();
        closeConnection();
    }

    /**
     * metoda za dodavanje role
     *
     * @param rola
     * @throws SQLException
     */
    public void addRole(Role role) throws SQLException {
        openConnection();
        PreparedStatement stmt = connection.prepareStatement("insert into role values(?,?)");
        stmt.setLong(1, role.ordinal() + 1);
        stmt.setString(2, role.name());
        stmt.execute();
        closeConnection();
    }

    /**
     * metoda za registrovanje korisnika
     *
     * @param korisnik
     * @throws SQLException
     */
    public void registerUser(Korisnik korisnik) throws SQLException {
        openConnection();
        PreparedStatement stmt = connection.prepareStatement("insert into korisnik(ime, prezime, email, adresa, brojTelefona, sifra)"
                + "values(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, korisnik.getIme());
        stmt.setString(2, korisnik.getPrezime());
        stmt.setString(3, korisnik.getEmail());
        stmt.setString(4, korisnik.getAdresa());
        stmt.setString(5, korisnik.getBrojTelefona());
        stmt.setString(6, korisnik.getSifra());
        stmt.executeUpdate();

        ResultSet resultSet = stmt.getGeneratedKeys();
        while (resultSet.next()) {
            long id = resultSet.getLong(1);
            korisnik.setId(id);
            addRoleToUser(korisnik);
        }
        closeConnection();
    }

    /**
     * metoda za davanje role korisniku, koja se koristi u metodi "registerUser"
     *
     * @param korisnik
     * @throws SQLException
     */
    private void addRoleToUser(Korisnik korisnik) throws SQLException {
        openConnection();
        PreparedStatement stmt = connection.prepareStatement("insert into korisnik_role("
                + "id_korisnik, id_role)"
                + " values(?,?)");
        stmt.setLong(1, korisnik.getId());
        stmt.setLong(2, korisnik.getRole().ordinal() + 1);
        stmt.execute();
        closeConnection();
    }

    /**
     * metoda za proveru da li korisnik koji zeli da se login postoji u nasoj
     * bazi
     *
     * @param email
     * @param password
     * @return
     * @throws SQLException
     */
    public Role checkUser(String email, String password) throws SQLException {
        openConnection();
        PreparedStatement stmt = connection.prepareStatement("select korisnik_role.id_role from korisnik, korisnik_role "
                + "where korisnik.email = ? and korisnik.sifra = ? "
                + "and korisnik.id = korisnik_role.id_korisnik ");
        stmt.setString(1, email);
        stmt.setString(2, password);

        ResultSet resultSet = stmt.executeQuery();
        if (resultSet.next()) {
            int idRole = resultSet.getInt(1);
            closeConnection();
            return Role.values()[idRole - 1];
        }
        closeConnection();
        return null;
    }

    /**
     * metoda za dodavanje automobilaa u bazu
     *
     * @param automobil
     * @throws SQLException
     */
    public void addAutomobil2(Automobil automobil) throws SQLException {
        openConnection();
        PreparedStatement stmt = connection.prepareStatement("insert into automobili2("
                + "model,cena) "
                + "values(?,?)");
        stmt.setString(1, automobil.getModel());
        stmt.setString(2, automobil.getCena());
        stmt.execute();
        closeConnection();
    }

    /**
     * metoda za stampanje svih automobila iz baze
     *
     * @return
     * @throws SQLException
     */
    public List<Automobil> getAutomobil2() throws SQLException {
        openConnection();
        PreparedStatement stmt = connection.prepareStatement("select * from automobili2");
        ResultSet resultSet = stmt.executeQuery();
        List<Automobil> automobil = new ArrayList<>();
        while (resultSet.next()) {
            automobil.add(new Automobil(
                    resultSet.getLong("id"),
                    resultSet.getString("model"),
                    resultSet.getString("cena"),
                    resultSet.getBoolean("reserved")));
        }
        closeConnection();
        return automobil;
    }
    /**
     * metoda za azuriranje automobila
     *
     * @param automobil
     * @throws SQLException
     */
    public void updateAutomobil2(Automobil automobil) throws SQLException {
        openConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "update automobili2 set model = ?,"
                + " cena = ? where id = ?");
        stmt.setString(1, automobil.getModel());
        stmt.setString(2, automobil.getCena());
        stmt.setLong(3, automobil.getId());
        stmt.execute();
        closeConnection();
    }
    
    public void kupiAutomobil2(Automobil automobil) throws SQLException {
        openConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "update automobili2 set reserved = 1 where id = ?");
        stmt.setLong(1, automobil.getId());
        stmt.execute();
        closeConnection();
    }
    
    /**
     * metoda za brisanje zeljenog automobila
     *
     * @param automobil
     * @throws SQLException
     */
    public void deleteAutomobil2(Automobil automobil) throws SQLException {
        openConnection();
        PreparedStatement stmt = connection.prepareStatement("delete from automobili2 where id = ?");
        stmt.setLong(1, automobil.getId());
        stmt.execute();
        closeConnection();
    }
    /**
     * metoda za ispitivanje da li korisnik sa unetim emailom vec postoji u bazi
     *
     * @param email
     * @return
     * @throws SQLException
     */
    public boolean existsUser(String email) throws SQLException {
        openConnection();
        PreparedStatement stmt = connection.prepareStatement("select * from korisnik where email = ?");
        stmt.setString(1, email);
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            closeConnection();
            return true;
        }
        closeConnection();
        return false;
    }
}
