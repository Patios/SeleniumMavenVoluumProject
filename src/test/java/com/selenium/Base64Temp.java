package com.selenium;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class Base64Temp {

	private static String encodedString, decodedString;

	public static void main(String... args) {

		String s = "Ola";

		String d = "UGF0aW9z";

		encodedString = encodeBase64(s);

		System.out.println("String: " + s + " encoded=> " + encodedString);
		
		decodedString = decodeBase64(d);
		
		System.out.println("show decoded: "+ decodedString);

	}

	public static String encodeBase64(String e) {

		byte[] authBytes = e.getBytes(StandardCharsets.UTF_8);
		String encoded = Base64.getEncoder().encodeToString(authBytes);
		return encoded;

	}

	public static String decodeBase64(String d) {

		byte[] decoded = Base64.getDecoder().decode(d);
		return new String(decoded);

	}

}
