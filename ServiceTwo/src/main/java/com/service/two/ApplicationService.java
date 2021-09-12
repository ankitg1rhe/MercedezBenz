package com.service.two;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.service.two.entity.Employee;
import com.service.two.entity.EmployeeWithFileType;
import com.service.two.service.CSVService;
import com.service.two.service.ReadWriteService;
import com.service.two.service.XmlService;
import com.service.two.utility.Utils;

public class ApplicationService {

	private ReadWriteService csvService;
	private ReadWriteService xmlService;

	public ApplicationService() {
		csvService = new CSVService();
		xmlService = new XmlService();
	}

	public void startReadService() {
		String employeeId = null;
		String fileName = Utils.READ_REQUEST;
		File file = new File(fileName);
		Scanner sc;
		try {
			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				System.out.println("Found in Read request file");
				employeeId = sc.nextLine().trim();
			}
		} catch (FileNotFoundException e) {
//				e.printStackTrace();
		}
		if (null != employeeId && employeeId != "") {
			clearContent(fileName);
			System.out.println("Fetching employee : " + employeeId);
			Employee employee = xmlService.read(employeeId);
			if (employee == null)
				employee = csvService.read(employeeId);
			writeEmployeeResponse(employee);
		}
	}

	public boolean writeEmployeeResponse(Employee object) {
		if(object == null) return false;
		String fileName = Utils.READ_RESPONSE;
		File file = new File(fileName);
		try {
			PrintWriter writer = new PrintWriter(file);
			writer.print(employeeToString(object));
			writer.close();
			System.out.println(file.exists());
			System.out.println("Written to file : " + fileName);
			System.out.println("Employee written in Service two : " + employeeToString(object));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void startWriteService() {
//		System.out.println("Started Write service........");
		String fileName = Utils.WRITE_REQUEST;
		EmployeeWithFileType empWithType;
		empWithType = fromString(getObject(fileName));
		if (null != empWithType) {
			clearContent(fileName);
			System.out.println("Adding employee : " + empWithType.getEmployee());
			clearContent(fileName);
			Utils.FILE_TYPE type = empWithType.getFileType();
			Employee employee = empWithType.getEmployee();
			if (type.equals(Utils.FILE_TYPE.CSV))
				csvService.write(employee);
			else
				xmlService.write(employee);
		}
	}

	public void startUpdateService() {
//		System.out.println("Started Update service........");
		String fileName = Utils.UPDATE_REQUEST;
		EmployeeWithFileType empWithType;
		empWithType = fromString(getObject(fileName));
		if (null != empWithType) {
			clearContent(fileName);
			System.out.println("Updating employee");
			clearContent(fileName);
			Utils.FILE_TYPE type = empWithType.getFileType();
			Employee employee = empWithType.getEmployee();
			if (type.equals(Utils.FILE_TYPE.CSV))
				csvService.update(employee);
			else
				xmlService.update(employee);
		}
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
		return object;
	}

	public EmployeeWithFileType fromString(String obj) {
		if(obj == null || obj == "") return null;
		String[] arr = obj.split(",");
		EmployeeWithFileType ret;
		Utils.FILE_TYPE type = arr[0].equals("CSV") ? Utils.FILE_TYPE.CSV : Utils.FILE_TYPE.XML;
		ret = new EmployeeWithFileType(new Employee(), type);
		int id = Integer.parseInt(arr[1]);
		ret.getEmployee().setEmployeeId(id);
		ret.getEmployee().setName(arr[2]);
		ret.getEmployee().setDateOfBirth(arr[3]);
		ret.getEmployee().setSalary(Double.valueOf(arr[4]));
		ret.getEmployee().setAge(Integer.valueOf(arr[5]));
		return ret;
	}

	public void clearContent(String fileName) {
		File file = new File(fileName);
		try {
			new FileOutputStream(file).close();
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}
	
	public String employeeToString(Employee emp) {
		int id = emp.getEmployeeId();
		StringBuilder sb = new StringBuilder(String.valueOf(id));
		System.out.println("Employee id : " + id);
		sb.append(",").append(emp.getName());
		sb.append(",").append(emp.getDateOfBirth());
		sb.append(",").append(emp.getSalary());
		sb.append(",").append(emp.getAge());
		return sb.toString();
	}
}
