/*
 * OpenFaces - JSF Component Library 3.0
 * Copyright (C) 2007-2012, TeamDev Ltd.
 * licensing@openfaces.org
 * Unless agreed in writing the contents of this file are subject to
 * the GNU Lesser General Public License Version 2.1 (the "LGPL" License).
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * Please visit http://openfaces.org/licensing/ for more details.
 */
package org.openfaces.component.table.impl;

import org.openfaces.component.table.AbstractTable;
import org.openfaces.component.table.Column;
import org.openfaces.component.table.Columns;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dmitry Pikhulya
 */
public class DynamicColumn extends Column implements DynamicCol {
    public static final String COMPONENT_TYPE = "org.openfaces.DynamicColumn";
    public static final String COMPONENT_FAMILY = "org.openfaces.DynamicColumn";

    private Columns columns;
    private Object colData;
    private int colIndex;

    public DynamicColumn() {
        setRendererType("org.openfaces.DynamicColumnRenderer");
    }

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public void setColumns(Columns columns) {
        this.columns = columns;
    }

    public void setColData(Object colData) {
        this.colData = colData;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    @Override
    public AbstractTable getTable() {
        return columns.getTable();
    }

    @Override
    public UIComponent getFacet(String name) {
        UIComponent component = super.getFacet(name);
        if (component != null)
            return component;
        return columns.getFacet(name);
    }

    @Override
    public UIComponent getHeader() {
        UIComponent header = super.getHeader();
        if (header != null) return header;
        return columns.getHeader();
    }

    @Override
    public UIComponent getFooter() {
        UIComponent footer = super.getFooter();
        if (footer != null) return footer;
        return columns.getFooter();
    }

    @Override
    public UIComponent getSubHeader() {
        UIComponent subHeader = super.getSubHeader();
        if (subHeader != null)
            return subHeader;
        return columns.getSubHeader();
    }

    @Override
    public UIComponent getGroupHeader() {
        UIComponent component = super.getGroupHeader();
        if (component != null)
            return component;
        return columns.getGroupHeader();
    }

    @Override
    public UIComponent getGroupFooter() {
        UIComponent component = super.getGroupFooter();
        if (component != null)
            return component;
        return columns.getGroupFooter();
    }

    @Override
    public UIComponent getInGroupHeader() {
        UIComponent component = super.getInGroupHeader();
        if (component != null)
            return component;
        return columns.getInGroupHeader();
    }

    @Override
    public UIComponent getInGroupFooter() {
        UIComponent component = super.getInGroupFooter();
        if (component != null)
            return component;
        return columns.getInGroupFooter();
    }

    @Override
    public Converter getConverter() {
        return columns.getConverter();
    }

    @Override
    public void setConverter(Converter converter) {
        columns.setConverter(converter);
    }

    public Runnable declareContextVariables() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> requestMap = context.getExternalContext().getRequestMap();

        String var = columns.getVar();
        final Object prevVarValue = requestMap.put(var, colData);
        Object prevIndexVarValue = null;

        String indexVar = columns.getIndexVar();
        if (indexVar != null)
            prevIndexVarValue = requestMap.put(indexVar, colIndex);

        final int prevColIndex = columns.getColumnIndex();
        columns.setColumnIndex(colIndex);
        final Object finalPrevIndexVarValue = prevIndexVarValue;
        return new Runnable() {
            public void run() {
                Map<String, Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();

                String var = columns.getVar();
                requestMap.put(var, prevVarValue);

                String indexVar = columns.getIndexVar();
                if (indexVar != null) {
                    requestMap.put(indexVar, finalPrevIndexVarValue);
                }

                columns.setColumnIndex(prevColIndex);
            }
        };
    }

    private Runnable restoreVariablesRunnable;

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        restoreVariablesRunnable = declareContextVariables();
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    @Override
    public void encodeEnd(FacesContext context) throws IOException {
        restoreVariablesRunnable.run();
    }

    public List<UIComponent> getChildrenForProcessing() {
        return columns.getChildren();
    }

    public Map<String, UIComponent> getFacetsForProcessing() {
        Map<String, UIComponent> facets = new HashMap(columns.getFacets());
        facets.remove(Columns.FACET_COLUMN_COMPONENTS);
        facets.putAll(getFacets());
        return facets;
    }

    @Override
    public void processDecodes(FacesContext context) {
        Runnable restoreVariables = declareContextVariables();
        Collection<UIComponent> facets = getFacetCollection();
        for (UIComponent component : facets) {
            component.processDecodes(context);
        }
        restoreVariables.run();
    }

    private Collection<UIComponent> getFacetCollection() {
        ArrayList<UIComponent> facets = new ArrayList<UIComponent>();
        UIComponent header = getHeader();
        UIComponent footer = getFooter();
        UIComponent subHeader = getSubHeader();
        if (header != null)
            facets.add(header);
        if (footer != null)
            facets.add(footer);
        if (subHeader != null)
            facets.add(subHeader);
        return facets;
    }

    @Override
    public void processValidators(FacesContext context) {
        Runnable restoreVariables = declareContextVariables();
        Collection<UIComponent> facets = getFacetCollection();
        for (UIComponent component : facets) {
            component.processValidators(context);
        }
        restoreVariables.run();
    }

    @Override
    public void processUpdates(FacesContext context) {
        Runnable restoreVariables = declareContextVariables();
        Collection<UIComponent> facets = getFacetCollection();
        for (UIComponent component : facets) {
            component.processUpdates(context);
        }
        restoreVariables.run();
    }

    @Override
    public ExpressionData getExpressionData(ValueExpression expression) {
        Runnable restoreVariables = declareContextVariables();
        try {
            return super.getExpressionData(expression);
        } finally {
            restoreVariables.run();
        }
    }
}