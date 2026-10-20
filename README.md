# DerivEasy
A console-based derivative calculator for Calculus students that simplifies derivatives using power, quotient, and chain rules.

## How to Compile and Run
1. Put all `.java` files in one project (default package) in Eclipse.
2. Add the JUnit 3 library: right-click the project, Build Path, Add Libraries, JUnit, version 3.
3. Run the program: right-click `Main.java`, Run As, Java Application.
4. Run the tests: right-click a test file (or the project), Run As, JUnit Test.

## Reflection

**What changed from our specification?**
- Token: we added `equals()`, `toString()`, `setKind()`, and `setValue()`. The null check now throws IllegalArgumentException (spec said NullPointerException) in the constructor and setters, matching the provided LinkedChain. (Origin: GenAI suggestion, adopted by group.)
- Main: the spec listed only `run()`. We added a no-argument constructor, a Scanner constructor so tests can feed fake input, and a `main` method. The class is named Main, not Calculator. (Origin: GenAI suggestion.)
- Validation: `isValid` only returns a boolean, and `run()` does the re-prompting. `Differentiation` throws ArithmeticException and `run()` catches it. (Origin: group discussion.)
- Dropped the chain interface and the separate Node class, because staff provided LinkedChain.java. (Origin: group discussion.)
- InputReader: `isValid` also rejects unsupported characters, unmatched or empty parentheses, a leading or trailing operator, two operators in a row, and a missing `*`. `readEquation` throws IllegalArgumentException on invalid input, and Main does the re-prompting. `hasParentheses` and `findUnmatchedParenthesis` were added and were not in the spec. 
- Simplification: removes `^1`, `*1`, `1*`, and `+0`/`-0`, and combines like terms of the form `c*x`. `format` was added to this class, as our revised scope said. 
- Differentiation: Token kinds were changed to lowercase so they match every other class. (Origin: Owen's own review.)
-  Main: `run()` prints "This equation is not supported yet." and re-prompts when Differentiation, Simplification, or format cannot handle valid input, and uses `findUnmatchedParenthesis` to report which parenthesis has no match. 

**What changed in the LinkedChain?**

Used unchanged. The same generic class holds Token (`readEquation`, `Differentiation`, `Simplification`, Main) and Character (`hasParentheses`, a stack of open parentheses called by `isValid`). It also holds Integer in `findUnmatchedParenthesis` (a stack of "(" positions), which is tested but not yet called by Main.

 **What happened to our unknowns?**

We figured out how to separate inputs and group terms together, how to simplify the final answer, and how to divide up the work.

 **What doesn't work yet?**

The MVP features that don't work yet are the quotient, product, and chain rule.

Tests: All current JUnit tests pass. The new rules (product, quotient, chain) have no tests yet.
Bad input: The bad-input cases from our scope (unsupported characters, unmatched or empty parentheses, leading/trailing operators, two operators in a row, missing *) are caught in InputReader, and Main re-prompts. Bad input involving the unfinished rules is not handled yet.

 ## Next Steps

 **Finish the MVP, in order:**
- Separating terms with parentheses
- Product rule
- Quotient rule
- Chain rule

 **Stretch goals:**
- Show which rule was used: committed
- Differentiating multivariable polynomials: committed
- Adding trig, log, and exponential derivatives: committed

**New tests need:**
- Methods and logic for the new rules will be needed, as well as test cases for all possible situations


**Who does what:**
- Owen - Main changes, plus tests
- Ari - Chain, product, and quotient rule
- Ehren - Trig, log, exponential, and multivariable polynomials
- Muna - Input reader allowing more difficult equations 

## Attribution


| Class/Test file | Who did it | How they did it |
| --- | --- | --- |
| Differentiation | Ari<br>Owen| wrote, revised, reviewed, tested<br>reviewed, revised, tested |
| DifferentiationTest | Ari<br>Owen | wrote, revised, reviewed, tested<br>reviewed, revised, tested |
| InputReader | Muna | wrote, revised, reviewed, tested |
| InputReaderTest | Muna | wrote, revised, reviewed, tested |
| LinkedChain | Provided by staff | N/A |
| LinkedChainTest | Owen | wrote, revised, reviewed, tested |
| Main | Owen | wrote, revised, reviewed, tested |
| MainTest | Owen | wrote, revised, reviewed, tested |
| Simplification | Ehren | wrote, revised, reviewed, tested |
| SimplificationTest | Ehren | wrote, reviseed, reviewed, tested |
| Token | Owen | wrote, revised, reviewed, tested |
| TokenTest | Owen | wrote, revised, reviewed, tested |

**GenAI note:** Owen used Claude as a tutor for the Token class idea, `equals`/`toString`,  Main's constructors, the ByteArray technique for testing `run()`, and to review his test classes. Muna: Had claude help with junit testing and implementing linkchain / token system. Ari: Had Gemini brainstorm ideas on how to separate the terms and debug. Ehren: Had Claude help with logic structure and how best to test.

## System Diagram

