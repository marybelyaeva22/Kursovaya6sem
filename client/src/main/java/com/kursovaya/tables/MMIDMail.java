package com.kursovaya.tables;

public class MMIDMail extends MakeIDMail {
    private String model;
    public MMIDMail(String make,String model,Integer id,String mail){
        super(make,id,mail);
        this.model=model;
    }
    public String getModel() {
        return model;
    }
}
