package com.ofir.coupons.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Logger {
	
	private File exceptionsLogFile;
	private File operationsLogFile;
	
	@Autowired
	public Logger(File exceptionsLogFile, File operationsLogFile) {
		this.exceptionsLogFile = exceptionsLogFile;
		this.operationsLogFile = operationsLogFile;
	}
	
	/**
	 * this method writes the successful operation to log file and prints the message.
	 * 
	 * @param text is the message of the successful operation that has been made
	 * @throws IOException
	 */
	public void logOperationAndPrint(String text) throws IOException {
		writeToFile(operationsLogFile, String.format("%s | %s", Utils.getCurrentTime(), text));
		System.out.println(text);
	}
	
	/**
	 * this method writes the exception message to log file.
	 * 
	 * @param text is the message of the exception that has been thrown
	 * @throws IOException
	 */
	public void logException(String text) throws IOException {
		writeToFile(exceptionsLogFile, String.format("%s | %s", Utils.getCurrentTime(), text));
	}

	private void writeToFile(File file, String text) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
		bufferedWriter.write(text+"\n\n");
		bufferedWriter.close();
	}



}
