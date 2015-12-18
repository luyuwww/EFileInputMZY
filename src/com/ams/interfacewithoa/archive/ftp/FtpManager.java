//package com.ams.interfacewithoa.archive.ftp;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.SocketException;
//
//import org.apache.commons.net.ftp.FTP;
//import org.apache.commons.net.ftp.FTPClient;
//import org.apache.commons.net.ftp.FTPFile;
//import org.apache.commons.net.ftp.FTPReply;
//
///**
// * 电子文件ftp管理服务类，电子文件已放到存储点，无需处理
// * @author Administrator
// *
// */
//public class FtpManager {
//	private static FtpManager connManager = null;
//	
//	public FtpManager() {
//	}
//	public static FtpManager getInstance(){
//		if(connManager == null){
//			synchronized(FtpManager.class){
//				connManager = new FtpManager();
//			}
//		}
//		return connManager;
//	}
//	public boolean testFtp(FtpConfig ftpConfig) {
//		 boolean flag = true; 
//		 FTPClient ftpClient = null;
//         try { 
//             ftpClient = new FTPClient(); 
//             ftpClient.setControlEncoding(ftpConfig.getFileEncoding()); 
//             ftpClient.connect(ftpConfig.getIp(), ftpConfig.getPort()); 
//             ftpClient.login(ftpConfig.getUsername(), ftpConfig.getPassword());
//             int reply = ftpClient.getReplyCode(); 
//             ftpClient.setDataTimeout(120000); 
//             if (!FTPReply.isPositiveCompletion(reply)) { 
//                 ftpClient.disconnect(); 
//                 System.out.println("FTP 服务拒绝连接！"); 
//                 flag = false; 
//             } 
//         } catch (SocketException e) { 
//                 flag = false; 
//                 e.printStackTrace(); 
//                 System.err.println("登录ftp服务器 " + ftpConfig.getIp() + " 失败,连接超时！"); 
//         } catch (IOException e) { 
//                 flag = false; 
//                 e.printStackTrace(); 
//                 System.err.println("登录ftp服务器 " + ftpConfig.getIp() + " 失败，FTP服务器无法打开！"); 
//         } 
//         return flag;
//	}
//    
//    /**
//     * 向FTP服务器上传文件
//     * @param url FTP服务器hostname
//     * @param port FTP服务器端口
//     * @param username FTP登录账号
//     * @param password FTP登录密码
//     * @param path FTP服务器保存目录,如果是根目录则为“/”
//     * @param filename 上传到FTP服务器上的文件名
//     * @param input 本地文件输入流
//     * @return 成功返回true，否则返回false
//     */
//    public boolean uploadFile(FtpConfig ftpConfig, String path, String filename, InputStream input) {
//        boolean result = false;
//        FTPClient ftpClient = new FTPClient();
//        try {
//            int reply;
//            ftpClient.setControlEncoding(ftpConfig.getFileEncoding());
//            ftpClient.connect(ftpConfig.getIp(), ftpConfig.getPort());// 连接FTP服务器
//            // 登录
//            ftpClient.login(ftpConfig.getUsername(), ftpConfig.getPassword());
//            // 检验是否连接成功
//            reply = ftpClient.getReplyCode();
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                System.out.println("连接失败");
//                ftpClient.disconnect();
//                return result;
//            }
// 
//            // 转移工作目录至指定目录下
//            boolean change = ftpClient.changeWorkingDirectory(path);
//            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//            if (change) {
//                result = ftpClient.storeFile(new String(filename.getBytes(ftpConfig.getFileEncoding()),"iso-8859-1"), input);
//                if (result) {
//                    System.out.println("上传成功!");
//                }
//            }
//            input.close();
//            ftpClient.logout();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (ftpClient.isConnected()) {
//                try {
//                    ftpClient.disconnect();
//                } catch (IOException ioe) {
//                }
//            }
//        }
//        return result;
//    }
// 
// 
//    /**
//     * 从FTP服务器下载文件
//     * 
//     * @param url FTP服务器hostname
//     * @param port FTP服务器端口
//     * @param username FTP登录账号
//     * @param password FTP登录密码
//     * @param remotePath FTP服务器上的相对路径
//     * @param fileName 要下载的文件名
//     * @param localPath 下载后保存到本地的路径
//     * @return
//     */
//    public boolean downFile(FtpConfig ftpConfig, String remotePath, String fileName,
//            String localPath) {
//        boolean result = false;
//        FTPClient ftpClient = new FTPClient();
//        try {
//            int reply;
//            ftpClient.setControlEncoding(ftpConfig.getFileEncoding());
//             
//            /*
//             *  为了上传和下载中文文件，有些地方建议使用以下两句代替
//             *  new String(remotePath.getBytes(encoding),"iso-8859-1")转码。
//             *  经过测试，通不过。
//             */
////            FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
////            conf.setServerLanguageCode("zh");
// 
//            ftpClient.connect(ftpConfig.getIp(), ftpConfig.getPort());
//            ftpClient.login(ftpConfig.getUsername(), ftpConfig.getPassword());// 登录
//            // 设置文件传输类型为二进制
//            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//            // 获取ftp登录应答代码
//            reply = ftpClient.getReplyCode();
//            // 验证是否登陆成功
//            if (!FTPReply.isPositiveCompletion(reply)) {
//                ftpClient.disconnect();
//                System.err.println("FTP server refused connection.");
//                return result;
//            }
//            // 转移到FTP服务器目录至指定的目录下
//            ftpClient.changeWorkingDirectory(new String(remotePath.getBytes(ftpConfig.getFileEncoding()),"iso-8859-1"));
//            // 获取文件列表
//            FTPFile[] fs = ftpClient.listFiles();
//            for (FTPFile ff : fs) {
//                if (ff.getName().equals(fileName)) {
//                    File localFile = new File(localPath + "/" + ff.getName());
//                    OutputStream is = new FileOutputStream(localFile);
//                    ftpClient.retrieveFile(ff.getName(), is);
//                    is.close();
//                }
//            }
//            ftpClient.logout();
//            result = true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (ftpClient.isConnected()) {
//                try {
//                    ftpClient.disconnect();
//                } catch (IOException ioe) {
//                }
//            }
//        }
//        return result;
//    }
// 
//
//}
