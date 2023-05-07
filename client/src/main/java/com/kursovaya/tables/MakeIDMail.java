package com.kursovaya.tables;

import java.io.Serializable;

public class MakeIDMail implements Serializable {
   private String make;
   private Integer id;
   private String mail;

   public MakeIDMail(String mk,Integer i,String ml){
        make=mk;
        id=i;
        mail=ml;
   }

   public String getMake() {
      return make;
   }

   public Integer getId() {
      return id;
   }

   public String getMail() {
      return mail;
   }
}
