package com.park.park.utils;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.park.park.model.User;


@Component
public class QrCode {

    public static void generateQrcode(User user)throws WriterException, IOException {
        try {
            
      
        Path resourceDirectory = Paths.get("src","main","resources","static"); 
        String qrCodePath =  resourceDirectory.toFile().getAbsolutePath();
        String qrCodeName = qrCodePath+"/" + user.getName() + user.getNim() + "-QRCODE.png";
        var qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
                "ID:" + user.getId() + "\n" + 
                "Name:" + user.getName() + "\n" + 
                "Nim:" + user.getNim() + "\n" + 
                "Plat:" + user.getPlat() + "\n" + 
                "Major:"+ user.getJurusan(),
                BarcodeFormat.QR_CODE, 400, 400);
        Path path = FileSystems.getDefault().getPath(qrCodeName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}
