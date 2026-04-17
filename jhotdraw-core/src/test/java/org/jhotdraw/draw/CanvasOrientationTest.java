package org.jhotdraw.draw;

import com.tngtech.jgiven.junit.ScenarioTest;
import com.tngtech.jgiven.Stage;

import org.junit.Test;
import java.awt.Dimension;
import static org.assertj.core.api.Assertions.assertThat;

public class CanvasOrientationTest extends
        ScenarioTest<
                CanvasOrientationTest.GivenCanvas,
                CanvasOrientationTest.WhenCanvas,
                CanvasOrientationTest.ThenCanvas> {

    @Test
    public void change_to_portrait() {
        given().a_landscape_canvas();
        when().user_changes_to_portrait();
        then().canvas_is_portrait();
    }

    public static class GivenCanvas {
        DefaultDrawingView view;

        public GivenCanvas a_landscape_canvas() {
            view = new DefaultDrawingView();
            view.setPreferredSize(new Dimension(800, 600));
            return this;
        }
    }

    public static class WhenCanvas extends GivenCanvas {

        public WhenCanvas user_changes_to_portrait() {
            Dimension size = view.getPreferredSize();
            view.setPreferredSize(new Dimension(size.height, size.width));
            return this;
        }
    }

    public static class ThenCanvas extends WhenCanvas {

        public ThenCanvas canvas_is_portrait() {
            Dimension size = view.getPreferredSize();
            assertThat(size.height).isGreaterThan(size.width);
            return this;
        }
    }
}