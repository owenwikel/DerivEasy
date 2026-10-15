import java.util.Scanner;
/**
 * Runs DerivEasy, a console derivative calculator.
 *
 * @author Owen Wikel
 * @version 2026.10.15
 */
public class Main {
    //~ Fields ................................................................
    private Scanner input;
    private InputReader reader;
    private Simplification simplifier;
    //~ Constructors ..........................................................
    /**
     * Starts the program.
     * 
     * @param args unused
     */
    public static void main(String[] args) {
        new Main().run();
    }
    
    /**
     * Creates a calculator that reads equations from the scanner.
     * 
     * @param input the scanner to read equations from.
     */
    public Main(Scanner input) {
        this.input = input;
        reader = new InputReader();
        simplifier = new Simplification();
    }
    
    /**
     * Creates a calculator that reads equations from the keyboard.
     */
    public Main() {
        this(new Scanner(System.in));
    }
    //~Public  Methods ........................................................
    /**
     * This is the Calculator of the program. Welcomes and prompts user.
     */
    public void run() {
        String next;
        LinkedChain<Token> equation;
        LinkedChain<Token> derivative;
        LinkedChain<Token> simplified;
        System.out.println("Welcome to DerivEasy- The derivative calculator"
            + " for students.\n" +
              "Write exponents with ^ and multiplication with *.\n "
            + "Use parentheses to group, like (3*x+1)^3.\n "
            + "Example: 3*x^2+2*x\n "
            + "Type quit to exit. ");
        while (true) {
            System.out.println("Enter your equation.");
            next = input.nextLine();
            if (next.toUpperCase().equals("QUIT")) {
                break;
            }
            if (!reader.isValid(next)) {
                System.out.println("This is not valid input.");
                continue;
            }
            equation = reader.readEquation(next);
            try {
                derivative = Differentiation.differentiate(equation);
            }
            catch (ArithmeticException e) {
                System.out.println("You cannot divide by 0.");
                continue;
            }
            simplified = simplifier.simplify(derivative);
            System.out.println(simplifier.format(simplified));
        }
    }
}
