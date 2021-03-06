<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://openfaces.org/" prefix="o" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Tasks</title>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen,projection"/>
    <link rel="stylesheet" href="css/navigation.css" type="text/css" media="screen,projection"/>
    <link rel="stylesheet" href="css/sidebar.css" type="text/css" media="screen,projection"/>
    <link rel="stylesheet" href="css/content.css" type="text/css" media="screen,projection"/>

    <link rel="stylesheet" href="css/print.css" type="text/css" media="print"/>
    <link rel="stylesheet" href="css/handheld.css" type="text/css" media="handheld"/>

    <link rel="shortcut icon" href="favicon.ico" type="image/vnd.microsoft.icon"/>
    <link rel="icon" href="favicon.ico" type="image/vnd.microsoft.icon"/>
    <link rel="stylesheet" href="css/mail.css" type="text/css" media="screen,projection"/>
    <script type="text/javascript">
        function requestsSelectionChanged() {
            var cancelChangesBtn = O$('form:cancelChangesLink');
            var inEditingState = cancelChangesBtn != null;

            if (inEditingState) {
                O$('form:cancelChangesLink').onclick();
            }
        }
    </script>
</head>
<body>
<f:view>
    <h:form id="form">

        <o:ajaxSettings onajaxend="refreshButtons();"/>

        <div id="Navigation" class="NavigationSelected-Tasks">
            <div class="NavigationPanel" id="Navigation-Email">
                <h1>Email</h1>
                <a href="mail.jsf" class="label">
                    <img src="images/titles/navigation-email.png" alt="Email"/>
                </a>
            </div>

            <div class="NavigationPanel" id="Navigation-Calendar">
                <h1>Calendar</h1>
                <a href="calendar.jsf" class="label">
                    <img src="images/titles/navigation-calendar.png" alt="Calendar"/>
                </a>
            </div>

            <div class="NavigationPanel" id="Navigation-Tasks">
                <h1>Tasks</h1>

                <h:panelGroup id="operations">
                    <h:panelGroup id="saveChangesPanelId" rendered="#{DayTableBean.editingRequest}"
                                  styleClass="NavigationControls">
                        <o:commandLink id="saveChangesLink"
                                       styleClass="NavigationControls-tasks-save"
                                       render="form:tasks form:operations"
                                       actionListener="#{DayTableBean.saveChanges}">
                            <h:outputText id="saveChanges" value="Save Changes"/>
                        </o:commandLink>
                        <o:commandLink id="cancelChangesLink"
                                       styleClass="CancelChanges"
                                       render="form:tasks form:operations"
                                       actionListener="#{DayTableBean.cancelEditing}">
                            <h:outputText id="cancelChanges" value="Cancel"/>
                        </o:commandLink>
                    </h:panelGroup>
                    <h:panelGroup id="editingRequestPanelId" rendered="#{!DayTableBean.editingRequest}"
                                  styleClass="NavigationControls">
                        <o:commandLink id="addTaskLink"
                                       styleClass="NavigationControls-tasks-new"
                                       render="form:tasks form:operations"
                                       actionListener="#{DayTableBean.addNewTask}">
                            <h:outputText id="addTask" value="New Task"/>
                        </o:commandLink>
                        <o:commandLink id="editTaskLink"
                                       styleClass="NavigationControls-tasks-edit"
                                       render="form:tasks form:operations"
                                       actionListener="#{DayTableBean.editTask}">
                            <h:outputText id="editTask" value="Edit Task"/>
                        </o:commandLink>
                        <o:commandLink id="deleteTaskLink"
                                       styleClass="NavigationControls-tasks-delete"
                                       render="form:tasks"
                                       actionListener="#{DayTableBean.deleteTask}">
                            <h:outputText id="deleteTask" value="Delete Task"/>
                        </o:commandLink>
                        <img src="images/icons/navigation-tasks-separator.png" alt="" style="float:left; margin:5px;"/>
                        <rich:dropDownMenu id="richMenu"
                                           value="Columns"
                                           submitMode="ajax"
                                           direction="bottom-right" styleClass="NavigationControls-tasks-columns">
                            <rich:menuGroup value="Hide" direction="right">
                                <rich:menuItem value="Summary" action="#{DayTableBean.hideSummaryColumn}"
                                               reRender="form:tasks, form:richMenu"
                                               disabled="#{!DayTableBean.summaryColumnRendered}"/>
                                <rich:menuItem value="Start Time" action="#{DayTableBean.hideStartTimeColumn}"
                                               reRender="form:tasks, form:richMenu"
                                               disabled="#{!DayTableBean.startTimeColumnRendered}"/>
                                <rich:menuItem value="End Time" action="#{DayTableBean.hideEndTimeColumn}"
                                               reRender="form:tasks, form:richMenu"
                                               disabled="#{!DayTableBean.endTimeColumnRendered}"/>
                                <rich:menuItem value="Description" action="#{DayTableBean.hideDescriptionColumn}"
                                               reRender="form:tasks, form:richMenu"
                                               disabled="#{!DayTableBean.descriptionColumnRendered}"/>
                            </rich:menuGroup>
                            <rich:menuGroup value="Show" direction="right">
                                <rich:menuItem value="Summary" action="#{DayTableBean.showSummaryColumn}"
                                               reRender="form:tasks, form:richMenu"
                                               disabled="#{DayTableBean.summaryColumnRendered}"/>
                                <rich:menuItem value="Start Time" action="#{DayTableBean.showStartTimeColumn}"
                                               reRender="form:tasks, form:richMenu"
                                               disabled="#{DayTableBean.startTimeColumnRendered}"/>
                                <rich:menuItem value="End Time" action="#{DayTableBean.showEndTimeColumn}"
                                               reRender="form:tasks, form:richMenu"
                                               disabled="#{DayTableBean.endTimeColumnRendered}"/>
                                <rich:menuItem value="Description" action="#{DayTableBean.showDescriptionColumn}"
                                               reRender="form:tasks, form:richMenu"
                                               disabled="#{DayTableBean.descriptionColumnRendered}"/>
                            </rich:menuGroup>
                        </rich:dropDownMenu>
                    </h:panelGroup>
                </h:panelGroup>

            </div>
        </div>

        <div id="Sidebar" class="Sidebar">
            <div class="Sidebar-content">

            </div>

            <div class="SidebarFooter">
                <a class="ButtonPageSource" onclick="O$('form:pageSource').showCentered(); return false;">
                    <span>View page source</span>
                </a>

                <div class="Copyright">
                    <p>&copy;&nbsp;TeamDev Ltd. | OpenFaces.org</p>
                </div>
            </div>
        </div>

        <div id="Content" class="Content" style="overflow-y: hidden">
            <o:dataTable id="tasks" var="task"
                         value="#{DayTableBean.tasks}"
                         headerRowClass="TableHeader"
                         sortedColumnHeaderStyle="background: url('images/treetable/header_selected.gif') repeat-x;"
                         sortedColumnClass="SortedColumn"
                         bodyRowStyle="height: 40px;"
                         bodyOddRowStyle="background: #f4f4f4; height: 40px;"
                         style="width: 100%; height: 100%;">
                <o:scrolling/>
                <o:singleRowSelection onchange="requestsSelectionChanged();"
                                      rowData="#{DayTableBean.selectedTask}"/>
                <o:row condition="#{DayTableBean.editingThisRow}" styleClass="EditableRow">
                    <o:cell>
                        <h:inputText value="#{task.name}" style="width: 90%" autocomplete="off"/>
                    </o:cell>
                    <o:cell>
                        <rich:calendar value="#{task.startDate}"
                                       popup="true"
                                       datePattern="d/M/yy HH:mm"
                                       locale="#{DayTableBean.locale}"/>
                    </o:cell>
                    <o:cell>
                        <rich:calendar value="#{task.endDate}"
                                       popup="true"
                                       datePattern="d/M/yy HH:mm"
                                       locale="#{DayTableBean.locale}"/>
                    </o:cell>
                    <o:cell>
                        <h:inputText value="#{task.description}" style="width: 90%" autocomplete="off"/>
                    </o:cell>
                </o:row>
                <o:column id="summaryColumn"
                          sortingExpression="#{task.name}"
                          rendered="#{DayTableBean.summaryColumnRendered}"
                          style="padding-left: 15px;">
                    <f:facet name="header">
                        <h:outputText value="Summary" id="summaryColumnHeader" style="width:100%;"/>
                    </f:facet>
                    <h:outputText value="#{task.name}"/>
                </o:column>
                <o:column sortingExpression="#{task.startDate}" id="startTimeColumn"
                          rendered="#{DayTableBean.startTimeColumnRendered}">
                    <f:facet name="header">
                        <h:outputText value="Start Time" id="startTimeColumnHeader"/>
                    </f:facet>
                    <h:outputText value="#{task.startDate}"
                                  converter="#{DayTableBean.taskDateConverter}"/>
                </o:column>
                <o:column id="endTimeColumn"
                          sortingExpression="#{task.endDate}"
                          rendered="#{DayTableBean.endTimeColumnRendered}">
                    <f:facet name="header">
                        <h:outputText value="End Time" id="endTimeColumnHeader"/>
                    </f:facet>
                    <h:outputText value="#{task.endDate}"
                                  converter="#{DayTableBean.taskDateConverter}"/>
                </o:column>
                <o:column id="descriptionColumn"
                          sortingExpression="#{task.description}"
                          rendered="#{DayTableBean.descriptionColumnRendered}">
                    <f:facet name="header">
                        <h:outputText value="Description" id="descriptionColumnHeader"/>
                    </f:facet>
                    <h:outputText value="#{task.description}" escape="false"/>
                </o:column>
            </o:dataTable>


        </div>

        <o:window id="pageSource" width="70%" height="70%"
                  styleClass="SourceView"
                  modal="true"
                  modalLayerClass="SourceViewModalLayer">
            <f:facet name="caption"><h:outputText value="tasks.jsp"/></f:facet>
            <rich:insert src="/tasks.jsp" highlight="xhtml"/>
        </o:window>
    </h:form>
</f:view>
</body>
</html>