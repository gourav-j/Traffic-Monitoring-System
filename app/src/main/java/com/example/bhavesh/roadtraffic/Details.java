package com.example.bhavesh.roadtraffic;

/**
 * Created by Bhavesh on 16-02-2018.
 */

public class Details {
    private String source;
    private String destination;
    private String dateTime;
    private String frequency;
    private String remarks;
    public Details(String source, String destination, String dateTime, String frequency,String remarks) {
        this.source = source;
        this.destination = destination;
        this.dateTime = dateTime;
        this.frequency = frequency;
        this.remarks = remarks;
    }
    Details(){}


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getRemarks(){ return remarks; }

    public void setRemarks(String remarks) { this.remarks = remarks; }
}
