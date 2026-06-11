package org.jhotdraw.draw;

import static org.jhotdraw.draw.AttributeKeys.CANVAS_HEIGHT;
import static org.jhotdraw.draw.AttributeKeys.CANVAS_WIDTH;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DefaultDrawingViewTest {

    @Test
    public void shouldChangeLandscapeCanvasToPortrait() {
        DefaultDrawingView view = new DefaultDrawingView();
        DefaultDrawing drawing = new DefaultDrawing();

        drawing.set(CANVAS_WIDTH, 800d);
        drawing.set(CANVAS_HEIGHT, 600d);

        view.setDrawing(drawing);

        view.setPortraitCanvas();

        assertEquals(600d, drawing.get(CANVAS_WIDTH), 0.01);
        assertEquals(800d, drawing.get(CANVAS_HEIGHT), 0.01);
    }

    @Test
    public void shouldKeepPortraitCanvasUnchanged() {
        DefaultDrawingView view = new DefaultDrawingView();
        DefaultDrawing drawing = new DefaultDrawing();

        drawing.set(CANVAS_WIDTH, 600d);
        drawing.set(CANVAS_HEIGHT, 800d);

        view.setDrawing(drawing);

        view.setPortraitCanvas();

        assertEquals(600d, drawing.get(CANVAS_WIDTH), 0.01);
        assertEquals(800d, drawing.get(CANVAS_HEIGHT), 0.01);
    }

    @Test
    public void shouldHandleNullDrawing() {
        DefaultDrawingView view = new DefaultDrawingView();

        view.setPortraitCanvas();

        assertEquals(null, view.getDrawing());
    }
}
