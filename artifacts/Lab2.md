
| Domain Class | Responsibility |
|---|---|
| `AlignAction` | Abstract base class for all alignment actions. Handles `actionPerformed()`, computes the bounding rectangle of the current selection via `getSelectionBounds()`, and delegates the concrete alignment logic to subclasses via the abstract `alignFigures()` method. Also manages the enabled/disabled state of the action. |
| `AlignAction.North` | Concrete subclass of `AlignAction`. Implements `alignFigures()` to align the top edges of all selected figures to the topmost figure's top edge. |
| `AlignAction.South` | Concrete subclass of `AlignAction`. Implements `alignFigures()` to align the bottom edges of all selected figures to the bottommost figure's bottom edge. |
| `AlignAction.West` | Concrete subclass of `AlignAction`. Implements `alignFigures()` to align the left edges of all selected figures to the leftmost figure's left edge. |
| `AlignAction.East` | Concrete subclass of `AlignAction`. Implements `alignFigures()` to align the right edges of all selected figures to the rightmost figure's right edge. |
| `AlignAction.Horizontal` | Concrete subclass of `AlignAction`. Implements `alignFigures()` to center all selected figures along a shared horizontal axis. |
| `AlignAction.Vertical` | Concrete subclass of `AlignAction`. Implements `alignFigures()` to center all selected figures along a shared vertical axis. |
| `AbstractSelectedAction` | Abstract superclass of `AlignAction`. Provides shared infrastructure for actions that operate on selected figures: access to the `DrawingEditor`, `DrawingView`, and `Drawing`, as well as undo support via `fireUndoableEditHappened()`. |
| `DrawingEditor` | Interface representing the central editor controller. Provides access to the active `DrawingView` and coordinates tool and action interactions across the application. |
| `DrawingView` | Interface representing the visual canvas. Exposes the current selection of figures (`getSelectedFigures()`), which `AlignAction` queries to determine which figures to align. |
| `Drawing` | Interface representing the drawing model (collection of figures). Updated when figures are repositioned during an alignment operation. |
| `Figure` | Core domain interface representing a drawable shape. Exposes `getBounds()` and `setBounds()` (or equivalent transform methods), which are called by each `AlignAction` subclass to reposition figures. |
