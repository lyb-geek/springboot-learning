package com.github.lybgeek.generate.util;


import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBSqlScriptUtils {
 
	public static void main(String[] args) throws Exception{
//		testWriteSqlStatement2File();
		testExecuteSqlStatements();
	}


	public static List<String>	prepareSqlStatementWithStringFormat(boolean isForFiles) {
		String sql = "alter table %s ADD COLUMN create_time DATETIME COMMENT '创建时间',ADD COLUMN created_by_id BIGINT(20) DEFAULT NULL COMMENT '创建人id', ADD COLUMN update_time DATETIME COMMENT '修改时间', ADD COLUMN last_updated_by_id BIGINT(20) DEFAULT NULL COMMENT '修改人id'";
		List<String> sqlStatements = new ArrayList<>();
		Map<String, List<String>> dbTableNamesMap = DbUtils.INSTANCE.getDbTablesNamesMap();
		dbTableNamesMap.forEach((dbName,tableNames) -> {
			for (String tableName : tableNames) {
                String record = dbName + "." + tableName;
				String sqlStatement = String.format(sql, record);
				//如果是要写入文件，则每条生成的sql语句，需追加分号
				if(isForFiles){
					sqlStatement = sqlStatement + ";";
				}
				System.out.println(sqlStatement);
				sqlStatements.add(sqlStatement);

			}

		});
		return sqlStatements;

	}


	/**
	 *
	 *
	 * SELECT
	 *   GROUP_CONCAT(
	 *     'alter table ',
	 *     table_schema,
	 *     '.',
	 *     table_name,
	 *     ' ADD COLUMN create_time DATETIME COMMENT \'创建时间\',ADD COLUMN created_by_id BIGINT(20) DEFAULT NULL COMMENT \'创建人id\', ADD COLUMN update_time DATETIME COMMENT \'修改时间\', ADD COLUMN last_updated_by_id BIGINT(20) DEFAULT NULL COMMENT \'修改人id\'' SEPARATOR ""
	 *   ) AS sqlStatement
	 * FROM
	 *   information_schema.tables
	 * WHERE table_schema = 'db_user'
	 *   AND table_name = 't_user'
	 * 准备要执行的sql脚本语句
	 * @param isForFiles 如果是要写入文件，则每条生成的sql语句，需追加分号
	 * @return
	 */
	public static List<String>	prepareSqlStatement(boolean isForFiles){
		List<String> sqlStatements = new ArrayList<>();
		String sql = "\n" +
				"SELECT\n" +
				"  GROUP_CONCAT(\n" +
				"    'alter table ',\n" +
				"    table_schema,\n" +
				"    '.',\n" +
				"    table_name,\n" +
				"    ' ADD COLUMN create_time DATETIME COMMENT \\'创建时间\\',ADD COLUMN created_by_id BIGINT(20) DEFAULT NULL COMMENT \\'创建人id\\', ADD COLUMN update_time DATETIME COMMENT \\'修改时间\\', ADD COLUMN last_updated_by_id BIGINT(20) DEFAULT NULL COMMENT \\'修改人id\\'' SEPARATOR \"\"\n" +
				"  ) AS sqlStatement \n" +
				"FROM\n" +
				"  information_schema.tables\n" +
				"WHERE table_schema = ?\n" +
				"  AND table_name = ? ";

			Map<String, List<String>> dbTableNamesMap = DbUtils.INSTANCE.getDbTablesNamesMap();
			dbTableNamesMap.forEach((dbName,tableNames) -> {
				for (String tableName : tableNames) {
					try {
						PreparedStatement ps = DbUtils.INSTANCE.getConnection().prepareStatement(sql);
						ps.setString(1,dbName);
						ps.setString(2,tableName);
						ResultSet rs = ps.executeQuery();
						while(rs.next()){
							String sqlStatement = rs.getString("sqlStatement");
							//如果是要写入文件，则每条生成的sql语句，需追加分号
							if(isForFiles){
								sqlStatement = sqlStatement + ";";
							}
							System.out.println(sqlStatement);
							sqlStatements.add(sqlStatement);
						}
					} catch (SQLException throwables) {
						throwables.printStackTrace();
					}
				}

			});

		DbUtils.INSTANCE.close();
		return sqlStatements;
	}
	private static void printTableColumns() {
		//存放当前表的字段
		List<String>  fields= new ArrayList<>();
		//存放当前表的字段类型
		List<String> fieldstype = new ArrayList<>();
		Map<String, List<String>> dbTableNamesMap = DbUtils.INSTANCE.getDbTablesNamesMap();

		dbTableNamesMap.forEach((dbName,tableNames) -> {
			for (String tableName : tableNames) {
				String record = dbName + "." + tableName;
				String sql = "select * from " + record;
				try {
					PreparedStatement ps = DbUtils.INSTANCE.getConnection().prepareStatement(sql);
					ResultSet rstable = ps.executeQuery();
					//结果集元数据
					ResultSetMetaData meta = rstable.getMetaData();
					//表列数量
					int columeCount=meta.getColumnCount();
					for (int i=1;i<=columeCount;i++)
					{
						fields.add(meta.getColumnName(i));
						fieldstype.add(meta.getColumnTypeName(i));

					}
					System.out.println("表"+record+",字段: "+fields);
					System.out.println("表"+record+",字段类型: "+fieldstype);
				} catch (SQLException throwables) {
					throwables.printStackTrace();
				}

			}
		});

		DbUtils.INSTANCE.close();
	}

	public static void writeSqlStatement2File(List<String> sqlStatements,String filePath){

		try {
			File file = new File(filePath);
			if(!file.exists()){
				file.createNewFile();
			}
			FileUtil.writeUtf8Lines(sqlStatements,file);
			System.out.println("数据库脚本写入"+filePath+"成功");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("数据库脚本写入"+filePath+"失败");
		}


	}

	public static void executeSqlStatements(List<String> sqlStatements){
		try {
			Statement statement = DbUtils.INSTANCE.getConnection().createStatement();
			for (String sqlStatement : sqlStatements) {
				statement.addBatch(sqlStatement);
			}
			statement.executeBatch();
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	private static void testWriteSqlStatement2File(){
		List<String> sqlStatements = prepareSqlStatement(true);
    	writeSqlStatement2File(sqlStatements,"E:\\db.sql");
	}

	private static void testExecuteSqlStatements(){
		List<String> sqlStatements = prepareSqlStatementWithStringFormat(false);
		executeSqlStatements(sqlStatements);
	}


}
 