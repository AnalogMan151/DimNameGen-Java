/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.docklokman.dimnamegen;

import java.util.Arrays;

/**
 *
 * @author DocKlokMan
 */
public class FileNameCleaner {
    final static int[] illegalChars = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 
        13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 
        31, 34, 42, 47, 58, 60, 62, 63, 92, 124};

    public static String cleanFileName(String badFileName) {
        StringBuilder cleanName = new StringBuilder();
        int len = badFileName.codePointCount(0, badFileName.length());
        for (int i=0; i<len; i++) {
            int c = badFileName.codePointAt(i);
            if (Arrays.binarySearch(illegalChars, c) < 0) {
                cleanName.appendCodePoint(c);
            }
        }
        return cleanName.toString();
    }
}