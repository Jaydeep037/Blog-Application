package com.blogApp.services.Impl;

import java.awt.Color;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

//import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blogApp.entities.User;
import com.blogApp.payload.UserDto;
//import com.blogApp.services.PdfService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
//import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PdfServiceImpl{ // implements PdfService{
	
	private Logger logger = LoggerFactory.getLogger(PdfServiceImpl.class);

	List<UserDto>users;
	
	
	
	public PdfServiceImpl(List<UserDto> users) {
		super();
		this.users = users;
	}


	
	private void writeTableHeaders(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(Color.WHITE);
		
		cell.setPhrase(new Phrase("User Id",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Name",font));
		table.addCell(cell);
		
//		cell.setPhrase(new Phrase("Username",font));
//		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Email",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("About ",font));
		table.addCell(cell);	
	}
	
	private void writeTableData(PdfPTable table) {
		
		
		for (UserDto user : users) {
			table.addCell(String.valueOf(user.getId()));
			table.addCell(user.getName());
			table.addCell(user.getEmail());
			table.addCell(user.getAbout());
		}
	}
	
	public void export(HttpServletResponse response ) throws DocumentException, IOException {
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setSize(14);
		font.setColor(Color.BLUE);
		
		Paragraph p = new Paragraph("List of Users" , font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] {1.5f,3.5f,3.0f,3.0f});
		table.setSpacingBefore(50);
		
		writeTableHeaders(table);
		writeTableData(table);
		
		document.add(table);
		
		document.close();
	}

	
	
	
	
	
	
	
	

//	@Override
//	public ByteArrayInputStream createPdf() {
//		logger.info("Create Pdf started:");
//		
//		String title="Welcome to blog";
//		String content = "We provide technical content";
//		
//		
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		
//		Document document = new Document();
//		
//		PdfWriter.getInstance(document, out);
//		document.open();
//		
//		Font titleFont =  FontFactory.getFont(FontFactory.HELVETICA_BOLD,20);
//		Paragraph titlepara = new Paragraph(title,titleFont);
//		titlepara.setAlignment(Element.ALIGN_CENTER);
//		document.add(titlepara);	
//		
//		Font contentFont =  FontFactory.getFont(FontFactory.HELVETICA,18);
//		Paragraph contentpara =  new Paragraph(content,contentFont);
//		
//		document.add(contentpara);
//		
//		document.close();
//		
//		return new ByteArrayInputStream(out.toByteArray());
//	}
}
