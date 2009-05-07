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

import org.openfaces.component.input.DropDownField;
import org.openfaces.component.input.DropDownItem;
import org.openfaces.component.input.DropDownItems;
import org.openfaces.component.table.DataTableFilter;
import org.openfaces.util.StyleUtil;
import org.openfaces.util.DefaultStyles;
import org.openfaces.util.StyleGroup;
import org.openfaces.util.ComponentUtil;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Dmitry Pikhulya
 */
public class DropDownFieldDataTableFilterRenderer extends TextSearchDataTableFilterRenderer {
    protected void configureInputComponent(FacesContext context, DataTableFilter filter, UIInput inputComponent) {
        DropDownField field = (DropDownField) inputComponent;
        field.setOnchange(getFilterSubmissionScript(filter, context));
        field.setOnkeypress(getFilterOnEnterScript(context, filter));
        field.setStyle(filter.getStyle());
        field.setStyleClass(filter.getStyleClass());
        field.setListStyle("font-weight: normal;");

        field.setPromptText(filter.getPromptText());
        field.setPromptTextClass(filter.getPromptTextClass());
        field.setPromptTextStyle(filter.getPromptTextStyle());

        String rolloverItemClass = StyleUtil.getCSSClass(context,
                filter, "background: " + DefaultStyles.getSelectionBackgroundColor() +
                        " !important; color: " + DefaultStyles.getSelectionTextColor() + " !important;", StyleGroup.selectedStyleGroup(), null);
        field.setRolloverListItemClass(rolloverItemClass);

        int childrenCount = field.getChildren().size();
        if (childrenCount != 1) {
            throw new IllegalStateException("Search component of DropDownFieldDataTableFilter should have exactly one child component - " +
                    "the DropDownItems component. children.size = " + childrenCount);
        }
        Object dropDownFieldItems = field.getChildren().get(0);
        if (!(dropDownFieldItems instanceof DropDownItems)) {
            throw new IllegalStateException("Search component of DropDownFieldDataTableFilter should have exactly one child component - " +
                    "instace of DropDownItems component. But was  - " + dropDownFieldItems.toString());
        }
        DropDownItems dropDownItems = (DropDownItems) dropDownFieldItems;
        Collection<String> possibleValuesCollection = filter.calculateAllCriterionNames(context);
        possibleValuesCollection.remove("");
        List<String> availableItems = new ArrayList<String>(possibleValuesCollection);
        List<DropDownItem> itemList = new ArrayList<DropDownItem>(availableItems.size());
        for (String itemStr : availableItems) {
            DropDownItem item = createDropDownItem(context, itemStr);
            itemList.add(item);
        }
        DropDownItem allRecordsItem = createDropDownItem(context, "");
        String allRecordsCriterionName = filter.getAllRecordsCriterionName();
        HtmlOutputText outputText = ComponentUtil.createOutputText(context, allRecordsCriterionName);
        String predefinedCriterionsClass = getPredefinedCriterionClass(context, filter);
        outputText.setStyleClass(predefinedCriterionsClass);
        allRecordsItem.getChildren().add(outputText);
        itemList.add(0, allRecordsItem);
        dropDownItems.setValue(itemList);
        List<UIComponent> children = field.getChildren();
        children.clear();
        children.add(dropDownItems);
    }

    private DropDownItem createDropDownItem(FacesContext context, String text) {
        DropDownItem item = (DropDownItem) context.getApplication().createComponent(DropDownItem.COMPONENT_TYPE);
        item.setValue(text);
        return item;
    }

}
