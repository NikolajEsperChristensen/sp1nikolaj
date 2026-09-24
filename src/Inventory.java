
// mit formål er at holde inventory-logikken adskilt fra selve Heros-klassen,
// så man senere nemt kan udvide den (fx med addItem/removeItem/hasItem) (fik jeg ikke gjort)
// uden at skulle ændre i Heros.
public class Inventory {
    private String[] items;

    public Inventory(String[] items) {
        this.items = items;
    }

    // Giver adgang til items-arrayet udefra, fx så Heros.printInventory()
    // kan loope igennem det.
    public String[] getItems() {
        return items;
    }
}