package com.example.dbms.api;


import com.fasterxml.jackson.annotation.JsonProperty;

public class GetresponseMessage extends ResponseMessage {
    private String nationality;
    public GetresponseMessage(String nationality, int code, String message) {
        super(code, message);
        this.nationality = nationality;
    }
    @JsonProperty
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String id) {
        this.nationality = nationality;
    }
}
