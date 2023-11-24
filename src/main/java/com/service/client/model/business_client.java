package com.service.client.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "business_client")
public class business_client extends client{
    @Id
    @Column(name = "Business_AccountId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Business_AccountId;
    @Column(name = "Business_name")
    private String Business_name;
    @Column(name = "Company_name")
    private String Company_name;
    @Column(name = "Headline")
    private String Headline;
    @Column(name = "Num_Authorized_Signers")
    private int Num_Authorized_Signers;

    public business_client(String type_product, String name_product, int business_AccountId, String business_name, String company_name, String headline, int num_Authorized_Signers) {
        super(type_product, name_product);
        Business_AccountId = business_AccountId;
        Business_name = business_name;
        Company_name = company_name;
        Headline = headline;
        Num_Authorized_Signers = num_Authorized_Signers;
    }
    public business_client(String type_product, String name_product, String business_name, String company_name, String headline, int num_Authorized_Signers) {
        super(type_product, name_product);
        Business_name = business_name;
        Company_name = company_name;
        Headline = headline;
        Num_Authorized_Signers = num_Authorized_Signers;
    }
    public business_client() {

    }
}
