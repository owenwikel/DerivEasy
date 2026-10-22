import student.TestCase;
import java.util.Arrays;

// -------------------------------------------------------------------------
/**
 * Tests the Differentiation Class
 * 
 * @author Ari Bajaj
 * @version Oct 20, 2026
 */
public class DifferentiationTestClass
    extends TestCase
{
    LinkedChain<Token> equation1;
    LinkedChain<Token> equation2;
    String[] term1;
    String[] term2;
    Simplification simplifier;

    public void setUp()
    {
        simplifier = new Simplification();
        equation1 = new LinkedChain<Token>();
        equation1.addToEnd(new Token("number", "3"));
        equation2 = new LinkedChain<Token>();
        equation2.addToEnd(new Token("variable", "x"));
        term1 = new String[1];
        term2 = new String[1];
        term1[0] = "3";
        term2[0] = "x";
    }


    // ----------------------------------------------------------
    /**
     * Tests the differentiateTerm() method
     */
    public void testDifferentiateTerm()
    {
        // constant
        String[] test1 = new String[1];
        test1[0] = "0";
        assertTrue(
            Arrays
                .equals(test1, Differentiation.differentiateTerm(term1, "x")));

        // base variable
        String[] test2 = new String[1];
        test2[0] = "1";
        assertTrue(
            Arrays
                .equals(test2, Differentiation.differentiateTerm(term2, "x")));

        // one term polynomial with coefficient or exponent
        term1 = Arrays.copyOf(term1, 3);
        term2 = Arrays.copyOf(term2, 3);
        term1[1] = "*";
        term1[2] = "x";
        term2[1] = "^";
        term2[2] = "2";
        String[] test3 = new String[] { "3" };
        String[] test4 = new String[] { "2", "*", "x" };
        assertTrue(
            Arrays.equals(
                test3,
                toArray(
                    simplifier.simplify(
                        convertToLinkedChain(
                            Differentiation.differentiateTerm(term1, "x"))))));
        assertTrue(
            Arrays.equals(
                test4,
                toArray(
                    simplifier.simplify(
                        convertToLinkedChain(
                            Differentiation.differentiateTerm(term2, "x"))))));

        // one term polynomial with coefficient and exponent
        term2[2] = "3";
        String[] test5 = new String[] { "3", "*", "x", "^", "2" };
        assertTrue(
            Arrays.equals(
                test5,
                toArray(
                    simplifier.simplify(
                        convertToLinkedChain(
                            Differentiation.differentiateTerm(term2, "x"))))));

        // one term polynomial with coefficient and exponent of 2
        term1 = Arrays.copyOf(term1, 5);
        term1[3] = "^";
        term1[4] = "3";
        String[] test6 = new String[] { "9", "*", "x", "^", "2" };
        assertTrue(
            Arrays.equals(
                test6,
                toArray(
                    simplifier.simplify(
                        convertToLinkedChain(
                            Differentiation.differentiateTerm(term1, "x"))))));

        // multivariable term with respect to y
        String[] term7 = new String[] { "y", "*", "x" };
        String[] test7 = new String[] { "y" };
        assertTrue(
            Arrays.equals(
                test7,
                toArray(
                    simplifier.simplify(
                        convertToLinkedChain(
                            Differentiation.differentiateTerm(term7, "x"))))));

        // multivariable term with respect to x
        String[] test8 = new String[] { "x" };
        assertTrue(
            Arrays.equals(
                test8,
                toArray(
                    simplifier.simplify(
                        convertToLinkedChain(
                            Differentiation.differentiateTerm(term7, "y"))))));

        // quotient rule
        String[] term9 = new String[] { "x", "/", "3" };
        String[] test9 = new String[] { "1", "/", "3" };
        assertTrue(
            Arrays.equals(
                test9,
                toArray(
                    simplifier.simplify(
                        convertToLinkedChain(
                            Differentiation.differentiateTerm(term9, "x"))))));

        // trig function
        String[] term10 = new String[] { "sin", "(", "x", ")" };
        String[] test10 = new String[] { "cos", "(", "x", ")" };
        assertTrue(
            Arrays.equals(
                test10,
                toArray(
                    simplifier.simplify(
                        convertToLinkedChain(
                            Differentiation.differentiateTerm(term10, "x"))))));

        // log function
        String[] term11 = new String[] { "ln", "(", "x", ")" };
        String[] test11 = new String[] { "1", "/", "x" };
        assertTrue(
            Arrays.equals(
                test11,
                toArray(
                    simplifier.simplify(
                        convertToLinkedChain(
                            Differentiation.differentiateTerm(term11, "x"))))));

        // exponential function
        String[] term12 = new String[] { "exp", "(", "x", ")" };
        String[] test12 = new String[] { "exp", "(", "x", ")" };
        assertTrue(
            Arrays.equals(
                test12,
                toArray(
                    simplifier.simplify(
                        convertToLinkedChain(
                            Differentiation.differentiateTerm(term12, "x"))))));

        // chain rule
        String[] term13 = new String[] { "sin", "(", "x", "^", "2", ")" };
        String[] test13 =
            new String[] { "2", "*", "x", "*", "cos", "(", "x", "^", "2", ")" };
        assertTrue(
            Arrays.equals(
                test13,
                toArray(
                    simplifier.simplify(
                        convertToLinkedChain(
                            Differentiation.differentiateTerm(term13, "x"))))));
    }


    // ----------------------------------------------------------
    /**
     * Tests the differentiate() method
     */
    public void testDifferentiate()
    {
        // Test constant
        String[] test1 = new String[1];
        test1[0] = "0";
        assertTrue(
            Arrays.equals(
                test1,
                toArray(
                    simplifier.simplify(
                        Differentiation.differentiate(equation1, "x")))));

        // Test base variable
        String[] test2 = new String[1];
        test2[0] = "1";
        assertTrue(
            Arrays.equals(
                test2,
                toArray(
                    simplifier.simplify(
                        Differentiation.differentiate(equation2, "x")))));

        // Test one term polynomial equation
        equation1.addToEnd(new Token("operator", "*"));
        equation1.addToEnd(new Token("variable", "x"));
        equation2.addToEnd(new Token("operator", "^"));
        equation2.addToEnd(new Token("number", "2"));
        String[] test3 = new String[] { "3" };
        String[] test4 = new String[] { "2", "*", "x" };
        assertTrue(
            Arrays.equals(
                test3,
                toArray(
                    simplifier.simplify(
                        Differentiation.differentiate(equation1, "x")))));
        assertTrue(
            Arrays.equals(
                test4,
                toArray(
                    simplifier.simplify(
                        Differentiation.differentiate(equation2, "x")))));

        equation2.remove(new Token("number", "2"));
        equation2.addToEnd(new Token("number", "3"));
        String[] test5 = new String[] { "3", "*", "x", "^", "2" };
        assertTrue(
            Arrays.equals(
                test5,
                toArray(
                    simplifier.simplify(
                        Differentiation.differentiate(equation2, "x")))));

        equation1.clear();
        equation1.addToEnd(new Token("number", "3"));
        equation1.addToEnd(new Token("operator", "*"));
        equation1.addToEnd(new Token("variable", "x"));
        equation1.addToEnd(new Token("operator", "^"));
        equation1.addToEnd(new Token("number", "3"));
        String[] test6 = new String[] { "9", "*", "x", "^", "2" };
        assertTrue(
            Arrays.equals(
                test6,
                toArray(
                    simplifier.simplify(
                        Differentiation.differentiate(equation1, "x")))));

        // multiterm with trig
        equation1.clear();
        equation1.addToEnd(new Token("number", "3"));
        equation1.addToEnd(new Token("operator", "*"));
        equation1.addToEnd(new Token("variable", "x"));
        equation1.addToEnd(new Token("operator", "+"));
        equation1.addToEnd(new Token("function", "cos"));
        equation1.addToEnd(new Token("parenthesis", "("));
        equation1.addToEnd(new Token("variable", "x"));
        equation1.addToEnd(new Token("parenthesis", ")"));
        String[] test7 =
            new String[] { "3", "-", "1", "*", "sin", "(", "x", ")" };
        assertTrue(
            Arrays.equals(
                test7,
                toArray(
                    simplifier.simplify(
                        Differentiation.differentiate(equation1, "x")))));

        // chain rule and log equation
        equation1.clear();
        equation1.addToEnd(new Token("parenthesis", "("));
        equation1.addToEnd(new Token("number", "3"));
        equation1.addToEnd(new Token("operator", "*"));
        equation1.addToEnd(new Token("variable", "x"));
        equation1.addToEnd(new Token("parenthesis", ")"));
        equation1.addToEnd(new Token("operator", "^"));
        equation1.addToEnd(new Token("number", "2"));
        equation1.addToEnd(new Token("operator", "-"));
        equation1.addToEnd(new Token("function", "ln"));
        equation1.addToEnd(new Token("parenthesis", "("));
        equation1.addToEnd(new Token("variable", "x"));
        equation1.addToEnd(new Token("parenthesis", ")"));
        String[] test8 = new String[] { "18", "*", "x", "-", "1", "/", "x" };
        assertTrue(
            Arrays.equals(
                test8,
                toArray(
                    simplifier.simplify(
                        Differentiation.differentiate(equation1, "x")))));

        // multivariable equation
        equation1.clear();
        equation1.addToEnd(new Token("variable", "y"));
        equation1.addToEnd(new Token("operator", "*"));
        equation1.addToEnd(new Token("variable", "x"));
        equation1.addToEnd(new Token("operator", "^"));
        equation1.addToEnd(new Token("number", "2"));
        equation1.addToEnd(new Token("operator", "+"));
        equation1.addToEnd(new Token("variable", "z"));
        equation1.addToEnd(new Token("operator", "*"));
        equation1.addToEnd(new Token("variable", "x"));
        String[] test9 = new String[] { "2", "*", "y", "*", "x", "+", "z" };
        assertTrue(
            Arrays.equals(
                test9,
                toArray(
                    simplifier.simplify(
                        Differentiation.differentiate(equation1, "x")))));

        // product and quotient rules with exponential
        equation1.clear();
        equation1.addToEnd(new Token("variable", "x"));
        equation1.addToEnd(new Token("operator", "*"));
        equation1.addToEnd(new Token("function", "exp"));
        equation1.addToEnd(new Token("parenthesis", "("));
        equation1.addToEnd(new Token("variable", "x"));
        equation1.addToEnd(new Token("parenthesis", ")"));
        equation1.addToEnd(new Token("operator", "-"));
        equation1.addToEnd(new Token("variable", "x"));
        equation1.addToEnd(new Token("operator", "/"));
        equation1.addToEnd(new Token("variable", "y"));
        String[] test10 = new String[] { "exp", "(", "x", ")", "+", "x", "*",
            "exp", "(", "x", ")", "-", "1", "/", "y" };
        assertTrue(
            Arrays.equals(
                test10,
                toArray(
                    simplifier.simplify(
                        Differentiation.differentiate(equation1, "x")))));
    }


    /**
     * Converts a chain of Tokens into an array of their values. Added so tests
     * can compare a chain to an expected String array.
     *
     * @param chain
     *            the chain to convert
     * @return the values of the chain's Tokens, in order
     */
    private String[] toArray(LinkedChain<Token> chain)
    {
        Token[] tokens = chain.toArray(new Token[chain.size()]);
        String[] values = new String[tokens.length];
        for (int i = 0; i < tokens.length; i++)
        {
            values[i] = tokens[i].getValue();
        }
        return values;
    }


    /**
     * Helper method to convert a String array into a LinkedChain of Tokens
     * 
     * @param array
     *            - array of String values to be converted
     * @return - LinkedChain of Tokens with the array values
     */
    public static LinkedChain<Token> convertToLinkedChain(String[] array)
    {
        LinkedChain<Token> chain = new LinkedChain<Token>();
        for (String val : array)
        {
            if (val != null)
            {
                String kind = "";
                if (val.equals("+") || val.equals("-") || val.equals("*")
                    || val.equals("/") || val.equals("^"))
                {
                    kind = "operator";
                }
                else if (val.equals("(") || val.equals(")"))
                {
                    kind = "parenthesis";
                }
                else if (val.equals("sin") || val.equals("cos")
                    || val.equals("tan") || val.equals("ln")
                    || val.equals("log") || val.equals("exp"))
                {
                    kind = "function";
                }
                else if (Character.isDigit(val.charAt(0)))
                {
                    kind = "number";
                }
                else
                {
                    kind = "variable";
                }
                chain.addToEnd(new Token(kind, val));
            }
        }
        return chain;
    }

}
