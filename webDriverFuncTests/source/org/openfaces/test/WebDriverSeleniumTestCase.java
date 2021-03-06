/*
 * OpenFaces - JSF Component Library 3.0
 * Copyright (C) 2007-2014, TeamDev Ltd.
 * licensing@openfaces.org
 * Unless agreed in writing the contents of this file are subject to
 * the GNU Lesser General Public License Version 2.1 (the "LGPL" License).
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * Please visit http://openfaces.org/licensing/ for more details.
 */

package org.openfaces.test;

import com.thoughtworks.selenium.SeleneseTestCase;
import org.openfaces.test.componentInspector.Element;
import org.openfaces.test.componentInspector.FoldingPanelInspector;
import org.openfaces.test.componentInspector.TabbedPaneInspector;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Author: Andrew.Palval@teamdev.com
 */
public abstract class WebDriverSeleniumTestCase  {

    static {
//        Properties properties = new Properties();
//        InputStream resourceAsStream = WebDriverSeleniumTestCase.class.getResourceAsStream("/funcTests.properties");
//        try {
//            properties.load(resourceAsStream);
//        } catch (IOException e) {
//            throw new RuntimeException("Can't obtain Selenium properties", e);
//        }
//        String startUrl = properties.getProperty("org.openfaces.funcTests.startUrl");

    }
     protected static WebDriver driver;

    protected FoldingPanelInspector foldingPanel(String id) {
        return new FoldingPanelInspector(id);
    }

    protected Element element(String id) {
        return new Element(id);
    }

    protected TabbedPaneInspector tabbedPane(String id) {
        return new TabbedPaneInspector(id);
    }
}