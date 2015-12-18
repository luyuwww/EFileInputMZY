package com.ams.interfacewithoa.archive;

import com.ams.interfacewithoa.archive.util.UUIDHexGenerator;

public class TestID {

	public static void main(String[] args) {
		UUIDHexGenerator id = new UUIDHexGenerator();
		String syscode = id.generate();
		System.out.println(syscode);
	}
}
