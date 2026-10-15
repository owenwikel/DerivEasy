
import student.TestCase;

/**
 * Test class for Simplification.
 *
 * @author Ehren Casto (ecasto221)
 * @version 2026.09.01
 */
public class SimplificationTest extends TestCase
{
    private Simplification   simp;
    private LinkedChain<Token> chain;

    public void setUp(){
        simp  = new Simplification();
        chain = new LinkedChain<>();
    }
        
    private void addTokens(Token... tokens){
        for (int i = tokens.length - 1; i >= 0; i--)
        {
            chain.add(tokens[i]);
        }
    }
 
    // Tests for simplify()
 
    public void testSimplifyEmptyAndTrivialExponent() {
        // empty chain
        assertTrue(simp.simplify(chain).isEmpty());
 
        // no change
        addTokens(new Token("number",   "3"), new Token("operator", "*"), 
                  new Token("variable", "x"));
        assertEquals("3*x", simp.format(simp.simplify(chain)));
 
        // ^1 removal
        chain = new LinkedChain<>();
        addTokens(new Token("number",   "2"), new Token("operator", "*"),
            new Token("variable", "x"), new Token("operator", "^"),
            new Token("number",   "1"));
        assertEquals("2*x", simp.format(simp.simplify(chain)));
    }
 
    /**
     * Covers: *1 removal, 1* removal, 0* removal, and lone zero removal.
     * x*1 → x.
     * 1*x → x.
     * 0*x → "0" (empty chain).
     * lone 0 → "0" (empty chain, no adjacent *).
     */
    public void testSimplifyTrivialCoefficientsAndZero(){
        // *1
        addTokens(new Token("variable", "x"), new Token("operator", "*"),
            new Token("number",   "1"));
        assertEquals("x", simp.format(simp.simplify(chain)));
 
        // 1*
        chain = new LinkedChain<>();
        addTokens(new Token("number",   "1"), new Token("operator", "*"),
            new Token("variable", "x"));
        assertEquals("x", simp.format(simp.simplify(chain)));
 
        // 0*x
        chain = new LinkedChain<>();
        addTokens(new Token("number",   "0"), new Token("operator", "*"),
            new Token("variable", "x"));
        assertEquals("0", simp.format(simp.simplify(chain)));
 
        // lone zero — no adjacent *
        chain = new LinkedChain<>();
        chain.add(new Token("number", "0"));
        assertEquals("0", simp.format(simp.simplify(chain)));
    }
 
    /**
     * Covers: +0 removal, -0 removal, like-term addition,
     * and like-term subtraction.
     * 3*x + 0 → 3*x.
     * 3*x - 0 → 3*x.
     * 3*x + 5*x → 8*x.
     * 7*x - 2*x → 5*x.
     */
    public void testSimplifyZeroTermsAndLikeTerms(){
        // +0
        addTokens(new Token("number",   "3"), new Token("operator", "*"),
            new Token("variable", "x"), new Token("operator", "+"),
            new Token("number",   "0"));
        assertEquals("3*x", simp.format(simp.simplify(chain)));
 
        // -0
        chain = new LinkedChain<>();
        addTokens(new Token("number",   "3"), new Token("operator", "*"),
            new Token("variable", "x"), new Token("operator", "-"),
            new Token("number",   "0"));
        assertEquals("3*x", simp.format(simp.simplify(chain)));
 
        // like terms addition
        chain = new LinkedChain<>();
        addTokens(new Token("number",   "3"), new Token("operator", "*"),
            new Token("variable", "x"), new Token("operator", "+"),
            new Token("number",   "5"), new Token("operator", "*"),
            new Token("variable", "x"));
        assertEquals("8*x", simp.format(simp.simplify(chain)));
 
        // like terms subtraction
        chain = new LinkedChain<>();
        addTokens(new Token("number",   "7"), new Token("operator", "*"),
            new Token("variable", "x"), new Token("operator", "-"),
            new Token("number",   "2"), new Token("operator", "*"),
            new Token("variable", "x"));
        assertEquals("5*x", simp.format(simp.simplify(chain)));
    }

    /**
     * Covers: unlike terms not combined, non-additive connector skipped,
     * multiple rules firing together, and bad coefficient no exception.
     * 3*x + 5*y → unchanged.
     * 3*x * 5*x → not combined (connector is *).
     * 1*x^1 + 0 → x (multiple rules).
     * bad*x + 2*x → no exception thrown (parseDouble catch block).
     */
    public void testSimplifyEdgeCases()
    {
        // unlike terms not combined
        addTokens( new Token("number",   "3"), new Token("operator", "*"),
            new Token("variable", "x"), new Token("operator", "+"),
            new Token("number",   "5"), new Token("operator", "*"),
            new Token("variable", "y"));
        assertEquals("3*x + 5*y", simp.format(simp.simplify(chain)));
 
        // non-additive connector — no combine
        chain = new LinkedChain<>();
        addTokens(new Token("number",   "3"), new Token("operator", "*"),
            new Token("variable", "x"), new Token("operator", "*"),
            new Token("number",   "5"), new Token("operator", "*"),
            new Token("variable", "x"));
        assertFalse(simp.simplify(chain).isEmpty());
 
        // multiple rules: 1*x^1 + 0 → x
        chain = new LinkedChain<>();
        addTokens( new Token("number",   "1"), new Token("operator", "*"),
            new Token("variable", "x"), new Token("operator", "^"),
            new Token("number",   "1"), new Token("operator", "+"),
            new Token("number",   "0"));
        assertEquals("x", simp.format(simp.simplify(chain)));
 
        // bad coefficient — parseDouble catch block, no exception
        chain = new LinkedChain<>();
        addTokens(
            new Token("number",   "bad"), new Token("operator", "*"),
            new Token("variable", "x"), new Token("operator", "+"),
            new Token("number",   "2"), new Token("operator", "*"),
            new Token("variable", "x"));
        Exception thrown = null;
        LinkedChain<Token> result = null;
        try
        {
            result = simp.simplify(chain);
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertNull(thrown);
        assertNotNull(result);
    }
 
    // Tests for format()
 
    /**
     * Covers: empty chain returns "0" and single token formats correctly.
     */
    public void testFormatEmptyAndSingle(){
        // empty chain → "0"
        assertEquals("0", simp.format(chain));
 
        // single number token
        chain.add(new Token("number", "5"));
        assertEquals("5", simp.format(chain));
    }
 
    /**
     * Covers: "+" spacing branch and "-" spacing branch.
     * 3*x + 2 → "3*x + 2".
     * 3*x - 2 → "3*x - 2".
     */
    public void testFormatAdditiveOperatorSpacing(){
        // plus
        addTokens(new Token("number",   "3"), new Token("operator", "*"),
            new Token("variable", "x"), new Token("operator", "+"),
            new Token("number",   "2"));
        assertEquals("3*x + 2", simp.format(chain));
 
        // minus
        chain = new LinkedChain<>();
        addTokens(new Token("number",   "3"), new Token("operator", "*"),
            new Token("variable", "x"), new Token("operator", "-"),
            new Token("number",   "2"));
        assertEquals("3*x - 2", simp.format(chain));
    }
 
    /**
     * Covers: else branch for non-additive operators (* and ^)
     * which receive no surrounding spaces.
     * 2*x^3 → "2*x^3".
     */
    public void testFormatNoSpacesAroundMultiplyAndPower(){
        addTokens(new Token("number",   "2"), new Token("operator", "*"),
            new Token("variable", "x"), new Token("operator", "^"),
            new Token("number",   "3"));
        assertEquals("2*x^3", simp.format(chain));
    }
 
    /**
     * Covers: full multi-term polynomial end-to-end, exercising both
     * spacing branches together in one chain.
     * 6*x^2 + 4*x - 1 → "6*x^2 + 4*x - 1".
     */
    public void testFormatMultiTermPolynomial(){
        addTokens(
            new Token("number",   "6"), new Token("operator", "*"),
            new Token("variable", "x"), new Token("operator", "^"),
            new Token("number",   "2"), new Token("operator", "+"),
            new Token("number",   "4"), new Token("operator", "*"),
            new Token("variable", "x"), new Token("operator", "-"),
            new Token("number",   "1"));
        assertEquals("6*x^2 + 4*x - 1", simp.format(chain));
    }

}