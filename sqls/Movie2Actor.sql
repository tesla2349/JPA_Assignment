CREATE TABLE `movie2actor` (
  `moviesActed_ID` int(11) NOT NULL,
  `actors_ID` int(11) NOT NULL,
  PRIMARY KEY (`moviesActed_ID`,`actors_ID`),
  KEY `FK_MOVIE2ACTOR_actors_ID` (`actors_ID`),
  CONSTRAINT `FK_MOVIE2ACTOR_actors_ID` FOREIGN KEY (`actors_ID`) REFERENCES `person` (`ID`),
  CONSTRAINT `FK_MOVIE2ACTOR_moviesActed_ID` FOREIGN KEY (`moviesActed_ID`) REFERENCES `movie` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;