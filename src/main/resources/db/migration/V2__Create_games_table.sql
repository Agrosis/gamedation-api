
CREATE TABLE `games` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `link` text NOT NULL,
  `name` varchar(64) NOT NULL,
  `description` varchar(256) NOT NULL,
  `windows` boolean NOT NULL,
  `mac` boolean NOT NULL,
  `linux` boolean NOT NULL,
  `browser` boolean NOT NULL,
  `iOS` boolean NOT NULL,
  `android` boolean NOT NULL,
  `points` int(4) NOT NULL,
  `posterId` bigint(11) NOT NULL,
  `site` int(4) NOT NULL,
  `submitted` bigint(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`posterId`) REFERENCES members(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
