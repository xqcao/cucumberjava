package com.clinecode.onecline.config;

import org.springframework.boot.test.context.SpringBootTest;

import io.cucumber.spring.CucumberContextConfiguration;
import com.clinecode.onecline.OneclineApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = OneclineApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberTestConfig {
}