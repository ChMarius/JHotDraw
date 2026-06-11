package org.jhotdraw.draw;

import static org.assertj.core.api.Assertions.assertThat;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

public class CanvasOrientationBDDTest extends
        ScenarioTest<
                CanvasOrientationBDDTest.GivenStage,
                CanvasOrientationBDDTest.WhenStage,
                CanvasOrientationBDDTest.ThenStage> {

    @Test
    public void change_canvas_to_portrait() {
        given().a_landscape_canvas();
        when().the_user_selects_portrait_orientation();
        then().the_canvas_is_portrait();
    }

    public static class GivenStage extends Stage<GivenStage> {

        DefaultDrawingView view;
        DefaultDrawing drawing;

        public GivenStage a_landscape_canvas() {
            view = new DefaultDrawingView();
            drawing = new DefaultDrawing();

            drawing.set(AttributeKeys.CANVAS_WIDTH, 800d);
            drawing.set(AttributeKeys.CANVAS_HEIGHT, 600d);

            view.setDrawing(drawing);

            return self();
        }
    }

    public static class WhenStage extends GivenStage {

        public WhenStage the_user_selects_portrait_orientation() {
            view.setPortraitCanvas();
            return this;
        }
    }

    public static class ThenStage extends WhenStage {

        public ThenStage the_canvas_is_portrait() {
            assertThat(
                    drawing.get(AttributeKeys.CANVAS_HEIGHT))
                    .isGreaterThan(
                    drawing.get(AttributeKeys.CANVAS_WIDTH));

            return this;
        }
    }
}