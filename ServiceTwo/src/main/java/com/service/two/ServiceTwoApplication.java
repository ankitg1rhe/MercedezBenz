package com.service.two;

public class ServiceTwoApplication {

	public static void main(String[] args) {

		ApplicationService appService = new ApplicationService();
		while (true) {
			appService.startReadService();
			appService.startWriteService();
			appService.startUpdateService();
		}
	}
}
