-- source D:/WorkPlace/EAlbum/src/main/webapp/EAblumSql.sql

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS user;
CREATE TABLE user (
    id INT PRIMARY KEY  AUTO_INCREMENT,
    username varchar(32) NOT NULL,
    password varchar(32) NOT NULL,
    email varchar(255) NOT NULL,
    active tinyint(1) NOT NULL
);

DROP TABLE IF EXISTS photo;
CREATE TABLE photo (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  uid int(11)  NOT NULL,
  aid int(11) NOT NULL,
  date datetime NOT NULL,
  prepath varchar(255)  NOT NULL,
  path varchar(255)  NOT NULL,
  deleted tinyint(1) NOT NULL  DEFAULT 0 ,
  deletedday datetime DEFAULT NULL
);

DROP TABLE IF EXISTS album;
CREATE TABLE album (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
  uid int(11)  NOT NULL,
  name varchar(255) NOT NULL,
  size int(11) NOT NULL,
  deleted tinyint(1) NOT NULL DEFAULT 0,
  previewPhotoPath VARCHAR(255)
);

-- 新建用户时创建默认相册
Drop TRIGGER IF EXISTS addDefaultAlbum;
CREATE trigger addDefaultAlbum
AFTER insert on user
FOR EACH ROW
insert into album(uid,name,size) VALUES(new.id,'默认相册',0);



-- album表的size、prepath通过trigger进行管理

-- 增加照片时更新对应album size、prepath
Drop TRIGGER IF EXISTS updateAlbumIfInsertPhoto;
CREATE trigger updateAlbumIfInsertPhoto
AFTER insert on photo
FOR EACH ROW
UPDATE album set size = size +1,previewPhotoPath = new.prepath 
where id = new.aid;

-- 恢复/删除照片时更新对应album 数量、路径、是否被删
Drop TRIGGER IF EXISTS updateAlbumSize;
delimiter // 
CREATE trigger updateAlbumSize
AFTER update on photo
FOR EACH ROW
BEGIN
IF old.deleted < new.deleted THEN
UPDATE album set size = size - 1 ,
previewPhotoPath = (select prepath from photo where aid = old.aid and deleted = 0   order by id desc limit 1) 
   where id = old.aid;
ELSEIF old.deleted > new.deleted THEN
UPDATE album set deleted = 0 where id = old.aid;
UPDATE album set size = size + 1 ,
previewPhotoPath = (select prepath from photo where aid = old.aid and deleted = 0  order by id desc limit 1)
   where id = old.aid;
END IF;
END;
//
delimiter ;
