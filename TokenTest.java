import junit.framework.TestCase;

/**
 * Tests for the Token class.
 *
 * @author Owen Wikel
 * @version 2026.10.13
 */
public class TokenTest extends TestCase {

    /**
     * Constructor stores kind and value.
     */
    public void testConstructorNormal() {
        Token token = new Token("variable", "x");
        String value = token.getValue();
        assertEquals("x", value);
    }

    /**
     * Constructor rejects a null kind.
     */
    public void testConstructorNullKind() {
        Exception exception = null;
        try {
            new Token(null, "x");
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    /**
     * Constructor rejects a null value.
     */
    public void testConstructorNullValue() {
        Exception exception = null;
        try {
            new Token("variable", null);
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    /**
     * getKind returns the kind.
     */
    public void testGetKind() {
        Token token = new Token("variable", "x");
        String kind = token.getKind();
        assertEquals("variable", kind);
    }

    /**
     * getValue returns the value.
     */
    public void testGetValue() {
        Token token = new Token("variable", "x");
        String value = token.getValue();
        assertEquals("x", value);
    }

    /**
     * equals is true for same kind and value.
     */
    public void testEqualsSame() {
        Token first = new Token("variable", "x");
        Token second = new Token("variable", "x");
        boolean result = first.equals(second);
        assertTrue(result);
    }

    /**
     * equals is false for a different value.
     */
    public void testEqualsDifferentValue() {
        Token first = new Token("variable", "x");
        Token second = new Token("variable", "y");
        boolean result = first.equals(second);
        assertFalse(result);
    }

    /**
     * equals is false for a different kind.
     */
    public void testEqualsDifferentKind() {
        Token first = new Token("variable", "x");
        Token second = new Token("number", "x");
        boolean result = first.equals(second);
        assertFalse(result);
    }

    /**
     * equals is false for null.
     */
    public void testEqualsNull() {
        Token token = new Token("variable", "x");
        assertFalse(token.equals(null));
    }

    /**
     * equals is false for a different type.
     */
    public void testEqualsOtherType() {
        Token token = new Token("variable", "x");
        String other = "x";
        assertFalse(token.equals(other));
    }

    /**
     * toString returns the value.
     */
    public void testToString() {
        Token token = new Token("variable", "x");
        String text = token.toString();
        assertEquals("x", text);
    }
}