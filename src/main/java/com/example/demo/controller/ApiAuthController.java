package com.example.demo.controller;

import com.example.demo.api.response.AuthCheckResponse;
import com.example.demo.api.response.CaptchaResponse;
import com.github.cage.GCage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    @RequestMapping("/check")
    @ResponseBody
    public AuthCheckResponse apiAuthCheck() {
        AuthCheckResponse authCheckResponse = new AuthCheckResponse();
        authCheckResponse.setResult(false);

        return authCheckResponse;
    }

    @RequestMapping("/captcha")
    @ResponseBody
    public CaptchaResponse captchaResponse() {

        GCage gCage = new GCage();

        String captchaText = gCage.getTokenGenerator().next();
        String secretCode = gCage.getTokenGenerator().next() + gCage.getTokenGenerator().next() + gCage.getTokenGenerator().next();

        BufferedImage image = gCage.drawImage(captchaText);

        BufferedImage resImage = resize(image, 100, 35);

        byte[] newImage = imgToBase64String(resImage, "png");

        String encodedString = Base64.getEncoder().encodeToString(newImage);

        return new CaptchaResponse("1234", "data:image/png;base64, " + encodedString);
    }

    public static byte[] imgToBase64String(final RenderedImage img, final String formatName) {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            ImageIO.write(img, formatName, os);
            os.close();
            return os.toByteArray();
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

}
