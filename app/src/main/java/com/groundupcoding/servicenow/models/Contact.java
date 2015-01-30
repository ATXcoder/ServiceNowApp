package com.groundupcoding.servicenow.models;

import java.util.List;

/**
 * Created by Thomas on 1/20/2015.
 */
public class Contact {
    String manager;
    String dv_manager;
    String dv_company;
    String dv_department;
    String dv_title;
    String dv_name;
    String dv_street;
    String dv_last_name;
    String dv_first_name;
    String dv_email;
    String dv_phone;
    String dv_location;
    String dv_active;
    String dv_city;
    String dv_zip;
    String dv_state;
    String dv_vip;
    String dv_photo;
    String dv_building;
    String dv_mobile_phone;

    public class Contacts {
        private List<Contact> records;

        public List<Contact> getRecords() {
            return records;
        }

        public void setRecords(List<Contact> records) {
            this.records = records;
        }
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getDv_manager() {
        return dv_manager;
    }

    public void setDv_manager(String dv_manager) {
        this.dv_manager = dv_manager;
    }

    public String getDv_company() {
        return dv_company;
    }

    public void setDv_company(String dv_company) {
        this.dv_company = dv_company;
    }

    public String getDv_department() {
        return dv_department;
    }

    public void setDv_department(String dv_department) {
        this.dv_department = dv_department;
    }

    public String getDv_title() {
        return dv_title;
    }

    public void setDv_title(String dv_title) {
        this.dv_title = dv_title;
    }

    public String getDv_name() {
        return dv_name;
    }

    public void setDv_name(String dv_name) {
        this.dv_name = dv_name;
    }

    public String getDv_street() {
        return dv_street;
    }

    public void setDv_street(String dv_street) {
        this.dv_street = dv_street;
    }

    public String getDv_last_name() {
        return dv_last_name;
    }

    public void setDv_last_name(String dv_last_name) {
        this.dv_last_name = dv_last_name;
    }

    public String getDv_first_name() {
        return dv_first_name;
    }

    public void setDv_first_name(String dv_first_name) {
        this.dv_first_name = dv_first_name;
    }

    public String getDv_email() {
        return dv_email;
    }

    public void setDv_email(String dv_email) {
        this.dv_email = dv_email;
    }

    public String getDv_phone() {
        return dv_phone;
    }

    public void setDv_phone(String dv_phone) {
        this.dv_phone = dv_phone;
    }

    public String getDv_location() {
        return dv_location;
    }

    public void setDv_location(String dv_location) {
        this.dv_location = dv_location;
    }

    public String getDv_active() {
        return dv_active;
    }

    public void setDv_active(String dv_active) {
        this.dv_active = dv_active;
    }

    public String getDv_city() {
        return dv_city;
    }

    public void setDv_city(String dv_city) {
        this.dv_city = dv_city;
    }

    public String getDv_zip() {
        return dv_zip;
    }

    public void setDv_zip(String dv_zip) {
        this.dv_zip = dv_zip;
    }

    public String getDv_state() {
        return dv_state;
    }

    public void setDv_state(String dv_state) {
        this.dv_state = dv_state;
    }

    public String getDv_vip() {
        return dv_vip;
    }

    public void setDv_vip(String dv_vip) {
        this.dv_vip = dv_vip;
    }

    public String getDv_photo() {
        return dv_photo;
    }

    public void setDv_photo(String dv_photo) {
        this.dv_photo = dv_photo;
    }

    public String getDv_building() {
        return dv_building;
    }

    public void setDv_building(String dv_building) {
        this.dv_building = dv_building;
    }

    public String getDv_mobile_phone() {
        return dv_mobile_phone;
    }

    public void setDv_mobile_phone(String dv_mobile_phone) {
        this.dv_mobile_phone = dv_mobile_phone;
    }
}
