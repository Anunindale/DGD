/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.developertools.tasksheets;

import java.util.Date;

/**
 *
 * @author asd_admin
 */
public class TaskSheetsReportDS {
 
   private String bugNumber; 
   private String type; 
   private String summary; 
   private String assigned; 
   private double time; 
   private  String date; 
   private String client; 

    public String getBugNumber() {
        return bugNumber;
    }

    public void setBugNumber(String bugNumber) {
        this.bugNumber = bugNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAssigned() {
        return assigned;
    }

    public void setAssigned(String assigned) {
        this.assigned = assigned;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

   

    
    }
