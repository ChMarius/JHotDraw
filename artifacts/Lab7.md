# Unit Tests – Align Feature

**File:** `jhotdraw-core/src/test/java/org/jhotdraw/draw/action/AlignActionTest.java`  
**Framework:** JUnit 4  
**Result:** 4 tests passed, 0 failed

---

## Overview

A JUnit 4 test class was created for `AlignAction` to verify the core business logic of the Align feature. The tests focus on two methods: `getSelectionBounds()` and `alignFiguresWithCalculator()`. Since `AlignAction` depends on `DrawingView` and a live `Drawing`, the test class uses a Java `Proxy` to stub `DrawingView` and a `StubFigure` subclass of `AbstractFigure` to isolate the logic under test from the rest of the framework.

---

## Test Infrastructure

**`createView(Set<Figure>)`** — builds a lightweight `DrawingView` proxy that returns a fixed set of selected figures, a selection count, and `true` for `isEnabled()`. This avoids needing a real Swing drawing canvas in tests.

**`TestAlignAction`** — a concrete subclass of `AlignAction` used only in tests. It overrides `alignFigures()` as a no-op, suppresses `fireUndoableEditHappened()` since no `Drawing` is attached, and exposes a `setView()` setter so each test can inject its own view proxy.

**`StubFigure`** — a minimal `AbstractFigure` implementation that stores a mutable bounding rectangle and counts how many times `transform()` has been called. This lets tests assert both that the right figures were moved and that non-transformable figures were correctly skipped.

---

## Tests

### `getSelectionBounds()` — Best Case
**`testGetSelectionBoundsMultipleFigures()`**  
Places two figures with non-overlapping bounds (`f1` at (10,20,30×40) and `f2` at (0,5,10×10)) into the selection and asserts that `getSelectionBounds()` returns the correct union rectangle: x=0, y=5, width=40, height=55. This is the normal path that runs every time the user triggers an alignment with multiple figures selected.

### `getSelectionBounds()` — Boundary Case
**`testGetSelectionBoundsNoSelectionReturnsNull()`**  
Passes an empty selection set and asserts that `getSelectionBounds()` returns `null`. This guards against a `NullPointerException` that would otherwise occur if `actionPerformed()` tried to align an empty selection.

### `alignFiguresWithCalculator()` — Best Case
**`testAlignFiguresWithCalculatorTransformsTransformableFigure()`**  
Passes a single transformable `StubFigure` and a calculator lambda that translates the figure to `y=0`. After the call, the test asserts that `transform()` was called exactly once and that the figure's new `y` coordinate is `0.0`. This verifies the core transform path that all six alignment directions rely on.

### `alignFiguresWithCalculator()` — Boundary Case
**`testAlignFiguresWithCalculatorSkipsNonTransformableFigure()`**  
Marks the stub figure as non-transformable (`setTransformable(false)`) and asserts that the calculator lambda is never invoked and `transform()` is never called. This ensures the method does not attempt to move locked or protected figures.
