package com.nttdata.client.model.entity;

import com.nttdata.client.model.enums.SubTypeClient;
import com.nttdata.client.model.enums.TypeClient;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity representing a client.
 * Maps to the "client" collection in MongoDB.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "client")
public class Client {

    /**
     * Unique identifier for the client.
     */
    @Id
    private String id;

    /**
     * Name of the client.
     */
    private String name;

    /**
     * Type of the client (e.g., individual, company).
     */
    private TypeClient type;
    /**
     * Document number of the client.
     */

    private Integer documentNumber;

    /**
     * Address of the client.
     */
    private String address;

    /**
     * Phone number of the client.
     */
    private String phone;

    /**
     * Email address of the client.
     */
    private String email;
    /**
     * Type of the subType client (e.g., 	VIP, PYME).
     */

    private SubTypeClient subType;
}
