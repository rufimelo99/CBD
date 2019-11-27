package Cassandra_P3;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class Ex3_3 {
	
	public static void main(String[] args) {
		
		String ipAddress = "127.0.0.1";
		String keyspace= "cbd";
		Cluster cluster;
		Session session;
		cluster = Cluster.builder().addContactPoint(ipAddress).build();
	
		session = cluster.connect(keyspace);
		ResultSet resultset;
		/*
		String sql_cmd_select = "SELECT * FROM user";
		resultset = session.execute(sql_cmd_select);
		
		for(Row row: resultset) {
			System.out.println(row);
		}
		
		
		String sql_cmd_insert = "INSERT INTO user (ts, name, username, email) VALUES (ToTimeStamp(now()), 'Rui', 'RM', 'rm@ua.pt');";
		session.execute(sql_cmd_insert);
		
		resultset = session.execute(sql_cmd_select);
		for(Row row: resultset) {
			System.out.println(row);
		}
		
		String sql_cmd_edit = "UPDATE user SET name = 'Joao Pedro' where username = 'JP';";
		session.execute(sql_cmd_edit);
		
		resultset = session.execute(sql_cmd_select);
		for(Row row: resultset) {
			System.out.println(row);
		}
		*/
		//query 3.2.d.1
		String sql_cmd_1 = "select * from comentarios where video_id='30c7da9c-c116-42cb-924d-d138fa3ceef9' limit 2;";
		resultset = session.execute(sql_cmd_1);
		
		System.out.println("\n3.2.d.1");
		System.out.println("id                                   | ts                              | author | content   | video_id\n" + 
				"--------------------------------------+---------------------------------+--------+-----------+--------------------------------------");
		for(Row row: resultset) {
			System.out.format("%s	%s	%s	%s	%s \n", row.getUUID("id"), row.getTimestamp("ts"), row.getString("author"), row.getString("content"), row.getString("video_id"));
		}
		//query 3.2.d.3
		String sql_cmd_3 = "SELECT * FROM videos WHERE tags CONTAINS '#dog';";
		resultset = session.execute(sql_cmd_3);

		System.out.println("3.2.d.3");
		System.out.println(" id                                   | author | description | name     | tags             | ts\n" + 
				"--------------------------------------+--------+-------------+----------+------------------+---------------------------------");
		for(Row row: resultset) {
			System.out.format("%s	%s		%s	%s	%s \n", row.getUUID("id"), row.getString("author"), row.getString("description"), row.getString("name"),  row.getList("tags", String.class), row.getTimestamp("ts") );
		}

		//query 3.2.d.7
		String sql_cmd_7 = "select followers_usernames from video_followers where video_id='e7bc07b3-c2fe-41b2-bd9f-77bba226db37'";
		resultset = session.execute(sql_cmd_7);

		System.out.println("\n3.2.d.7");
		System.out.println(" followers_usernames\n" + 
				"---------------------");
		for(Row row: resultset) {
			System.out.format("%s \n", row.getSet("followers_usernames", String.class));
		}

		//query 3.2.d.9
		String sql_cmd_9 = "SELECT id, avg(rating) as MEDIA FROM video_ratings;";
		resultset = session.execute(sql_cmd_9);

		System.out.println("\n3.2.d.9");
		System.out.println("id                                   | media\n" + 
				"--------------------------------------+-------\n" );
		for(Row row: resultset) {
			System.out.format("%s 				%d \n", row.getUUID("id"), row.getInt("media"));
		}
		
	}

}
