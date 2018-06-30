package com.example.demo.controller;
import org.springframework.stereotype.Controller;
@Controller
public class HelloController implements HelloControllerImp{

	@Override
	public String hello() {
		return "hello";
	}
}
