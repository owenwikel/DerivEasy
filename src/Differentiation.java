

import java.util.Arrays;

// -------------------------------------------------------------------------
/**
 * Differentiates a user-inputted equation
 * 
 * @author Ari Bajaj
 * @version Oct 14, 2026
 */
public class Differentiation
{
    // ----------------------------------------------------------
    /**
     * Differentiates an equation in a LinkedChain structure
     * 
     * @param equation
     *            - a LinkedChain of Nodes that make up an equation
     * @return - the differentiated LinkedChain
     */
    public static LinkedChain<Token> differentiate(LinkedChain<Token> equation)
    {
        // Added this
        Token[] tokens = equation.toArray(new Token[equation.size()]);
        String[] eq = new String[tokens.length];
        for (int n = 0; n < tokens.length; n++)
        {
            eq[n] = tokens[n].getValue();
        }
        //
        String[] diffEq = new String[eq.length * 2];
        LinkedChain<Token> differentiated = new LinkedChain<Token>();
        int i = 0;
        int finalIndex = 0;
        while (i < eq.length)
        {
            int start = i;
            int end = i;
            while (end < eq.length && !eq[end].equals("+")
                && !eq[end].equals("-"))
            {
                end++;
            }
            String[] term = new String[end - start];
            int termLength = 0;
            for (int j = start; j < end; j++)
            {
                term[termLength] = eq[j];
                termLength++;
            }
            String[] result = differentiateTerm(term);
            for (int k = 0; k < result.length; k++)
            {
                diffEq[finalIndex] = result[k];
                finalIndex++;
            }
            if (end < eq.length)
            {
                diffEq[finalIndex] = eq[end];
                finalIndex++;
                i = end + 1;
            }
            else
            {
                i = end;
            }
        }
        for (int l = 0; l < diffEq.length; l++)
        {
            if (diffEq[l] != null)
            {
                String kind = "";
                if (diffEq[l].equals("+") || diffEq[l].equals("-")
                    || diffEq[l].equals("*")||diffEq[l].equals("^"))
                {
                    kind = "operator";
                }
                else if (Character.isDigit(diffEq[l].charAt(0)))
                {
                    kind = "number";
                }
                else
                {
                    kind = "variable";
                }
                differentiated.addToEnd(new Token(kind, diffEq[l]));
            }
        }
        return differentiated;
    }


    // ----------------------------------------------------------
    /**
     * Differentiates a term
     * 
     * @param term
     *            - a String array of a term in the equation
     * @return - the differentiated term
     */
    public static String[] differentiateTerm(String[] termInput)
    {
        String[] term = termInput.clone();
        int termLength = term.length;
        if (termLength == 1)
        {
            if (Character.isDigit(term[0].charAt(0)))
            {
                term[0] = "0";
            }
            else
            {
                term[0] = "1";
            }
        }
        else if (termLength == 3)
        {
            if (term[1].equals("*"))
            {
                term = Arrays.copyOf(term, 1);
            }
            else
            {
                int exp = Integer.parseInt(term[2]);
                String var = term[0];
                term[0] = String.valueOf(exp);
                term[1] = "*";
                term[2] = var;
                if (exp - 1 > 1)
                {
                    term = Arrays.copyOf(term, 5);
                    term[3] = "^";
                    term[4] = String.valueOf(exp - 1);
                }
            }
        }
        else
        {
            int exp = Integer.parseInt(term[4]);
            int newCoef = Integer.parseInt(term[0]) * exp;
            term[0] = String.valueOf(newCoef);
            if (exp - 1 > 1)
            {
                term[4] = String.valueOf(exp - 1);
            }
            else
            {
                term = Arrays.copyOf(term, 3);
            }
        }
        return term;
    }

}
