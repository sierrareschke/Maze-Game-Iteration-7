package csci.ooad.polymorphia.artifacts;

public class ArtifiactFactory {

    public static Armor createArmor(String nmae){
        return new Armor(nmae);
    }
}
