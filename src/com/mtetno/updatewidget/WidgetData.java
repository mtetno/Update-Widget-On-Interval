package com.mtetno.updatewidget;

public class WidgetData {

    private String widgetName;
    private String widgetDesc;

    public WidgetData(String name, String desc) {
        this.widgetName = name;
        this.widgetDesc = desc;
    }

    public String getWidgetName() {
        return widgetName;
    }

    public String getWidgetDesc() {
        return widgetDesc;
    }

}
