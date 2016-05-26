package com.github.bilak.oauth2poc.resource;

import com.github.bilak.oauth2poc.resource.ResourceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ResourceApplication.class)
@WebAppConfiguration
public class ResourceApplicationTests {

	@Test
	public void contextLoads() {
	}

}
