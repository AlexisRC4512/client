package com.nttdata.client.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nttdata.client.model.enums.SubTypeClient;
import com.nttdata.client.model.enums.TypeClient;
import com.nttdata.client.util.ClientSubTypeDeserializer;
import com.nttdata.client.util.ClientTypeDeserializer;
import lombok.*;

@NoArgsConstructor
@Getter
public class ClientRequest {
    private String name;

    @JsonDeserialize(using = ClientTypeDeserializer.class)
    private TypeClient type;

    private Integer documentNumber;

    private String address;

    private String phone;

    private String email;

    @JsonDeserialize(using = ClientSubTypeDeserializer.class)
    private SubTypeClient subTypeClient;

    public ClientRequest(String name, TypeClient type, Integer documentNumber, String address, String phone, String email, SubTypeClient subTypeClient) {
        setName(name);
        setType(type);
        setAddress(address);
        setDocumentNumber(documentNumber);
        setEmail(email);
        setPhone(phone);
        setSubTypeClient(subTypeClient);
    }
    public void setSubTypeClient(SubTypeClient subTypeClient) {
        this.subTypeClient = subTypeClient;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Name must be less than 100 characters");
        }
        this.name = name;
    }



    public void setType(TypeClient type) {
        if (type == null) {
            throw new IllegalArgumentException("Type is required");
        }
        this.type = type;
    }


    public void setDocumentNumber(Integer documentNumber) {
        this.documentNumber = documentNumber;
    }



    public void setAddress(String address) {
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Address is required");
        }
        if (address.length() > 200) {
            throw new IllegalArgumentException("Address must be less than 200 characters");
        }
        this.address = address;
    }



    public void setPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Phone is required");
        }
        if (phone.length() > 15) {
            throw new IllegalArgumentException("Phone must be less than 15 characters");
        }
        this.phone = phone;
    }


    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email should be valid");
        }
        this.email = email;
    }

}
