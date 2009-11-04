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

O$.Filters = {
  _showFilter: function(componentId, filterId) {
    var filteredComponent = O$(componentId);
    var f = O$(filterId);

    if (!filteredComponent._filtersToHide)
      filteredComponent._filtersToHide = [];
    filteredComponent._filtersToHide.push(f);
  },

  _filterComponent: function(componentId, filterId, filterField) {
    O$.cancelDelayedAction(null, componentId);
    // setTimeout in the following script is needed to avoid page blinking when using combo-box filter in IE (JSFC-2263)
    setTimeout(function() {
      O$.Filters._submitFilter(componentId, filterId, filterField);
    }, 1);
  },

  _submitFilter: function(componentId, filterId, filterField) {
    var filteredComponent = O$(componentId);
    if (filteredComponent._useAjax)
      O$._submitComponentWithField(componentId, filterField, null, filterId ? [filterId] : []);
    else
      O$.submitEnclosingForm(filteredComponent);
  },

  _filterFieldKeyPressHandler: function(componentId, filterId, filterField, event, autoFilteringDelay) {
    if (event.keyCode == 13) {

      // filter only in cases when onchange is not fired by pressing Enter to avoid double filter requests
      var onchangeFired = filterField.nodeName.toUpperCase() != "INPUT";
      if (!onchangeFired)
        O$.Filters._filterComponent(componentId, filterId, filterField);

      event.cancelBubble = true;
      return false;
    } else if (autoFilteringDelay != -1) {
      var valueBefore = filterField.getValue ? filterField.getValue() : filterField.value;
      setTimeout(function() {
        var valueAfter = filterField.getValue ? filterField.getValue() : filterField.value;
        if (valueBefore == valueAfter)
          return;
        O$.invokeFunctionAfterDelay(function() {
          O$.Filters._filterComponent(componentId, filterId, filterField);
        }, autoFilteringDelay, componentId);
      }, 1);

    }
    var inFieldNavigation = (event.keyCode >= 35 && event.keyCode <= 40);
    if (inFieldNavigation)
      event.cancelBubble = true;
    return true;
  }


};