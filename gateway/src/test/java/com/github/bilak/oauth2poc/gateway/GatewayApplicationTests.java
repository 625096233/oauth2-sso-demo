package com.github.bilak.oauth2poc.gateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by lvasek on 26/05/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = GatewayApplicationTests.class)
@WebAppConfiguration
public class GatewayApplicationTests {

	@Test
	public void contextLoads() {
	}
}
