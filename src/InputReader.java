/**
 * Reads, validates, and turns users input equation into tokens.
 * 
 * @author Muna Ali
 * @version Oct 14, 2026
 */
public class InputReader
{
    // ~ Fields ................................................................

    // ~ Constructors ..........................................................

    // ~Public Methods ........................................................

    /**
     * Checks whether a character is one of the supported operators: + - * / ^
     *
     * @param c
     *            the character to check
     * @return true if c is an operator, false otherwise
     */
    private boolean isOperator(char c)
    {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }


    /**
     * Checks that parentheses match, not just that they exist. Adds to a
     * LinkedChain<Character> on "(" and removes on ")". Invalid if a ")"
     * appears with nothing open, or if a "(" is left over at the end. An input
     * with no parentheses at all is fine.
     * 
     * @param input
     *            the equation the user typed
     * @return true if all parentheses match, false otherwise (including null)
     */
    public boolean hasParentheses(String input)
    {
        if (input == null)
        {
            return false;
        }

        LinkedChain<Character> open = new LinkedChain<>();
        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if (c == '(')
            {
                open.add('(');
            }
            else if (c == ')')
            {
                if (open.isEmpty())
                {
                    return false;
                }
                open.remove();
            }
        }
        return open.isEmpty();
    }


    /**
     * Checks if input is a valid equation: only digits, x, operators, and
     * parentheses, no missing "*", no dangling operators, matching parentheses.
     * 
     * @param input
     *            the equation the user typed
     * @return true if the input is a valid equation, false otherwise
     */
    public boolean isValid(String input)
    {
        if (input == null || input.isEmpty())
        {
            return false;
        }

        for (int i = 0; i < input.length(); i++)
        {
            char character = input.charAt(i);
            boolean vaild = Character.isDigit(character) || character == 'x'
                || isOperator(character) || character == '('
                || character == ')';
            if (!vaild)
            {
                return false;
            }
        }

        if (!hasParentheses(input))
        {
            return false;
        }

        char firstchar = input.charAt(0);
        char lastchar = input.charAt(input.length() - 1);
        if (isOperator(firstchar) || firstchar == ')')
        {
            return false;
        }
        if (isOperator(lastchar) || lastchar == '(')
        {
            return false;
        }
        for (int i = 0; i < input.length() - 1; i++)
        {
            char a = input.charAt(i);
            char b = input.charAt(i + 1);

            if (isOperator(a) && isOperator(b))
            {
                return false; // e.g. "2+*3"
            }
            if (isOperator(a) && b == ')')
            {
                return false; // e.g. "(2+)"
            }
            if (a == '(' && (isOperator(b) || b == ')'))
            {
                return false; // e.g. "(+2)" or "()"
            }
            // missing "*": 2x, x2, 3(x), (x)(x), x(x), (x)2, xx
            boolean endsOperand = Character.isDigit(a) || a == 'x' || a == ')';
            boolean startsOperand =
                Character.isDigit(b) || b == 'x' || b == '(';
            boolean sameNumber = Character.isDigit(a) && Character.isDigit(b);
            if (endsOperand && startsOperand && !sameNumber)
            {
                return false;
            }
        }
        return true;
    }


    /**
     * Split a valid equation into tokens, if invalid, illegalArgument is
     * thrown..
     * 
     * @param input
     *            the equation the user typed
     * @return a LinkedChain of Tokens in the order the user typed them
     * @throws IllegalArgumentException
     *             if the input is not valid
     */
    public LinkedChain<Token> readEquation(String input)
    {
        if (!isValid(input))
        {
            throw new IllegalArgumentException("Invalid equation: " + input);
        }
        LinkedChain<Token> chain = new LinkedChain<>();
        int i = input.length() - 1;
        while (i >= 0)
        {
            char character = input.charAt(i);
            if (Character.isDigit(character))
            {
                int start = i;
                while (start > 0 && Character.isDigit(input.charAt(start - 1)))
                {
                    start--;
                }
                chain.add(new Token("number", input.substring(start, i + 1)));
                i = start - 1;
            }
            else if (character == 'x')
            {
                chain.add(new Token("variable", "x"));
                i--;
            }
            else if (character == '(' || character == ')')
            {
                chain.add(new Token("parenthesis", String.valueOf(character)));
                i--;
            }
            else
            {
                chain.add(new Token("operator", String.valueOf(character)));
                i--;
            }
        }
        return chain;
    }


    /**
     * Finds the position of a parenthesis that has no match. Uses a
     * LinkedChain<Integer> as a stack of the positions of "(".
     *
     * @param input
     *            the equation the user typed
     * @return -1 if every parenthesis matches; otherwise the index of the first
     *             unmatched ")", or of the last unmatched "("
     * @throws IllegalArgumentException
     *             if input is null
     */
    public int findUnmatchedParenthesis(String input)
    {
        if (input == null)
        {
            throw new IllegalArgumentException("Input must not be null");
        }
        LinkedChain<Integer> open = new LinkedChain<>();
        for (int i = 0; i < input.length(); i++)
        {
            char character = input.charAt(i);
            if (character == '(')
            {
                open.add(i);
            }
            else if (character == ')')
            {
                if (open.isEmpty())
                {
                    return i;
                }
                open.remove();
            }
        }
        if (open.isEmpty())
        {
            return -1;
        }
        return open.remove();
    }

}
