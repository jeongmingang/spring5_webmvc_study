select user(), database ();

desc member;

select * from member;

delete from member where ID > 59;

/* webmvc_mybatis test */
select id, email, password, name, regdate from member;

select id, email, password, name, regdate
	from member
 where email="test3@naver.com";

