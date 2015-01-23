package cn.taoys.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;

public class ThreadUtil implements Runnable{

	private static Logger logger = Logger.getLogger(ThreadUtil.class);
	
	//设置读取的字符编码
	private String charset = "gbk";
	private List<String> list;
	private InputStream inputStream;
	
	public ThreadUtil(InputStream inputStream, List<String> list){
		this.inputStream = inputStream;
		this.list = list;
	}
	
	public void start(){
		Thread thread = new Thread(this);
		thread.setDaemon(true);//将其设置为守护线程
		thread.start();
	}
	
	@Override
	public void run() {
		BufferedReader br = null;
		try{
			br = new BufferedReader(new InputStreamReader(inputStream, charset));
			String line = null;
			while((line = br.readLine())!=null){
				if(line != null){
					logger.debug(line);
					list.add(line);
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				//释放资源
				inputStream.close();
				br.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

}
