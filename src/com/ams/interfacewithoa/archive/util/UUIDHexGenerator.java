//$Id: UUIDHexGenerator.java,v 1.1 2009/05/04 02:38:44 wanglei Exp $
package com.ams.interfacewithoa.archive.util;

import java.io.Serializable;

import java.util.Properties;


/**
 * <b>uuid.hex</b><br>
 * <br>
 * A <tt>UUIDGenerator</tt> that returns a string of length 32,
 * This string will consist of only hex digits. Optionally, 
 * the string may be generated with seperators between each 
 * component of the UUID.
 *
 * @see UUIDStringGenerator
 * @author Gavin King
 */

public class UUIDHexGenerator extends UUIDGenerator{
	
	private String sep = "";
	
	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace( 8-formatted.length(), 8, formatted );
		return buf.toString();
	}
	
	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace( 4-formatted.length(), 4, formatted );
		return buf.toString();
	}
	
	public String generate() {
		return new StringBuffer(36)
		.append( format( getIP() ) ).append(sep)
		.append( format( getJVM() ) ).append(sep)
		.append( format( getHiTime() ) ).append(sep)
		.append( format( getLoTime() ) ).append(sep)
		.append( format( getCount() ) )
		.toString().toUpperCase();
	}
}






