/**
 * barsuift-simlife is a life simulator program
 * 
 * Copyright (C) 2010 Cyrille GACHOT
 * 
 * This file is part of barsuift-simlife.
 * 
 * barsuift-simlife is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * barsuift-simlife is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with barsuift-simlife. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package barsuift.simLife.j3d;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.MessageFormat;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.J3DGraphics2D;

import barsuift.simLife.time.FpsCounter;
import barsuift.simLife.universe.UniverseContext;

import com.sun.j3d.utils.universe.SimpleUniverse;

public class BasicSimLifeCanvas3D extends Canvas3D implements SimLifeCanvas3D {

    private static final long serialVersionUID = -8802614056574013014L;

    private static final MessageFormat GRAPHIC_FPS_MESSAGE = new MessageFormat(
            "Graphic FPS={0,number,000} Avg(FPS)={1,number,000}");

    private static final MessageFormat CORE_FPS_MESSAGE = new MessageFormat(
            "Core FPS={0,number,000} Avg(FPS)={1,number,000}");

    private final FpsCounter graphicFps;

    private BufferedImage drawIm;

    private Graphics2D drawg2d;

    private J3DGraphics2D render2d;

    private Font font;

    private UniverseContext universeContext;


    public BasicSimLifeCanvas3D(UniverseContext universeContext) {
        super(SimpleUniverse.getPreferredConfiguration());
        this.universeContext = universeContext;
        graphicFps = new FpsCounter();
        font = new Font(Font.MONOSPACED, Font.BOLD, 18);
        render2d = getGraphics2D();
    }

    @Override
    public void postRender() {
        super.postRender();
        if (universeContext.isFpsShowing()) {
            graphicFps.tick();
            drawIm = new BufferedImage(350, 48, BufferedImage.TYPE_4BYTE_ABGR);
            drawg2d = drawIm.createGraphics();
            drawg2d.setFont(font);
            drawg2d.setColor(Color.RED);

            String graphicMessage = GRAPHIC_FPS_MESSAGE.format(new Object[] { graphicFps.getFps(),
                    graphicFps.getAvgFps() });
            FpsCounter coreFps = universeContext.getUniverse().getFpsCounter();
            String coreMessage = CORE_FPS_MESSAGE.format(new Object[] { coreFps.getFps(), coreFps.getAvgFps() });
            // position the left bottom pixel of the first character at (2,18)
            drawg2d.drawString(graphicMessage, 2, 18);
            // position the left bottom pixel of the first character at (2,42)
            drawg2d.drawString(coreMessage, 2, 42);

            render2d.drawAndFlushImage(drawIm, 0, 0, this);
        }
    }

}
