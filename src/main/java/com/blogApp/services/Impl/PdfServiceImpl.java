package com.blogApp.services.Impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blogApp.services.PdfService;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PdfServiceImpl implements PdfService{
	
	private Logger logger = LoggerFactory.getLogger(PdfServiceImpl.class);

	@Override
	public ByteArrayInputStream createPdf() {
		logger.info("Create Pdf started:");
		String title="Welcome to blog";
		String content = "We provide technical content";
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		Document document = new Document();
		
		PdfWriter.getInstance(document, out);
		document.open();
		
		Font titleFont =  FontFactory.getFont(FontFactory.HELVETICA_BOLD,20);
		Paragraph titlepara = new Paragraph(title,titleFont);
		titlepara.setAlignment(Element.ALIGN_CENTER);
		document.add(titlepara);	
		
		Font contentFont =  FontFactory.getFont(FontFactory.HELVETICA,18);
		Paragraph contentpara =  new Paragraph(content,contentFont);
		
		document.add(contentpara);
		
		document.close();
		
		return new ByteArrayInputStream(out.toByteArray());
	}

}
