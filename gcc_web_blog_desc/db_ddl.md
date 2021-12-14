# user
```sql
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) COLLATE utf8_bin NOT NULL,
  `password` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `nickname` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `level` int(1) NOT NULL DEFAULT '1',
  `enable` int(1) NOT NULL DEFAULT '1',
  `email` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin
```
# article
```sql
CREATE TABLE `article` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `content` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `userID` int NOT NULL,
  `topicID` int DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin
```
# category
```sql
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `desc` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin
```
# comment
```sql
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `articleId` int NOT NULL,
  `content` text COLLATE utf8_bin NOT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin
```