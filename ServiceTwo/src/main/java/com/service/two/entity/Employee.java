package com.service.two.entity;

import java.io.Serializable;
import java.util.Objects;

public class Employee implements Serializable {

	private static final long serialVersionUID = 5509837108493990868L;
	private int employeeId;
	private String name;
	private String dateOfBirth;
	private double salary;
	private int age;

	public Employee() {

	}

	public Employee(int employeeId, String name, String dateOfBirth, double salary, int age) {
		this.employeeId = employeeId;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.salary = salary;
		this.age = age;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Employee {" 
				+ "employeeId=" + employeeId 
				+ ", name=" + name 
				+ ", dateOfBirth=" + dateOfBirth
				+ ", salary=" + salary 
				+ ", age=" + age + "}";
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeId, name, dateOfBirth, salary, age);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (employeeId != other.employeeId || age != other.age
				|| Double.doubleToLongBits(salary) != Double.doubleToLongBits(other.salary))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
