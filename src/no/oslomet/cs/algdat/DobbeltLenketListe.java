package no.oslomet.cs.algdat;


////////////////// class DobbeltLenketListe //////////////////////////////


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

public class DobbeltLenketListe<T> implements Liste<T> {


    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    /////////////////////////////////////////////////Ingen endring hit til hitt



    ////////////////oppgave1//////////////

    public DobbeltLenketListe() {   //standardkonstruktør
        hode = hale = null;
        antall = endringer= 0;
    }

    public DobbeltLenketListe(T[] a) {
        // this();
        if(a.length ==0){
            Objects.requireNonNull(a, "tabellen er null");
        }

         hode = hale = new Node<>(null);
        for (T elementer : a) {
            if (elementer != null) {

                hale = hale.neste = new Node(elementer, hale, null);
                antall++;
            }
        }
        if (antall == 0) {
            hode = hale = null;
        } else {
            (hode = hode.neste).forrige = null;
        }
    }


    ///////////////////////oppgave 1////////////////////////////////////////////////////////////////
    @Override
    public int antall() {
        return antall;
    }

    @Override  //skal retunere true hvis listen er tom og false ellers.
    public boolean tom() {
        return (antall == 0 && hode == null && hale == null);

    }
///////////////////////////////////////////////////////////////////////////////////////////////////



////////////////////////////////////////oppgave 2/////////////////////////////////////////////
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append('[');
        if (!tom()){
            s.append(hode.verdi);
            for(Node<T> p = hode.neste; p !=null; p = p.neste) {
                s.append(',').append(' ').append(p.verdi);


            }
        }
        s.append("]");
        return s.toString();
    }

    public String omvendtString() {
        StringBuilder s = new StringBuilder();
        s.append('[');

        if (!tom())
        {
            s.append(hale.verdi);

            for (Node<T> p = hale.forrige; p != null; p = p.forrige)
            {
                s.append(',').append(' ').append(p.verdi);
            }
        }

        s.append(']');
        return s.toString();
    }



    @Override  //(t) skal legges inn verdien bakerst i listen.
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");
        if (antall == 0) {
            hode = hale = new Node<>(verdi, null, null);
        } else{
            hale = hale.neste = new Node<>(verdi, hale, null);
        }
        antall++;      // ny verdi i listen
        endringer++;   // en endring i listen
          return true;
    }

//////////////////////////////////////////////////////////////////////////////////////////




////////////////////////////////oppgave 3//////////////////////////////////////////////
    public Liste<T> subliste(int fra, int til) {
        throw new NotImplementedException();
    }



    //hjelpe metode som skal brukes for å hente, fjerne og oppdatere noder
    private Node<T> finnNode(int indeks) {
        Node<T> p;
        if (indeks <= antall / 2) {
            p = hode;
            for (int i = 0; i < indeks; i++) {
                p = p.neste;
            }
        } else {
            p = hale;
            for (int i = antall - 1; i > indeks; i--) {
                p = p.forrige;
            }
        }
        return p;
    }



    @Override
    //skal hente uten å fjerne verdien på indeksen. hvis den er negativ eller >= antall i listen skal det kastes IndexOfBE.
    public T hent(int indeks) {
        indeksKontroll(indeks, false);  // Se Liste, false: indeks = antall er ulovlig
        return finnNode(indeks).verdi;

    }
    //hjelpe metode som arve fra liste

    private void indeksKontroll(int indeks) {
        if (indeks < 0) {
            throw new IndexOutOfBoundsException("Indeks " +
                    indeks + " er negativ!");
        } else if (indeks >= antall) {
            throw new IndexOutOfBoundsException("Indeks " +
                    indeks + " >= antall(" + antall + ") noder!");
        }
    }


    @Override    //skal erstatte eksisterende verdi på indeks med verdien verdi. Den gamle verdien skal retuneres.
    // hvis det er ulovlig index (negativ eller >= antall listen) skal  kastes InexsOfBE
    public T oppdater(int indeks, T nyverdi) {
        indeksKontroll(indeks);
        Objects.requireNonNull(nyverdi, "Ikke tillatt med null-verdier!");
        indeksKontroll(indeks, false);  // Se Liste, false: indeks = antall er ulovlig
        Node<T> p = finnNode(indeks);
        T gammelVerdi = p.verdi;

        p.verdi = nyverdi;
        endringer++;
        return gammelVerdi;
    }
/////////////////////////////////////////////oppgave4/////////////////////////////
    @Override  //skal retunere indeksen til første forekomst av verdi, hvis den ikke finnes i listen skal retuneres -1
    public int indeksTil(T verdi) {
        if (verdi == null) return -1;

        Node<T> p = hode;

        for (int i = 0; i < antall; i++, p = p.neste)
        {
            if (p.verdi.equals(verdi))
            {
                return i;
            }
        }
        return -1;
    }


    @Override
    //kan legge verdien bakerst, dvs. det er tillatt med indeks lik antall verider i listen. da brukes true som
    //parameter i indeskKontroll(). Hvis det ikke er tillatt med indeks lik antall verdier brukes false.
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////oppgave5////////////////////////
@Override
// har to argumenter.Den første bestemmer hvor verdi skal legges.
// Det betyr at de opprinnelige verdiene i listen fra og med posisjon indeks og utover vil få sine posisjoner
// økt med 1. Vi kan si at de forskyves mot høyre (eller nedover om vi vil) i listen. Hvis indeks er negativ
// eller er større enn antallet verdier, skal det kastes en IndexOutOfBoundsException. Det er imidlertid
// lovlig med indeks lik antallet verdier. Det betyr at den nye verdien skal legges bakerst.
public void leggInn(int indeks, T verdi) {
    Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

    indeksKontroll(indeks, true);  // Se Liste, true: indeks = antall er lovlig

    if (antall == 0) {
        hode = hale = new Node<>(verdi, null, null);
    }
        else if (indeks== 0) {
        hode = hode.forrige = new Node<>(verdi, null, hode);
    }
    else if (indeks == antall)
    {
        hale = hale.neste = new Node<>(verdi, hale, null);
    }
    else {
        Node<T> p = finnNode(indeks);

        p.forrige = p.forrige.neste= new Node<>(verdi, p.forrige, p);
    }

    antall++;// listen har fått en ny verdi
    endringer++;
    }


    @Override  //skal fjerne første forekomst av verdi og retunere sann, antall verdier dermed blir 1 mindere enn
    // før og alle verdiene som kommer etterpå får redusert sin index med 1 og hvis verdi ikk er i listen, retuners usann.

    public boolean fjern(T verdi) {
        throw new NotImplementedException();


    }


    @Override    // skal fjerne og retunere verdien med denne indeksen. antall verdier dermed blir 1 mindere enn
    // før og alle verdiene som kommer etterpå får redusert sin index med 1.
    // hvis det er en ulovlig indeks (negativ >= antallet i listen) skal kastes IndexOutOfBE
    public T fjern(int indeks) {
        throw new NotImplementedException();
    }


    @Override
    //skal tømme listen, dvs. sørge for at det som eventulet ligger igjen blir resikulert og at listen deretter blir tom.
    public void nullstill() {
        Node<T> p = hode, q = null;

        while (p != null)
        {
            q = p.neste;
            p.neste = null;
            p.verdi = null;
            p = q;
        }

        hode = hale = null;
        antall = 0;
        endringer++;


    }

    @Override
    public Iterator<T> iterator() {
        throw new NotImplementedException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new NotImplementedException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            throw new NotImplementedException();
        }

        private DobbeltLenketListeIterator(int indeks){
            throw new NotImplementedException();
        }

        @Override
        public boolean hasNext(){
            throw new NotImplementedException();
        }

        @Override
        public T next(){
            throw new NotImplementedException();
        }

        @Override
        public void remove(){
            throw new NotImplementedException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new NotImplementedException();
    }

} // class DobbeltLenketListe



