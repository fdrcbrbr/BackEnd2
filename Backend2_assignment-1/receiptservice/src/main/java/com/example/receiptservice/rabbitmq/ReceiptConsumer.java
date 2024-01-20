package com.example.receiptservice.rabbitmq;

import com.example.receiptservice.config.ReceiptConfig;
import com.example.receiptservice.dto.ReceiptRequest;
import com.example.receiptservice.service.ReceiptService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public record ReceiptConsumer(ReceiptService receiptService) {

    @RabbitListener(queues = ReceiptConfig.QUEUE)
    public void consumer(ReceiptRequest receiptRequest) {
        log.info("Consumed {} from queue", receiptRequest);
        doc(receiptRequest);
        receiptService.send(receiptRequest);
    }

    private void doc(ReceiptRequest receiptRequest){
        Document document = new Document();
        try{
            PdfWriter.getInstance(document, new FileOutputStream(
                    "invoice_" + receiptRequest.toCustomerName() + LocalDateTime.now() + ".pdf")
            );

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            document.add(new Paragraph(new Chunk(
                    receiptRequest.welcomeMessage(), font)));
            document.add(new Paragraph("\n"));
            document.add(new Paragraph(new Chunk( "Customer Name: " +
                    receiptRequest.toCustomerName(), font)));
            document.add(new Paragraph(new Chunk("Item: " +
                    receiptRequest.toItemName(), font)));
            document.add(new Paragraph(new Chunk("Date: " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), font)));


            document.close();
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }

    }


}
