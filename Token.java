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
    /**
     * Creates a Token with the given kind and value.
     *
     * @param kind the kind of piece
     * @param value the piece itself
     * @throws IllegalArgumentException if kind or value is null
     */
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
    Sets the kind of value. O(1)
    * @param the new kind of value.
    * @throws IllegalArgumentException if kind is null
    */
    public void setKind(String kind) throws IllegalArgumentException {
        if (kind == null) {
            throw new IllegalArgumentException();
        }
        this.kind = kind;
    }
    
    /**
     * returns value. O(1)
     * @return the value
     */
    public String getValue() {
        return value;
    }
    
    /**
     Sets the value. O(1)
     * @param the new value.
     * @throws IllegalArgumentException if value is null
     */
    public void setValue(String value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        this.value = value;
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
