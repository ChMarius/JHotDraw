## Lab 4 (RefactLab)

In the `validateHandles()` method of the `DefaultDrawingView` class, there are nested loops, multiple conditionals, and a `while` loop with `continue`. This introduces cognitive complexity and makes the method difficult to understand and maintain.

According to **Kerievsky [Ker05]**, this corresponds to **Conditional Complexity**.

### Planned Refactoring

The goal is to:
- Extract nested logic into smaller helper methods.
- Reduce branching inside the main method.
- Improve readability and maintainability.

The original method handles:
- State validation
- Handle creation
- Retry logic
- Registration of handles

The chosen refactoring pattern is:

**Replace Conditional Calculations with Strategy**

This separates conditional logic into smaller methods, reducing complexity and making future modifications easier.