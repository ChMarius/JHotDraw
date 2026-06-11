## Lab 5 (ActLab)

### Clean Architecture

Clean Architecture organizes software into layers and isolates business logic from UI and infrastructure. Changes in one layer have minimal impact on others.

In the JHotDraw case study, the feature modifies canvas orientation, which is a presentation concern. Clean Architecture helps place this functionality in the correct layer.

### SOLID Principles

**Single Responsibility Principle**

Orientation logic is kept inside `DefaultDrawingView`.

**Open Closed Principle**

The system is extended without modifying the drawing model.

**Liskov Substitution Principle**

`QuadTreeDrawingView` behaves consistently with `DefaultDrawingView`.

**Interface Segregation Principle**

`CanvasTool` interacts with `DrawingView` and does not require orientation specific methods.

**Dependency Inversion Principle**

UI components such as `CanvasToolBar` depend on the `DrawingView` abstraction rather than directly on `DefaultDrawingView`.