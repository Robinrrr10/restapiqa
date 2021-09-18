package com.github.restapiqa.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	public static List<String> getAllLines(String filePath){
		List<String> allLines = new ArrayList<String>();
		File file = new File(filePath);
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		BufferedReader bufReader = new BufferedReader(fileReader);
		String line;
		try {
			while((line = bufReader.readLine()) != null) {
				allLines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return allLines;
	}
	
}
