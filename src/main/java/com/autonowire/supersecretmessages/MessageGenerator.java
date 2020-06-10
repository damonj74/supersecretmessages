/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.autonowire.supersecretmessages;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;


/**
 *
 * @author Damon Jones
 */
public class MessageGenerator {
    public static void main(String[] args) throws IOException {
        MessageGenerator mg = new MessageGenerator();
        String text = "You can blame my friends on the other side!";
        String secretKey = "PrincesAndTheFrog";
        String encryptedResult = mg.superEncryptText(text, secretKey);
        System.out.println("Encrypted Result: " + encryptedResult);
        String decryptedResult = mg.superDecryptText(encryptedResult, secretKey);
        System.out.println("Decrypted Result: " + decryptedResult);
        //v1.0 Patch
        System.out.println("This is a v1.0 patch 1.0.1!");
    }
    
    private void executeTest() throws IOException {
        
        File file = new File(
	getClass().getClassLoader().getResource("data.txt").getFile()
        );
        String text = readText(file.getAbsolutePath());
        System.out.println("Incoming Text: " + text);
        Base64 encoder = new Base64();
        String val = encoder.encodeAsString(text.getBytes());
        System.out.println("Encoded: " + val);
        String secretKey = val;
        String val2 = new String(encoder.decode(val));
        System.out.println("Decoded: " + val2);
        
        String encryptedString = AES.encrypt(text, secretKey);
        System.out.println("Encrypted AES: " + encryptedString);
        String decryptedString = AES.decrypt(encryptedString, secretKey) ;
        System.out.println("Decrypted AES: " + decryptedString);
    }
    
    public String superEncryptText(String text, String secretKey) {
        //System.out.println("Incoming Text: " + text);
        Base64 encoder = new Base64();
        String val = encoder.encodeAsString(text.getBytes());
        //System.out.println("Base Encoded: " + val);
        String encryptedString = AES.encrypt(val, secretKey);
        //System.out.println("Encrypted AES: " + encryptedString);
        return encryptedString;
    }
    
    public String superDecryptText(String encryptedString, String secretKey) {
        Base64 encoder = new Base64();
        String decryptedString = AES.decrypt(encryptedString, secretKey) ;
        //System.out.println("Decrypted AES: " + decryptedString);
        String baseDecodedString = new String(encoder.decode(decryptedString));
        //System.out.println("Base Decoded: " + baseDecodedString);
        return baseDecodedString;
    }
    
    private byte[] convertTextToImage(String text) throws IOException {
        byte[] imageBytes;
        OutputStream os = null;
        BufferedImage bufferedImage = new BufferedImage(170, 30,
        BufferedImage.TYPE_INT_RGB);
    
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, 200, 50);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial Black", Font.BOLD, 20));
        graphics.drawString(text, 10, 25);
        ImageIO.write(bufferedImage, "jpg", os);
        ByteArrayOutputStream bos = (ByteArrayOutputStream)os;
        imageBytes = bos.toByteArray();
        return imageBytes;
    }
    
    private String readText(String fileName) throws IOException {
        String text = "";
        Path path = Paths.get(fileName);
        byte[] bytes = Files.readAllBytes(path);
        List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
        for (String line: allLines) {
            text += line + "\n";
        }
        return text;
    }
}
