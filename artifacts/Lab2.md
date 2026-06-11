## Lab 2 (CLLab)

| Domain Class | Responsibility |
|-------------|---------------|
| `AttributeKeys` | Defines a set of well known attributes. |
| `DefaultDrawing` | A default implementation of `Drawing` useful for drawings which contain only a few figures. |
| `DefaultDrawingView` | A default implementation of `DrawingView` suited for viewing drawings with a small number of figures. |
| `QuadTreeDrawing` | An implementation of `Drawing` which uses a `QuadTree` to provide good responsiveness for drawings containing many figures. |
| `CanvasToolBar` | Toolbar UI component which provides buttons and actions related to canvas operations. |
| `CanvasTool` | Tool used to interact with the canvas, handling mouse events and enabling user actions. |
| `ViewToolBar` | Toolbar for general view operations. |