package com.service.client.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BusinessClientTest {

        @Test
        void testBusinessClientCreation() {

            business_client client = new business_client("Type", "Name", 1, "Business", "Company", "Headline", 3);


            assertNotNull(client);
            assertEquals("Type", client.getType_product());
            assertEquals("Name", client.getName_product());
            assertEquals(1, client.getBusiness_AccountId());
            assertEquals("Business", client.getBusiness_name());
            assertEquals("Company", client.getCompany_name());
            assertEquals("Headline", client.getHeadline());
            assertEquals(3, client.getNum_Authorized_Signers());
        }

        @Test
        void testBusinessClientDefaultConstructor() {

            business_client client = new business_client();

            assertNotNull(client);
            assertNull(client.getType_product());
            assertNull(client.getName_product());
            assertEquals(0, client.getBusiness_AccountId());
            assertNull(client.getBusiness_name());
            assertNull(client.getCompany_name());
            assertNull(client.getHeadline());
            assertEquals(0, client.getNum_Authorized_Signers());
        }

        @Test
        void testBusinessClientSetter() {

            business_client client = new business_client();


            client.setBusiness_name("NewBusiness");
            client.setCompany_name("NewCompany");
            client.setHeadline("NewHeadline");
            client.setNum_Authorized_Signers(5);


            assertEquals("NewBusiness", client.getBusiness_name());
            assertEquals("NewCompany", client.getCompany_name());
            assertEquals("NewHeadline", client.getHeadline());
            assertEquals(5, client.getNum_Authorized_Signers());
        }

    }

