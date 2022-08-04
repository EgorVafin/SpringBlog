package com.example.demo.service;

import com.example.demo.api.response.CaptchaResponse;
import com.example.demo.dao.CaptchaRepository;
import com.example.demo.model.CaptchaCode;
import com.github.cage.GCage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.Timestamp;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class CaptchaGenerator {

    private final CaptchaRepository captchaRepository;
    private final CaptchaConfig captchaConfig;

    public CaptchaResponse createCaptcha() {

        GCage gCage = new GCage();

        String captchaText = gCage.getTokenGenerator().next();
        String secretCode = gCage.getTokenGenerator().next() + gCage.getTokenGenerator().next() + gCage.getTokenGenerator().next();

        BufferedImage image = gCage.drawImage(captchaText);
        BufferedImage resImage = resize(image, 100, 35);
        byte[] newImage = imgToBase64String(resImage, "png");
        String encodedString = Base64.getEncoder().encodeToString(newImage);

        CaptchaCode captchaCode = new CaptchaCode();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        captchaCode.setTime(timestamp);
        captchaCode.setCode(captchaText);
        captchaCode.setSecretCode(secretCode);
        captchaRepository.save(captchaCode);

        captchaRepository.deleteByLifeTime(captchaConfig.getLifeTime());

        return new CaptchaResponse(secretCode, "data:image/png;base64, " + encodedString);

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
