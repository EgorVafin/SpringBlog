package com.example.demo.service;

import com.example.demo.api.response.CaptchaResponse;
import com.github.cage.GCage;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Base64;

@Service
public class CaptchaGenerator {

    public CaptchaResponse createCaptcha() {


        GCage gCage = new GCage();

        String captchaText = gCage.getTokenGenerator().next();
        String secretCode = gCage.getTokenGenerator().next() + gCage.getTokenGenerator().next() + gCage.getTokenGenerator().next();

        BufferedImage image = gCage.drawImage(captchaText);

        BufferedImage resImage = resize(image, 100, 35);

        byte[] newImage = imgToBase64String(resImage, "png");

        String encodedString = Base64.getEncoder().encodeToString(newImage);

        return new CaptchaResponse(secretCode, "data:image/png;base64, " + encodedString);



//
//        // 1) Создать рисунок в виде base64 строки
//        2) Cгенерировать секретный код (случайная строка 20 символов буквы и цифры)
//        3) Очистить старые записи в базе
//                4) Вернуть капча сложно


        return null;
    }


    public byte[] imgToBase64String(final RenderedImage img, final String formatName) {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            ImageIO.write(img, formatName, os);
            os.close();
            return os.toByteArray();
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    public BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
