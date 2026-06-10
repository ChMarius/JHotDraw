package org.jhotdraw.draw.action;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.jhotdraw.draw.AttributeKey;
import javax.swing.undo.UndoableEdit;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.AbstractFigure;
import org.jhotdraw.draw.figure.Figure;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlignActionTest {

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

    private static class TestAlignAction extends AlignAction {
        private DrawingView view;

        public TestAlignAction() {
            super(null);
        }

        @Override
        protected void alignFigures(Collection<Figure> selectedFigures, Rectangle2D.Double selectionBounds) {
            // no-op: tests exercise helper and selection-bound logic directly
        }

        @Override
        protected void fireUndoableEditHappened(UndoableEdit edit) {
            // No drawing is attached in unit tests. Ignore undo events.
        }

        public void setView(DrawingView view) {
            this.view = view;
        }

        @Override
        protected DrawingView getView() {
            return view;
        }
    }

    private static class StubFigure extends AbstractFigure {
        private Rectangle2D.Double bounds;
        private int transformCount;

        public StubFigure(double x, double y, double width, double height) {
            this.bounds = new Rectangle2D.Double(x, y, width, height);
        }

        @Override
        public void draw(Graphics2D g) {
        }

        @Override
        public Rectangle2D.Double getBounds() {
            return bounds;
        }

        @Override
        public Rectangle2D.Double getDrawingArea() {
            return new Rectangle2D.Double(bounds.x, bounds.y, bounds.width, bounds.height);
        }

        @Override
        public boolean contains(Point2D.Double p) {
            return bounds.contains(p);
        }

        @Override
        public void transform(AffineTransform tx) {
            assert tx != null : "transform matrix must not be null";
            Point2D.Double start = new Point2D.Double(bounds.x, bounds.y);
            Point2D.Double end = new Point2D.Double(bounds.x + bounds.width, bounds.y + bounds.height);
            tx.transform(start, start);
            tx.transform(end, end);
            bounds = new Rectangle2D.Double(start.x, start.y, end.x - start.x, end.y - start.y);
            transformCount++;
        }

        @Override
        public Rectangle2D.Double getDrawingArea(double factor) {
            return new Rectangle2D.Double(bounds.x, bounds.y, bounds.width * factor, bounds.height * factor);
        }

        @Override
        public <T> void set(AttributeKey<T> key, T value) {
        }

        @Override
        public <T> T get(AttributeKey<T> key) {
            return key.getDefaultValue();
        }

        @Override
        public Map<AttributeKey<?>, Object> getAttributes() {
            return Collections.emptyMap();
        }

        @Override
        public Object getAttributesRestoreData() {
            return null;
        }

        @Override
        public void restoreAttributesTo(Object restoreData) {
        }

        @Override
        public Object getTransformRestoreData() {
            return null;
        }

        @Override
        public void restoreTransformTo(Object restoreData) {
        }

        public int getTransformCount() {
            return transformCount;
        }
    }

    @Test
    public void testGetSelectionBoundsMultipleFigures() {
        StubFigure f1 = new StubFigure(10, 20, 30, 40);
        StubFigure f2 = new StubFigure(0, 5, 10, 10);
        Set<Figure> selected = new LinkedHashSet<>();
        selected.add(f1);
        selected.add(f2);

        TestAlignAction action = new TestAlignAction();
        action.setView(createView(selected));

        Rectangle2D.Double bounds = action.getSelectionBounds();

        assertNotNull(bounds);
        assertEquals(0.0, bounds.x, 0.0);
        assertEquals(5.0, bounds.y, 0.0);
        assertEquals(40.0, bounds.width, 0.0);
        assertEquals(55.0, bounds.height, 0.0);
    }

    @Test
    public void testGetSelectionBoundsNoSelectionReturnsNull() {
        TestAlignAction action = new TestAlignAction();
        action.setView(createView(Collections.emptySet()));

        assertNull(action.getSelectionBounds());
    }

    @Test
    public void testAlignFiguresWithCalculatorTransformsTransformableFigure() {
        StubFigure figure = new StubFigure(5, 15, 20, 20);
        Rectangle2D.Double selectionBounds = new Rectangle2D.Double(0, 0, 10, 10);

        TestAlignAction action = new TestAlignAction();
        action.alignFiguresWithCalculator(Collections.singleton(figure), selectionBounds,
                (bounds, figBounds) -> {
                    AffineTransform tx = new AffineTransform();
                    tx.translate(0, bounds.y - figBounds.y);
                    return tx;
                });

        assertEquals(1, figure.getTransformCount());
        assertEquals(5.0, figure.getBounds().x, 0.0);
        assertEquals(0.0, figure.getBounds().y, 0.0);
    }

    @Test
    public void testAlignFiguresWithCalculatorSkipsNonTransformableFigure() {
        StubFigure figure = new StubFigure(5, 15, 20, 20);
        figure.setTransformable(false);

        TestAlignAction action = new TestAlignAction();
        action.alignFiguresWithCalculator(Collections.singleton(figure),
                new Rectangle2D.Double(0, 0, 10, 10),
                (bounds, figBounds) -> {
                    fail("Calculator should not be invoked for non-transformable figures");
                    return null;
                });

        assertEquals(0, figure.getTransformCount());
        assertEquals(15.0, figure.getBounds().y, 0.0);
    }
}
