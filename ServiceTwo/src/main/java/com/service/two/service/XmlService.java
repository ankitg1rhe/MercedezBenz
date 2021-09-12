package com.service.two.service;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.service.two.entity.Employee;
import com.service.two.utility.Utils;

public class XmlService implements ReadWriteService {

	@Override
	public Employee read(String employeeId) {
		Employee emp = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			File xmlFile = new File(Utils.XML_FILE);
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(xmlFile);

			dom.normalizeDocument();
			Element root = dom.getDocumentElement();
			NodeList nodeList = root.getElementsByTagName("employee");
			for (int i = 0, length = nodeList.getLength(); i < length; i++) {
				Node node = nodeList.item(i);
				Element employee = (Element) node;
				String empId = employee.getElementsByTagName("id").item(0).getTextContent();
				if (empId.trim().equals(employeeId.trim())) {
					emp = new Employee();
					emp.setEmployeeId(Integer.parseInt(employeeId));
					String dob = employee.getElementsByTagName("dob").item(0).getTextContent();
					emp.setDateOfBirth(dob);
					String salary = employee.getElementsByTagName("salary").item(0).getTextContent();
					emp.setSalary(Double.valueOf(salary));
					String name = employee.getElementsByTagName("name").item(0).getTextContent();
					emp.setName(name);
					String age = employee.getElementsByTagName("age").item(0).getTextContent();
					emp.setAge(Integer.parseInt(age.trim()));
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emp;
	}

	@Override
	public void write(Employee emp) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			File xmlFile = new File(Utils.XML_FILE);
			Document dom = builder.parse(xmlFile);
			Element root = dom.getDocumentElement();
			Element employee = convertEmployeeToXML(emp, dom);
			root.appendChild(employee);

			dom.getDocumentElement().normalize();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource domSource = new DOMSource(dom);
			StreamResult result = new StreamResult(xmlFile);
			transformer.transform(domSource, result);

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException | TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	private Element convertEmployeeToXML(Employee emp, Document dom) {
		Element employee = dom.createElement("employee");

		Element id = dom.createElement("id");
		id.setTextContent(emp.getEmployeeId() + "");
		employee.appendChild(id);

		Element name = dom.createElement("name");
		name.setTextContent(emp.getName());
		employee.appendChild(name);

		Element dob = dom.createElement("dob");
		dob.setTextContent(emp.getDateOfBirth());
		employee.appendChild(dob);

		Element salary = dom.createElement("salary");
		salary.setTextContent(emp.getSalary() + "");
		employee.appendChild(salary);

		Element age = dom.createElement("age");
		age.setTextContent(emp.getAge() + "");
		employee.appendChild(age);

		return employee;
	}

	@Override
	public void update(Employee emp) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			File xmlFile = new File(Utils.XML_FILE);
			Document dom = builder.parse(xmlFile);
			Element root = dom.getDocumentElement();
			NodeList nodeList = root.getElementsByTagName("employee");
			String employeeId = String.valueOf(emp.getEmployeeId());

			for (int i = 0, length = nodeList.getLength(); i < length; i++) {
				Node node = nodeList.item(i);
				Element employee = (Element) node;
				String empId = employee.getElementsByTagName("id").item(0).getTextContent();
				if (empId.trim().equals(employeeId.trim())) {
					Element name = (Element) employee.getElementsByTagName("name").item(0);
					name.setTextContent(emp.getName());
					Element dob = (Element) employee.getElementsByTagName("dob").item(0);
					dob.setTextContent(emp.getDateOfBirth());
					Element salary = (Element) employee.getElementsByTagName("salary").item(0);
					salary.setTextContent(String.valueOf(emp.getSalary()));
					Element age = (Element) employee.getElementsByTagName("age").item(0);
					age.setTextContent(String.valueOf(emp.getAge()));
					break;
				}
			}

			dom.getDocumentElement().normalize();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource domSource = new DOMSource(dom);
			StreamResult result = new StreamResult(xmlFile);
			transformer.transform(domSource, result);

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException | TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

}
