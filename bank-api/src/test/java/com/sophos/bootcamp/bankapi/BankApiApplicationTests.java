package com.sophos.bootcamp.bankapi;

import com.sophos.bootcamp.bankapi.controllers.ClientController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BankApiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private ClientController clientController;

	@Test
	public void testUserService() {
		// Agregar código para realizar la prueba aquí
	}

}
