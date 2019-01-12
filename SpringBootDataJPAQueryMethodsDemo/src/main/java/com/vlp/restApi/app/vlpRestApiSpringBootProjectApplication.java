package com.vlp.restApi.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vlp.restApi.app.service.AnthresholdService;

@SpringBootApplication
public class vlpRestApiSpringBootProjectApplication implements CommandLineRunner{

	@Autowired
	private AnthresholdService anthresholdService;
	
	public static void main(String[] args) {
		SpringApplication.run(vlpRestApiSpringBootProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		/*
		 * List<NoasVLPInventory> list=
		 * anthresholdService.getPersonsInfoByLastName("80012878282");
		 * System.out.println(list.size());
		 */
		//anthresholdService.delete1("123");
		 
	}

	

}
