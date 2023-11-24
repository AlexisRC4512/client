package com.service.client.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "personal_client")
@Entity
public class personal_client extends client{
    @Id
    @Column(name = "Personal_Account_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Personal_Account_ID;
    @Column(name = "Name")
    private String Name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "addres")
    private String addres;
    @Column(name = "Email")
    private String Email;

    public personal_client(String type_product, String name_product, int personal_Account_ID, String name, String last_name, String addres, String email) {
        super(type_product, name_product);
        Personal_Account_ID = personal_Account_ID;
        Name = name;
        this.last_name = last_name;
        this.addres = addres;
        Email = email;
    }
    public personal_client(String type_product, String name_product, String name, String last_name, String addres, String email) {
        super(type_product, name_product);
        Name = name;
        this.last_name = last_name;
        this.addres = addres;
        Email = email;
    }
    public personal_client() {}
}
