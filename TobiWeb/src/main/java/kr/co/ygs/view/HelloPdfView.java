package kr.co.ygs.view;

import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class HelloPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(
            Map<String, Object> model,
            Document document,
            PdfWriter pdfWriter,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) throws Exception {
        Chapter chapter = new Chapter(new Paragraph("Spring message"), 1);
        chapter.add(new Paragraph((String)model.get("message")));

        document.add(chapter);
    }

}
