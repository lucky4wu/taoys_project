package cn.taoys.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		String line = null;
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		String ip = "www.baidu.com";
		boolean res = false;
		try{
			process = runtime.exec("ping " + ip);
			is = process.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			while((line = br.readLine()) != null){
				System.out.println("line="+line);
				if(line.contains("TTL")){
					res = true;
					break;
				}
			}
			is.close();
			isr.close();
			br.close();
			if(res){
				System.out.println("ping 通 ...");
			}else{
				System.out.println("ping 不通 ...");
			}
		}catch(IOException e){
			System.out.println(e);
			runtime.exit(1);
		}
	}

}
