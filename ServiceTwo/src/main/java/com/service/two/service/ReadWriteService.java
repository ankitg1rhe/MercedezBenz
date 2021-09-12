package com.service.two.service;

import com.service.two.entity.Employee;

public interface ReadWriteService {

	public Employee read(String employeeId);

	public void write(Employee employee);

	public void update(Employee employee);
}
