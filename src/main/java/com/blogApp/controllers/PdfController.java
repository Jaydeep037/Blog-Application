package com.blogApp.controllers;

//import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogApp.payload.UserResponse;
//import com.blogApp.services.PdfService;
import com.blogApp.services.UserService;
import com.blogApp.services.Impl.PdfServiceImpl;
import com.lowagie.text.DocumentException;

@RestController
@RequestMapping("/pdf")
public class PdfController {
	
//	@Autowired
//	PdfService pdfService;
	
	@Autowired
	UserService userService;
	
//	@GetMapping("/createPdf")
//	public ResponseEntity<InputStreamResource> createPdf() {
//	ByteArrayInputStream	pdf =pdfService.createPdf();   
//	
//	HttpHeaders httpHeaders =  new HttpHeaders();
//	httpHeaders.add("Content-Disposition", "inline;file=lcwd.pdf");
//	
//	return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(pdf));
//		
//	}

	@GetMapping("/users/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
         
         UserResponse userResponse = this.userService.getAllUsers(0, 50, "name", "asc");
                
        PdfServiceImpl exporter = new PdfServiceImpl(userResponse.getUser());
        exporter.export(response);
         
    }
	
}
