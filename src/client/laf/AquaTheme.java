package client.laf;

import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

/**
 * Swing nice Metal LAF theme <br/>
 * This class describes a theme using "blue-green" colors.
 *
 * @author ola
 */
public class AquaTheme extends DefaultMetalTheme {

    public String getName() {
        return "Aqua";
    }

    private final ColorUIResource primary1 = new ColorUIResource(102, 153, 153);
    private final ColorUIResource primary2 = new ColorUIResource(128, 192, 192);
    private final ColorUIResource primary3 = new ColorUIResource(159, 235, 235);

    protected ColorUIResource getPrimary1() {
        return primary1;
    }

    protected ColorUIResource getPrimary2() {
        return primary2;
    }

    protected ColorUIResource getPrimary3() {
        return primary3;
    }

}
