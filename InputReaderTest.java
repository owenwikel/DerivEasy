import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for InputReader. Covers hasParentheses, isValid, and readEquation, with
 * at least one normal case and one bad-input case for each.
 *
 * @author Muna Ali
 * @version Oct 14, 2026
 */
public class InputReaderTest
{
    // ~ Fields ................................................................

    private InputReader reader;

    // ~ Constructors ..........................................................

    // ~ Public Methods ........................................................

    /**
     * Creates a fresh InputReader before each test.
     */
    @BeforeEach
    public void setUp()
    {
        reader = new InputReader();
    }

    // ---- hasParentheses ----


    /**
     * Normal case: matching nested parentheses are accepted.
     */
    @Test
    public void hasNormalParentheses()
    {
        assertTrue(reader.hasParentheses("((x+7)*2)"));
    }


    /**
     * Normal case: an equation with no parentheses at all is accepted.
     */
    @Test
    public void hasNoParentheses()
    {
        assertTrue(reader.hasParentheses("3*x+7"));
    }


    /**
     * Bad input: a ")" with nothing open is rejected, which means removing from
     * an empty chain.
     */
    @Test
    public void incorrectParentheses()
    {
        assertFalse(reader.hasParentheses(")"));
        assertFalse(reader.hasParentheses("x+9)("));
    }


    /**
     * Bad input: a "(" that is never closed is rejected (leftover in the
     * chain).
     */
    @Test
    public void openParentheses()
    {
        assertFalse(reader.hasParentheses("(x+9"));
    }


    /**
     * Bad input: null input is rejected without crashing.
     */
    @Test
    public void hasNullParentheses()
    {
        assertFalse(reader.hasParentheses(null));
    }

    // ---- isValid ----


    /**
     * Normal case: well-formed equations are accepted.
     */
    @Test
    public void isValidTest()
    {
        assertTrue(reader.isValid("(x+1)"));
        assertTrue(reader.isValid("3*x^2+(x+4)*6"));
    }


    /**
     * Bad input: an empty string is rejected.
     */
    @Test
    public void isValidEmpty()
    {
        assertFalse(reader.isValid(""));
    }


    /**
     * Bad input: null input is rejected without crashing.
     */
    @Test
    public void isValidNull()
    {
        assertFalse(reader.isValid(null));
    }


    /**
     * Bad input: spaces in the equation are rejected.
     */
    @Test
    public void isValidSpaces()
    {
        assertFalse(reader.isValid("9 * x"));
    }


    /**
     * Bad input: spelled-out numbers (words) are rejected.
     */
    @Test
    public void isValidSpelling()
    {
        assertFalse(reader.isValid("two * x"));
    }


    /**
     * Bad input: a missing "*" between a number and a variable or parenthesis
     * is rejected.
     */
    @Test
    public void isValidNoMultiplying()
    {
        assertFalse(reader.isValid("8x"));
        assertFalse(reader.isValid("4(x+1)"));
    }


    /**
     * Bad input: an equation that ends with an operator is incomplete and is
     * rejected.
     */
    @Test
    public void isValidNotComplete()
    {
        assertFalse(reader.isValid("9+x*"));
    }


    /**
     * Bad input: unmatched or empty parentheses are rejected.
     */
    @Test
    public void isValid_unbalancedParens()
    {
        assertFalse(reader.isValid("(x+1"));
        assertFalse(reader.isValid("x+1)"));
        assertFalse(reader.isValid("()"));
    }


    /**
     * Bad input: an equation that starts with an operator is rejected.
     */
    @Test
    public void isValidStartsWithOperator()
    {
        assertFalse(reader.isValid("*3"));
    }


    /**
     * Bad input: two operators in a row are rejected.
     */
    @Test
    public void isValidDoubleOperator()
    {
        assertFalse(reader.isValid("2++3"));
    }


    /**
     * Bad input: an operator right before ")" is rejected.
     */
    @Test
    public void isValidOperatorBeforeCloseParen()
    {
        assertFalse(reader.isValid("(2+)"));
    }

    // ---- readEquation ----


    /**
     * Normal case: the chain keeps the order the user typed.
     */
    @Test
    public void readEquation_normalKeepsTypedOrder()
    {
        LinkedChain<Token> chain = reader.readEquation("3*x+1");
        Token[] pieces = chain.toArray(new Token[chain.size()]);
        assertEquals(5, pieces.length);
        assertEquals("3", pieces[0].getValue());
        assertEquals("*", pieces[1].getValue());
        assertEquals("x", pieces[2].getValue());
        assertEquals("+", pieces[3].getValue());
        assertEquals("1", pieces[4].getValue());
    }


    /**
     * Normal case: multi-digit numbers become one token, and each token has the
     * right kind.
     */
    @Test
    public void readEquation_multiDigitNumberAndKinds()
    {
        LinkedChain<Token> chain = reader.readEquation("12*x");
        Token[] pieces = chain.toArray(new Token[chain.size()]);
        assertEquals(3, pieces.length);
        assertEquals("12", pieces[0].getValue());
        assertEquals("number", pieces[0].getKind());
        assertEquals("operator", pieces[1].getKind());
        assertEquals("variable", pieces[2].getKind());
    }


    /**
     * Normal case: parentheses become tokens of kind "parenthesis".
     */
    @Test
    public void readEquation_parenthesesTokens()
    {
        LinkedChain<Token> chain = reader.readEquation("(x+1)");
        Token[] pieces = chain.toArray(new Token[chain.size()]);
        assertEquals(5, pieces.length);
        assertEquals("parenthesis", pieces[0].getKind());
        assertEquals("(", pieces[0].getValue());
        assertEquals(")", pieces[4].getValue());
    }


    /**
     * Bad input: empty input throws IllegalArgumentException.
     */
    @Test
    public void readEquation_emptyInputThrows()
    {
        assertThrows(
            IllegalArgumentException.class,
            () -> reader.readEquation(""));
    }


    /**
     * Bad input: unbalanced input throws IllegalArgumentException.
     */
    @Test
    public void readEquation_unbalancedInputThrows()
    {
        assertThrows(
            IllegalArgumentException.class,
            () -> reader.readEquation("(x+1"));
    }

    // ---- findUnmatchedParenthesis and empty-chain cases ----


    /**
     * Empty collection: an empty string leaves the chain empty, so it is
     * accepted by hasParentheses.
     */
    @Test
    public void hasParentheses_emptyString()
    {
        assertTrue(reader.hasParentheses(""));
    }


    /**
     * Removing something that is not there: each ")" tries to remove from an
     * empty chain, so the input is rejected.
     */
    @Test
    public void hasParentheses_removeFromEmptyChain()
    {
        assertFalse(reader.hasParentheses("))"));
    }


    /**
     * Normal case: all parentheses match, so -1 is returned.
     */
    @Test
    public void findUnmatched_allMatched()
    {
        assertEquals(-1, reader.findUnmatchedParenthesis("((x+1)*2)"));
    }


    /**
     * Empty collection: an empty string has nothing unmatched.
     */
    @Test
    public void findUnmatched_emptyString()
    {
        assertEquals(-1, reader.findUnmatchedParenthesis(""));
    }


    /**
     * Bad input: an extra ")" is reported at its position.
     */
    @Test
    public void findUnmatched_extraClose()
    {
        assertEquals(3, reader.findUnmatchedParenthesis("x+1)"));
    }


    /**
     * Bad input: a "(" that is never closed is reported at its position.
     */
    @Test
    public void findUnmatched_leftoverOpen()
    {
        assertEquals(2, reader.findUnmatchedParenthesis("3*(x+1"));
    }


    /**
     * Bad input: null throws IllegalArgumentException.
     */
    @Test
    public void findUnmatched_nullThrows()
    {
        assertThrows(
            IllegalArgumentException.class,
            () -> reader.findUnmatchedParenthesis(null));
    }
}
