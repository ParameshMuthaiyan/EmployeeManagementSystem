package com.employeemanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.samplers.ConstSampler;

@Configuration
public class JeagerConfig {
	@Bean
	  public JaegerTracer jaegerTracer() {

	    return new io.jaegertracing.Configuration("EmployeeManagementSystem")
	        .withSampler(new io.jaegertracing.Configuration.SamplerConfiguration().withType(ConstSampler.TYPE)
	        .withParam(1))
	        .withReporter(new io.jaegertracing.Configuration.ReporterConfiguration().withLogSpans(true))
	        .getTracer();
	  }

}
