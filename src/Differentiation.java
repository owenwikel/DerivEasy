import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * Differentiates a user-inputted equation with respect to a general target
 * variable.
 * 
 * @author Ari Bajaj
 * @version Oct 21, 2026
 */
public class Differentiation
{
    /**
     * Differentiates an equation in a LinkedChain structure with respect to a
     * target variable.
     * 
     * @param equation
     *            - a LinkedChain of Nodes that make up an equation
     * @param targetVar
     *            - the variable to differentiate with respect to (e.g., "x",
     *            "y", "t")
     * @return - the differentiated LinkedChain
     */
    public static
        LinkedChain<Token>
        differentiate(LinkedChain<Token> equation, String targetVar)
    {
        Token[] tokens = equation.toArray(new Token[equation.size()]);
        String[] eq = new String[tokens.length];
        for (int n = 0; n < tokens.length; n++)
        {
            eq[n] = tokens[n].getValue();
        }

        List<String> diffEqList = new ArrayList<>();
        int i = 0;

        while (i < eq.length)
        {
            int start = i;
            int end = i;
            int parenDepth = 0;

            // separate terms based on '+' and '-' operators
            while (end < eq.length)
            {
                if (eq[end].equals("("))
                {
                    parenDepth++;
                }
                else if (eq[end].equals(")"))
                {
                    parenDepth--;
                }
                else if (parenDepth == 0
                    && (eq[end].equals("+") || eq[end].equals("-")))
                {
                    break;
                }
                end++;
            }

            String[] term = Arrays.copyOfRange(eq, start, end);
            String[] result = differentiateTerm(term, targetVar); // Pass target
                                                                  // variable
                                                                  // down

            for (String token : result)
            {
                if (token != null)
                    diffEqList.add(token);
            }

            if (end < eq.length)
            {
                diffEqList.add(eq[end]); // add the '+' or '-'
                i = end + 1;
            }
            else
            {
                i = end;
            }
        }

        // reconstruct into LinkedChain
        LinkedChain<Token> differentiated = new LinkedChain<Token>();
        for (String val : diffEqList)
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
            else if (val.equals("sin") || val.equals("cos") || val.equals("tan")
                || val.equals("ln") || val.equals("log") || val.equals("exp"))
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
            differentiated.addToEnd(new Token(kind, val));
        }
        return differentiated;
    }


    /**
     * Differentiates a single term using product, quotient, chain, power, trig,
     * exponential, or logarithmic rules with respect to a target variable.
     * 
     * @param term
     *            - a String array of a term in the equation
     * @param targetVar
     *            - the variable to differentiate with respect to
     * @return - the differentiated term
     */
    public static String[] differentiateTerm(String[] term, String targetVar)
    {
        if (term.length == 0)
        {
            return new String[0];
        }
        //  c*x
        if (term.length == 3 && Character.isDigit(term[0].charAt(0))
            && term[1].equals("*") && term[2].equals(targetVar))
        {
            return new String[] { term[0] };
        }

        //  x^n
        if (term.length == 3 && term[0].equals(targetVar)
            && term[1].equals("^") && Character.isDigit(term[2].charAt(0)))
        {
            int exp = Integer.parseInt(term[2]);
            if (exp - 1 > 1)
            {
                return new String[] { String.valueOf(exp), "*", targetVar,
                    "^", String.valueOf(exp - 1) };
            }
            return new String[] { String.valueOf(exp), "*", targetVar };
        }

        //  c*x^n
        if (term.length == 5 && Character.isDigit(term[0].charAt(0))
            && term[1].equals("*") && term[2].equals(targetVar)
            && term[3].equals("^") && Character.isDigit(term[4].charAt(0)))
        {
            int exp = Integer.parseInt(term[4]);
            String coef = String.valueOf(Integer.parseInt(term[0]) * exp);
            if (exp - 1 > 1)
            {
                return new String[] { coef, "*", targetVar, "^",
                    String.valueOf(exp - 1) };
            }
            return new String[] { coef, "*", targetVar };
        }
        // strip the outer parentheses of a term
        if (term[0].equals("(") && term[term.length - 1].equals(")"))
        {
            int depth = 0;
            boolean purelyWrapped = true;
            for (int i = 0; i < term.length - 1; i++)
            {
                if (term[i].equals("("))
                    depth++;
                else if (term[i].equals(")"))
                    depth--;
                if (depth == 0 && i > 0)
                {
                    purelyWrapped = false;
                    break;
                }
            }
            if (purelyWrapped)
            {
                return differentiateTerm(
                    Arrays.copyOfRange(term, 1, term.length - 1),
                    targetVar);
            }
        }

        // fixes the inside of (3*x+2*x)
        int sumDepth = 0;
        for (int k = term.length - 1; k > 0; k--)
        {
            if (term[k].equals(")"))
                sumDepth++;
            else if (term[k].equals("("))
                sumDepth--;
            else if (sumDepth == 0
                && (term[k].equals("+") || term[k].equals("-")))
            {
                String[] left = Arrays.copyOfRange(term, 0, k);
                String[] right = Arrays.copyOfRange(term, k + 1, term.length);
                List<String> sumResult = new ArrayList<>();
                sumResult.addAll(
                    Arrays.asList(differentiateTerm(left, targetVar)));
                sumResult.add(term[k]);
                if (term[k].equals("-"))
                    sumResult.add("(");
                sumResult.addAll(
                    Arrays.asList(differentiateTerm(right, targetVar)));
                if (term[k].equals("-"))
                    sumResult.add(")");
                return sumResult.toArray(new String[0]);
            }
        }
        // scan backwards for important operations
        int parenDepth = 0;
        int multiplyIndex = -1;
        int divideIndex = -1;
        int powerIndex = -1;

        for (int i = term.length - 1; i >= 0; i--)
        {
            if (term[i].equals(")"))
                parenDepth++;
            else if (term[i].equals("("))
                parenDepth--;
            else if (parenDepth == 0)
            {
                if (term[i].equals("*") && multiplyIndex == -1)
                    multiplyIndex = i;
                else if (term[i].equals("/") && divideIndex == -1)
                    divideIndex = i;
                else if (term[i].equals("^") && powerIndex == -1)
                    powerIndex = i;
            }
        }

        // quotient rule
        if (divideIndex != -1)
        {
            String[] f = Arrays.copyOfRange(term, 0, divideIndex);
            String[] g = Arrays.copyOfRange(term, divideIndex + 1, term.length);
            String[] fPrime = differentiateTerm(f, targetVar);
            String[] gPrime = differentiateTerm(g, targetVar);

            List<String> result = new ArrayList<>();
            result.add("(");
            result.add("(");
            result.addAll(Arrays.asList(fPrime));
            result.add(")");
            result.add("*");
            result.add("(");
            result.addAll(Arrays.asList(g));
            result.add(")");
            result.add("-");
            result.add("(");
            result.addAll(Arrays.asList(f));
            result.add(")");
            result.add("*");
            result.add("(");
            result.addAll(Arrays.asList(gPrime));
            result.add(")");
            result.add(")");
            result.add("/");
            result.add("(");
            result.addAll(Arrays.asList(g));
            result.add(")");
            result.add("^");
            result.add("2");
            return result.toArray(new String[0]);
        }

        // product rule
        if (multiplyIndex != -1)
        {
            String[] f = Arrays.copyOfRange(term, 0, multiplyIndex);
            String[] g =
                Arrays.copyOfRange(term, multiplyIndex + 1, term.length);
            String[] fPrime = differentiateTerm(f, targetVar);
            String[] gPrime = differentiateTerm(g, targetVar);

            List<String> result = new ArrayList<>();
            result.add("(");
            result.addAll(Arrays.asList(fPrime));
            result.add(")");
            result.add("*");
            result.add("(");
            result.addAll(Arrays.asList(g));
            result.add(")");
            result.add("+");
            result.add("(");
            result.addAll(Arrays.asList(f));
            result.add(")");
            result.add("*");
            result.add("(");
            result.addAll(Arrays.asList(gPrime));
            result.add(")");
            return result.toArray(new String[0]);
        }

        // chain rule/power rule
        if (powerIndex != -1)
        {
            String[] u = Arrays.copyOfRange(term, 0, powerIndex);
            String exponentStr = term[powerIndex + 1];
            int n = Integer.parseInt(exponentStr);
            String[] uPrime = differentiateTerm(u, targetVar);

            List<String> result = new ArrayList<>();
            result.add(String.valueOf(n));
            result.add("*");
            result.add("(");
            result.addAll(Arrays.asList(u));
            result.add(")");
            if (n - 1 != 1)
            {
                result.add("^");
                result.add(String.valueOf(n - 1));
            }

            if (!(uPrime.length == 1 && uPrime[0].equals("1")))
            {
                result.add("*");
                result.add("(");
                result.addAll(Arrays.asList(uPrime));
                result.add(")");
            }
            return result.toArray(new String[0]);
        }

        // trig, log, exponentials
        String firstToken = term[0];
        if (firstToken.equals("sin") || firstToken.equals("cos")
            || firstToken.equals("tan") || firstToken.equals("ln")
            || firstToken.equals("exp"))
        {
            String[] u = Arrays.copyOfRange(term, 2, term.length - 1);
            String[] uPrime = differentiateTerm(u, targetVar);
            List<String> result = new ArrayList<>();

            if (firstToken.equals("sin"))
            {
                result.add("cos");
                result.add("(");
                result.addAll(Arrays.asList(u));
                result.add(")");
            }
            else if (firstToken.equals("cos"))
            {
                result.add("-1");
                result.add("*");
                result.add("sin");
                result.add("(");
                result.addAll(Arrays.asList(u));
                result.add(")");
            }
            else if (firstToken.equals("tan"))
            {
                result.add("sec");
                result.add("(");
                result.addAll(Arrays.asList(u));
                result.add(")");
                result.add("^");
                result.add("2");
            }
            else if (firstToken.equals("ln"))
            {
                result.add("1");
                result.add("/");
                result.add("(");
                result.addAll(Arrays.asList(u));
                result.add(")");
            }
            else if (firstToken.equals("exp"))
            {
                result.add("exp");
                result.add("(");
                result.addAll(Arrays.asList(u));
                result.add(")");
            }

            if (!(uPrime.length == 1 && uPrime[0].equals("1")))
            {
                result.add("*");
                result.add("(");
                result.addAll(Arrays.asList(uPrime));
                result.add(")");
            }
            return result.toArray(new String[0]);
        }

        // constants and base variables
        if (term.length == 1)
        {
            if (term[0].equals(targetVar))
            {
                return new String[] { "1" };
            }
            return new String[] { "0" };
        }
        return term;
    }
}
