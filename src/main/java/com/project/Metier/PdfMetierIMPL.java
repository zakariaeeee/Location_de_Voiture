package com.project.Metier;

import com.project.DAO.AdminRepository;
import com.project.DAO.ClientRepository;
import com.project.DAO.ReservationRepository;
import com.project.DAO.VoitureRepository;
import com.project.Entities.Admin;
import com.project.Entities.Client;
import com.project.Entities.Reservation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Component
public class PdfMetierIMPL implements IPdfMetier{
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    VoitureRepository voitureRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AdminRepository adminRepository;
    @Override
    public void generatePDF(HttpServletRequest request,int idRes) throws IOException {
        HttpSession session= request.getSession();
        int idAdmin= (int) session.getAttribute("id");
        int idClient= (int) session.getAttribute("idClient");


        PDDocument document = new PDDocument();

        PDPage mypage = new PDPage();
        document.addPage(mypage);
        try {
            //Prepare Content Stream
            PDPageContentStream cs = new PDPageContentStream(document, mypage);

            PDImageXObject logo = PDImageXObject.createFromFile("C:\\Users\\YASSINE\\Desktop\\EHEI 4 eme anne\\JEE\\TP\\Location_de_Voiture\\src\\main\\resources\\static\\cssClient\\images\\logo.png", document);
            cs.drawImage(logo, 25, 700, 100, 100);


            //Writing Single Line text
            //Writing the Invoice title
            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 20);
            float centerX = mypage.getMediaBox().getWidth() / 2;
            float y = 750;

            cs.newLineAtOffset(centerX+150, y);
            LocalDate currentDate = LocalDate.now();
            cs.showText("Le "+ currentDate);
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 18);
            cs.newLineAtOffset(centerX, 690);
            cs.showText("Facture du Reservation");
            cs.endText();

            //Writing Multiple Lines
            //writing the customer details
            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            cs.setLeading(20f);
            cs.newLineAtOffset(60, 610);
            Client client=clientRepository.findById(idClient);
            cs.showText("Client :   ");
            cs.newLine();
            cs.showText("Téléphone:  ");
            cs.newLine();
            cs.showText("E-mail:  ");
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            cs.setLeading(20f);
            cs.newLineAtOffset(170, 610);
            cs.showText(client.getNom());
            cs.newLine();
            cs.showText(client.getTelephone());
            cs.newLine();
            cs.showText(client.getEmail());
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            cs.setLeading(20f);
            cs.newLineAtOffset(350, 610);
            Admin admin=adminRepository.findById(idAdmin);
            cs.showText("Admin :   ");
            cs.newLine();
            cs.showText("Téléphone:  ");
            cs.newLine();
            cs.showText("E-mail:  ");
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            cs.setLeading(20f);
            cs.newLineAtOffset(440, 610);
            cs.showText(admin.getNom());
            cs.newLine();
            cs.showText(admin.getTelephone());
            cs.newLine();
            cs.showText(admin.getEmail());
            cs.endText();


            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            cs.newLineAtOffset(80, 540);
            cs.showText("Voiture");
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            cs.newLineAtOffset(200, 540);
            cs.showText("Date Debut");
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            cs.newLineAtOffset(310, 540);
            cs.showText("Date Fin");
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            cs.newLineAtOffset(410, 540);
            cs.showText("Prix");
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 12);
            cs.setLeading(20f);
            Reservation reservation=reservationRepository.findById(idRes);
            cs.newLineAtOffset(80, 520);
            cs.showText(reservation.getVoiture().getNom());
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(200, 520);
            cs.showText(reservation.getDate_de_debut().toString());
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(310, 520);
            cs.showText(reservation.getDate_de_fin().toString());
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 12);
            cs.setLeading(20f);
            cs.newLineAtOffset(410, 520);
            cs.showText(String.valueOf(reservation.getVoiture().getPrix()));
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            cs.newLineAtOffset(310, (500-(20*5)));
            cs.showText("Total: ");
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.TIMES_ROMAN, 14);
            //Calculating where total is to be written using number of products
            cs.newLineAtOffset(410, (500-(20*5)));
            cs.showText(String.valueOf(reservation.getPaiement().getCout()));
            cs.endText();

            //Close the content stream
            cs.close();
            //Save the PDF
            document.save("C:\\Users\\YASSINE\\Desktop\\EHEI 4 eme anne\\JEE\\TP\\Location_de_Voiture\\src\\main\\resources\\static\\cssAdmin\\facture\\facture.pdf");
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
