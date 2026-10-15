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

 **What doesn't work yet?**

 ## Next Steps

 **Finish the MVP, in order:**

 **Stretch goals:**
- [Goal from the scope]: committed / dropped
- [Goal from the scope]: committed / dropped
