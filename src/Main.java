import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Opretter to Heros-objekter med forskellig klassetype.
        // Constructoren sørger selv for at give dem forskelligt inventory
        // baseret på klasseType (se Heros-konstruktøren).
        Heros cola = new Heros("Cola", 150, "Mage");
        Heros pepsi = new Heros("Pepsi", 200, "Warrior");

        cola.printCharacterSheet();
        pepsi.printCharacterSheet();


        System.out.println("===First round combat===");
        System.out.println();

        // --- Simulerer en runde med XP, skade, guld og et "potion"-køb ---
        // addXP kan trigge et level-up inde i metoden selv (se Heros.addXP)
        cola.addXP(1100);
        cola.takeDamage(50);
        cola.addGold(150.0);

        // removeGold returnerer true/false alt efter om der var nok guld.
        // Det bruges her som betingelse for om helten kan "købe en potion".
        if (cola.removeGold(100.0)) {
            System.out.println("Bought a potion!");
            cola.heal(25);
        } else {
            System.out.println("Not enough gold!");
        }

        cola.printCharacterSheet();


        pepsi.addXP(500);
        pepsi.takeDamage(50);
        pepsi.addGold(150.0);

        if (pepsi.removeGold(100.0)) {
            System.out.println("Bought a potion!");
            pepsi.heal(25);
        } else {
            System.out.println("Not enough gold!");
        }

        pepsi.printCharacterSheet();

        Scanner imput = new Scanner(System.in);

        // Kamp-loopet kører så længe En er i live.
        // Brugeren styrer selv hvem der angriber via konsol-input,
        // og kan afbryde manuelt med "Quit".
        while (cola.isAlive() && pepsi.isAlive()) {
            System.out.println();
            System.out.println("Attack 'Cola', attack 'Pepsi', or 'Quit':");

            String command = imput.nextLine();

            if (command.equalsIgnoreCase("Cola")) {
                cola.attack(pepsi);
            } else if (command.equalsIgnoreCase("Pepsi")) {
                pepsi.attack(cola);
            } else if (command.equalsIgnoreCase("Quit")) {
                break;
            } else {
                System.out.println("Unknown command, try again.");
            }
        }
    }
}