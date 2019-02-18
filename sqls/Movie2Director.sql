CREATE TABLE `movie2director` (
  `moviesdirected_ID` int(11) NOT NULL,
  `directors_ID` int(11) NOT NULL,
  PRIMARY KEY (`moviesdirected_ID`,`directors_ID`),
  KEY `FK_MOVIE2DIRECTOR_directors_ID` (`directors_ID`),
  CONSTRAINT `FK_MOVIE2DIRECTOR_directors_ID` FOREIGN KEY (`directors_ID`) REFERENCES `person` (`ID`),
  CONSTRAINT `FK_MOVIE2DIRECTOR_moviesdirected_ID` FOREIGN KEY (`moviesdirected_ID`) REFERENCES `movie` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;