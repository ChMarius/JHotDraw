# Actualization – Clean Architecture & SOLID Principles
**Feature:** Align Palette (North, South, East, West, Horizontal, Vertical)

---

## Clean Architecture

Clean Architecture organizes software into layers and its structure isolates business logic from UI and infrastructure. Changes in one layer cause minimal impact on others. In the JHotDraw case study, the Align feature modifies the position of selected figures on the canvas. This is a domain concern. Clean Architecture helps place this change in the correct layer.

The alignment logic lives inside `AlignAction`, which sits in the controller layer and operates exclusively through the `Figure` and `DrawingView` interfaces. It never reaches into Swing widgets or toolbar components directly. The UI layer — `ButtonFactory` and the SVG toolbar panel — wires the actions to buttons, but the alignment logic itself has no knowledge of those components. This means the coordinate calculation and the undo logic can change without touching the UI, and the UI can be restructured without touching the alignment logic.

---

## SOLID Principles

**Single Responsibility Principle** was applied by keeping all alignment coordination logic inside `AlignAction`. It has one job: retrieve the selection, compute the bounding rectangle, fire the undo event, and delegate repositioning. Figure rendering, tool switching, and persistence are handled by separate classes.

**Open/Closed Principle** was respected by designing `AlignAction` so that new alignment modes can be added by supplying a new `FigureAligner` lambda without modifying the existing loop or undo logic. The base class is closed for modification and open for extension.

**Liskov Substitution Principle** was preserved by ensuring that `AlignAction` operates only against the `Figure` interface. Any concrete figure — `SVGRectFigure`, `TextFigure`, or any custom figure — can be substituted into the selection and will be repositioned correctly, because all figures honour the `getBounds()` and `setBounds()` contract defined by the interface.

**Interface Segregation Principle** was respected by having `AlignAction` interact only with `DrawingView` and `Figure`, without depending on attribute, connector, or serialisation interfaces that alignment does not need. Each interface exposes only what its clients require.

**Dependency Inversion Principle** was respected by having `AlignAction` depend on the `DrawingEditor` interface rather than any concrete editor implementation, and by injecting it through the constructor. The UI components such as `ButtonFactory` depend on `AlignAction` through the `Action` interface and not on any concrete alignment subclass directly.