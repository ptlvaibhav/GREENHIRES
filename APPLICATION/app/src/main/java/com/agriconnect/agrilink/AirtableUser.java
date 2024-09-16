package com.agriconnect.agrilink;

import java.util.List;

public class AirtableUser {
    private List<Record> records;

    public AirtableUser(List<Record> records) {
        this.records = records;
    }

    public static class Record {
        private Fields fields;

        public Record(Fields fields) {
            this.fields = fields;
        }

        public static class Fields {
            private String Email;
            private String Name;
            private String Phone;
            private String Password;
            private String Role;

            public Fields(String email, String name, String phone, String password, String role) {
                this.Email = email;
                this.Name = name;
                this.Phone = phone;
                this.Password = password;
                this.Role = role;
            }
        }
    }
}