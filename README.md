IDM Homework
===
- - -

1.  Used dsl tech : jooq

table schema : 

    HOMEWORK
        id:bigint auto_increment primary key
        creation_time : timestamp not null default current_titmestamp
        modification_time : timestamp not null default current_titmestamp
        description : varchar(500)
        title : varchar(100)
        content varchar(1000)
        
2.  SQLs

     a. insert into homework (title, description, content) values ('First Homework','IDM Homework','It's cool'); 

    b. update homework set title = 'cool homework' where content = 'cool!!!' 

    c. select * from homework where description = 'IDM Homework' order by title;

3.  Execute commands : 

    * download zip and extract it
    * cd homework
    * mvn generate-sources java -jar target/homework-0.0.1-SNAPSHOT.jar


4. Result PlainSQL - 

result : 

    DSL - ID: 29 title: cool homework1 desc: IDM Homework content: cool! 
    PlainSQL - ID: 30 title: cool homework2 desc: IDM Homework content: cool!!! 
    PlainSQL - ID: 31 title: cool homework3 desc: IDM Homework content: It's cool 
    DSL - ID: 29 title: cool homework1 desc: IDM Homework content: cool! 
    DSL - ID: 30 title: cool homework2 desc: IDM Homework content: cool!!! 
    DSL - ID: 31 title: cool homework3 desc: IDM Homework content: It's cool 
    DSL - Left data cnt : 0
