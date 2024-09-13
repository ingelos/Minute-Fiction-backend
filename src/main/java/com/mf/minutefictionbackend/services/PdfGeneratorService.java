package com.mf.minutefictionbackend.services;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.mf.minutefictionbackend.models.Story;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PdfGeneratorService {

    public void exportStoriesToPdf(List<Story> stories, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=stories.pdf";
        response.setHeader(headerKey, headerValue);

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        for (Story story : stories) {
            document.add(new Paragraph(story.getTitle()));
            document.add(new Paragraph(story.getContent()));
            document.add(Chunk.NEWLINE);
        }
        document.close();
    }
}
