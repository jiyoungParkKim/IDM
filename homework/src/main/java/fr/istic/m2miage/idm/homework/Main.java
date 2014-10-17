package fr.istic.m2miage.idm.homework;

import static fr.istic.m2miage.idm.homework.generated.Tables.HOMEWORK;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.SQLDialect;
import org.jooq.SelectSeekStep1;
import org.jooq.impl.DSL;

import fr.istic.m2miage.idm.homework.generated.tables.records.HomeworkRecord;

public class Main {

	public static void main(String[] args) {
		Connection conn = null;

        String userName = "sa";
        String password = "";
        String url = "jdbc:h2:target/idm";

        try {
            Class.forName("org.h2.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            DSLContext ctx = DSL.using(conn, SQLDialect.H2);
            
            // SQL1 : insert into homework (title, description, content) values ('First Homework','IDM Homework','It's cool');
            for(int i = 0 ; i < 10 ; i ++){
                ctx.insertInto(HOMEWORK, HOMEWORK.TITLE, HOMEWORK.DESCRIPTION, HOMEWORK.CONTENT)
		            .values("First Homework", "IDM Homework","It's cool" + i)
		            .execute();
            }

	            
            // SQL2 : select * from homework where description = 'IDM Homework' order by title; 
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

                System.out.println("FETCH1 - ID: " + id + " title: " + title + " desc: " + desc + " content: " + content);
            }
            
            // Same query : Create a ResultQuery object and execute it, fetching results:
            ResultQuery<Record> resultQuery2 = ctx.resultQuery("SELECT * FROM HOMEWORK");
            Result<Record> results = resultQuery2.fetch();
            for (Record r : results) {
                Long id = r.getValue(HOMEWORK.ID);
                String title = r.getValue(HOMEWORK.TITLE);
                String content = r.getValue(HOMEWORK.CONTENT);
                String desc = r.getValue(HOMEWORK.DESCRIPTION);

                System.out.println("FETCH2 - ID: " + id + " title: " + title + " desc: " + desc + " content: " + content);
            }
            
            
            // SQL3 : delete from homework where description = 'IDM Homework'
            ctx.delete(HOMEWORK).where(HOMEWORK.DESCRIPTION.equal("IDM Homework")).execute();
            System.out.println("Left data cnt : " + ctx.fetchCount(HOMEWORK));
            
        } catch (Exception e) {
            // For the sake of this tutorial, let's keep exception handling simple
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
