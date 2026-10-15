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

 **What happened to our unknowns?**

We figured out how to separate inputs and group terms together, how to simplify the final answer, and how to divide up the work.

 **What doesn't work yet?**

The MVP features that don't work yet are the quotient, product, and chain rule.

 ## Next Steps

 **Finish the MVP, in order:**
- Separating terms with parentheses
- Product rule
- Quotient rule
- Chain rule

 **Stretch goals:**
- Show which rule was used: committed
- Differentiating multivariable polynomials: committed / dropped
- Adding trig, log, and exponential derivatives: committed / dropped

**New tests need:**
- Methods and logic for the new rules will be needed, as well as test cases for all possible situations

**What changed in the LinkedChain?**

Used unchanged. The same generic class holds Token (`readEquation`, `Differentiation`, `Simplification`, Main) and Character (`hasParentheses`, a stack of open parentheses called by `isValid`). It also holds Integer in `findUnmatchedParenthesis` (a stack of "(" positions), which is tested but not yet called by Main.

**Who does what:**
- Owen - Main changes, plus their tests
- Ari - 
- Ehren - 
- Muna -

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

**GenAI note:** Owen used Claude as a tutor for the Token class idea, `equals`/`toString`,  Main's constructors, the ByteArray technique for testing `run()`, and to review his test classes. Muna: [your note]. Ari: [your note]. Ehren: [your note].
