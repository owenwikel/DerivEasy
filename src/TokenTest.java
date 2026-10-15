import junit.framework.TestCase;

/**
 * Tests for the Token class.
 *
 * @author Owen Wikel
 * @version 2026.10.13
 */
public class TokenTest extends TestCase {

    /**
     * Checks that constructor stores kind and value.
     */
    public void testConstructorNormal() {
        Token token = new Token("variable", "x");
        String value = token.getValue();
        assertEquals("x", value);
    }

    /**
     * Checks that constructor rejects a null kind.
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
     * Makes sure that constructor rejects a null value.
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
     * Checks that getKind returns the kind.
     */
    public void testGetKind() {
        Token token = new Token("variable", "x");
        String kind = token.getKind();
        assertEquals("variable", kind);
    }

    /**
     * tests that getValue returns the value.
     */
    public void testGetValue() {
        Token token = new Token("variable", "x");
        String value = token.getValue();
        assertEquals("x", value);
    }

    /**
     * Checks equals is true for same kind and value.
     */
    public void testEqualsSame() {
        Token first = new Token("variable", "x");
        Token second = new Token("variable", "x");
        boolean result = first.equals(second);
        assertTrue(result);
    }

    /**
     * Makes sure equals is false for a different value.
     */
    public void testEqualsDifferentValue() {
        Token first = new Token("variable", "x");
        Token second = new Token("variable", "y");
        boolean result = first.equals(second);
        assertFalse(result);
    }

    /**
     * Checks equals is false for a different kind.
     */
    public void testEqualsDifferentKind() {
        Token first = new Token("variable", "x");
        Token second = new Token("number", "x");
        boolean result = first.equals(second);
        assertFalse(result);
    }

    /**
     * Checks equals is false for null.
     */
    public void testEqualsNull() {
        Token token = new Token("variable", "x");
        assertFalse(token.equals(null));
    }

    /**
     * Tests that equals is false for a different type.
     */
    public void testEqualsOtherType() {
        Token token = new Token("variable", "x");
        String other = "x";
        assertFalse(token.equals(other));
    }

    /**
     * Tests that toString returns the value.
     */
    public void testToString() {
        Token token = new Token("variable", "x");
        String text = token.toString();
        assertEquals("x", text);
    }
    
    /**
     * Tests that setKind changes the kind.
     */
    public void testSetKind() {
        Token token = new Token("variable", "x");
        token.setKind("number");
        String kind = token.getKind();
        assertEquals("number", kind);
    }

    /**
     * Assures setKind rejects null.
     */
    public void testSetKindNull() {
        Token token = new Token("variable", "x");
        Exception exception = null;
        try {
            token.setKind(null);
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    /**
     * Checks setValue changes the value.
     */
    public void testSetValue() {
        Token token = new Token("variable", "x");
        token.setValue("y");
        String value = token.getValue();
        assertEquals("y", value);
    }

    /**
     * Makes sure setValue rejects null.
     */
    public void testSetValueNull() {
        Token token = new Token("variable", "x");
        Exception exception = null;
        try {
            token.setValue(null);
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
    }
}