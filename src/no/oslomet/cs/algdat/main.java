package no.oslomet.cs.algdat;

public class main {

    public static void main(String[] args) {
         String[] s = {"Ole",null,"Per","Kari​",null};
        Liste<String> liste = new DobbeltLenketListe<>(s);
        System.out.println(liste.antall() +"​ ​"+ liste.tom());



        String[] s1 = {},s2 = {"A"},s3 = {null,"A",null,"B",null};
        DobbeltLenketListe<String> l1 = new DobbeltLenketListe<>(s1);
        DobbeltLenketListe<String> l2 = new DobbeltLenketListe<>(s2);
        DobbeltLenketListe<String> l3 = new DobbeltLenketListe<>(s3);
        System.out.println(l1.toString() +"​ ​"+ l2.toString()  +"​ ​"+ l3.toString() +"​"+ l1.omvendtString() +"​"
                + l2.omvendtString() +"​​"+ l3.omvendtString());

       /* DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();
        System.out.println(liste.toString() + "​ ​"+ liste.omvendtString());
   ​       for(int​i = 1; i <= 3; i++) {
               liste.leggInn(i);
               System.out.println(liste.toString() +"​ ​"+ liste.omvendtString());   }*/





    }
}
