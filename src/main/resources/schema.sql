CREATE TABLE planet (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  swid varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  climate varchar(255) DEFAULT NULL,
  terrain varchar(255) DEFAULT NULL,
  films int(11) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=312 DEFAULT CHARSET=latin1