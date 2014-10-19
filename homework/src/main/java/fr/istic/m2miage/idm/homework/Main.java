package fr.istic.m2miage.idm.homework;

import static fr.istic.m2miage.idm.homework.generated.Tables.HOMEWORK;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectSeekStep1;
import org.jooq.impl.DSL;

import fr.istic.m2miage.idm.homework.generated.tables.records.HomeworkRecord;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Connection conn = null;
		
		Properties props = new Properties();
		props.load(new FileInputStream("src/main/resources/config.properties"));
		
        String userName = props.getProperty("db.username");
        String password = "";
        String url = props.getProperty("db.url");
        String driver = props.getProperty("db.driver");

        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            DSLContext ctx = DSL.using(conn, SQLDialect.H2);
            
            // SQL1 : insert into homework (title, description, content) values ('First Homework','IDM Homework','It's cool');
            // plain SQL
            conn.createStatement().execute("insert into homework (title, description, content) values ('First Homework','IDM Homework','cool!')");
            conn.createStatement().execute("insert into homework (title, description, content) values ('First Homework','IDM Homework','cool!!!')");
            // DLS
            ctx.insertInto(HOMEWORK, HOMEWORK.TITLE, HOMEWORK.DESCRIPTION, HOMEWORK.CONTENT)
            .values("First Homework", "IDM Homework","It's cool")
            .execute();
            
            // SQL2 : update homework set title = 'cool homework' where content = 'cool!!!');
            // plain sql
            conn.prepareStatement("update homework set title = 'cool homework1' where content = 'cool!'").execute();
            conn.prepareStatement("update homework set title = 'cool homework2' where content = 'cool!!!'").execute();
            ctx.update(HOMEWORK).set(HOMEWORK.TITLE, "cool homework3").where(HOMEWORK.CONTENT.equal("It's cool")).execute();

	            
            // SQL3 : select * from homework where description = 'IDM Homework' order by title;
            // plain SQL
            PreparedStatement ps = conn.prepareStatement("select * from homework where description = 'IDM Homework' order by title");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
            	Long id = rs.getLong("id");
            	String title = rs.getString("title");
            	String content = rs.getString("content");
            	String desc = rs.getString("description");
            	System.out.println("PlainSQL - ID: " + id + " title: " + title + " desc: " + desc + " content: " + content);
                 
            }
            // DSL
            SelectSeekStep1<HomeworkRecord, String> q 
            			= ctx.selectFrom(HOMEWORK)
            					.where(HOMEWORK.DESCRIPTION.equal("IDM Homework"))
            					.orderBy(HOMEWORK.TITLE);
            Result<HomeworkRecord> result = q.fetch();	
            for (Record r : result) {
                Long id = r.getValue(HOMEWORK.ID);
                String title = r.getValue(HOMEWORK.TITLE);
                String content = r.getValue(HOMEWORK.CONTENT);
                String desc = r.getValue(HOMEWORK.DESCRIPTION);

                System.out.println("DSL - ID: " + id + " title: " + title + " desc: " + desc + " content: " + content);
            }
            
                       
            // SQL3 : delete from homework where description = 'IDM Homework'
            ctx.delete(HOMEWORK).execute();
            System.out.println("DSL - Left data cnt : " + ctx.fetchCount(HOMEWORK));
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) {
                }
            }
        }
	}

}
