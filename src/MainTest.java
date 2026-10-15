import java.io.*;
import java.util.Scanner;
import junit.framework.TestCase;

/**
 * Tests for the Main class. Used ByteArray for tests which
 * I learned about last project from LLM. \n emulates the enter key.
 *
 * @author Owen Wikel
 * @version 2026.10.14
 */
public class MainTest extends TestCase {
    private ByteArrayOutputStream out;
    private PrintStream ogOut;
    private InputStream ogIn;

    /**
     * Redirects printed output into a holder so tests can read it.
     */
    public void setUp() {
        ogOut = System.out;
        ogIn = System.in;
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    /**
     * Runs Calculator on fake typing and returns whats printed.
     * LLM recommended to avoid using repetitive code.
     *
     * @param text the fake typing, each line ending in a newline
     * @return everything the Calculator printed
     */
    private String runWith(String text) {
        new Main(new Scanner(text)).run();
        return out.toString();
    }
    
    /**
     * Puts real keyboard and screen back to how they were.
     */
    public void tearDown() {
        System.setOut(ogOut);
        System.setIn(ogIn);
    }

    /**
     * tests no arg constructor reads from the keyboard.
     */
    public void testDefaultConstructor() {
        System.setIn(new ByteArrayInputStream("quit\n".getBytes()));
        new Main().run();
        String printed = out.toString();
        assertTrue(printed.contains("Welcome"));
    }

    /**
     * tests that empty input prints invalid message and reprompts.
     */
    public void testRunInvalidInput() {
        String printed = runWith("\nquit\n");
        assertTrue(printed.contains("This is not valid input."));
    }

    /**
     * Checks that valid input prints no invalid message.
     */
    public void testRunValidInput() {
        String printed = runWith("2*x\nquit\n");
        assertFalse(printed.contains("This is not valid input."));
    }

    /**
     * Makes sure division by zero prints the divide message and asks again.
     */
    public void testRunDivideByZero() {
        String printed = runWith("1/0\nquit\n");
        assertTrue(printed.contains("You cannot divide by 0."));
    }
    
    /**
     * Tests that main starts the program and welcomes the user.
     */
    public void testMain() {
        System.setIn(new ByteArrayInputStream("quit\n".getBytes()));
        Main.main(new String[0]);
        String printed = out.toString();
        assertTrue(printed.contains("Welcome"));
    }
}