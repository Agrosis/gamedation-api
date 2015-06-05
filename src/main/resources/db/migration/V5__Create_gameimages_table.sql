
CREATE TABLE `game_images` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `gameId` bigint(11) NOT NULL,
  `link` text NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`gameId`) REFERENCES games(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
