import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class Heros {
    private String heroName;
    private int healthPoints;
    private int maxHealth;
    private int lvl;
    private int experiencePoints;
    private double gold;
    private boolean isAlive;
    private String klasseType;
    private Inventory inventory;

    public Heros(String heroName, int maxHealth, String klasseType) {
        this.heroName = heroName;
        this.maxHealth = maxHealth;
        this.healthPoints = maxHealth;
        this.klasseType = klasseType;
        this.lvl = 1;
        this.experiencePoints = 0;
        this.gold = 0;
        this.isAlive = true;

        // To faste "startsæt" af items, ét pr. klassetype.
        String[] inventoryM = {"Staff", "Cape", "Large hat"};
        String[] inventoryW = {"Sword", "Shield", "Armor"};

        // Her vælges hvilket inventory helten skal starte med, alt efter
        // hvilken klasse den er. equalsIgnoreCase bruges for at gøre "user oplevelsen" bedre
        // Fallback: hvis klassen hverken er Mage eller Warrior (fx en fremtidig
        // "Rogue"), får helten et tomt inventory i stedet for at crashe.
        if (klasseType.equalsIgnoreCase("Mage")) {
            this.inventory = new Inventory(inventoryM);
        } else if (klasseType.equalsIgnoreCase("Warrior")) {
            this.inventory = new Inventory(inventoryW);
        } else {
            this.inventory = new Inventory(new String[]{});   // fallback til andre nye klasse
        }
    }

    public void printCharacterSheet() {
        System.out.println("=== CHARECTER SHEET ===");
        System.out.println("Name: " + heroName);
        System.out.println("Health: " + healthPoints + "/" + maxHealth);
        // Viser kun advarslen hvis helten er under 25% health (se isHealthCritical)
        if (isHealthCritical()) System.out.println("Warning HP is low");
        System.out.println("Level: " + lvl);
        System.out.println("XP: " + experiencePoints);
        System.out.println("Gold: " + gold);
        System.out.println("Alive: " + isAlive);
        System.out.println();
        System.out.println("---Hero class---");
        System.out.println("Class: " + klasseType);
        System.out.println();
        printInventory();
        System.out.println();
    }

    // Trækker skade fra health. Health kan aldrig blive negativ (clamp til 0).
    // Sætter isAlive = false så snart health rammer 0.
    public void takeDamage(int amount) {
        healthPoints -= amount;
        if (healthPoints < 0) healthPoints = 0;
        if (healthPoints < 1) isAlive = false;
    }

    // Lægger health til igen, men kan aldrig overstige maxHealth (clamp til loft).
    public void heal(int amount) {
        healthPoints += amount;
        if (healthPoints > maxHealth) healthPoints = maxHealth;
    }

    public void addGold(double amount) {
        gold += amount;
    }

    // Trækker kun guld fra hvis der rent faktisk er nok.
    // Returnerer true/false så kalderen (Main) kan reagere på om
    // "købet" lykkedes, uden selv at skulle tjekke gold-feltet direkte.
    public boolean removeGold(double amount) {
        if (gold >= amount) {
            gold -= amount;
            return true;
        }
        return false;
    }

    // Lægger XP til og tjekker om der er nok til at levele op.
    // grænsen er fast (1000), uafhængig af nuværende level, havde planer om at ændre, fik aldrig gjort.
    public void addXP(int amount) {
        experiencePoints += amount;
        if (experiencePoints >= 1000) {
            levelUp();
        }
    }

    // Level op: nulstiller XP, øger max-health, og heler helten helt op.
    public void levelUp() {
        lvl++;
        experiencePoints = 0;
        maxHealth += 20;
        healthPoints = maxHealth;
    }

    // "Critical" defineres som under 25% af max health.
    public boolean isHealthCritical() {
        return getHealthPercentage() < 25;
    }

    // Simpel angrebsmetode: trækker en tilfældig skade mellem 0 og 30.
    // 0 tolkes specielt som et "miss" i stedet for 0 skade.
    // Metoden opdaterer target direkte (target.takeDamage), og tjekker
    // bagefter om target er død som følge af angrebet.
    public void attack(Heros target) {
        Random random = new Random();
        int damage = random.nextInt(31);

        if (damage == 0) {
            System.out.println(heroName + " missed øv bøv");
        } else {
            System.out.println(heroName + " attacks " + target.heroName + " for " + damage + " damage!");
            target.takeDamage(damage);
            System.out.println(target.heroName + " health: " + target.getHealthPercentage() + "%");
        }
        if (target.isHealthCritical()) {
            System.out.println("Warning: " + target.heroName + "'s HP is low!");
        }

        if (!target.isAlive()) {
            System.out.println(target.heroName + " has died");
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    // Udregner health i procent som double, fx 45/150 -> 30.0%
    // Bruges bl.a. af isHealthCritical().
    public double getHealthPercentage() {
        return (double) healthPoints / maxHealth * 100;
    }

    // Looper igennem alle items i inventory og printer dem ét ad gangen,
    // samtidig med at den tæller hvor mange items der er i alt.
    public void printInventory() {
        System.out.println("---Inventory Values---");
        String[] items = inventory.getItems();
        int itemCount = 0;

        for (String item : items) {
            System.out.println(item);
            itemCount++;
        }

        System.out.println("Total items in inventory: " + itemCount);
    }
}