# Refactoring Report – Align Feature

---

## Code Smells (Chapter 4, [Ker05])


**Duplicated Code** is the most prominent smell. All 6 alignment subclasses (`North`, `South`, `East`, `West`, `Horizontal`, `Vertical`) contained identical `alignFigures()` implementations — the same loop, the same `willChange()` / `changed()` calls, differing only in a single coordinate expression. This is a textbook case of the *Duplicated Code* smell: the same code structure repeated in sibling subclasses, making every bug fix or change a six-place operation.

The second smell is **Speculative Generality / Incomplete Abstraction**. The transformation logic was hardcoded directly inside each subclass with no shared abstraction, making the intent of each class hard to see at a glance. An `// XXX - Fire edit events` comment also pointed to *Dead Code / Incomplete Work*, a signal that the codebase was left in an unfinished state.

The goal was to eliminate the duplicated loop body shared across all six subclasses, and to introduce a clear abstraction that makes each class's *unique* responsibility — its coordinate calculation — immediately visible. The `XXX` comment was to be removed by ensuring event firing was properly handled in one place.

The strategy follows two refactorings from [Ker05]:

**1. Extract Method**
The repeated loop body (`willChange()` → reposition → `changed()`) was extracted into a single shared helper method `alignFiguresWithCalculator()` in the base class. This means the loop is written once and all six subclasses delegate to it. The reasoning is straightforward: any future change to how figures are repositioned (e.g. adding animation, adding extra event firing) now needs to happen in exactly one place.

**2. Introduce Strategy (Replace Conditional / Subclass Logic with Polymorphism via Interface)**
Rather than keeping six subclasses each with their own override, a `TransformCalculator` functional interface was introduced. Each subclass now passes a lambda that expresses only its unique coordinate logic. This is the *Strategy Pattern* applied at the method level — the varying behaviour (the calculation) is separated from the invariant behaviour (the loop, the event calls). The result is that each subclass is reduced to a single line, making its intent immediately clear, and the shared mechanics are no longer obscured by repetition.

The external behaviour of all six alignment modes is unchanged. Only the internal structure was improved.