package com.groundupcoding.servicenow.models;

import java.util.List;

/**
 * Created by Thomas on 5/30/2015.
 */
public class Record {

    private String id;

    public class Records{
        private List<Record> records;

        public List<Record> getRecords(){return records;};

        public void setRecords(List<Record> records){this.records = records;};
    }

    public void setId(String id){this.id = id;};
    public String getId(){return this.id;};
}
