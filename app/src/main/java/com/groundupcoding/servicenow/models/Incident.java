package com.groundupcoding.servicenow.models;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Thomas on 1/27/2015.
 */
public class Incident {
    private String impact;
    private String urgency;
    private String priority;
    private String category;
    private String severity;
    private String active;
    private String number;
    private String comments_and_work_notes;
    private String opened_at;
    private String short_description;
    private String assigned_to;
    private String contact_type;
    private String opened_by;
    private String sys_id;
    private String incident_state;

    public class Incidents{
        private List<Incident> records;

        public List<Incident> getRecords() {
            return records;
        }

        public void setRecords(List<Incident> records) {
            this.records = records;
        }
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getComments_and_work_notes() {
        return comments_and_work_notes;
    }

    public void setComments_and_work_notes(String comments_and_work_notes) {
        this.comments_and_work_notes = comments_and_work_notes;
    }

    public String getOpened_at() {
        return opened_at;
    }

    public void setOpened_at(String opened_at) {
        this.opened_at = opened_at;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getAssigned_to() {
        return assigned_to;
    }

    public void setAssigned_to(String assigned_to) {
        this.assigned_to = assigned_to;
    }

    public String getContact_type() {
        return contact_type;
    }

    public void setContact_type(String contact_type) {
        this.contact_type = contact_type;
    }

    public String getOpened_by() {
        return opened_by;
    }

    public void setOpened_by(String opened_by) {
        this.opened_by = opened_by;
    }

    public String getSys_id() {
        return sys_id;
    }

    public void setSys_id(String sys_id) {
        this.sys_id = sys_id;
    }

    public String getIncident_state() {
        return incident_state;
    }

    public void setIncident_state(String incident_state) {
        this.incident_state = incident_state;
    }
}
