PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE hello
(
	id integer not null primary key,
	title varchar(150),
	text text
);
INSERT INTO "hello" VALUES(1,'title1','text1');
INSERT INTO "hello" VALUES(2,'title2','text2');
COMMIT;
