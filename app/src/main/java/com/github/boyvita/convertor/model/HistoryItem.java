package com.github.boyvita.convertor.model;

import java.util.Date;

public class HistoryItem {
    private String valuteFrom;
    private String valuteTo;
    private Double valueFrom;
    private Double valueTo;
    private String date;

    public String getValuteFrom() {
        return valuteFrom;
    }

    public void setValuteFrom(String valuteFrom) {
        this.valuteFrom = valuteFrom;
    }

    public String getValuteTo() {
        return valuteTo;
    }

    public void setValuteTo(String valuteTo) {
        this.valuteTo = valuteTo;
    }

    public Double getValueFrom() {
        return valueFrom;
    }

    public void setValueFrom(Double valueFrom) {
        this.valueFrom = valueFrom;
    }

    public Double getValueTo() {
        return valueTo;
    }

    public void setValueTo(Double valueTo) {
        this.valueTo = valueTo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HistoryItem(String valuteFrom, String valuteTo, double valueFrom, double valueTo, String date) {
        this.valuteFrom = valuteFrom;
        this.valuteTo = valuteTo;
        this.valueFrom = valueFrom;
        this.valueTo = valueTo;
        this.date = date;
    }
}
