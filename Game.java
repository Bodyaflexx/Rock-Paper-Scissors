package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Game {
    Scanner scanner = new Scanner(System.in);
    String playerChose;
    String computerChose;
    String options = "";
    String[] ops;
    int rating = 0;


    public void startGame() {
        greeting();
        playerChose = scanner.nextLine();
        while (checkPlayerChose()) {
            if (playerChose.equalsIgnoreCase("!rating")) {
                System.out.printf("Your rating: %s\n", rating);
                playerChose = scanner.nextLine();
                continue;
            }
            computerRandom();
            checkOnWin();
            playerChose = scanner.nextLine();
        }
        System.out.println("Bye!");
    }

    private boolean checkPlayerChose() {
        List<String> trueOptions = Arrays.asList("!exit", "rock", "gun", "lightning",
                "devil", "dragon", "water", "air",
                "paper", "sponge", "wolf", "tree",
                "human", "snake", "scissors", "fire", "!rating");
        while (!trueOptions.contains(playerChose.toLowerCase())) {
            System.out.println("Invalid input");
            playerChose = scanner.nextLine();
        }
        return !playerChose.equalsIgnoreCase("!exit");
    }

    private void computerRandom() {
        Random random = new Random();
        computerChose = ops[random.nextInt(0, ops.length)];
    }

    private void checkOnWin() {
        if (computerChose.equalsIgnoreCase(playerChose)) {
            System.out.printf("There is a draw (%s)\n", computerChose);
            rating += 50;
        } else {
            List<String> temp = new ArrayList<>();
            int index = 0;
            for (int i = 0; i < ops.length; i++) {
                if (ops[i].equalsIgnoreCase(playerChose)) {
                    index = i;
                    break;
                }
            }
            boolean checker = false;
            temp.addAll(Arrays.asList(ops).subList(index + 1, ops.length));
            temp.addAll(Arrays.asList(ops).subList(0, index));
            for (int i = 0; i < temp.size() / 2; i++) {
                if (temp.get(i).equalsIgnoreCase(computerChose)) {
                    System.out.printf("Sorry, but the computer chose <%s>\n", computerChose);
                    checker = true;
                    break;
                }
            }
            if (!checker) {
                System.out.printf("Well done. The computer chose <%s> and failed\n", computerChose);
                rating += 100;
            }
        }

    }

    private void greeting() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.printf("Hello, %s\n", name);
        File file = new File("rating.txt ");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.toLowerCase().contains(name.toLowerCase())) {
                    rating = Integer.parseInt(line.split(" ")[1]);
                }
            }
        } catch (FileNotFoundException ignored) {
        }
        options = scanner.nextLine();
        if (options.isEmpty()) {
            ops = new String[]{"rock", "paper", "scissors"};
        } else {
            ops = options.trim().split(",");
        }
        System.out.println("Okay, let's start");
    }
}
