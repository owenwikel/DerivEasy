/**
 * One piece of an equation: a number, variable, operator, or parenthesis.
 * 
 * @author Owen Wikel
 * @version 2026.10.13
 */

public class Token {
    //~ Fields ................................................................
    private String kind;
    private String value;
    //~ Constructors ..........................................................
    public Token(String kind, String value) throws IllegalArgumentException {
        if (kind == null || value == null) {
            throw new IllegalArgumentException();
        }
        this.kind = kind;
        this.value = value;
    }
    //~Public  Methods ........................................................
    /**
     * Returns the kind of value. O(1)
     * @return the kind of value.
     */
    public String getKind() {
        return kind;
    }
    
    /**
     * returns value. O(1)
     * @return the value
     */
    public String getValue() {
        return value;
    }
    
    /**
     * returns true if objects are equal. O(1)
     * Added(was not in spec).
     * 
     * @param obj the object you are checking is equal or not.
     * @return true or false if the objects are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Token t = (Token) obj;
        return t.getKind().equals(this.getKind())
            && t.getValue().equals(this.getValue());
    }
    
    /**
     * Added(was not in spec). O(1)
     * 
     * @return the toString of this object.
     */
    @Override
    public String toString() {
        return "" + value;
    }
    
    
}
