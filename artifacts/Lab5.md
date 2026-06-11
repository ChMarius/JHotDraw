## Lab 5 (ActLab)

### Clean Architecture

Clean Architecture organizes software into layers and isolates business logic from UI and infrastructure. Changes in one layer have minimal impact on others.

The portrait orientation feature was implemented in the presentation layer by modifying DefaultDrawingView. The implementation reuses the existing CANVAS_WIDTH and CANVAS_HEIGHT attributes, avoiding changes to the domain model.

### SOLID Principles

**Single Responsibility Principle**

The implementation separates responsibilities through `setPortraitCanvas`, `setLandscapeCanvas`, `updateCanvasOrientation`, and `swapCanvasDimensions`.

**Open Closed Principle**

The system is extended without modifying the drawing model.

**Liskov Substitution Principle**

`QuadTreeDrawingView` behaves consistently with `DefaultDrawingView`.

**Interface Segregation Principle**

`CanvasTool` interacts with `DrawingView` and does not require orientation specific methods.

**Dependency Inversion Principle**

UI components such as `CanvasToolBar` depend on the `DrawingView` abstraction rather than directly on `DefaultDrawingView`.