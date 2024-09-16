package com.agriconnect.agrilink;
import java.util.List;

public class AirtableResponse {
    private List<UserRecord> records;

    public List<UserRecord> getRecords() {
        return records;
    }

    public void setRecords(List<UserRecord> records) {
        this.records = records;
    }

    public static class UserRecord {
        private String id;
        private Fields fields;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Fields getFields() {
            return fields;
        }

        public void setFields(Fields fields) {
            this.fields = fields;
        }
    }

    public static class Fields {
        private String Phone;
        private String Role;
        private String Password;
        private String Email;
        private String Name;

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String phone) {
            Phone = phone;
        }

        public String getRole() {
            return Role;
        }

        public void setRole(String role) {
            Role = role;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }
    }
}