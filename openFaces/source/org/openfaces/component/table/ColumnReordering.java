/*
 * OpenFaces - JSF Component Library 2.0
 * Copyright (C) 2007-2009, TeamDev Ltd.
 * licensing@openfaces.org
 * Unless agreed in writing the contents of this file are subject to
 * the GNU Lesser General Public License Version 2.1 (the "LGPL" License).
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * Please visit http://openfaces.org/licensing/ for more details.
 */
package org.openfaces.component.table;

import org.openfaces.util.ValueBindings;

import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * @author Dmitry Pikhulya
 */
public class ColumnReordering extends UIComponentBase {
    public static final String COMPONENT_TYPE = "org.openfaces.ColumnReordering";
    public static final String COMPONENT_FAMILY = "org.openfaces.ColumnReordering";

    private String draggedCellStyle;
    private String draggedCellClass;
    private Double draggedCellTransparency;

    public ColumnReordering() {
        setRendererType("org.openfaces.ColumnReorderingRenderer");
    }

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    @Override
    public Object saveState(FacesContext context) {
        return new Object[]{
            super.saveState(context),
            draggedCellStyle,
            draggedCellClass,
            draggedCellTransparency
        };
    }

    @Override
    public void restoreState(FacesContext context, Object stateObj) {
        Object[] state = (Object[]) stateObj;
        int i = 0;
        super.restoreState(context, state[i++]);
        draggedCellStyle = (String) state[i++];
        draggedCellClass = (String) state[i++];
        draggedCellTransparency = (Double) state[i++];
    }

    public UIComponent getConfiguredComponent() {
        return getTable();
    }

    private AbstractTable getTable() {
        return (AbstractTable) getParent();
    }

    public String getDraggedCellStyle() {
        return ValueBindings.get(this, "draggedCellStyle", draggedCellStyle);
    }

    public void setDraggedCellStyle(String draggedCellStyle) {
        this.draggedCellStyle = draggedCellStyle;
    }

    public String getDraggedCellClass() {
        return ValueBindings.get(this, "draggedCellClass", draggedCellClass);
    }

    public void setDraggedCellClass(String draggedCellClass) {
        this.draggedCellClass = draggedCellClass;
    }

    public double getDraggedCellTransparency() {
        return ValueBindings.get(this, "draggedCellTransparency", draggedCellTransparency, 0.5);
    }

    public void setDraggedCellTransparency(double draggedCellTransparency) {
        this.draggedCellTransparency = draggedCellTransparency;
    }
}