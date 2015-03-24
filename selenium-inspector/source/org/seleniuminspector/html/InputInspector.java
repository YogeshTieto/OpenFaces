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
package org.seleniuminspector.html;

import org.openqa.selenium.By;
import org.seleniuminspector.ElementByLocatorInspector;
import org.seleniuminspector.ElementByReferenceInspector;
import org.seleniuminspector.ElementInspector;

/**
 * @author Dmitry Pikhulya
 */
public class InputInspector extends ElementByReferenceInspector {
    public InputInspector(ElementInspector inputElement) {
        super(inputElement);
        assertNodeName("input");
    }

    public InputInspector(String locator) {
        super(new ElementByLocatorInspector(locator));
        assertNodeName("input");
    }


    public String type() {
        return evalExpression("type");
    }

    public String name() {
        return evalExpression("name");
    }

    public String value() {
        return evalExpression("value");
    }

    public boolean disabled() {
        return evalBooleanExpression("disabled");
    }


    public void assertValue(String value) {
        assertExpressionEquals("value", value);
    }

    public void type(String text) {
        getDriver().findElement(By.xpath(getXPath())).sendKeys(text);
    }

    public void typeKeys(String text) {
        getSelenium().typeKeys(asSeleniumLocator(), text);
    }

    public void clear() {
        getDriver().findElement(By.xpath(getXPath())).clear();
    }
}
