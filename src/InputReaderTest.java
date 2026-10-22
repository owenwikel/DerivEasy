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


    /**
     * Normal case: nested parentheses and powers of groups are accepted.
     */
    public void testNestedAndGroupedPowers()
    {
        assertTrue(reader.isValid("(x^2+1)^3"));
        assertTrue(reader.isValid("((2*x)+1)*x"));
        assertTrue(reader.isValid("(x+1)*(x-1)"));
        assertTrue(reader.isValid("(x^2)/(x+1)"));
        assertTrue(reader.isValid("x^(1/2)"));
    }


    /**
     * Normal case: a leading minus is accepted where a student would type it.
     */
    public void testUnaryMinusAccepted()
    {
        assertTrue(reader.isValid("-x^2"));
        assertTrue(reader.isValid("(-x)^2"));
        assertTrue(reader.isValid("x^-1"));
        assertTrue(reader.isValid("2*-x"));
        assertTrue(reader.isValid("3/-x"));
    }


    /**
     * Bad input: only a single "-" may act as a sign.
     */
    public void testUnaryMinusRejected()
    {
        assertFalse(reader.isValid("+x"));
        assertFalse(reader.isValid("(+x)"));
        assertFalse(reader.isValid("2--x"));
        assertFalse(reader.isValid("2+-x"));
        assertFalse(reader.isValid("-"));
        assertFalse(reader.isValid("x^-"));
    }


    /**
     * Normal case: decimals and multi-digit numbers are accepted.
     */
    public void testDecimals()
    {
        assertTrue(reader.isValid("0.5*x"));
        assertTrue(reader.isValid("12*x^10"));
        assertTrue(reader.isValid("x^2.5"));
    }


    /**
     * Bad input: malformed decimals are rejected.
     */
    public void testBadDecimals()
    {
        assertFalse(reader.isValid("1.*x"));
        assertFalse(reader.isValid(".5*x"));
        assertFalse(reader.isValid("1.2.3*x"));
        assertFalse(reader.isValid("x*1."));
    }


    /**
     * Normal case: other single-letter variables and the constant e.
     */
    public void testOtherVariables()
    {
        assertTrue(reader.isValid("x*y"));
        assertTrue(reader.isValid("x^2*y^3"));
        assertTrue(reader.isValid("e^x"));
        assertTrue(reader.isValid("3*t+2*u"));
    }


    /**
     * Bad input: letters side by side, or capitals, are rejected.
     */
    public void testBadLetters()
    {
        assertFalse(reader.isValid("xy"));
        assertFalse(reader.isValid("two*x"));
        assertFalse(reader.isValid("X+1"));
        assertFalse(reader.isValid("2e"));
    }


    /**
     * Normal case: functions followed by parentheses are accepted.
     */
    public void testFunctionsAccepted()
    {
        assertTrue(reader.isValid("sin(x)"));
        assertTrue(reader.isValid("sin(x^2)"));
        assertTrue(reader.isValid("2*cos(x)+ln(x)"));
        assertTrue(reader.isValid("sin(cos(x))"));
        assertTrue(reader.isValid("-sin(x)"));
        assertTrue(reader.isValid("x*log(x)"));
    }


    /**
     * Bad input: functions with no parentheses, empty parentheses, or no "*"
     * next to them are rejected.
     */
    public void testFunctionsRejected()
    {
        assertFalse(reader.isValid("sin"));
        assertFalse(reader.isValid("sin x"));
        assertFalse(reader.isValid("sinx"));
        assertFalse(reader.isValid("sin()"));
        assertFalse(reader.isValid("sin+x"));
        assertFalse(reader.isValid("x sin(x)"));
        assertFalse(reader.isValid("xsin(x)"));
        assertFalse(reader.isValid("2sin(x)"));
        assertFalse(reader.isValid("sin(x)cos(x)"));
        assertFalse(reader.isValid("sin(x)*"));
    }


    /**
     * Bad input: the original rules still hold after the changes.
     */
    public void testOldRulesStillHold()
    {
        assertFalse(reader.isValid("2x"));
        assertFalse(reader.isValid("2(x+1)"));
        assertFalse(reader.isValid("(x)(x)"));
        assertFalse(reader.isValid("()"));
        assertFalse(reader.isValid("(x+1"));
        assertFalse(reader.isValid("x+1)"));
        assertFalse(reader.isValid("x++2"));
        assertFalse(reader.isValid("*x"));
        assertFalse(reader.isValid("x+"));
        assertFalse(reader.isValid("^2"));
        assertFalse(reader.isValid("x^"));
        assertFalse(reader.isValid("3 * x ^ 2"));
        assertFalse(reader.isValid("x@2"));
    }


    /**
     * Tokens: a function gets its own token kind and the order is kept.
     */
    public void testReadEquationFunction()
    {
        LinkedChain<Token> chain = reader.readEquation("2*sin(x)");
        Token[] pieces = chain.toArray(new Token[chain.size()]);
        assertEquals(6, pieces.length);
        assertEquals("number", pieces[0].getKind());
        assertEquals("operator", pieces[1].getKind());
        assertEquals("function", pieces[2].getKind());
        assertEquals("sin", pieces[2].getValue());
        assertEquals("(", pieces[3].getValue());
        assertEquals("variable", pieces[4].getKind());
        assertEquals(")", pieces[5].getValue());
    }


    /**
     * Tokens: a decimal stays one token, a leading minus is an operator token,
     * and e is a constant.
     */
    public void testReadEquationDecimalMinusConstant()
    {
        LinkedChain<Token> chain = reader.readEquation("-0.5*e^y");
        Token[] pieces = chain.toArray(new Token[chain.size()]);
        assertEquals(6, pieces.length);
        assertEquals("-", pieces[0].getValue());
        assertEquals("operator", pieces[0].getKind());
        assertEquals("0.5", pieces[1].getValue());
        assertEquals("number", pieces[1].getKind());
        assertEquals("constant", pieces[3].getKind());
        assertEquals("e", pieces[3].getValue());
        assertEquals("y", pieces[5].getValue());
        assertEquals("variable", pieces[5].getKind());
    }


    /**
     * Bad input: readEquation throws for a function with nothing after it.
     */
    public void testReadEquationBadFunctionThrows()
    {
        try
        {
            reader.readEquation("sin");
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }
}
