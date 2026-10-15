import junit.framework.TestCase;

/**
 * Tests for the LinkedChain class.
 *
 * @author Owen Wikel
 * @version 2026.10.15
 */
public class LinkedChainTest extends TestCase {

    private LinkedChain<String> chain = new LinkedChain<String>();

    /**
     * Checks that add puts the entry at the front.
     */
    public void testAdd() {
        chain.add("a");
        chain.add("b");
        assertEquals("b", chain.get(0));
    }

    /**
     * Checks that add rejects null.
     */
    public void testAddNull() {
        Exception exception = null;
        try {
            chain.add(null);
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    /**
     * Tests that addToEnd keeps insertion order.
     */
    public void testAddToEnd() {
        chain.addToEnd("a");
        chain.addToEnd("b");
        assertEquals("b", chain.get(1));
    }

    /**
     * Assures addToEnd rejects null.
     */
    public void testAddToEndNull() {
        Exception exception = null;
        try {
            chain.addToEnd(null);
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    /**
     * Checks that remove returns the front entry.
     */
    public void testRemoveFront() {
        chain.add("a");
        assertEquals("a", chain.remove());
    }

    /**
     * Makes sure remove returns null on an empty chain.
     */
    public void testRemoveFrontEmpty() {
        assertNull(chain.remove());
    }

    /**
     * Tests that remove(T) removes the first entry.
     */
    public void testRemoveEntryFirst() {
        chain.add("a");
        assertTrue(chain.remove("a"));
    }

    /**
     * Tests that remove(T) removes an entry after the first.
     */
    public void testRemoveEntryMiddle() {
        chain.add("a");
        chain.add("b");
        assertTrue(chain.remove("a"));
    }

    /**
     * Checks that remove(T) is false when the entry is not there.
     */
    public void testRemoveEntryAbsent() {
        chain.add("a");
        chain.add("b");
        assertFalse(chain.remove("z"));
    }

    /**
     * Checks that remove(T) is false for null.
     */
    public void testRemoveEntryNull() {
        chain.add("a");
        assertFalse(chain.remove(null));
    }

    /**
     * Makes sure remove(T) is false on an empty chain.
     */
    public void testRemoveEntryEmpty() {
        assertFalse(chain.remove("a"));
    }

    /**
     * Tests that clear empties the chain.
     */
    public void testClear() {
        chain.add("a");
        chain.clear();
        assertTrue(chain.isEmpty());
    }

    /**
     * Checks that get rejects a negative index.
     */
    public void testGetNegative() {
        Exception exception = null;
        try {
            chain.get(-1);
        }
        catch (IndexOutOfBoundsException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    /**
     * Assures get rejects an index equal to the size.
     */
    public void testGetTooLarge() {
        Exception exception = null;
        try {
            chain.get(0);
        }
        catch (IndexOutOfBoundsException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    /**
     * Checks that contains finds a present entry.
     */
    public void testContainsPresent() {
        chain.add("a");
        assertTrue(chain.contains("a"));
    }

    /**
     * Checks that contains is false on an empty chain.
     */
    public void testContainsAbsent() {
        assertFalse(chain.contains("a"));
    }

    /**
     * Tests that count counts repeated entries.
     */
    public void testCount() {
        chain.add("a");
        chain.add("a");
        chain.add("b");
        assertEquals(2, chain.count("a"));
    }

    /**
     * Makes sure count of null is zero.
     */
    public void testCountNull() {
        assertEquals(0, chain.count(null));
    }

    /**
     * Tests that size counts the entries.
     */
    public void testSize() {
        chain.add("a");
        assertEquals(1, chain.size());
    }

    /**
     * Checks that isEmpty changes after an add.
     */
    public void testIsEmpty() {
        assertTrue(chain.isEmpty());
        chain.add("a");
        assertFalse(chain.isEmpty());
    }

    /**
     * Tests that toArray copies the entries.
     */
    public void testToArray() {
        chain.add("a");
        assertEquals("a", chain.toArray(new String[1])[0]);
    }

    /**
     * Assures toArray rejects a null array.
     */
    public void testToArrayNull() {
        Exception exception = null;
        try {
            chain.toArray(null);
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    /**
     * Makes sure toArray rejects an array that is too small.
     */
    public void testToArrayTooSmall() {
        chain.add("a");
        Exception exception = null;
        try {
            chain.toArray(new String[0]);
        }
        catch (IllegalArgumentException e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    /**
     * Tests that toString lists the entries.
     */
    public void testToString() {
        chain.add("a");
        chain.add("b");
        assertEquals("[b, a]", chain.toString());
    }

    /**
     * Checks that toString of an empty chain is empty brackets.
     */
    public void testToStringEmpty() {
        assertEquals("[]", chain.toString());
    }

    /**
     * Tests that the same class holds a second element type.
     */
    public void testSecondType() {
        LinkedChain<Integer> numbers = new LinkedChain<Integer>();
        numbers.add(7);
        assertEquals(Integer.valueOf(7), numbers.get(0));
    }
}