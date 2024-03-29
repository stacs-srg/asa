/*
 * @(#)JGraphHeavyweightView.java 1.0 12-OCT-2004
 * 
 * Copyright (c) 2001-2005, Gaudenz Alder
 * All rights reserved. 
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */
package uk.ac.stand.dcs.asa.networkmonitor.plugins.output.jgraph;

import org.jgraph.graph.CellView;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.VertexView;

import java.awt.*;

/**
 * @author Gaudenz Alder
 */
public class JGraphHeavyweightView extends VertexView {
    public class JGraphHeavyweightRenderer implements CellViewRenderer {
        public Component getRendererComponent(org.jgraph.JGraph graph,
                CellView view, boolean sel, boolean focus, boolean preview) {
            return configureRenderer(component);
        }
    }

    public transient Component component;
    private transient CellViewRenderer renderer_overridden;

    public JGraphHeavyweightView() {
        super();
    }

    public JGraphHeavyweightView(Object cell) {
        super(cell);
    }

    public CellViewRenderer getRenderer() {
        if (renderer_overridden != null) return renderer_overridden;
        return super.getRenderer();
    }

    public void setCell(Object cell) {
        super.setCell(cell);
        if (cell instanceof DefaultGraphCell) {
            Object userObject = ((DefaultGraphCell) cell).getUserObject();
            if (userObject instanceof Component) {
                this.component = (Component) userObject;
                this.renderer_overridden = new JGraphHeavyweightRenderer();
            }
        }
    }

    /**
     * A hook for subclassers to eg. use the border from the attributes:
     */
    protected Component configureRenderer(Component component) {
        return component;
    }
}
