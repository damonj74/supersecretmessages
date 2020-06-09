/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.autonowire.supersecretmessages;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.codec.binary.Base64;


/**
 *
 * @author Damon Jones
 */
public class MessageGenerator {
    public static void main(String[] args) throws IOException {
        MessageGenerator mg = new MessageGenerator();
        mg.execute();
    }
    
    public void execute() throws IOException {
        File file = new File(
	getClass().getClassLoader().getResource("data.txt").getFile()
        );
        String text = readText(file.getAbsolutePath());
        System.out.println("Incoming Text: " + text);
        Base64 encoder = new Base64();
        String val = encoder.encodeAsString(text.getBytes());
        System.out.println("Encoded: " + val);
        String val2 = new String(encoder.decode(val));
        System.out.println("Decoded: " + val2);
    }
    
    public String readText(String fileName) throws IOException {
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
