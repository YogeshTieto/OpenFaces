/*
 * OpenFaces - JSF Component Library 2.0
 * Copyright (C) 2007-2012, TeamDev Ltd.
 * licensing@openfaces.org
 * Unless agreed in writing the contents of this file are subject to
 * the GNU Lesser General Public License Version 2.1 (the "LGPL" License).
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * Please visit http://openfaces.org/licensing/ for more details.
 */

package org.openfaces.component.filter;

import org.openfaces.util.ReflectionUtil;

import java.io.Serializable;

public class SimplePropertyLocatorFactory implements PropertyLocatorFactory {

    public PropertyLocator create(Object expression) {
        return new SimplePropertyLocator(expression);
    }

    public static class SimplePropertyLocator extends PropertyLocator implements Serializable {
        public SimplePropertyLocator(Object expression) {
            super(expression);
        }


        public Object getPropertyValue(Object obj) {
            return ReflectionUtil.readProperty(obj, (String) expression);

        }

    }


}
