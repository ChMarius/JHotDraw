# BDD Tests – Align Feature

**File:** `jhotdraw-core/src/test/java/org/jhotdraw/draw/action/AlignActionBDDTest.java`  
**Framework:** JUnit 4 + JGiven + AssertJ  
**Result:** 2 tests passed, 0 failed

---

## User Story to BDD Mapping

| User Story | BDD Scenario |
|---|---|
| As a graphic designer, I want to align multiple selected figures to a common edge so that I can produce clean, professional-looking drawings. | **Given** two figures at different vertical positions **When** the alignment action is applied **Then** both figures should share the same top y-coordinate |
| As a graphic designer, I want locked figures to remain in place during alignment so that I do not accidentally move figures I have intentionally fixed. | **Given** a non-transformable figure is selected **When** the alignment action is applied **Then** the figure should remain unchanged |

---

## Scenarios

### Scenario 1: Align Selected Figures to the Top Edge

**User Story:** As a graphic designer, I want to align multiple selected figures to a common edge so that I can produce clean, professional-looking drawings.

**Given** two figures at different vertical positions (y=10 and y=40)  
**When** the alignment action aligns them to the top edge  
**Then** both figures should share the same top y-coordinate (y=10)

```java
@Test
public void should_align_selected_figures_to_the_top_edge() {
    // Given: two selected figures at different vertical positions
    topFigure = new RectangleFigure(10, 10, 20, 20);
    bottomFigure = new RectangleFigure(5, 40, 20, 20);
    Set<Figure> selected = new LinkedHashSet<>();
    selected.add(topFigure);
    selected.add(bottomFigure);
    action.setView(createView(selected));

    // When: the action aligns figures to the top edge
    Rectangle2D.Double selectionBounds = action.getSelectionBounds();
    action.alignFiguresWithCalculator(
            Collections.unmodifiableSet(new LinkedHashSet<>(action.getView().getSelectedFigures())),
            selectionBounds,
            (bounds, figBounds) -> {
                AffineTransform tx = new AffineTransform();
                tx.translate(0, bounds.y - figBounds.y);
                return tx;
            });

    // Then: both figures share the same top y-coordinate
    Assertions.assertThat(topFigure.getBounds().y)
            .as("Top figure should remain at y=10")
            .isEqualTo(10.0);
    Assertions.assertThat(bottomFigure.getBounds().y)
            .as("Bottom figure should be transformed to y=10")
            .isEqualTo(10.0);
}
```

---

### Scenario 2: Skip Non-Transformable Figures

**User Story:** As a graphic designer, I want locked figures to remain in place during alignment so that I do not accidentally move figures I have intentionally fixed.

**Given** a non-transformable figure marked with `setTransformable(false)`  
**When** the alignment action attempts to align the selection  
**Then** the figure's position should remain unchanged

```java
@Test
public void should_skip_non_transformable_figures() {
    // Given: a non-transformable figure
    lockedFigure = new RectangleFigure(10, 10, 20, 20);
    lockedFigure.setTransformable(false);
    Set<Figure> selected = Collections.singleton(lockedFigure);
    action.setView(createView(selected));

    double originalX = lockedFigure.getBounds().x;
    double originalY = lockedFigure.getBounds().y;

    // When: the action attempts to align the selection
    Rectangle2D.Double selectionBounds = action.getSelectionBounds();
    action.alignFiguresWithCalculator(
            Collections.unmodifiableSet(new LinkedHashSet<>(action.getView().getSelectedFigures())),
            selectionBounds,
            (bounds, figBounds) -> {
                AffineTransform tx = new AffineTransform();
                tx.translate(0, bounds.y - figBounds.y);
                return tx;
            });

    // Then: the non-transformable figure is left unchanged
    Assertions.assertThat(lockedFigure.getBounds().y)
            .as("Non-transformable figure should not be moved vertically")
            .isEqualTo(originalY);
    Assertions.assertThat(lockedFigure.getBounds().x)
            .as("Non-transformable figure should not be moved horizontally")
            .isEqualTo(originalX);
}
