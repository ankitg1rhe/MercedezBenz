package com.service.two.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import com.service.two.entity.Employee;
import com.service.two.utility.Utils;

public class CSVService implements ReadWriteService{

	@Override
	public Employee read(String employeeId) {
		File file = new File(Utils.CSV_FILE);
		if (!file.exists()) {
			System.out.println(Utils.CSV_FILE + " file not found error");
			return null;
		}

		Employee emp = null;
		try {
			FileReader reader = new FileReader(file);
			CSVReader csvReader = new CSVReader(reader);
			csvReader.readNext();
			String[] nextRecord;

			while ((nextRecord = csvReader.readNext()) != null) {
				if (nextRecord[0].trim().equals(employeeId.trim())) {
					emp = new Employee();
					emp.setEmployeeId(Integer.parseInt(nextRecord[0].trim()));
					emp.setName(nextRecord[1]);
					emp.setDateOfBirth(nextRecord[2]);
					emp.setSalary(Double.valueOf(nextRecord[3]));
					emp.setAge(Integer.valueOf(nextRecord[4]));
					break;
				}
			}
		} catch (IOException | CsvValidationException e1) {
			e1.printStackTrace();
		}

		return emp;
	}

	@Override
	public void write(Employee emp) {
		File file = new File(Utils.CSV_FILE);
		if (!file.exists()) {
			System.out.println(Utils.CSV_FILE + " file not found error");
			return;
		}

		String[] arr = convertEmployeeToArr(emp);
		try {
			FileReader reader = new FileReader(file);
			CSVReader csvReader = new CSVReader(reader);
			List<String[]> csvBody = csvReader.readAll();

			for (String[] err : csvBody) {
				if (err[0].equalsIgnoreCase(String.valueOf(emp.getEmployeeId()))) {
					csvReader.close();
					System.out.println("Employee with id (" + emp.getEmployeeId() + ") already exists");
					return;
				}
			}
			csvReader.close();
			
			csvBody.add(arr);
			FileWriter writer = new FileWriter(file);
			CSVWriter csvWriter = new CSVWriter(writer);
			csvWriter.writeAll(csvBody);
			csvWriter.flush();
			csvWriter.close();
		} catch (IOException | CsvException e) {
			e.printStackTrace();
		}
	}

	private String[] convertEmployeeToArr(Employee emp) {
		String[] arr = new String[5];
		arr[0] = String.valueOf(emp.getEmployeeId());
		arr[1] = String.valueOf(emp.getName());
		arr[2] = String.valueOf(emp.getDateOfBirth());
		arr[3] = String.valueOf(emp.getSalary());
		arr[4] = String.valueOf(emp.getAge());
		return arr;
	}

	@Override
	public void update(Employee emp) {
		File file = new File(Utils.CSV_FILE);
		if (!file.exists()) {
			System.out.println(Utils.CSV_FILE + " file not found error");
			return;
		}

		try {
			FileReader reader = new FileReader(file);
			CSVReader csvReader = new CSVReader(reader);
			List<String[]> csvBody = csvReader.readAll();

			for (String[] arr : csvBody) {
				if (arr[0].equalsIgnoreCase(String.valueOf(emp.getEmployeeId()))) {
					arr[1] = emp.getName();
					arr[2] = emp.getDateOfBirth();
					arr[3] = String.valueOf(emp.getSalary());
					arr[4] = String.valueOf(emp.getAge());
					break;
				}
			}
			csvReader.close();

			FileWriter writer = new FileWriter(file);
			CSVWriter csvWriter = new CSVWriter(writer);
			System.out.println("Writing to csv file");
			csvWriter.writeAll(csvBody);
			csvWriter.flush();
			csvWriter.close();
			System.out.println("Completed");
		} catch (IOException | CsvException e) {
			e.printStackTrace();
		}
	}
}
