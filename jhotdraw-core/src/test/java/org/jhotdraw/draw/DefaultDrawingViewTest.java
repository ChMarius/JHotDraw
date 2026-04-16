package org.jhotdraw.draw;
import java.awt.Dimension;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class DefaultDrawingViewTest {

    private DefaultDrawingView view;

    @Before
    public void setup() {
        view = new DefaultDrawingView();
    }

    @Test
    public void testGetPreferredSizeReturnsAssignedSize() {
        Dimension expected = new Dimension(800, 600);

        view.setPreferredSize(expected);
        Dimension actual = view.getPreferredSize();

        assertEquals(expected.width, actual.width);
        assertEquals(expected.height, actual.height);
    }

    @Test
    public void testCanvasViewBoundsWithZeroSize() {
        view.setSize(0, 0);

        java.awt.Rectangle bounds = view.getCanvasViewBounds();

        assertEquals(0, bounds.width);
        assertEquals(0, bounds.height);
    }
    @Test
    public void testSetBoundsWithMinimalSize() {
        view.setBounds(0, 0, 1, 1);

        assertEquals(1, view.getWidth());
        assertEquals(1, view.getHeight());
    }
}