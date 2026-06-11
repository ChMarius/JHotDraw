## Lab 9 (TestLab2)

### User Story

As a user, I want to change the canvas from landscape to portrait so that I can create drawings in portrait format.

### BDD Scenarios

#### Scenario 1: Change canvas to portrait

**Given**
- A drawing view with landscape orientation

**When**
- The user selects portrait format

**Then**
- The canvas height becomes greater than its width

---

#### Scenario 2: Keep portrait when already portrait

**Given**
- A drawing view already in portrait orientation

**When**
- The user selects portrait format

**Then**
- The canvas size remains unchanged

---

#### Scenario 3: Switch back to landscape

**Given**
- A drawing view in portrait orientation

**When**
- The user selects landscape format

**Then**
- The canvas width becomes greater than its height