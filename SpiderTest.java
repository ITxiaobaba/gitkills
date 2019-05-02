package test.java.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.*;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class SpiderTest {
	
	//URL
	private static String url="http://other.web.rd01.sycdn.kuwo.cn/resource/n1/18/27/633824864.mp3";
	public static void main(String[] args) {
		//下载方法
		Scanner sc=new Scanner(System.in);
		String m=sc.nextLine();
		downLoadGoogleIcon(m);
	}
	
	/**
	 * 依据URL获取文件名，当文件名为空时 设置文件名为default.temp
	 * @param url
	 * @return
	 */
	private static String getFileNameFromUrl(String url) {
		if(url==null) {
			url="default.temp";
		}else {
			if(url.lastIndexOf("/")!=-1) {
				url=url.substring(url.lastIndexOf("/")+1,url.length());
			}else {
				if(url.lastIndexOf(".")!=-1) {
					url.substring(url.lastIndexOf(".")+1,url.length());
				}else {
					url="default.temp";
				}
			}
		}
		System.out.println("the file name is "+url);
		return url;
	}
	
	/**
	 * 下载网络资源
	 * @param downFileURL
	 */
	private static void downLoadGoogleIcon(String downFileURL) {
		HttpURLConnection httpConnection=null;//连接类
		InputStream is=null;//输入流
		FileOutputStream fos=null;//输出流 文件部分
		String icon=downFileURL;
		byte buf[]=null;
		try {
			if(icon==null) {
				throw new Exception("访问的URL不存在,请重试.");
			}
			URL url=new URL(icon);
			//文件路径
			String path="/home/zd/downLoad";
			httpConnection=(HttpURLConnection)url.openConnection();
			is=httpConnection.getInputStream();
			File pathFile=new File(path);
			if(!pathFile.exists()) {
				pathFile.mkdirs();
			}
			fos=new FileOutputStream(new File(path+"/"+getFileNameFromUrl(downFileURL)),true);
			buf=new byte[1024*8];
			int len=0;
			int count=0;
			while((len=is.read(buf))>0) {
				count++;
				fos.write(buf,0,len);
				if(count==64) {
					count=0;
					try {
						System.out.println("download is paused!!!");
						Thread.sleep(1);
					}catch(InterruptedException e) {
						
					}
				}
			}
			System.out.println("download is completed!!!");
			httpConnection.disconnect();
			is.close();
			fos.flush();
			fos.close();
			buf=null;
			httpConnection=null;
			is=null;
			fos=null;
		}catch(Exception e) {
			System.out.print("download error!!!");
		}finally {
			try {
				if(is!=null) {
					is.close();
				}
				is=null;
				if(fos!=null) {
					fos.close();
				}
				fos=null;
				if(buf!=null)
					buf=null;
			}catch(Exception e) {
				
			}
		}
	}
}
