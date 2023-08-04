/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glavno;

import exceptions.NeValidnaCenaException;
import exceptions.PraznoPoljeException;

/**
 *
 * @author Urkom
 */
public class Automobil {
    
    private long id;
    private String model, cena;
    private boolean reserved;

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
    
    public Automobil() {
    }

    public Automobil(long id,String model, String cena) {
        this.id = id;
        this.model = model;
        this.cena = cena;
    }

    public Automobil(long id, String model, String cena, boolean reserved) {
        this.id = id;
        this.model = model;
        this.cena = cena;
        this.reserved = reserved;
    }

    public Automobil(String model, String cena) {
        this.model = model;
        this.cena = cena;
    }

  
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) throws PraznoPoljeException {
        if (model.isEmpty()) {
            throw new PraznoPoljeException("Ne mozete ostaviti polje prazno");
        }
        this.model = model;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    @Override
    public String toString() {
        return "Automobil{" + "id=" + id + ", model=" + model + ", cena=" + cena + ", reserved=" + reserved + '}';
    }

}
