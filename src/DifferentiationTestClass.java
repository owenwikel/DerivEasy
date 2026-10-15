
import student.TestCase;
import java.util.Arrays;

// -------------------------------------------------------------------------
/**
 * Tests the Differentiation Class
 * 
 * @author Ari Bajaj
 * @version Oct 14, 2026
 */
public class DifferentiationTestClass
    extends TestCase
{
    LinkedChain<Node> equation1;
    LinkedChain<Node> equation2;
    String[] term1;
    String[] term2;

    public void setUp()
    {
        equation1 = new LinkedChain<Node>();
        equation1.add("3");
        equation2 = new LinkedChain<Node>();
        equation2.add("x");
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
        String[] test1 = new String[1];
        test1[0] = "0";
        assertTrue(
            Arrays.equals(test1, Differentiation.differentiateTerm(term1)));

        String[] test2 = new String[1];
        test2[0] = "1";
        assertTrue(
            Arrays.equals(test2, Differentiation.differentiateTerm(term2)));

        term1 = Arrays.copyOf(term1, 3);
        term2 = Arrays.copyOf(term2, 3);
        term1[1] = "*";
        term1[2] = "x";
        term2[1] = "^";
        term2[2] = "2";
        String[] test3 = new String[1];
        String[] test4 = new String[3];
        test3[0] = "3";
        test4[0] = "2";
        test4[1] = "*";
        test4[2] = "x";
        assertTrue(
            Arrays.equals(test3, Differentiation.differentiateTerm(term1)));
        assertTrue(
            Arrays.equals(test4, Differentiation.differentiateTerm(term2)));

        term2[2] = "3";
        String[] test5 = new String[5];
        test5[0] = "3";
        test5[1] = "*";
        test5[2] = "x";
        test5[3] = "^";
        test5[4] = "2";
        assertTrue(
            Arrays.equals(test5, Differentiation.differentiateTerm(term2)));

        term1 = Arrays.copyOf(term1, 5);
        term1[3] = "^";
        term1[4] = "3";
        String[] test6 = new String[5];
        test6[0] = "9";
        test6[1] = "*";
        test6[2] = "x";
        test6[3] = "^";
        test6[4] = "2";
        assertTrue(
            Arrays.equals(test6, Differentiation.differentiateTerm(term1)));

        term1[4] = "2";
        String[] test7 = new String[3];
        test7[0] = "6";
        test7[1] = "*";
        test7[2] = "x";
        assertTrue(
            Arrays.equals(test7, Differentiation.differentiateTerm(term1)));
    }


    // ----------------------------------------------------------
    /**
     * Tests the differentiate() method
     */
    public void testDifferentiate()
    {
        String[] test1 = new String[1];
        test1[0] = "0";
        assertTrue(
            Arrays.equals(
                test1,
                toArray(Differentiation.differentiate(equation1))));

        String[] test2 = new String[1];
        test2[0] = "1";
        assertTrue(
            Arrays.equals(
                test2,
                toArray(Differentiation.differentiate(equation2))));

        equation1.addToEnd("*");
        equation1.addToEnd("x");
        equation2.addToEnd("^");
        equation2.addToEnd("2");
        String[] test3 = new String[1];
        String[] test4 = new String[3];
        test3[0] = "3";
        test4[0] = "2";
        test4[1] = "*";
        test4[2] = "x";
        assertTrue(
            Arrays.equals(
                test3,
                toArray(Differentiation.differentiate(equation1))));
        assertTrue(
            Arrays.equals(
                test4,
                toArray(Differentiation.differentiate(equation2))));

        equation2.remove("2");
        equation2.addToEnd("3");
        String[] test5 = new String[5];
        test5[0] = "3";
        test5[1] = "*";
        test5[2] = "x";
        test5[3] = "^";
        test5[4] = "2";
        assertTrue(
            Arrays.equals(
                test5,
                toArray(Differentiation.differentiate(equation2))));

        equation1.clear();
        equation1.addToEnd("3");
        equation1.addToEnd("*");
        equation1.addToEnd("x");
        equation1.addToEnd("^");
        equation1.addToEnd("3");
        String[] test6 = new String[5];
        test6[0] = "9";
        test6[1] = "*";
        test6[2] = "x";
        test6[3] = "^";
        test6[4] = "2";
        assertTrue(
            Arrays.equals(
                test6,
                toArray(Differentiation.differentiate(equation1))));

        equation1.clear();
        equation1.addToEnd("3");
        equation1.addToEnd("*");
        equation1.addToEnd("x");
        equation1.addToEnd("^");
        equation1.addToEnd("2");
        String[] test7 = new String[3];
        test7[0] = "6";
        test7[1] = "*";
        test7[2] = "x";
        assertTrue(
            Arrays.equals(
                test7,
                toArray(Differentiation.differentiate(equation1))));
    }
}
