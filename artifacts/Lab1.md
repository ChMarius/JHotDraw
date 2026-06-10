# User Story: 

**As a** graphic designer working in JHotDraw,
**I want to** align multiple selected figures along a common edge or axis (top, bottom, left, right, horizontal center, or vertical center),
**so that** I can produce clean, professional-looking drawings without having to manually reposition each element.

---

## Acceptance Criteria

1. **Given** I have two or more figures selected on the canvas,
   **When** I choose "Align Top" from the Align palette,
   **Then** all selected figures should align their top edges to the topmost figure's top edge.

2. **Given** I have two or more figures selected,
   **When** I choose "Align Bottom",
   **Then** all selected figures should align their bottom edges to the bottommost figure's bottom edge.

3. **Given** I have two or more figures selected,
   **When** I choose "Align Left" or "Align Right",
   **Then** all selected figures should align to the leftmost or rightmost edge respectively.

4. **Given** I have two or more figures selected,
   **When** I choose "Align Horizontal" or "Align Vertical",
   **Then** all selected figures should be centered along the horizontal or vertical axis.

5. **Given** fewer than two figures are selected,
   **When** I open the Align palette,
   **Then** the alignment options should be disabled (greyed out), since alignment requires at least two figures.
