package com.kingcobra.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration("/spring/spring-context.xml")
@WebAppConfiguration("/spring-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest {
}
