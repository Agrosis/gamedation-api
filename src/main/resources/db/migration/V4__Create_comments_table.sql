
CREATE TABLE `comments` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `gameId` bigint(11) NOT NULL,
  `memberId` bigint(11) NOT NULL,
  `parentId` bigint(11) NULL,
  `text` text NOT NULL,
  `time` bigint(11) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`memberId`) REFERENCES members(`id`),
  FOREIGN KEY (`gameId`) REFERENCES games(`id`),
  FOREIGN KEY (`parentId`) REFERENCES comments(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
