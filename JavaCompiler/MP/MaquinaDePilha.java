import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class MaquinaDePilha {

    Stack<Integer> numbersStack;
    Queue<String> instructions;

    public Stack<Integer> getNumbersStack() {
        return this.numbersStack;
    }

    public Queue<String> getInstructions() {
        return this.instructions;
    }

    int processOps() {
        return 0;
    }

    public MaquinaDePilha() {
        instructions = new LinkedList<>();
        numbersStack = new Stack<>();
    }

    public static void main(String[] args) {
        Stream<String> stream;
        MaquinaDePilha mp = new MaquinaDePilha();

        try {
            stream = Files.lines(Paths.get(args[0]));
            Object[] strs = stream.toArray();
            System.out.println(strs[0]);
            stream.forEach(line -> mp.getInstructions().add(line));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mp.getInstructions().forEach(System.out::println);
        System.out.println(mp.getInstructions().peek()); 
    }
}
