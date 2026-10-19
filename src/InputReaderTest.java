import junit.framework.TestCase;

/**
 * Tests for InputReader. Covers hasParentheses, isValid, readEquation and
 * findUnmatchedParenthesis, with at least one normal case and one bad-input
 * case for each.
 *
 * @author Muna Ali
 * @version Oct 15, 2026
 */
public class InputReaderTest
    extends TestCase
{
    // ~ Fields ................................................................

    private InputReader reader;

    // ~ Constructors ..........................................................

    // ~ Public Methods ........................................................

    /**
     * Creates a fresh InputReader before each test.
     */
    public void setUp()
    {
        reader = new InputReader();
    }


    /**
     * Normal case: matching nested parentheses are accepted.
     */
    public void testHasNormalParentheses()
    {
        assertTrue(reader.hasParentheses("((x+7)*2)"));
    }


    /**
     * Normal case: an equation with no parentheses at all is accepted.
     */
    public void testHasNoParentheses()
    {
        assertTrue(reader.hasParentheses("3*x+7"));
    }


    /**
     * Bad input: a ")" with nothing open is rejected, which means removing from
     * an empty chain.
     */
    public void testIncorrectParentheses()
    {
        assertFalse(reader.hasParentheses(")"));
        assertFalse(reader.hasParentheses("x+9)("));
    }


    /**
     * Bad input: a "(" that is never closed is rejected (leftover in the
     * chain).
     */
    public void testOpenParentheses()
    {
        assertFalse(reader.hasParentheses("(x+9"));
    }


    /**
     * Bad input: null input is rejected without crashing.
     */
    public void testHasNullParentheses()
    {
        assertFalse(reader.hasParentheses(null));
    }


    /**
     * Normal case: well-formed equations are accepted.
     */
    public void testIsValid()
    {
        assertTrue(reader.isValid("(x+1)"));
        assertTrue(reader.isValid("3*x^2+(x+4)*6"));
    }


    /**
     * Bad input: an empty string is rejected.
     */
    public void testIsValidEmpty()
    {
        assertFalse(reader.isValid(""));
    }


    /**
     * Bad input: null input is rejected without crashing.
     */
    public void testIsValidNull()
    {
        assertFalse(reader.isValid(null));
    }


    /**
     * Bad input: spaces in the equation are rejected.
     */
    public void testIsValidSpaces()
    {
        assertFalse(reader.isValid("9 * x"));
    }


    /**
     * Bad input: spelled-out numbers (words) are rejected.
     */
    public void testIsValidSpelling()
    {
        assertFalse(reader.isValid("two * x"));
    }


    /**
     * Bad input: a missing "*" between a number and a variable or parenthesis
     * is rejected.
     */
    public void testIsValidNoMultiplying()
    {
        assertFalse(reader.isValid("8x"));
        assertFalse(reader.isValid("4(x+1)"));
    }


    /**
     * Bad input: an equation that ends with an operator is incomplete and is
     * rejected.
     */
    public void testIsValidNotComplete()
    {
        assertFalse(reader.isValid("9+x*"));
    }


    /**
     * Bad input: unmatched or empty parentheses are rejected.
     */
    public void testIsValidUnbalancedParens()
    {
        assertFalse(reader.isValid("(x+1"));
        assertFalse(reader.isValid("x+1)"));
        assertFalse(reader.isValid("()"));
    }


    /**
     * Bad input: an equation that starts with an operator is rejected.
     */
    public void testIsValidStartsWithOperator()
    {
        assertFalse(reader.isValid("*3"));
    }


    /**
     * Bad input: two operators in a row are rejected.
     */
    public void testIsValidDoubleOperator()
    {
        assertFalse(reader.isValid("2++3"));
    }


    /**
     * Bad input: an operator right before ")" is rejected.
     */
    public void testIsValidOperatorBeforeCloseParen()
    {
        assertFalse(reader.isValid("(2+)"));
    }


    /**
     * Normal case: the chain keeps the order the user typed.
     */
    public void testReadEquationKeepsTypedOrder()
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
    public void testReadEquationMultiDigitNumberAndKinds()
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
    public void testReadEquationParenthesesTokens()
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
    public void testReadEquationEmptyInputThrows()
    {
        try
        {
            reader.readEquation("");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }


    /**
     * Bad input: unbalanced input throws IllegalArgumentException.
     */
    public void testReadEquationUnbalancedInputThrows()
    {
        try
        {
            reader.readEquation("(x+1");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }


    /**
     * Empty collection: an empty string leaves the chain empty, so it is
     * accepted by hasParentheses.
     */
    public void testHasParenthesesEmptyString()
    {
        assertTrue(reader.hasParentheses(""));
    }


    /**
     * Removing something that is not there: each ")" tries to remove from an
     * empty chain, so the input is rejected.
     */
    public void testHasParenthesesRemoveFromEmptyChain()
    {
        assertFalse(reader.hasParentheses("))"));
    }


    /**
     * Normal case: all parentheses match, so -1 is returned.
     */
    public void testFindUnmatchedAllMatched()
    {
        assertEquals(-1, reader.findUnmatchedParenthesis("((x+1)*2)"));
    }


    /**
     * Empty collection: an empty string has nothing unmatched.
     */
    public void testFindUnmatchedEmptyString()
    {
        assertEquals(-1, reader.findUnmatchedParenthesis(""));
    }


    /**
     * Bad input: an extra ")" is reported at its position.
     */
    public void testFindUnmatchedExtraClose()
    {
        assertEquals(3, reader.findUnmatchedParenthesis("x+1)"));
    }


    /**
     * Bad input: a "(" that is never closed is reported at its position.
     */
    public void testFindUnmatchedLeftoverOpen()
    {
        assertEquals(2, reader.findUnmatchedParenthesis("3*(x+1"));
    }


    /**
     * Bad input: null throws IllegalArgumentException.
     */
    public void testFindUnmatchedNullThrows()
    {
        try
        {
            reader.findUnmatchedParenthesis(null);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {

            // expected
        }
    }
}
