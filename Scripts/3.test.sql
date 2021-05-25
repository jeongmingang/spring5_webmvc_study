select user(), database ();

desc member;

select * from member;

delete from member where ID > 59;

/* webmvc_mybatis test */
select id, email, password, name, regdate from member;

select id, email, password, name, regdate
	from member
 where email="test3@naver.com";

select * 
	from member 
 where regdate between '2021-05-17 00' and '2021-05-22 00'
 order by regdate desc;

