package com.service.two.entity;

import java.io.Serializable;

import com.service.two.utility.Utils;

public class EmployeeWithFileType implements Serializable {

	private static final long serialVersionUID = -2658621868108286894L;
	private Utils.FILE_TYPE fileType;
	private Employee employee;

	public EmployeeWithFileType(Employee employee, Utils.FILE_TYPE fileType) {
		this.fileType = fileType;
		this.employee = employee;
	}

	public Utils.FILE_TYPE getFileType() {
		return fileType;
	}

	public void setFileType(Utils.FILE_TYPE fileType) {
		this.fileType = fileType;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(this.fileType.toString());
		sb.append(",").append(this.employee.getEmployeeId());
		sb.append(",").append(this.employee.getName());
		sb.append(",").append(this.employee.getDateOfBirth());
		sb.append(",").append(this.employee.getSalary());
		sb.append(",").append(this.employee.getAge());
		return sb.toString();
	}
}
