package com.service.one.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.service.one.entity.Employee;
import com.service.one.entity.EmployeeWithFileType;
import com.service.one.utility.Utils;

@Service
public class ApiService {

	public Employee getEmployee(int employeeId) {
		String fileName = Utils.READ_REQUEST;
		Employee employee = null;
		try {
			PrintWriter writer = new PrintWriter(new File(fileName));
			System.out.println("Employee id : " + employeeId);
			System.out.println("Writing to file : " + fileName);
			writer.print(String.valueOf(employeeId));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		long start = System.currentTimeMillis();
		String object = null;
		while (object == null) {
			long current = System.currentTimeMillis();
			if ((current - start) > 5000)
				return null;
			object = readResponseFile(Utils.READ_RESPONSE);
		}
		clearContent(Utils.READ_RESPONSE);
		System.out.println("In service one : "  +object);
		employee = fromString(object);
		return (employee.getEmployeeId() == employeeId) ? employee : null;
	}
	
	public String readResponseFile(String fileName) {
		File file = new File(fileName);
		Scanner sc;
		String object = null;
		try {
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				System.out.println("Found in Read response file");
				object = sc.nextLine().trim();
			}
		} catch (FileNotFoundException e) {
//				e.printStackTrace();
		}
		return object;
	}

	public boolean addEmployee(Employee emp, Utils.FILE_TYPE type) {
		System.out.println("Adding employee : ");
		EmployeeWithFileType empWithType = new EmployeeWithFileType(emp, type);
		System.out.println(empWithType);
		String fileName = Utils.WRITE_REQUEST;
		return writeToFile(fileName, empWithType);
	}

	public boolean updateEmployee(Employee emp, Utils.FILE_TYPE type) {
		EmployeeWithFileType empWithType = new EmployeeWithFileType(emp, type);
		String fileName = Utils.UPDATE_REQUEST;
		return writeToFile(fileName, empWithType);
	}

	public boolean writeToFile(String fileName, EmployeeWithFileType object) {
		File file = new File(fileName);
		try {
			PrintWriter writer = new PrintWriter(file);
			writer.print(object.toString());
			writer.close();
			System.out.println(file.exists());
			System.out.println("Written to file : " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String getObject(String fileName) {
		File file = new File(fileName);
		Scanner sc;
		String object = null;
		try {
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				object = sc.nextLine().trim();
			}
		} catch (FileNotFoundException e) {
//				e.printStackTrace();
		}
		clearContent(fileName);
		return object;
	}

	public Employee fromString(String obj) {
		if(obj == null || obj == "") return null;
		System.out.println("Object found in service one : " + obj);
		String[] arr = obj.split(",");
		Employee ret = new Employee();
		int id = Integer.parseInt(arr[0]);
		ret.setEmployeeId(id);
		ret.setName(arr[1]);
		ret.setDateOfBirth(arr[2]);
		ret.setSalary(Double.valueOf(arr[3]));
		ret.setAge(Integer.valueOf(arr[4]));
		return ret;
	}

	public void clearContent(String fileName) {
		File file = new File(fileName);
		try {
			new FileOutputStream(file).close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
