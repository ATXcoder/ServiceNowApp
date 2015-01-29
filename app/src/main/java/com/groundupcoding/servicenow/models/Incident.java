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
    private String description;
    private String assigned_to;
    private String contact_type;
    private String opened_by;
    private String sys_id;
    private String incident_state;
    private String caller_id;

    public class Incidents{
        private List<Incident> records;

        public List<Incident> getRecords() {
            return records;
        }

        public void setRecords(List<Incident> records) {
            this.records = records;
        }
    }

    public String getCaller_id() {
        return caller_id;
    }

    public void setCaller_id(String caller_id) {
        this.caller_id = caller_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    /**
     * DISPLAY VALUES
     */
    private String dv_impact;
    private String dv_urgency;
    private String dv_priority;
    private String dv_category;
    private String dv_severity;
    private String dv_active;
    private String dv_number;
    private String dv_comments_and_work_notes;
    private String dv_opened_at;
    private String dv_short_description;
    private String dv_description;
    private String dv_assigned_to;
    private String dv_contact_type;
    private String dv_opened_by;
    private String dv_sys_id;
    private String dv_incident_state;
    private String dv_caller_id;

    public String getDv_impact() {
        return dv_impact;
    }

    public void setDv_impact(String dv_impact) {
        this.dv_impact = dv_impact;
    }

    public String getDv_urgency() {
        return dv_urgency;
    }

    public void setDv_urgency(String dv_urgency) {
        this.dv_urgency = dv_urgency;
    }

    public String getDv_priority() {
        return dv_priority;
    }

    public void setDv_priority(String dv_priority) {
        this.dv_priority = dv_priority;
    }

    public String getDv_category() {
        return dv_category;
    }

    public void setDv_category(String dv_category) {
        this.dv_category = dv_category;
    }

    public String getDv_severity() {
        return dv_severity;
    }

    public void setDv_severity(String dv_severity) {
        this.dv_severity = dv_severity;
    }

    public String getDv_active() {
        return dv_active;
    }

    public void setDv_active(String dv_active) {
        this.dv_active = dv_active;
    }

    public String getDv_number() {
        return dv_number;
    }

    public void setDv_number(String dv_number) {
        this.dv_number = dv_number;
    }

    public String getDv_comments_and_work_notes() {
        return dv_comments_and_work_notes;
    }

    public void setDv_comments_and_work_notes(String dv_comments_and_work_notes) {
        this.dv_comments_and_work_notes = dv_comments_and_work_notes;
    }

    public String getDv_opened_at() {
        return dv_opened_at;
    }

    public void setDv_opened_at(String dv_opened_at) {
        this.dv_opened_at = dv_opened_at;
    }

    public String getDv_short_description() {
        return dv_short_description;
    }

    public void setDv_short_description(String dv_short_description) {
        this.dv_short_description = dv_short_description;
    }

    public String getDv_description() {
        return dv_description;
    }

    public void setDv_description(String dv_description) {
        this.dv_description = dv_description;
    }

    public String getDv_assigned_to() {
        return dv_assigned_to;
    }

    public void setDv_assigned_to(String dv_assigned_to) {
        this.dv_assigned_to = dv_assigned_to;
    }

    public String getDv_contact_type() {
        return dv_contact_type;
    }

    public void setDv_contact_type(String dv_contact_type) {
        this.dv_contact_type = dv_contact_type;
    }

    public String getDv_opened_by() {
        return dv_opened_by;
    }

    public void setDv_opened_by(String dv_opened_by) {
        this.dv_opened_by = dv_opened_by;
    }

    public String getDv_sys_id() {
        return dv_sys_id;
    }

    public void setDv_sys_id(String dv_sys_id) {
        this.dv_sys_id = dv_sys_id;
    }

    public String getDv_incident_state() {
        return dv_incident_state;
    }

    public void setDv_incident_state(String dv_incident_state) {
        this.dv_incident_state = dv_incident_state;
    }

    public String getDv_caller_id() {
        return dv_caller_id;
    }

    public void setDv_caller_id(String dv_caller_id) {
        this.dv_caller_id = dv_caller_id;
    }
}
