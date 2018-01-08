package com.taotao.uuid;


import java.util.UUID;

import org.junit.Test;

public class UUIDTest {

	@Test
	public void testUUID() {
		
		UUID uuid = UUID.randomUUID();
		String result = uuid.toString();
		System.out.println(result);
	}

}
