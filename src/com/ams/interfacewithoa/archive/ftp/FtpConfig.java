package com.ams.interfacewithoa.archive.ftp;


public class FtpConfig {
	private String ip = null;
	private Integer port = null; 
	private String username = null;
	private String password = null;
	private String fileEncoding = null;
	public FtpConfig(String ip, Integer port, String username, String password, String fileEncoding) {
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
		this.fileEncoding = fileEncoding;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFileEncoding() {
		return fileEncoding;
	}
	public void setFileEncoding(String fileEncoding) {
		this.fileEncoding = fileEncoding;
	}
	
}
