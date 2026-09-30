package com.sp.jobportal;

import java.util.TimeZone;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private RedisTemplate redisTemplate;

	static {
		// 🔥 Runs BEFORE everything (even before Spring Boot)
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@Test
	void contextLoads() {
	}

	//@Test
	public void testRedis() {
		redisTemplate.opsForValue().set("country", "india");
		Object country = redisTemplate.opsForValue().get("country");
		Object name = redisTemplate.opsForValue().get("name");
		System.out.println(name);
	}

}
