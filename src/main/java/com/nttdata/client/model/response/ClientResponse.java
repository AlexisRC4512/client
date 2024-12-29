package com.nttdata.client.model.response;

import com.nttdata.client.model.entity.Client;
import com.nttdata.client.model.enums.SubTypeClient;
import com.nttdata.client.model.enums.TypeClient;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
        private String id;
        private String name;
        private TypeClient type;
        private Integer documentNumber;
        private String address;
        private String phone;
        private String email;
        private SubTypeClient subTypeClient;
}
