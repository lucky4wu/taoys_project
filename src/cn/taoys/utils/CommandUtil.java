package cn.taoys.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandUtil {

	//保存进程的输入流信息
	private List<String> stdoutList = new ArrayList<String>();
	//保存进程的错误流信息
	private List<String> erroroutList = new ArrayList<String>();
	
	public Integer executeCommand(String command){
		//先清空
		stdoutList.clear();
		erroroutList.clear();
		
		Process p = null;
        try {
            p = Runtime.getRuntime().exec(command);

            // 创建2个线程，分别读取输入流缓冲区和错误流缓冲区
            ThreadUtil stdoutUtil = new ThreadUtil(p.getInputStream(), stdoutList);
            ThreadUtil erroroutUtil = new ThreadUtil(p.getErrorStream(), erroroutList);
            //启动线程读取缓冲区数据
            stdoutUtil.start();
            erroroutUtil.start();

            int exitValue = p.waitFor();
            return exitValue;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
	
	public List<String> getStdoutList(){
		return stdoutList;
	}
	
	public List<String> getErroroutList(){
		return erroroutList;
	}
}
