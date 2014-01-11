DROP TABLE RECORDS; 
DROP TABLE Clients;
DROP TABLE Sellers;
DROP TABLE Cars;
DROP TABLE Users;

CREATE TABLE Clients
(
	ClientID INT NOT NULL GENERATED ALWAYS AS IDENTITY,
	FirstName VARCHAR (30) NOT NULL,
	LastName VARCHAR (30) NOT NULL,
	Street VARCHAR (150) NOT NULL,
	City VARCHAR (30) NOT NULL,
	State VARCHAR (2) NOT NULL,
	Zip VARCHAR (5) NOT NULL,
	BirthDate VARCHAR (10) NOT NULL,
	Email VARCHAR (50) NOT NULL,
	PRIMARY KEY ("CLIENTID")
);

INSERT INTO Clients (FirstName,LastName,Street,City,State,Zip,BirthDate,Email) VALUES 
    ('Russell','Crowe','5 Bay St.','San Francisco','CA','94133','01/02/2013','Russell@stars.com'),
    ('Sean','Connery','100 5th Ave.','New York','NY','10011','02/03/2012','Sean@stars.com'),
    ('Angelina','Jolie','3600 Delmar Blvd.','St. Louis','MO','63108','03/04/2011','Angelina@stars.com'),
    ('Al','Pacino','300 Massachusetts Ave.','Boston','MA','02115','05/06/2010','Al@stars.com'),
    ('Anthony','Hopkins','500 South St.','Philadelphia','PA','19147','07/08/2009','Hopkins@stars.com'),
    ('Tom','Hanks','1200 Stout St.','Denver','CO','80204','09/10/2008','Tom@stars.com'),
    ('Will','Smith','1000 Harbor Ave.','Seattle','WA','98116','11/12/2007','Smith@stars.com'),
    ('Bruce','Willis','1000 Harbor Ave.','Seattle','WA','98116','11/12/2007','Smith@stars.com'),
    ('Denzel','Washington','1000 Harbor Ave.','Seattle','WA','98116','11/12/2007','Smith@stars.com'),
    ('Gerard','Butler','1000 Harbor Ave.','Seattle','WA','98116','11/12/2007','Smith@stars.com'),
    ('Jackie','Chan','1000 Harbor Ave.','Seattle','WA','98116','11/12/2007','Smith@stars.com'),
    ('Robin','Williams','1000 Harbor Ave.','Seattle','WA','98116','11/12/2007','Smith@stars.com'),
    ('Will','Smith','1000 Harbor Ave.','Seattle','WA','98116','11/12/2007','Smith@stars.com'),
    ('Julia','Roberts','123 Michigan Cir.','Chicago','IL','60605','12/12/2006','Julia.Roberts@stars.com'), 
    ('Nicole','Kidman','678 Harbor Ave.','Seattle','VT','12345','11/12/2007','Nicole@stars.com'),
    ('Sandra','Bullock','1000 Blue Ave.','Los Angeles','CA','98116','11/12/2007','Sandra@stars.com'),
    ('Halle','Berry','1650 Black Ave.','Las Vegas','NV','88546','11/12/2007','Halle.B@stars.com'),
    ('Natalie','Portman','15 Yellow St.','Chicago','IL','23485','11/12/2007','NPortman@stars.com'),
    ('Cate','Blanchett','789 Harbor Ave.','New York','NY','96571','11/12/2007','Cate@stars.com'),
    ('Amanda','Seyfried','15 Yellow St.','Chicago','IL','23485','11/12/2007','NPortman@stars.com'),
    ('Scarlett','Johansson','15 Yellow St.','Chicago','IL','23485','11/12/2007','NPortman@stars.com'),
    ('Jessica','Alba','15 Yellow St.','Chicago','IL','23485','11/12/2007','NPortman@stars.com'),
    ('Drew','Barrymore','15 Yellow St.','Chicago','IL','23485','11/12/2007','NPortman@stars.com'),
    ('Keira','Knightley','15 Yellow St.','Chicago','IL','23485','11/12/2007','NPortman@stars.com'),
    ('Kate','Hudson','15 Yellow St.','Chicago','IL','23485','11/12/2007','NPortman@stars.com');
   
   
CREATE TABLE Sellers
(
	SellerID INT NOT NULL GENERATED ALWAYS AS IDENTITY,
	FirstName VARCHAR (30) NOT NULL,
	LastName VARCHAR (30) NOT NULL,
	Street VARCHAR (150) NOT NULL,
	City VARCHAR (30) NOT NULL,
	State VARCHAR (2) NOT NULL,
	Zip VARCHAR (5) NOT NULL,
	BirthDate VARCHAR (10) NOT NULL,
	Email VARCHAR (50) NOT NULL,
	PRIMARY KEY ("SELLERID")
);

INSERT INTO Sellers (FirstName,LastName,Street,City,State,Zip,BirthDate,Email) VALUES 
    ('Bob','Green','5 Bay St.','San Francisco','CA','94133','01/02/2013','Bob.Green@car.com'),
    ('Liz','White','100 5th Ave.','New York','NY','10011','02/03/2012','Liz.White@car.com'),
    ('Mike','Brown','3600 Delmar Blvd.','St. Louis','MO','63108','03/04/2011','Mike.Brown@car.com'),
    ('Mary','Green','300 Massachusetts Ave.','Boston','MA','02115','05/06/2010','Mary.Green@car.com'),
    ('John','Gray','500 South St.','Philadelphia','PA','19147','07/08/2009','John.Gray@car.com'),
    ('Susan','Blue','500 South St.','Philadelphia','PA','19147','07/08/2009','John.Gray@car.com'),
    ('Henry','Red','500 South St.','Philadelphia','PA','19147','07/08/2009','John.Gray@car.com'),
    ('Joe','Yellow','500 South St.','Philadelphia','PA','19147','07/08/2009','John.Gray@car.com'),
    ('Lacy','Brown','500 South St.','Philadelphia','PA','19147','07/08/2009','John.Gray@car.com'),
    ('Matt','Green','500 South St.','Philadelphia','PA','19147','07/08/2009','John.Gray@car.com'),
    ('Emily','Silver','500 South St.','Philadelphia','PA','19147','07/08/2009','John.Gray@car.com'),
    ('Ashley','Gold','500 South St.','Philadelphia','PA','19147','07/08/2009','John.Gray@car.com'),
    ('Chris','Black','500 South St.','Philadelphia','PA','19147','07/08/2009','John.Gray@car.com');
   
  
CREATE TABLE Cars
(
	VIN VARCHAR (17) NOT NULL,
	Make VARCHAR (30) NOT NULL,
	Model VARCHAR (30) NOT NULL,
	ModelYear VARCHAR (4) NOT NULL,
        Mileage INT,
	Color VARCHAR (30) NOT NULL,
	Motor VARCHAR (30) NOT NULL,
	City VARCHAR (30) NOT NULL,
	Sold BOOLEAN NOT NULL,
	PurchaserID INT,
	PRIMARY KEY ("VIN"),
	FOREIGN KEY ("PURCHASERID") REFERENCES "CLIENTS" ("CLIENTID")
	
);

INSERT INTO Cars (VIN,Make,Model,ModelYear,Mileage,Color,Motor,City,Sold,PurchaserID) VALUES 
    ('19UYA31581L001260','Ford','Focus','1995',150000,'Black','2.0 L','Salt Lake City',false,null),
    ('16UYA31581L351001','Toyota','Avalon','2000',75000,'White','2.2 L','Provo',false,null),
    ('12UYA31581L005482','Nissan','Maxima','2003',10000,'White','3.5 L V6','Taylorsville',false,null),
    ('16UYA31581L042563','Honda','Civic','2009',45000,'Blue','4 Cylinder','Provo',true,1),
    ('19UYA35981L002654','Chevrolet','Corvette','2013',1500,'Red','V10','Salt Lake City',true,2),
    ('16BCD31581L033005','Ford','Escape','2003',135000,'Green','2.5 L 4C','Salt Lake City',false,null),
    ('14FCD33681L033321','Nissan','Sentra','2001',128596,'Red','32JV6C','Taylorsville',false,null),
    ('18UVZ46281L964752','Hyundai','Elantra','2006',95250,'Gray','2.0 L 4Cyl','Provo',false,null),
    ('19UYA75881L423585','Nissan','370Z','2013',650,'Gun Metal','3.7 L V6','Salt Lake City',false,null),
    ('18UVZ46281L964753','Ford','Focus','2006',95250,'Gray','2.0 L 4Cyl','Provo',false,null),
    ('18UVZ46281L964754','Ford','F150','2006',95250,'Gray','2.0 L 4Cyl','Provo',false,null),
    ('18UVZ46281L964755','Chevrolet','Volt','2006',95250,'Gray','2.0 L 4Cyl','Provo',false,null),
    ('18UVZ46281L964756','Nissan','Altima','2006',95250,'Gray','2.0 L 4Cyl','Provo',false,null),
    ('12UVZ46281L964757','Hyundai','Elantra','2006',95250,'Gray','2.0 L 4Cyl','Provo',true,5),
    ('13UVZ46281L964758','Honda','Civi','2006',95250,'Gray','2.0 L 4Cyl','Provo',true,7),
    ('14UVZ46281L964759','Acura','NSX','2006',95250,'Gray','2.0 L 4Cyl','Provo',true,9),
    ('15UVZ46281L964760','Audi','A8','2006',95250,'Gray','2.0 L 4Cyl','Provo',true,10),
    ('16UVZ46281L964761','Porsche','911','2006',95250,'Gray','2.0 L 4Cyl','Provo',true,11);
   
   
CREATE TABLE RECORDS
(
	RECORDNUMBER INT NOT NULL GENERATED ALWAYS AS IDENTITY,
	SellerID INT NOT NULL,
	ClientID INT NOT NULL,
	CarVIN VARCHAR (17) NOT NULL,
	Date VARCHAR (10) NOT NULL, -- mm/dd/year
	Amount INT NOT NULL,
	PRIMARY KEY ("RECORDNUMBER"),
	FOREIGN KEY ("SELLERID") REFERENCES "SELLERS" ("SELLERID"),
	FOREIGN KEY ("CLIENTID") REFERENCES "CLIENTS" ("CLIENTID"),
	FOREIGN KEY ("CARVIN") REFERENCES "CARS" ("VIN")	
);

INSERT INTO RECORDS (SellerID,ClientID,CarVIN,Date,Amount) VALUES 
    (1,1,'16UYA31581L042563','01/02/2013',5000),
    (2,2,'19UYA35981L002654','03/04/2013',16000),
    (2,5,'12UVZ46281L964757','03/06/2013',2100),
    (3,7,'13UVZ46281L964758','03/08/2013',17000),
    (2,9,'14UVZ46281L964759','03/02/2013',120),
    (1,10,'15UVZ46281L964760','03/13/2013',75000),
    (2,11,'16UVZ46281L964761','03/18/2013',12575);


CREATE TABLE Users
(
	USERNAME VARCHAR (17) NOT NULL,
	PASSWORD VARCHAR (30) NOT NULL,
        ACTIVE BOOLEAN NOT NULL,
        PRIMARY KEY ("USERNAME")
);

INSERT INTO USERS (USERNAME,PASSWORD,ACTIVE) VALUES 
    ('christophern','Password',true),
    ('borisj','12345',true),
    ('wadep','qwerty',true),
    ('bill.gates','Money',true),
    ('steve.jobs','Dead',false);