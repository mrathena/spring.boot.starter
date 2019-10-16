package com.mrathena.test;

import com.mrathena.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author mrathena on 2019-10-16 23:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class BaseTest {

	@Test
	public void test() {
		System.out.println("Hello World");
	}

}