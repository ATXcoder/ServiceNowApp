package com.groundupcoding.servicenow.models;

/**
 * Created by Thomas on 1/20/2015.
 */
public class Request {

    private String description;
    private String priority;
    private String number;
    private String category;
    private String active;
    private String due_date;
    private String assignment_group;
    private String short_description;


    public void setDescription(String description){this.description = description;}
    public void setPriority(String priority){this.priority = priority;}
    public void setNumber(String number){this.number = number;}
    public void setCategory(String category){this.category = category;}
    public void setActive(String active){this.active = active;}
    public void setDue_date(String due_date){this.due_date = due_date;}
    public void setAssignment_group(String assignment_group){this.assignment_group = assignment_group;}
    public void setShort_description(String short_description){this.short_description = short_description;}


    public String getDescription(){return this.description;}
    public String getPriority(){return this.priority;}
    public String getNumber(){return this.number;}
    public String getCategory(){return this.category;}
    public String getActive(){return this.active;}
    public String getDue_date(){return this.due_date;}
    public String getAssignment_group(){return this.assignment_group;}
    public String getShort_description(){return this.short_description;}
}
