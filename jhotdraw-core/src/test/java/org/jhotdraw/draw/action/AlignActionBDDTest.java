package org.jhotdraw.draw.action;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.RectangleFigure;
import org.junit.Before;
import org.junit.Test;

/**
 * BDD-style tests for AlignAction using Given-When-Then scenarios.
 * 
 * This test class documents the business logic of figure alignment through
 * executable scenarios. Each test method represents a user story with:
 * - Given: Initial setup of figures and alignment action
 * - When: Action is executed (alignment operation)
 * - Then: Verify expected outcome (figures aligned or unchanged)
 */
public class AlignActionBDDTest {

    private TestAlignAction action;
    private RectangleFigure topFigure;
    private RectangleFigure bottomFigure;
    private RectangleFigure lockedFigure;

    @Before
    public void setup() {
        action = new TestAlignAction();
    }

    /**
     * User Story: Align multiple selected figures to top edge
     * 
     * Scenario: should_align_selected_figures_to_the_top_edge
     * - Given: Two figures at different vertical positions (y=10 and y=40)
     * - When: The alignment action aligns them to the top edge
     * - Then: Both figures should share the same top y-coordinate (y=10)
     */
    @Test
    public void should_align_selected_figures_to_the_top_edge() {
        // Given: two selected figures at different vertical positions
        topFigure = new RectangleFigure(10, 10, 20, 20);
        bottomFigure = new RectangleFigure(5, 40, 20, 20);
        Set<Figure> selected = new LinkedHashSet<>();
        selected.add(topFigure);
        selected.add(bottomFigure);
        action.setView(createView(selected));

        // When: the action aligns figures to the top edge
        Rectangle2D.Double selectionBounds = action.getSelectionBounds();
        action.alignFiguresWithCalculator(
                Collections.unmodifiableSet(new LinkedHashSet<>(action.getView().getSelectedFigures())),
                selectionBounds,
                (bounds, figBounds) -> {
                    AffineTransform tx = new AffineTransform();
                    tx.translate(0, bounds.y - figBounds.y);
                    return tx;
                });

        // Then: the figures are transformed to share the same top y coordinate
        Assertions.assertThat(topFigure.getBounds().y)
                .as("Top figure should remain at y=10")
                .isEqualTo(10.0);
        Assertions.assertThat(bottomFigure.getBounds().y)
                .as("Bottom figure should be transformed to y=10")
                .isEqualTo(10.0);
    }

    /**
     * User Story: Skip non-transformable figures during alignment
     * 
     * Scenario: should_skip_non_transformable_figures
     * - Given: A non-transformable figure that is marked with setTransformable(false)
     * - When: The alignment action attempts to align the selection
     * - Then: The non-transformable figure should remain unchanged
     */
    @Test
    public void should_skip_non_transformable_figures() {
        // Given: a non-transformable figure
        lockedFigure = new RectangleFigure(10, 10, 20, 20);
        lockedFigure.setTransformable(false);
        Set<Figure> selected = Collections.singleton(lockedFigure);
        action.setView(createView(selected));
        
        double originalX = lockedFigure.getBounds().x;
        double originalY = lockedFigure.getBounds().y;

        // When: the action attempts to align the selection
        Rectangle2D.Double selectionBounds = action.getSelectionBounds();
        action.alignFiguresWithCalculator(
                Collections.unmodifiableSet(new LinkedHashSet<>(action.getView().getSelectedFigures())),
                selectionBounds,
                (bounds, figBounds) -> {
                    AffineTransform tx = new AffineTransform();
                    tx.translate(0, bounds.y - figBounds.y);
                    return tx;
                });

        // Then: the non-transformable figure is left unchanged
        Assertions.assertThat(lockedFigure.getBounds().y)
                .as("Non-transformable figure should not be moved vertically")
                .isEqualTo(originalY);
        Assertions.assertThat(lockedFigure.getBounds().x)
                .as("Non-transformable figure should not be moved horizontally")
                .isEqualTo(originalX);
    }

    /**
     * Test helper: Creates a mock DrawingView using Java Proxy pattern.
     * This avoids mock framework dependencies while providing test doubles.
     */
    private static DrawingView createView(final Set<Figure> selectedFigures) {
        return (DrawingView) Proxy.newProxyInstance(
                DrawingView.class.getClassLoader(),
                new Class[]{DrawingView.class},
                (proxy, method, args) -> {
                    switch (method.getName()) {
                        case "getSelectedFigures":
                            return selectedFigures;
                        case "getSelectionCount":
                            return selectedFigures.size();
                        case "isEnabled":
                            return true;
                        default:
                            Class<?> returnType = method.getReturnType();
                            if (returnType == boolean.class) {
                                return false;
                            }
                            if (returnType == int.class) {
                                return 0;
                            }
                            if (returnType == double.class) {
                                return 0.0;
                            }
                            return null;
                    }
                });
    }

    /**
     * Test implementation of AlignAction that allows direct invocation
     * of protected methods and suppresses undo event handling.
     */
    private static class TestAlignAction extends AlignAction {
        private DrawingView view;

        public TestAlignAction() {
            super(null);
        }

        @Override
        protected void alignFigures(Collection<Figure> selectedFigures, Rectangle2D.Double selectionBounds) {
            // no-op: this test exercises helper logic directly
        }

        public void setView(DrawingView view) {
            this.view = view;
        }

        @Override
        protected DrawingView getView() {
            return view;
        }

        @Override
        protected void fireUndoableEditHappened(javax.swing.undo.UndoableEdit edit) {
            // ignore undo events in BDD tests
        }
    }
}

