import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;
import java.io.File;
import java.util.Scanner;
import java.util.regex.*;

public class MaquinaDePilha {

    Stack<Integer> numbersStack;
    Queue<String> instructions;

    int processOps() {
        return 0;
    }

    public MaquinaDePilha() {
        instructions = new LinkedList<>();
        numbersStack = new Stack<>();
    }

    public static void main(String[] args) {
        MaquinaDePilha mp = new MaquinaDePilha();

        try {
            File inputFile = new File(args[0]);
            Scanner fileReader = new Scanner(inputFile);

            while (fileReader.hasNextLine()) {
                mp.instructions.add(fileReader.nextLine());
            }

            fileReader.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        // mp.instructions.forEach(System.out::println);

        Pattern operations = Pattern.compile("SUM|MULT|SUB|DIV");

        mp.instructions.forEach(instruction -> {
            if (instruction.contains("PUSH")) {
                mp.numbersStack.add(Integer.parseInt(instruction.substring(5)));
            } else if (operations.matcher(instruction).matches()) {
                //System.out.println(instruction);
                if (mp.numbersStack.size() >= 2) {
                    //mp.numbersStack.forEach(System.out::println);
                    int num0 = mp.numbersStack.pop();
                    int num1 = mp.numbersStack.pop();

                    switch (instruction) {
                        case "SUM":
                            mp.numbersStack.add(num1 + num0);
                            break;
                        case "MULT":
                            mp.numbersStack.add(num1 * num0);
                            break;
                        case "SUB":
                            mp.numbersStack.add(num1 - num0);
                            break;
                        case "DIV":
                            mp.numbersStack.add(num1 / num0);
                            break;
                    }
                } else {
                    System.out.println("Erro! Máquina de Pilha não conseguiu processar o programa.");
                }
            } else if (instruction.equals("PRINT")) {
                System.out.println(mp.numbersStack.pop());
            } else {
                System.out.println("Erro! Máquina de Pilha não reconhece essa instrução!");
            }
        });

        // mp.numbersStack.forEach(System.out::println);

    }
}
