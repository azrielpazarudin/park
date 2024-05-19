package com.park.park.utils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.park.park.model.User;
import java.awt.image.BufferedImage;

@Component
public class QrCode {

    public static byte[] generateQrcode(User user)throws WriterException, IOException {
        try {
            
      
        // Path resourceDirectory = Paths.get("src","main","resources","static"); 
        // String qrCodePath =  resourceDirectory.toFile().getAbsolutePath();
        // String qrCodeName = qrCodePath+"/" + user.getName() + user.getNim() + "-QRCODE.png";
        var qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
                "ID:" + user.getId() + "\n" + 
                "Name:" + user.getName() + "\n" + 
                "Nim:" + user.getNim() + "\n" + 
                "Plat:" + user.getPlat() + "\n" + 
                "Major:"+ user.getJurusan(),
                BarcodeFormat.QR_CODE, 400, 400);
        // Path path = FileSystems.getDefault().getPath(qrCodeName);
        // MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        MatrixToImageConfig config = new MatrixToImageConfig();
        try {
            BufferedImage image = new BufferedImage(bitMatrix.getWidth(), bitMatrix.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
            int onColor = config.getPixelOnColor();
            int offColor = config.getPixelOffColor();
            int[] rowPixels = new int[bitMatrix.getWidth()];
            BitArray row = new BitArray(bitMatrix.getWidth());
            for (int y = 0; y < bitMatrix.getHeight(); y++) {
            row = bitMatrix.getRow(y, row);
            for (int x = 0; x < bitMatrix.getWidth(); x++) {
                rowPixels[x] = row.get(x) ? onColor : offColor;
            }
                image.setRGB(0, y, bitMatrix.getWidth(), 1, rowPixels, 0, bitMatrix.getWidth());
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte [] myByte = baos.toByteArray();
            baos.close();    
            return myByte;  
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    } catch (Exception e) {
        e.printStackTrace();
    }
        return null;
    }
}
