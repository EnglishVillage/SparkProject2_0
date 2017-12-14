package MyCommons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * 名称：王坤造
 * 时间：2017/7/11.
 * 名称：
 * 备注：
 */
public class MysqlUtils {
	static String selectsql = null;
	static ResultSet retsult = null;

	public static final String name = "com.mysql.jdbc.Driver";
	public static final String url = "jdbc:mysql://192.168.1.136/base_new";
	public static final String user = "root";
	public static final String password = "111";

	public static Connection conn = null;
	public static PreparedStatement pst = null;

	public static void main(String[] args) {
		int paraCount = 4; //读取参数数量
		selectsql = "select active_ingredient_cn,alternative_name,alternative_inn,alternative_active_ingredient_name from yymf_drugs_name_dic limit 10";//SQL语句

		try {
			Class.forName(name);//指定连接类型
			conn = DriverManager.getConnection(url, user, password);//获取连接
			pst = conn.prepareStatement(selectsql);//准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
		}

		Object [] paras = new Object [paraCount];
		try {
			retsult = pst.executeQuery();//执行语句，得到结果集

			while (retsult.next()) {
				for(int i = 0;i<paraCount;i++){
					//paras[i] = retsult.getString(i+1);
					paras[i] = retsult.getObject(i+1);
					System.out.println(paras[i].getClass());
				}
				System.out.println(Arrays.toString(paras));

			}//显示数据
			retsult.close();
			conn.close();//关闭连接
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
