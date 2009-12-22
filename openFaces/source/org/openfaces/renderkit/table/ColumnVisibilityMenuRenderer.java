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
package org.openfaces.renderkit.table;

import org.openfaces.component.action.MenuItem;
import org.openfaces.component.table.AbstractTable;
import org.openfaces.component.table.BaseColumn;
import org.openfaces.component.table.ColumnVisibilityMenu;
import org.openfaces.renderkit.TableUtil;
import org.openfaces.renderkit.action.PopupMenuRenderer;
import org.openfaces.renderkit.select.SelectBooleanCheckboxImageManager;
import org.openfaces.util.ResourceUtil;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.List;

public class ColumnVisibilityMenuRenderer extends PopupMenuRenderer {
    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ColumnVisibilityMenu cvm = (ColumnVisibilityMenu) component;
        List<UIComponent> menuChildren = cvm.getChildren();
        menuChildren.clear();
        UIComponent parent = cvm.getParent();
        if (!(parent instanceof AbstractTable))
            throw new FacesException("<o:columnVisibilityMenu> can only be inserted into the \"columnMenu\" facet of the <o:dataTable> or <o:treeTable> components.");
        AbstractTable table = (AbstractTable) parent;
        cvm.getAttributes().put(PopupMenuRenderer.ATTR_DEFAULT_INDENT_CLASS, "o_popup_menu_indent o_columnVisibilityMenuIndent");

        List<BaseColumn> visibleColumns = table.getColumnsForRendering();
        List<BaseColumn> allColumns = table.getAllColumns();
        for (BaseColumn column : allColumns) {
            MenuItem menuItem = new MenuItem();
            menuItem.setValue(TableUtil.obtainColumnHeader(column));
            boolean columnVisible = visibleColumns.contains(column);
            menuItem.setIconUrl(ResourceUtil.getInternalResourceURL(context,
                    SelectBooleanCheckboxImageManager.class,
                    columnVisible
                            ? SelectBooleanCheckboxImageManager.DEFAULT_SELECTED_IMAGE
                            : SelectBooleanCheckboxImageManager.DEFAULT_UNSELECTED_IMAGE,
                    false));
            menuChildren.add(menuItem);
        }
        super.encodeBegin(context, component);
    }

}
