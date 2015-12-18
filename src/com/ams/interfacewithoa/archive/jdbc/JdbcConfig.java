package com.ams.interfacewithoa.archive.jdbc;


public class JdbcConfig {
	private String jdbc_url = null;
	private String jdbc_username = null;
	private String jdbc_password = null;
	public JdbcConfig(String jdbcUrl, String jdbcUsername, String jdbcPassword) {
		this.jdbc_url = jdbcUrl;
		this.jdbc_username = jdbcUsername;
		this.jdbc_password = jdbcPassword;
	}
	public String getJdbc_url() {
		return jdbc_url;
	}
	public void setJdbc_url(String jdbcUrl) {
		jdbc_url = jdbcUrl;
	}
	public String getJdbc_username() {
		return jdbc_username;
	}
	public void setJdbc_username(String jdbcUsername) {
		jdbc_username = jdbcUsername;
	}
	public String getJdbc_password() {
		return jdbc_password;
	}
	public void setJdbc_password(String jdbcPassword) {
		jdbc_password = jdbcPassword;
	}

}
