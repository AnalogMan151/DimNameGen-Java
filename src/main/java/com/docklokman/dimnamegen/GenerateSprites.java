/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.docklokman.dimnamegen;

import static com.docklokman.dimnamegen.FileNameCleaner.cleanFileName;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;


/**
 *
 * @author DocKlokMan
 */
public class GenerateSprites {

    private static String createOutputFolder(String outputFolder) {
        outputFolder = cleanFileName(outputFolder);
        if (outputFolder.equals(""))
        {
            outputFolder = "output";
        }
        new File("./"+outputFolder).mkdirs();
        return outputFolder;
    }
    
    private BufferedImage createNameSprite(String name) throws IOException {
        BufferedImage charmap;
        BufferedImage sprite;
        
        
        // Load alphabet character map
        try {                  
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("assets/VB_Alphabet_ENG.png");
            charmap = ImageIO.read(in);

        } catch (IOException e) {
            System.out.println("VB_Alphabet_ENG.png was not loaded.");
            return null;
        }
        
        
        // Map each letter image to an array indexed by the letter
        Map<String, BufferedImage> letter = new HashMap<>();
        
        letter.put("A", charmap.getSubimage(0, 0, 9, 15));
        letter.put("B", charmap.getSubimage(10, 0, 7, 15));
        letter.put("C", charmap.getSubimage(18, 0, 8, 15));
        letter.put("D", charmap.getSubimage(27, 0, 8, 15));
        letter.put("E", charmap.getSubimage(36, 0, 5, 15));
        letter.put("F", charmap.getSubimage(42, 0, 5, 15));
        letter.put("G", charmap.getSubimage(48, 0, 10, 15));
        letter.put("H", charmap.getSubimage(59, 0, 8, 15));
        letter.put("I", charmap.getSubimage(68, 0, 2, 15));
        letter.put("J", charmap.getSubimage(71, 0, 6, 15));
        letter.put("K", charmap.getSubimage(78, 0, 8, 15));
        letter.put("L", charmap.getSubimage(87, 0, 5, 15));
        letter.put("M", charmap.getSubimage(93, 0, 12, 15));

        letter.put("N", charmap.getSubimage(106, 0, 10, 15));
        letter.put("O", charmap.getSubimage(117, 0, 10, 15));
        letter.put("P", charmap.getSubimage(128, 0, 7, 15));
        letter.put("Q", charmap.getSubimage(136, 0, 10, 15));
        letter.put("R", charmap.getSubimage(147, 0, 7, 15));
        letter.put("S", charmap.getSubimage(155, 0, 8, 15));
        letter.put("T", charmap.getSubimage(164, 0, 6, 15));
        letter.put("U", charmap.getSubimage(171, 0, 8, 15));
        letter.put("V", charmap.getSubimage(180, 0, 10, 15));
        letter.put("W", charmap.getSubimage(191, 0, 13, 15));
        letter.put("X", charmap.getSubimage(205, 0, 10, 15));
        letter.put("Y", charmap.getSubimage(216, 0, 8, 15));
        letter.put("Z", charmap.getSubimage(225, 0, 9, 15));

        letter.put(" ", charmap.getSubimage(235, 0, 4, 15));
        letter.put("-", charmap.getSubimage(240, 0, 4, 15));
        letter.put(":", charmap.getSubimage(245, 0, 2, 15));
        letter.put(".", charmap.getSubimage(248, 0, 2, 15));
        letter.put("(", charmap.getSubimage(251, 0, 4, 15));
        letter.put(")", charmap.getSubimage(256, 0, 4, 15));

        letter.put("1", charmap.getSubimage(261, 0, 3, 15));
        letter.put("2", charmap.getSubimage(265, 0, 5, 15));
        letter.put("3", charmap.getSubimage(271, 0, 5, 15));
        letter.put("4", charmap.getSubimage(277, 0, 6, 15));
        letter.put("5", charmap.getSubimage(284, 0, 5, 15));
        letter.put("6", charmap.getSubimage(290, 0, 6, 15));
        letter.put("7", charmap.getSubimage(297, 0, 5, 15));
        letter.put("8", charmap.getSubimage(303, 0, 6, 15));
        letter.put("9", charmap.getSubimage(310, 0, 6, 15));
        letter.put("0", charmap.getSubimage(317, 0, 6, 15));
        
        // List of serif letters for left margin spacing calculation
        final String[] serifLetters = {"A", "M", "S", "V", "W"};
        
        Color green = new Color(0, 255, 0);
        int white = 0xFFFFFFFF;
        
        // Create initial 80px wide sprite to begin pasting letters into
        sprite = new BufferedImage(80, 15, TYPE_INT_RGB);
        BufferedImage tempResize;
        Graphics2D graphics = sprite.createGraphics();
        Graphics2D tempGraphics;
        graphics.setColor(green);
        graphics.fillRect(0, 0, 80, 15);
        int x = 1;
        int y;
        int canvasWidth = 80;
        int letterWidth;
        
        // Check if letter in string is in character map
        for (char c : name.toUpperCase().toCharArray()) {
            BufferedImage currentLetter = letter.get(String.format("%s", c));
            if (currentLetter == null) {
                System.out.println("Could not find letter " + c + " in character sheet.");
                System.out.println("Available characters: \"A-Z -:.() 0-9\"");
                continue;
            }
            
            // Iterate through each letter, check how close the two adjoining
            // letters are and add space if too close.
            x += 1;
            y = 3;
            letterWidth = currentLetter.getWidth();
            
            // If the current X position for the next letter would go beyond the
            // sprites current width, increase width another 80px
            if (letterWidth + x > canvasWidth) {
                canvasWidth += 80;
                tempResize = new BufferedImage(canvasWidth, 15, TYPE_INT_RGB);
                tempGraphics = tempResize.createGraphics();
                tempGraphics.setColor(green);
                tempGraphics.fillRect(0, 0, canvasWidth, 15);
                tempGraphics.drawImage(sprite, 0, 0, null);
                sprite = tempResize;
                tempGraphics.dispose();
                graphics = sprite.createGraphics();
            }
            
            // This simulates the kerning style from the officially released 
            // Vital Hero name sprites
            while(y <= 12) {
                if (currentLetter.getRGB(0, y) != white) {
                    y += 1;
                    continue;
                }
                if (
                    sprite.getRGB(x-2, y) == white ||
                    sprite.getRGB(x-2, y-1) == white ||
                    sprite.getRGB(x-2, y-2) == white ||
                    sprite.getRGB(x-2, y+1) == white ||
                    sprite.getRGB(x-2, y+2) == white
                ) {

                    x += 1;

                } else {
                    y += 1;
                }
            }
            
            // Add letter into name sprite
            graphics.drawImage(currentLetter, x, 0, null);
            x += letterWidth;
        }
        
        // Get width of already added text
        int textWidth = canvasWidth - 1;
        y = 3;
        while (textWidth > 0) {
            if (sprite.getRGB(textWidth, y) == white) {
                break;
            } else if (y < 12) {
                y += 1;
            } else {
                textWidth -= 1;
                y = 3;
            }
        }
        
        // Account for the two pixel margin on left
        textWidth -= 2;
        
        
        // Determine new left padding based on sprite width
        int leftMargin;
        
        switch (canvasWidth) {
            // Center sprite with a minimum left margin of 2px
            case 80 -> {
                x = (80 - textWidth) / 2;
                leftMargin = x - 2;
            }
            // Add a 3px / 4px left margin based on wether the first letter is serif
            case 160 -> {
                x = (Arrays.asList(serifLetters).contains(name.substring(0)) ? 3 : 4);
                leftMargin = (textWidth + x > 160 ? 0 : x-2);
            }
            // Add a 5px / 6px left margin based on wether the first letter is serif
            default -> {
                x = (Arrays.asList(serifLetters).contains(name.substring(0)) ? 5 : 6);
                leftMargin = ((textWidth + x) > 240 ? 0 : x-2);
            }
        }

        if (leftMargin < 0) {
            leftMargin = 0;
        }
        
        // Check if right margin is at least 2x. If not, use alternate MON kerning
        if ((name.length() > 3 ? name.substring(name.length() - 3) : name).equals("MON")) {
            if ((textWidth + 2 + leftMargin) > (canvasWidth - 3)) {
                x = ((textWidth + 2 + leftMargin) == (canvasWidth - 1) ? 
                        canvasWidth-36 : canvasWidth-37);

                graphics.drawImage(letter.get("M"), x, 0, null);
                graphics.drawImage(letter.get(" "), x+12, 0, null);
                graphics.drawImage(letter.get("O"), x+13, 0, null);
                graphics.drawImage(letter.get(" "), x+23, 0, null);
                graphics.drawImage(letter.get("N"), x+24, 0, null);
                graphics.drawImage(letter.get(" "), x+34, 0, null);
            }
        }
        
        // Create new canvas for new margins and paste into it
        tempResize = new BufferedImage(canvasWidth, 15, TYPE_INT_RGB);
        tempGraphics = tempResize.createGraphics();
        tempGraphics.setColor(green);
        tempGraphics.fillRect(0, 0, canvasWidth, 15);
        tempGraphics.drawImage(sprite, leftMargin, 0, null);
        sprite = tempResize;
        tempGraphics.dispose();
        
        return sprite;
    }
    
    public void generateSprites(String nameList, String outputFolder, Boolean keepOrder) throws IOException {
        BufferedImage sprite;
        outputFolder = createOutputFolder(outputFolder);
        nameList = nameList.toUpperCase();        
        String[] names = nameList.split("\r\n|\r|\n");
        
        // Check if official Bandai sprite exists
        for (int i=0; i<names.length; i++) {
            if (getClass().getClassLoader().getResource(String.format("assets/%s.png", names[i])) != null) {
                try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(String.format("assets/%s.png", names[i]))) {
                    if (keepOrder) {
                        Files.copy(is, Paths.get(String.format("./%s/%02d_%s.png",outputFolder, i, names[i])));
                    } else {
                        Files.copy(is, Paths.get(String.format("./%s/%s.png",outputFolder, names[i])));
                    }
                } catch (IOException e) {
                    System.out.println(String.format("Could not copy %s.png", names[i]));
               }
            
            // Generate new sprite
            } else {
                sprite = createNameSprite(names[i]);
                if (sprite == null) {
                    System.out.println("Failed to generate sprite");
                    System.exit(1);
                }
                File outFile;
                if (keepOrder) {
                    outFile = new File(String.format("%s/%02d_%s.png", outputFolder, i, cleanFileName(names[i])));
                } else {
                    outFile = new File(String.format("%s/%s.png", outputFolder, cleanFileName(names[i])));
                }
                
                // Save result to output folder
                ImageIO.write(sprite, "png", outFile);
            }
        }
    }  
}
