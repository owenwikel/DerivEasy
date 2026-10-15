/**
 * Simplifies a differentiated equation stored as a LinkedChain of Tokens.
 * Handles removal of trivial terms (^1, *1, +0), combination of like terms,
 * and formatting the final chain into a readable String.
 *
 * @author Ehren Casto (ecasto221)
 * @version 2026.09.01
 */
public class Simplification
{
    public LinkedChain<Token> simplify(LinkedChain<Token> derivative){
        Token[] tokens = (Token[]) derivative.toArray(new Token[derivative.size()]);
        int size = tokens.length;

        boolean[] keep = new boolean[size];
        for (int i = 0; i < size; i++) {
            keep[i] = true;
        }

        for (int i = 0; i < size; i++) {
            if (!keep[i]) {
                continue;
            }

            Token curr = tokens[i];

            if (curr.getKind().equals("operator") && curr.getValue().equals("^") && i + 1 < size
                && tokens[i + 1].getKind().equals("number") && tokens[i + 1].getValue().equals("1")) {
                keep[i]     = false;   // drop ^
                keep[i + 1] = false;   // drop 1
            }

            if (curr.getKind().equals("operator") && curr.getValue().equals("*") && i + 1 < size
                && tokens[i + 1].getKind().equals("number") && tokens[i + 1].getValue().equals("1")) {
                keep[i]     = false;
                keep[i + 1] = false;
            }

            if (curr.getKind().equals("number") && curr.getValue().equals("1") && i + 1 < size
                && tokens[i + 1].getKind().equals("operator") && tokens[i + 1].getValue().equals("*")) {
                keep[i]     = false;
                keep[i + 1] = false;
            }

            if (curr.getKind().equals("number") && curr.getValue().equals("0"))
            {
                keep[i] = false;
                if (i + 1 < size && tokens[i + 1].getKind().equals("operator") && tokens[i + 1].getValue().equals("*")) {
                    keep[i + 1] = false;
                }
            }

            if (curr.getKind().equals("operator") && (curr.getValue().equals("+") || curr.getValue().equals("-"))
                && i + 1 < size && tokens[i + 1].getKind().equals("number") && tokens[i + 1].getValue().equals("0")) {
                keep[i]     = false;
                keep[i + 1] = false;
            }
        }

        LinkedChain<Token> intermediate = new LinkedChain<>();
        for (int i = size - 1; i >= 0; i--) {
            if (keep[i]) {
                intermediate.add(tokens[i]);
            }
        }

 

        Token[] mid       = (Token[]) intermediate.toArray(new Token[intermediate.size()]);
        int     midSize   = mid.length;
        boolean[] midKeep = new boolean[midSize];
        for (int i = 0; i < midSize; i++) {
            midKeep[i] = true;
        }

        for (int i = 0; i < midSize; i++) {
            if (!midKeep[i]) {
                continue;
            }

            if (i + 2 < midSize && mid[i].getKind().equals("number") && mid[i + 1].getKind().equals("operator")
                && mid[i + 1].getValue().equals("*") && mid[i + 2].getKind().equals("variable")) {
                String varName = mid[i + 2].getValue();
                double coeff   = parseDouble(mid[i].getValue());

                for (int j = i + 3; j + 2 < midSize; j++) {
                    if (!midKeep[j]) {
                        continue;
                    }

                    if (!mid[j].getKind().equals("operator")) {
                        continue;
                    }
                    String connectOp = mid[j].getValue();
                    if (!connectOp.equals("+") && !connectOp.equals("-")) {
                        continue;
                    }

                    if (j + 3 < midSize && midKeep[j + 1] && mid[j + 1].getKind().equals("number")
                        && midKeep[j + 2] && mid[j + 2].getKind().equals("operator") && mid[j + 2].getValue().equals("*")
                        && midKeep[j + 3] && mid[j + 3].getKind().equals("variable") && mid[j + 3].getValue().equals(varName)) {
                        double nextCoeff = parseDouble(mid[j + 1].getValue());
                        if (connectOp.equals("-")) {
                            nextCoeff = -nextCoeff;
                        }
                        coeff += nextCoeff;

                        midKeep[j]     = false;   // connecting + or -
                        midKeep[j + 1] = false;   // second coefficient
                        midKeep[j + 2] = false;   // second *
                        midKeep[j + 3] = false;   // second variable
                    }
                }

                String combined = formatCoeff(coeff);
                mid[i] = new Token("number", combined);
            }
        }

        LinkedChain<Token> result = new LinkedChain<>();
        for (int i = midSize - 1; i >= 0; i--)
        {
            if (midKeep[i])
            {
                result.add(mid[i]);
            }
        }

        return result;
    }

    public String format(LinkedChain<Token> answer)
    {
        if (answer.isEmpty()){
            return "0";
        }

        Token[]       tokens = (Token[]) answer.toArray(new Token[answer.size()]);
        StringBuilder sb     = new StringBuilder();

        for (int i = 0; i < tokens.length; i++){
            Token curr = tokens[i];

            if (curr.getKind().equals("operator") && (curr.getValue().equals("+") || curr.getValue().equals("-"))){
                sb.append(" ");
                sb.append(curr.getValue());
                sb.append(" ");
            }
            else
            {
                sb.append(curr.getValue());
            }
        }

        return sb.toString().trim();
    }

    private double parseDouble(String value)
    {
        try
        {
            return Double.parseDouble(value);
        }
        catch (NumberFormatException e)
        {
            return 0.0;
        }
    }

    private String formatCoeff(double coeff)
    {
        if (coeff == Math.floor(coeff) && !Double.isInfinite(coeff))
        {
            return String.valueOf((int) coeff);
        }
        return String.valueOf(coeff);
    }
}