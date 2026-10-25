package com.lipan.pdfrag;

import org.springframework.boot.SpringApplication;

public class TestPdfRagApplication {

	public static void main(String[] args) {
		SpringApplication.from(PdfRagApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
