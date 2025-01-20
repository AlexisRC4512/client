package com.nttdata.client;

import com.nttdata.client.controller.ClientController;
import com.nttdata.client.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest
class ClientApplicationTests {
	@Autowired
	private ClientController clientController;
	@Autowired
	private ClientService clientService;
	@Autowired
	private ApplicationContext applicationContext;
	@Test
	void contextLoads() {
		assertThat(clientController).isNotNull();
		assertThat(clientService).isNotNull();
	}
	@Test
	void testApplicationStarts() {
		ClientApplication.main(new String[] {});
		assertThat(applicationContext).isNotNull();

	}
}
