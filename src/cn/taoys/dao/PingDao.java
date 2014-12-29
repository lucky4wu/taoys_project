package cn.taoys.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.taoys.entity.PingRecord;
import cn.taoys.utils.ConnectionUtils;

public class PingDao {

	private Logger logger = Logger.getLogger(PingDao.class);
	
	private static final String sql_addPing = 
			"insert into taoys_ping value(id,uname,email,ipAddress,timeOut,pingTimes,createTime) "
			+ "values(taoys_seq_ping.nextval, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS'))";
	

	public void save(String remoteIpAddress, Integer pingTimes,
			Integer timeOut, String uname)  {
		Connection conn = null;
		PreparedStatement prep = null;
		try {
			conn = ConnectionUtils.getConnection();
			prep = conn.prepareStatement(sql_addPing);
			prep.setString(1, uname);
			prep.setString(2, "email");
			prep.setString(3, remoteIpAddress);		
			prep.setString(4, pingTimes.toString());
			prep.setString(5, timeOut.toString());
			prep.executeUpdate();
		} catch (Exception e) {
			logger.error(e);
		} finally{
			if(prep != null){
				try {
					prep.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}
		
	}

	private static final String sql_findPingByUname = 
			"select * from taoys_ping where uname = ? order by createTime desc";
	
	public List<PingRecord> findPingListByUname(String uname) {
		Connection conn = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		List<PingRecord> pingList = new ArrayList<PingRecord>();
		try {
			conn = ConnectionUtils.getConnection();
			prep = conn.prepareStatement(sql_findPingByUname);
			prep.setString(1, uname);
			rs = prep.executeQuery();
			PingRecord ping = null;
			if(rs.next()){
				ping = new PingRecord();
				ping.setId(rs.getInt("id"));
				ping.setUname(rs.getString("uname"));
				ping.setEmail(rs.getString("email"));
				ping.setIpAddress(rs.getString("ipAddress"));
				ping.setTimeOut(rs.getString("timeOut"));
				ping.setPingTimes(rs.getString("pingTimes"));
				ping.setCreateTime(rs.getString("createTime"));
				pingList.add(ping);
			}
		} catch (Exception e) {
			logger.error(e);
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
			if(prep != null ){
				try {
					prep.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}
		
		return pingList;
	}
}
