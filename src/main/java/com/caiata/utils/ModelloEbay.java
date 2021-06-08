package com.caiata.utils;

import java.util.ArrayList;

public class ModelloEbay {

    private String titolo;
    private String descrizione;
    private String prezzo;
    private String img;

    public ModelloEbay() {
    }

    public ModelloEbay(String titolo, String descrizione, String prezzo, String img) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.img = img;
    }

    public ModelloEbay(String prezzo) {
        this.prezzo = prezzo;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(String prezzo) {
        this.prezzo = prezzo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void stampaElementi(ArrayList<ModelloEbay> listaModello){
        for(ModelloEbay elemento : listaModello){
            System.out.println(elemento.getTitolo());
            System.out.println(elemento.getDescrizione());
            System.out.println(elemento.getPrezzo());
            System.out.println(elemento.getImg());
            System.out.println("------------------");
        }
    }

    public void stampaPrezziCart(ArrayList<ModelloEbay> ris){
        for(ModelloEbay elemento : ris){
            System.out.println(elemento.getPrezzo());
        }
    }
}
