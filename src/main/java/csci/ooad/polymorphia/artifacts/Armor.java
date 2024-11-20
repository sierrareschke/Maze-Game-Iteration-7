package csci.ooad.polymorphia.artifacts;

public record Armor(String name){
    @Override
    public String toString() {
        return name + " armor";
    }
}
