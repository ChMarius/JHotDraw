
## Impact Analysis Process (Following Figure 7.9)

1. All classes in the interaction diagram are initially marked **BLANK**.
2. Classes identified during concept location are marked **CHANGED** (the starting set).
3. All BLANK neighbors of CHANGED classes are marked **NEXT**.
4. Each NEXT class is evaluated and assigned: **CHANGED**, **PROPAGATES**, or **UNCHANGED**.
5. Steps 3–4 repeat until no NEXT classes remain.

---

## Estimated Impact Set

| Class | Mark | Reason |
|---|---|---|
| `AlignAction` | **CHANGED** | Core class directly implementing the align feature; `alignFigures()`, `actionPerformed()`, and `getSelectionBounds()` will be modified or extended. |
| `AlignAction.North` | **CHANGED** | Concrete subclass providing "Align Top" logic; directly modified to reposition figures. |
| `AlignAction.South` | **CHANGED** | Concrete subclass providing "Align Bottom" logic; directly modified. |
| `AlignAction.West` | **CHANGED** | Concrete subclass providing "Align Left" logic; directly modified. |
| `AlignAction.East` | **CHANGED** | Concrete subclass providing "Align Right" logic; directly modified. |
| `AlignAction.Horizontal` | **CHANGED** | Concrete subclass for centering horizontally; directly modified. |
| `AlignAction.Vertical` | **CHANGED** | Concrete subclass for centering vertically; directly modified. |
| `AbstractSelectedAction` | **PROPAGATES** | Parent class of `AlignAction`; provides `getView()`, `getDrawing()`, and `fireUndoableEditHappened()`. Not modified itself, but any change to `AlignAction`'s constructor or inherited methods propagates through this class. |
| `DrawingEditor` *(interface)* | **PROPAGATES** | Supplies the active `DrawingView` to `AlignAction`. Its contract is relied upon; if the editor's view-management changes, alignment is affected. |
| `DrawingView` *(interface)* | **PROPAGATES** | Provides `getSelectedFigures()` — the collection that `AlignAction` iterates over. Changes here would break the alignment loop. |
| `Drawing` *(interface)* | **PROPAGATES** | The drawing model is notified when figures are repositioned via `willChange()` / `changed()`. Not directly modified, but participates in the update cycle triggered by alignment. |
| `Figure` *(interface)* | **PROPAGATES** | Every aligned figure's position is updated by calling `getBounds()` / `setBounds()` (or `transform()`). The interface contract is consumed heavily but not altered. |
| `DefaultDrawingView` | **PROPAGATES** | Concrete implementation of `DrawingView`; manages the selection set and repaints after figures are moved during alignment. Impacted indirectly. |
| `DefaultDrawingEditor` | **UNCHANGED** | Manages tool switching and active view; alignment actions use it as a read-only context. No alignment-specific logic lives here. |
| `ButtonFactory` | **CHANGED** | Factory class that constructs and wires up the toolbar buttons, including the Align palette buttons. Must be updated to register any new or renamed `AlignAction` subclasses. |
| `AbstractFigure` | **PROPAGATES** | Base implementation of `Figure`; provides default `transform()` / `getBounds()` behaviour inherited by all concrete figures that get repositioned. |
| `UndoableEdit` *(javax.swing.undo)* | **PROPAGATES** | Undo records are fired via `fireUndoableEditHappened()` in `AbstractSelectedAction` after each alignment. If undo behaviour is extended, this is the propagation path. |
