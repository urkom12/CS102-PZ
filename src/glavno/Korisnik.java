/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glavno;

import exceptions.BrojTelefonaNijeValidanException;
import exceptions.KorisnikNijeValidanException;
import exceptions.PraznoPoljeException;

/**
 *
 * @author Urkom
 */
public class Korisnik {
    private long id;
    private String ime, prezime, email, adresa, brojTelefona, sifra;

    public Korisnik(long id, String ime, String prezime, String email, String adresa, String brojTelefona, String sifra) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.adresa = adresa;
        this.brojTelefona = brojTelefona;
        this.sifra = sifra;
    }
    
    public void setEmail(String email) throws PraznoPoljeException, KorisnikNijeValidanException {
        if (email.isEmpty()) {
            throw new PraznoPoljeException("Ne mozete ostaviti prazno polje");
        } else if (!email.contains(".") && !email.contains("@")) {
            throw new KorisnikNijeValidanException("Email nije validan");
        }
        this.email = email;
    }
    
    public void setSifra(String sifra) throws PraznoPoljeException {
        if (sifra.isEmpty()) {
            throw new PraznoPoljeException("Ne mozete ostaviti prazno polje");
        }
        this.sifra = sifra;
    }
    
    public void setIme(String ime) throws PraznoPoljeException, KorisnikNijeValidanException {
        if (ime.isEmpty()) {
            throw new PraznoPoljeException("Ne mozete ostaviti prazno polje");
        } else if (!ime.matches("[a-zA-Z]+")) {
            throw new KorisnikNijeValidanException("Korisnicko ime nije validno");
        }
        this.ime = ime;
    }
    
    public void setPrezime(String prezime) throws PraznoPoljeException, KorisnikNijeValidanException {
        if (prezime.isEmpty()) {
            throw new PraznoPoljeException("Ne mozete ostaviti prazno polje");
        } else if (!prezime.matches("[a-zA-Z]+")) {
            throw new KorisnikNijeValidanException("Korisnicko prezime nije validno");
        }
        this.prezime = prezime;
    }
    
    public void setAdresa(String adresa) throws PraznoPoljeException, KorisnikNijeValidanException {
        if (adresa.isEmpty()) {
            throw new PraznoPoljeException("Ne mozete ostaviti prazno polje");
        } else if (!adresa.matches("[0-9a-zA-Z/\\-\\ ]+")) {
            throw new KorisnikNijeValidanException("Adresa nije validna");
        }
        this.adresa = adresa;
    }
    
    public void setBrojTelefona(String brojTelefona) throws PraznoPoljeException, BrojTelefonaNijeValidanException {
        if (brojTelefona.isEmpty()) {
            throw new PraznoPoljeException("Ne mozete ostaviti prazno polje");
        } else if (!brojTelefona.matches("[0-9/\\-\\+]+")) {
            throw new BrojTelefonaNijeValidanException("Ne validan broj telefona");
        }
        this.brojTelefona = brojTelefona;
    }
    public Korisnik() {
    }
    private Role role;
    
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getEmail() {
        return email;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public String getSifra() {
        return sifra;
    }
    
    
}
