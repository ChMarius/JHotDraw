## Lab 9 (TestLab2)

### User Story

As a user, I want to change the canvas from landscape to portrait so that I can create drawings in portrait format.

### BDD Scenarios

#### Scenario 1: Change canvas to portrait

**Given**
- A drawing view with landscape orientation.

**When**
- The user selects portrait orientation.

**Then**
- The canvas width and height are swapped, and the canvas is displayed in portrait format.

---

#### Scenario 2: Keep portrait when already portrait

**Given**
- A drawing view already in portrait orientation.

**When**
- The user selects portrait orientation.

**Then**
- The canvas dimensions remain unchanged.
