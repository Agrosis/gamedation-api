
CREATE TABLE `upvotes` (
  `memberId` bigint(11) NOT NULL,
  `gameId` bigint(11) NOT NULL,
  `time` bigint(11) NOT NULL,
  FOREIGN KEY (`memberId`) REFERENCES members(`id`),
  FOREIGN KEY (`gameId`) REFERENCES games(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;
