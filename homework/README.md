1. Used dsl tech : jooq
2. table schema : 
create table if not exists homework (
  id bigint auto_increment primary key,
  creation_time timestamp not null default current_timestamp,
  description varchar(500),
  modification_time timestamp not null default current_timestamp,
  title varchar(100),
  content varchar(1000)
);

3. SQLs
	a. insert into homework (title, description, content) values ('First Homework','IDM Homework','It's cool');
	b. update homework set title = 'cool homework' where content = 'cool!!!'
	c. select * from homework where description = 'IDM Homework' order by title;

4. Execute commands
	mvn generate-sources
	java -jar target/homework-0.0.1-SNAPSHOT.jar
	
5. Result
PlainSQL - ID: 29 title: cool homework1 desc: IDM Homework content: cool!
PlainSQL - ID: 30 title: cool homework2 desc: IDM Homework content: cool!!!
PlainSQL - ID: 31 title: cool homework3 desc: IDM Homework content: It's cool
DSL - ID: 29 title: cool homework1 desc: IDM Homework content: cool!
DSL - ID: 30 title: cool homework2 desc: IDM Homework content: cool!!!
DSL - ID: 31 title: cool homework3 desc: IDM Homework content: It's cool
DSL - Left data cnt : 0
	