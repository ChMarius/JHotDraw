## Lab 7 (TestLab1)

Unit tests were implemented for core canvas sizing logic.

The tests focused on methods responsible for:
- View dimensions
- Canvas bounds

### Best Case Scenario

Verified that the preferred size returned the expected dimensions after configuration.

### Boundary Cases

Tested:
- Zero size canvas bounds
- Minimal dimension updates through `setBounds()`

Swing rendering dependencies were avoided to ensure isolated unit testing.

The feature was verified by confirming correct dimension handling, which is essential for supporting portrait and landscape canvas formats.