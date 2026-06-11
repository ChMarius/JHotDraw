## Lab 7 (TestLab1)

Unit tests were implemented for the portrait canvas feature.

The tests focused on the methods responsible for changing the canvas orientation:
- `setPortraitCanvas()`
- `setLandscapeCanvas()`

### Best Case Scenario

Verified that a landscape canvas is correctly converted to portrait orientation by swapping the canvas width and height.

### Boundary Cases

Tested:
- Selecting portrait orientation when the canvas is already in portrait mode.
- Calling the orientation method when no drawing is attached to the view.

Swing rendering dependencies were avoided to ensure isolated unit testing.

The feature was verified by confirming that the canvas dimensions are updated correctly while preserving existing behavior and handling edge cases safely.