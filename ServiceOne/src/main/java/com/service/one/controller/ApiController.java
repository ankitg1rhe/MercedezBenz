package com.service.one.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.service.one.entity.Employee;
import com.service.one.service.ApiService;
import com.service.one.utility.Utils;

@RestController
public class ApiController {

	@Autowired
	private ApiService apiService;

	@RequestMapping(value = "/employee/{employeeId}", method = RequestMethod.GET)
	public Employee getEmployee(@PathVariable("employeeId") int employeeId) {
		return apiService.getEmployee(employeeId);
	}

	@RequestMapping(value = "/employee/{type}/add", method = RequestMethod.POST)
	public boolean addEmployee(
			@PathVariable("type") Utils.FILE_TYPE type, 
			@RequestBody Employee employee
	) {
		System.out.println(type);
		return apiService.addEmployee(employee, type);
	}

	@RequestMapping(value = "/employee/{type}/update", method = RequestMethod.PUT)
	public boolean updateEmployee(
			@PathVariable("type") Utils.FILE_TYPE type, 
			@RequestBody Employee employee
	) {
		System.out.println(type);
		return apiService.updateEmployee(employee, type);
	}
}
