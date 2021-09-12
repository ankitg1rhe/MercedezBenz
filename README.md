# MercedezBenz

1. ServiceOne and ServiceTwo are two services created in Java.

2. Communication between services is event based
	- When the event is written to file by one service
	- The event gets triggered in another service

3. Data is stored in files
	- data/employee.xml
	- data/employee.csv

4. Data is of type employee
	{ "employeeId": 5, "name": "Chetan Verma", "dateOfBirth": "24 January 1995",
		"salary": 65000.0, "age": 32  }
	- each employee have unique id associated with him 

5. ServiceOne is SpringBoot Application
	- This service starts on port 1001 e.g. : http://localhost:1001
	- Service one provides three api endpoints
		1. GET api ->  /employee/{employeeId}
			- employeeId is path variable
			- if two employees with same id present in bothe xml and csv file, employee in xml file is returned
			- writes request to "apis/read_request.txt"
			- reads response from "apis/read_response.txt"
			
		2. POST api -> /employee/{type}/add
			- type is either XML or CSV
			- this api takes employee in JSON format as request body
			- if employee with given id already exists, error is thrown in ServiceTwo
			- writes request to apis/write_request.txt
		
		3. PUT api  -> /employee/{type}/update
			- type is either XML or CSV
			- this api takes employee in JSON format as request body
			- if employee with given id already exists, particular employee gets updated
			- writes request to apis/update_request.txt
			
6. ServiceTwo is stand alone Java Maven project, supporting to events written in apis/* files 
	- For GET api
		- reads request from "apis/read_request.txt"
		- write response to "apis/read_response.txt"
	- For POST api
		- reads request from "apis/write_request.txt"
		- add content to data files based on file type
	- For PUT api
		- reads request from "apis/update_request.txt"
		- update content to data files based on file type
		
7. Commands
	1. Run for both services
		"mvn compile"
		"mvn clean install"
		"mvn package"
	2. JAR files created from both services have been added to Deployable_Services folder
	

8. Running Services
	Below files added for events happening
	/data/_data_files
	/apis/_apis_request_response_files
	
	- use command "java -jar ServiceOne-0.0.1-SNAPSHOT.jar" to run service one
	- use command "java -jar ServiceTwo-0.0.1-SNAPSHOT-jar-with-dependencies.jar" to run service two
	

	