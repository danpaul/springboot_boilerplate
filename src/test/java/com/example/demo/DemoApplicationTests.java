package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// @SpringBootTest tells Spring Boot to start up the full application
// (load all beans, read application.properties, connect to the DB, etc.)
// before running the tests in this class.
@SpringBootTest
class DemoApplicationTests {

	// @Test marks this method as a JUnit test case that the test runner will execute.
	@Test
	void contextLoads() {
		// This method is intentionally empty.
		// The test passes if the Spring application context starts successfully,
		// and fails if any bean, configuration, or dependency is broken.
		// It acts as a "smoke test" to confirm the app can boot at all.
	}

}
