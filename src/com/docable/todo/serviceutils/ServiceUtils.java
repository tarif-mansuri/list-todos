//$Id$
package com.docable.todo.serviceutils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class ServiceUtils {
	public static String parseString(String todoText) {
		todoText = todoText.replaceAll("%2C", ",");
		todoText = todoText.replaceAll("[+]", " ");
		todoText = todoText.replaceAll("%27", "'");
		todoText = todoText.replaceAll("%22", "\"");
		todoText = todoText.replaceAll("%26", "&");
		todoText = todoText.replaceAll("%3F", "?");
		return todoText;
	}

	public static HashMap<String, String> parsePayLoad(String payLod) {
		String[] parsedString = payLod.split("&");
		HashMap<String, String> map = new HashMap<>();
		for (int i = 0; i < parsedString.length; i++) {
			String[] keyPair = parsedString[i].split("=");
			if (keyPair.length == 2) {
				map.put(keyPair[0], keyPair[1]);
			}
		}
		return map;
	}

	public static String getTaskName(String requestURI) {
		String[] uriPieces = requestURI.split("/");
		return uriPieces[uriPieces.length - 1];
	}

	public static void printPayLoad(String payLod) throws IOException {
		System.out.println("DATA:" + payLod);
		HashMap<String, String> payLoadMap = parsePayLoad(payLod);
		for (Entry<String, String> entry : payLoadMap.entrySet()) {
			System.out.println(entry.getKey() + ":      " + entry.getValue());
		}
		return;
	}
}
