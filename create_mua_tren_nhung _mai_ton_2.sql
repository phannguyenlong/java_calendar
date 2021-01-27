-- CREATE TABLE
CREATE DATABASE SQLCalendar;

-- DROP EXISTING DATABASE
DROP TABLE IF EXISTS orderItem;
DROP TABLE IF EXISTS eventInstance;
DROP TABLE IF EXISTS orderList;
DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS item;

-- CREATE TABLE
CREATE TABLE employee(
	ssn CHAR(11) not NULL,
	fname VARCHAR(29) not NULL,
	lname VARCHAR(29) not NULL,
	bdate DATE,
	address VARCHAR(59),
	sex CHAR(1),
	phone CHAR(10),
	type VARCHAR(19),
	username VARCHAR(29),
	password VARCHAR(29),
	constraint employee_ssn_pk primary key (ssn),
);

CREATE TABLE orderList(
	orderID INT IDENTITY(1,1),
	date DATE,
	time TIME,
	totalPrice MONEY,
	essn CHAR(11),
	CONSTRAINT order_orderID_pk 
		PRIMARY KEY (orderID),
	CONSTRAINT order_prderID_fk 
		FOREIGN KEY (essn) 
		REFERENCES employee(ssn) 
		ON DELETE NO ACTION
		ON UPDATE CASCADE
);

CREATE TABLE item(
	itemID	TINYINT not NULL IDENTITY(1,1),
	itemName VARCHAR(59),
	price MONEY not NULL,
	CONSTRAINT item_itemID_pk
		PRIMARY KEY (itemID),
);

CREATE TABLE orderItem(
	orderID INT,
	itemID TINYINT,
	quantity TINYINT,
	CONSTRAINT orderItem_orderItem_pk
		PRIMARY KEY (orderID, itemID),
	CONSTRAINT orderItem_orderID_fk1
		FOREIGN KEY (orderID) 
		REFERENCES orderList(orderID)
		ON DELETE CASCADE,
	CONSTRAINT orderItem_itemID_fk2
		FOREIGN KEY (itemID) 
		REFERENCES item(itemID)
		ON DELETE NO ACTION
		ON UPDATE CASCADE
);

CREATE TABLE event(
	eventID INT IDENTITY(1,1),
	eventName VARCHAR(59),
	startDate DATE,
	startTime TIME,
	endDate DATE,
	endTime TIME,
	eventType VARCHAR(19),
	CONSTRAINT event_eventID_pk 
		PRIMARY KEY (eventID)
);

CREATE TABLE eventInstance(
	essn CHAR(11),
	eventID INT,
	date DATE,
	status VARCHAR(11),
	CONSTRAINT eventInstance_pk
		PRIMARY KEY (essn, eventID, date),
	CONSTRAINT eventInstance_fk 
		FOREIGN KEY (essn)
		REFERENCES employee(ssn)
		ON DELETE CASCADE,
	CONSTRAINT eventInstance_fk2 
		FOREIGN KEY (eventID)
		REFERENCES event(eventID)
		ON DELETE CASCADE
);


-- CREATE EMPLOYEE TABLE
insert into employee (ssn, fname, lname, bdate, address, sex, phone, type, username, password) values ('432-35-7378', 'Pace', 'Jennins', '1/12/1995', '71628 Mallard Court', 'M', '2738014731', 'manager', 'pjennins0', 'ozOrsJ2Sm');
insert into employee (ssn, fname, lname, bdate, address, sex, phone, type, username, password) values ('479-02-2076', 'Kaylil', 'Ebbers', '10/19/2000', '8308 Lighthouse Bay Hill', 'F', '4567836573', 'manager', 'kebbers1', 'bnfd3KzKM0t');
insert into employee (ssn, fname, lname, bdate, address, sex, phone, type, username, password) values ('515-59-1113', 'Jarvis', 'Swepson', '4/13/1996', '170 Stoughton Alley', 'M', '2757201634', 'cashier', 'jswepson2', 'F1fDUcwlZLk');
insert into employee (ssn, fname, lname, bdate, address, sex, phone, type, username, password) values ('820-67-3659', 'Toby', 'Akast', '9/23/1999', '862 Marquette Junction', 'M', '4674789654', 'cashier', 'takast3', 'YyWKVhNtyyRT');
insert into employee (ssn, fname, lname, bdate, address, sex, phone, type, username, password) values ('369-60-6431', 'Ileane', 'Towler', '5/9/1997', '26 North Plaza', 'F', '3762537122', 'cashier', 'itowler4', 'qJo6kOmv');
insert into employee (ssn, fname, lname, bdate, address, sex, phone, type, username, password) values ('155-64-7688', 'Cliff', 'Rigmand', '12/3/1999', '970 Meadow Vale Avenue', 'M', '6818343912', 'staff', 'crigmand5', 'ss2sH5R3HbQR');
insert into employee (ssn, fname, lname, bdate, address, sex, phone, type, username, password) values ('288-92-2071', 'Nevins', 'Ambrogiotti', '3/31/1994', '18585 Scofield Junction', 'M', '7452745505', 'staff', 'nambrogiotti6', '12LV1uF');
insert into employee (ssn, fname, lname, bdate, address, sex, phone, type, username, password) values ('504-06-2817', 'Fields', 'Burnsall', '8/4/2000', '54502 Coolidge Parkway', 'M', '1122692537', 'staff', 'fburnsall7', 'MeSPuA');
insert into employee (ssn, fname, lname, bdate, address, sex, phone, type, username, password) values ('484-28-8022', 'Eloisa', 'Willetts', '7/28/1994', '4121 Mitchell Hill', 'F', '7586719292', 'staff', 'ewilletts8', '9EiftA');
insert into employee (ssn, fname, lname, bdate, address, sex, phone, type, username, password) values ('765-59-1185', 'Elbert', 'Bennallck', '3/6/2000', '5825 Ryan Point', 'M', '9792993180', 'staff', 'ebennallck9', 'hyGVmm11hE');
insert into employee (ssn, fname, lname, bdate, address, sex, phone, type, username, password) values ('114-37-5085', 'Martainn', 'Pudan', '9/2/1992', '79 Melvin Road', 'M', '3434836700', 'staff', 'mpudana', '8Z68tj');

-- CREATE ITEM TABLE
insert into item (itemName, Price) values ('Water - Tonic', '$16.88');
insert into item (itemName, Price) values ('Spice - Chili Powder Mexican', '$3.26');
insert into item (itemName, Price) values ('Uniform Linen Charge', '$17.27');
insert into item (itemName, Price) values ('Cabbage - Red', '$3.64');
insert into item (itemName, Price) values ('Lettuce - Frisee', '$17.39');
insert into item (itemName, Price) values ('Soup - Campbells, Minestrone', '$3.38');
insert into item (itemName, Price) values ('Foil Wrap', '$2.32');
insert into item (itemName, Price) values ('Juice - Mango', '$5.23');
insert into item (itemName, Price) values ('Duck - Legs', '$12.77');
insert into item (itemName, Price) values ('Beef - Bones, Cut - Up', '$11.30');
insert into item (itemName, Price) values ('Chicken - Whole Fryers', '$6.31');

-- CREATE EVENT TABLE
insert into event (eventName, startDate, startTime, endDate, endTime, eventType) values ('Event 1', '12/23/2020', '7:00', '12/30/2020', '23:00', 'daily');
insert into event (eventName, startDate, startTime, endDate, endTime, eventType) values ('Event 2', '12/20/2020', '7:00', '1/9/2021', '13:00', 'weekly');
--insert into event (eventName, startDate, startTime, endDate, endTime, eventType) values ('Event 3', '12/20/2020', '13:00', '1/9/2021', '23:00', 'monthly');
insert into event (eventName, startDate, startTime, endDate, endTime, eventType) values ('Event 4', '1/4/2021', '7:00', '1/11/2021', '23:00', 'daily');
insert into event (eventName, startDate, startTime, endDate, endTime, eventType) values ('Event 5', '1/7/2021', '13:00', '1/7/2021', '23:00', 'no repeat');

-- CREATE EVENT_INSTANCE
-- staff
insert into eventInstance (essn, eventID, date, status) values ('765-59-1185', 1, '12/28/2020', 'present');
insert into eventInstance (essn, eventID, date, status) values ('155-64-7688', 1, '12/27/2020', 'absent');
insert into eventInstance (essn, eventID, date, status) values ('155-64-7688', 1, '12/25/2020', 'present');
insert into eventInstance (essn, eventID, date, status) values ('504-06-2817', 1, '12/24/2020', 'present');
insert into eventInstance (essn, eventID, date, status) values ('155-64-7688', 1, '12/29/2020', 'absent');
insert into eventInstance (essn, eventID, date, status) values ('765-59-1185', 1, '12/30/2020', 'present');
insert into eventInstance (essn, eventID, date, status) values ('504-06-2817', 1, '12/29/2020', 'absent');
insert into eventInstance (essn, eventID, date, status) values ('484-28-8022', 1, '12/24/2020', 'absent');
insert into eventInstance (essn, eventID, date, status) values ('484-28-8022', 1, '12/23/2020', 'present');
insert into eventInstance (essn, eventID, date, status) values ('155-64-7688', 1, '12/26/2020', 'absent');
insert into eventInstance (essn, eventID, date, status) values ('504-06-2817', 1, '12/26/2020', 'absent');
-- cashier
insert into eventInstance (essn, eventID, date, status) values ('369-60-6431', 1, '12/23/2020', 'present');
insert into eventInstance (essn, eventID, date, status) values ('369-60-6431', 1, '12/24/2020', 'present');
insert into eventInstance (essn, eventID, date, status) values ('369-60-6431', 1, '12/25/2020', 'present');
insert into eventInstance (essn, eventID, date, status) values ('820-67-3659', 1, '12/26/2020', 'present');
insert into eventInstance (essn, eventID, date, status) values ('369-60-6431', 1, '12/27/2020', 'present');
insert into eventInstance (essn, eventID, date, status) values ('515-59-1113', 1, '12/28/2020', 'present');
insert into eventInstance (essn, eventID, date, status) values ('820-67-3659', 1, '12/29/2020', 'present');

-- CREATE ORDERLIST TABLE
insert into orderList (date, time, essn) values ('12/27/2020', '10:54', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '8:40', '369-60-6431');
insert into orderList (date, time, essn) values ('12/24/2020', '9:08', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '11:42', '369-60-6431');
insert into orderList (date, time, essn) values ('12/24/2020', '12:20', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '8:04', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '8:59', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '18:14', '369-60-6431');
insert into orderList (date, time, essn) values ('12/24/2020', '19:00', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '17:53', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '12:15', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '8:51', '369-60-6431');
insert into orderList (date, time, essn) values ('12/24/2020', '19:10', '369-60-6431');
insert into orderList (date, time, essn) values ('12/24/2020', '8:07', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '20:35', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '22:41', '369-60-6431');
insert into orderList (date, time, essn) values ('12/27/2020', '20:11', '369-60-6431');
insert into orderList (date, time, essn) values ('12/27/2020', '17:21', '369-60-6431');
insert into orderList (date, time, essn) values ('12/27/2020', '15:26', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '19:00', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '9:54', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '16:37', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '22:43', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '8:38', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '18:41', '369-60-6431');
insert into orderList (date, time, essn) values ('12/27/2020', '19:27', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '10:41', '369-60-6431');
insert into orderList (date, time, essn) values ('12/24/2020', '12:34', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '8:53', '369-60-6431');
insert into orderList (date, time, essn) values ('12/27/2020', '8:52', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '11:37', '369-60-6431');
insert into orderList (date, time, essn) values ('12/24/2020', '9:54', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '18:09', '369-60-6431');
insert into orderList (date, time, essn) values ('12/27/2020', '18:06', '369-60-6431');
insert into orderList (date, time, essn) values ('12/27/2020', '20:36', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '9:46', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '22:14', '369-60-6431');
insert into orderList (date, time, essn) values ('12/24/2020', '17:51', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '22:26', '369-60-6431');
insert into orderList (date, time, essn) values ('12/24/2020', '9:11', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '12:05', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '16:35', '369-60-6431');
insert into orderList (date, time, essn) values ('12/27/2020', '8:11', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '19:33', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '21:13', '369-60-6431');
insert into orderList (date, time, essn) values ('12/24/2020', '11:09', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '9:28', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '19:32', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '22:41', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '11:23', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '21:05', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '11:16', '369-60-6431');
insert into orderList (date, time, essn) values ('12/27/2020', '14:48', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '11:12', '369-60-6431');
insert into orderList (date, time, essn) values ('12/27/2020', '17:36', '369-60-6431');
insert into orderList (date, time, essn) values ('12/23/2020', '9:19', '369-60-6431');
insert into orderList (date, time, essn) values ('12/24/2020', '22:49', '369-60-6431');
insert into orderList (date, time, essn) values ('12/25/2020', '21:53', '369-60-6431');
insert into orderList (date, time, essn) values ('12/24/2020', '20:59', '369-60-6431');
insert into orderList (date, time, essn) values ('12/28/2020', '22:08', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '13:34', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '18:13', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '8:03', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '21:12', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '19:25', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '13:25', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '20:15', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '21:45', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '12:03', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '20:11', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '8:13', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '9:54', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '19:52', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '13:18', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '17:38', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '7:26', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '22:00', '515-59-1113');
insert into orderList (date, time, essn) values ('12/28/2020', '18:58', '515-59-1113');
insert into orderList (date, time, essn) values ('12/29/2020', '11:04', '820-67-3659');
insert into orderList (date, time, essn) values ('12/29/2020', '16:47', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '20:17', '820-67-3659');
insert into orderList (date, time, essn) values ('12/29/2020', '19:20', '820-67-3659');
insert into orderList (date, time, essn) values ('12/29/2020', '11:17', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '16:26', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '20:19', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '20:34', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '11:19', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '21:32', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '11:57', '820-67-3659');
insert into orderList (date, time, essn) values ('12/29/2020', '12:46', '820-67-3659');
insert into orderList (date, time, essn) values ('12/29/2020', '8:06', '820-67-3659');
insert into orderList (date, time, essn) values ('12/29/2020', '11:03', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '18:55', '820-67-3659');
insert into orderList (date, time, essn) values ('12/29/2020', '7:54', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '14:02', '820-67-3659');
insert into orderList (date, time, essn) values ('12/29/2020', '7:30', '820-67-3659');
insert into orderList (date, time, essn) values ('12/29/2020', '7:43', '820-67-3659');
insert into orderList (date, time, essn) values ('12/29/2020', '18:58', '820-67-3659');
insert into orderList (date, time, essn) values ('12/29/2020', '15:19', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '17:13', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '20:57', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '15:25', '820-67-3659');
insert into orderList (date, time, essn) values ('12/29/2020', '13:14', '820-67-3659');
insert into orderList (date, time, essn) values ('12/29/2020', '19:12', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '14:02', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '9:02', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '12:29', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '7:31', '820-67-3659');
insert into orderList (date, time, essn) values ('12/29/2020', '22:12', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '22:32', '820-67-3659');
insert into orderList (date, time, essn) values ('12/29/2020', '11:20', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '13:35', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '16:31', '820-67-3659');
insert into orderList (date, time, essn) values ('12/26/2020', '15:10', '820-67-3659');
insert into orderList (date, time, essn) values ('12/29/2020', '18:42', '820-67-3659');

-- CREATE ORDERITEM
insert into orderItem (orderID, itemID, quantity) values (6, 1, 6);
insert into orderItem (orderID, itemID, quantity) values (60, 2, 6);
insert into orderItem (orderID, itemID, quantity) values (110, 3, 1);
insert into orderItem (orderID, itemID, quantity) values (48, 4, 7);
insert into orderItem (orderID, itemID, quantity) values (55, 5, 1);
insert into orderItem (orderID, itemID, quantity) values (53, 6, 2);
insert into orderItem (orderID, itemID, quantity) values (94, 7, 7);
insert into orderItem (orderID, itemID, quantity) values (25, 8, 6);
insert into orderItem (orderID, itemID, quantity) values (75, 9, 4);
insert into orderItem (orderID, itemID, quantity) values (64, 10, 7);
insert into orderItem (orderID, itemID, quantity) values (76, 11, 1);
insert into orderItem (orderID, itemID, quantity) values (77, 1, 4);
insert into orderItem (orderID, itemID, quantity) values (114, 2, 3);
insert into orderItem (orderID, itemID, quantity) values (95, 3, 1);
insert into orderItem (orderID, itemID, quantity) values (66, 4, 7);
insert into orderItem (orderID, itemID, quantity) values (1, 5, 1);
insert into orderItem (orderID, itemID, quantity) values (20, 6, 7);
insert into orderItem (orderID, itemID, quantity) values (15, 7, 7);
insert into orderItem (orderID, itemID, quantity) values (14, 8, 3);
insert into orderItem (orderID, itemID, quantity) values (14, 9, 3);
insert into orderItem (orderID, itemID, quantity) values (16, 10, 5);
insert into orderItem (orderID, itemID, quantity) values (20, 11, 3);
insert into orderItem (orderID, itemID, quantity) values (41, 1, 7);
insert into orderItem (orderID, itemID, quantity) values (76, 2, 6);
insert into orderItem (orderID, itemID, quantity) values (8, 3, 7);
insert into orderItem (orderID, itemID, quantity) values (60, 4, 5);
insert into orderItem (orderID, itemID, quantity) values (34, 5, 4);
insert into orderItem (orderID, itemID, quantity) values (101, 6, 5);
insert into orderItem (orderID, itemID, quantity) values (26, 7, 3);
insert into orderItem (orderID, itemID, quantity) values (80, 8, 3);
insert into orderItem (orderID, itemID, quantity) values (10, 9, 2);
insert into orderItem (orderID, itemID, quantity) values (50, 10, 5);
insert into orderItem (orderID, itemID, quantity) values (71, 11, 6);
insert into orderItem (orderID, itemID, quantity) values (22, 1, 6);
insert into orderItem (orderID, itemID, quantity) values (95, 2, 1);
insert into orderItem (orderID, itemID, quantity) values (91, 3, 1);
insert into orderItem (orderID, itemID, quantity) values (108, 4, 7);
insert into orderItem (orderID, itemID, quantity) values (75, 5, 2);
insert into orderItem (orderID, itemID, quantity) values (108, 6, 6);
insert into orderItem (orderID, itemID, quantity) values (83, 7, 6);
insert into orderItem (orderID, itemID, quantity) values (52, 8, 1);
insert into orderItem (orderID, itemID, quantity) values (32, 9, 2);
insert into orderItem (orderID, itemID, quantity) values (12, 10, 4);
insert into orderItem (orderID, itemID, quantity) values (94, 11, 7);
insert into orderItem (orderID, itemID, quantity) values (23, 1, 6);
insert into orderItem (orderID, itemID, quantity) values (102, 2, 1);
insert into orderItem (orderID, itemID, quantity) values (113, 3, 3);
insert into orderItem (orderID, itemID, quantity) values (6, 4, 2);
insert into orderItem (orderID, itemID, quantity) values (49, 5, 6);
insert into orderItem (orderID, itemID, quantity) values (65, 6, 7);
insert into orderItem (orderID, itemID, quantity) values (43, 7, 6);
insert into orderItem (orderID, itemID, quantity) values (112, 8, 5);
insert into orderItem (orderID, itemID, quantity) values (84, 9, 7);
insert into orderItem (orderID, itemID, quantity) values (52, 10, 4);
insert into orderItem (orderID, itemID, quantity) values (19, 11, 6);
insert into orderItem (orderID, itemID, quantity) values (101, 1, 2);
insert into orderItem (orderID, itemID, quantity) values (101, 2, 4);
insert into orderItem (orderID, itemID, quantity) values (71, 3, 4);
insert into orderItem (orderID, itemID, quantity) values (12, 4, 7);
insert into orderItem (orderID, itemID, quantity) values (67, 5, 6);
insert into orderItem (orderID, itemID, quantity) values (76, 6, 2);
insert into orderItem (orderID, itemID, quantity) values (20, 7, 5);
insert into orderItem (orderID, itemID, quantity) values (52, 8, 5);
insert into orderItem (orderID, itemID, quantity) values (89, 9, 7);
insert into orderItem (orderID, itemID, quantity) values (109, 10, 5);
insert into orderItem (orderID, itemID, quantity) values (31, 11, 2);
insert into orderItem (orderID, itemID, quantity) values (7, 1, 4);
insert into orderItem (orderID, itemID, quantity) values (40, 2, 1);
insert into orderItem (orderID, itemID, quantity) values (22, 3, 2);
insert into orderItem (orderID, itemID, quantity) values (81, 4, 7);
insert into orderItem (orderID, itemID, quantity) values (23, 5, 7);
insert into orderItem (orderID, itemID, quantity) values (109, 6, 3);
insert into orderItem (orderID, itemID, quantity) values (5, 7, 5);
insert into orderItem (orderID, itemID, quantity) values (112, 8, 6);
insert into orderItem (orderID, itemID, quantity) values (71, 9, 6);
insert into orderItem (orderID, itemID, quantity) values (25, 10, 5);
insert into orderItem (orderID, itemID, quantity) values (88, 11, 1);
insert into orderItem (orderID, itemID, quantity) values (46, 1, 3);
insert into orderItem (orderID, itemID, quantity) values (85, 2, 3);
insert into orderItem (orderID, itemID, quantity) values (97, 3, 2);
insert into orderItem (orderID, itemID, quantity) values (34, 4, 1);
insert into orderItem (orderID, itemID, quantity) values (8, 5, 5);
insert into orderItem (orderID, itemID, quantity) values (31, 6, 5);
insert into orderItem (orderID, itemID, quantity) values (41, 7, 5);
insert into orderItem (orderID, itemID, quantity) values (66, 8, 3);
insert into orderItem (orderID, itemID, quantity) values (60, 9, 6);
insert into orderItem (orderID, itemID, quantity) values (56, 10, 3);
insert into orderItem (orderID, itemID, quantity) values (84, 11, 1);
insert into orderItem (orderID, itemID, quantity) values (107, 1, 1);
insert into orderItem (orderID, itemID, quantity) values (102, 2, 7);
insert into orderItem (orderID, itemID, quantity) values (89, 3, 6);
insert into orderItem (orderID, itemID, quantity) values (52, 4, 2);
insert into orderItem (orderID, itemID, quantity) values (2, 5, 1);
insert into orderItem (orderID, itemID, quantity) values (74, 6, 4);
insert into orderItem (orderID, itemID, quantity) values (26, 7, 6);
insert into orderItem (orderID, itemID, quantity) values (105, 8, 3);
insert into orderItem (orderID, itemID, quantity) values (94, 9, 4);
insert into orderItem (orderID, itemID, quantity) values (12, 10, 7);
insert into orderItem (orderID, itemID, quantity) values (20, 11, 7);
insert into orderItem (orderID, itemID, quantity) values (93, 1, 7);
insert into orderItem (orderID, itemID, quantity) values (45, 2, 6);
insert into orderItem (orderID, itemID, quantity) values (76, 3, 5);
insert into orderItem (orderID, itemID, quantity) values (47, 4, 5);
insert into orderItem (orderID, itemID, quantity) values (64, 5, 5);
insert into orderItem (orderID, itemID, quantity) values (49, 6, 4);
insert into orderItem (orderID, itemID, quantity) values (64, 7, 7);
insert into orderItem (orderID, itemID, quantity) values (34, 8, 7);
insert into orderItem (orderID, itemID, quantity) values (21, 9, 2);
insert into orderItem (orderID, itemID, quantity) values (107, 10, 3);
insert into orderItem (orderID, itemID, quantity) values (25, 11, 5);
insert into orderItem (orderID, itemID, quantity) values (93, 1, 4);
insert into orderItem (orderID, itemID, quantity) values (63, 2, 3);
insert into orderItem (orderID, itemID, quantity) values (65, 3, 3);
insert into orderItem (orderID, itemID, quantity) values (72, 4, 7);
insert into orderItem (orderID, itemID, quantity) values (59, 5, 1);
insert into orderItem (orderID, itemID, quantity) values (91, 6, 6);
insert into orderItem (orderID, itemID, quantity) values (32, 7, 3);
insert into orderItem (orderID, itemID, quantity) values (7, 8, 7);
insert into orderItem (orderID, itemID, quantity) values (33, 9, 1);
insert into orderItem (orderID, itemID, quantity) values (3, 10, 1);
insert into orderItem (orderID, itemID, quantity) values (32, 11, 4);
insert into orderItem (orderID, itemID, quantity) values (115, 1, 3);
insert into orderItem (orderID, itemID, quantity) values (40, 2, 2);
insert into orderItem (orderID, itemID, quantity) values (41, 3, 6);
insert into orderItem (orderID, itemID, quantity) values (64, 4, 2);
insert into orderItem (orderID, itemID, quantity) values (4, 5, 2);
insert into orderItem (orderID, itemID, quantity) values (36, 6, 2);
insert into orderItem (orderID, itemID, quantity) values (93, 7, 7);
insert into orderItem (orderID, itemID, quantity) values (83, 8, 3);
insert into orderItem (orderID, itemID, quantity) values (31, 9, 2);
insert into orderItem (orderID, itemID, quantity) values (11, 10, 2);
insert into orderItem (orderID, itemID, quantity) values (105, 11, 3);
insert into orderItem (orderID, itemID, quantity) values (65, 1, 4);
insert into orderItem (orderID, itemID, quantity) values (23, 2, 6);
insert into orderItem (orderID, itemID, quantity) values (82, 3, 1);
insert into orderItem (orderID, itemID, quantity) values (73, 4, 3);
insert into orderItem (orderID, itemID, quantity) values (17, 5, 2);
insert into orderItem (orderID, itemID, quantity) values (12, 6, 7);
insert into orderItem (orderID, itemID, quantity) values (47, 7, 5);
insert into orderItem (orderID, itemID, quantity) values (45, 8, 4);
insert into orderItem (orderID, itemID, quantity) values (83, 9, 3);
insert into orderItem (orderID, itemID, quantity) values (88, 10, 3);
insert into orderItem (orderID, itemID, quantity) values (15, 11, 4);
insert into orderItem (orderID, itemID, quantity) values (32, 1, 2);
insert into orderItem (orderID, itemID, quantity) values (62, 2, 6);
insert into orderItem (orderID, itemID, quantity) values (68, 3, 7);
insert into orderItem (orderID, itemID, quantity) values (23, 4, 1);
insert into orderItem (orderID, itemID, quantity) values (100, 5, 5);
insert into orderItem (orderID, itemID, quantity) values (98, 6, 7);
insert into orderItem (orderID, itemID, quantity) values (54, 7, 7);
insert into orderItem (orderID, itemID, quantity) values (58, 8, 3);
insert into orderItem (orderID, itemID, quantity) values (48, 9, 6);
insert into orderItem (orderID, itemID, quantity) values (13, 10, 6);
insert into orderItem (orderID, itemID, quantity) values (112, 11, 4);
insert into orderItem (orderID, itemID, quantity) values (108, 1, 7);
insert into orderItem (orderID, itemID, quantity) values (12, 2, 1);
insert into orderItem (orderID, itemID, quantity) values (16, 3, 4);
insert into orderItem (orderID, itemID, quantity) values (53, 4, 1);
insert into orderItem (orderID, itemID, quantity) values (115, 5, 1);
insert into orderItem (orderID, itemID, quantity) values (2, 6, 1);
insert into orderItem (orderID, itemID, quantity) values (32, 7, 4);
insert into orderItem (orderID, itemID, quantity) values (69, 8, 3);
insert into orderItem (orderID, itemID, quantity) values (48, 9, 6);
insert into orderItem (orderID, itemID, quantity) values (84, 10, 2);
insert into orderItem (orderID, itemID, quantity) values (25, 11, 2);
insert into orderItem (orderID, itemID, quantity) values (60, 1, 1);
insert into orderItem (orderID, itemID, quantity) values (14, 2, 4);
insert into orderItem (orderID, itemID, quantity) values (25, 3, 2);
insert into orderItem (orderID, itemID, quantity) values (35, 4, 3);
insert into orderItem (orderID, itemID, quantity) values (82, 5, 6);
insert into orderItem (orderID, itemID, quantity) values (38, 6, 6);
insert into orderItem (orderID, itemID, quantity) values (51, 7, 5);
insert into orderItem (orderID, itemID, quantity) values (95, 8, 2);
insert into orderItem (orderID, itemID, quantity) values (99, 9, 6);
insert into orderItem (orderID, itemID, quantity) values (73, 10, 2);
insert into orderItem (orderID, itemID, quantity) values (35, 11, 2);
insert into orderItem (orderID, itemID, quantity) values (60, 1, 2);
insert into orderItem (orderID, itemID, quantity) values (94, 2, 4);
insert into orderItem (orderID, itemID, quantity) values (112, 3, 6);
insert into orderItem (orderID, itemID, quantity) values (62, 4, 6);
insert into orderItem (orderID, itemID, quantity) values (76, 5, 2);
insert into orderItem (orderID, itemID, quantity) values (90, 6, 1);
insert into orderItem (orderID, itemID, quantity) values (64, 7, 7);
insert into orderItem (orderID, itemID, quantity) values (93, 8, 5);
insert into orderItem (orderID, itemID, quantity) values (65, 9, 7);
insert into orderItem (orderID, itemID, quantity) values (12, 10, 4);
insert into orderItem (orderID, itemID, quantity) values (115, 11, 6);
insert into orderItem (orderID, itemID, quantity) values (78, 1, 6);
insert into orderItem (orderID, itemID, quantity) values (68, 2, 7);
insert into orderItem (orderID, itemID, quantity) values (90, 3, 2);
insert into orderItem (orderID, itemID, quantity) values (106, 4, 1);
insert into orderItem (orderID, itemID, quantity) values (37, 5, 2);
insert into orderItem (orderID, itemID, quantity) values (101, 6, 5);
insert into orderItem (orderID, itemID, quantity) values (77, 7, 4);
insert into orderItem (orderID, itemID, quantity) values (6, 8, 4);
insert into orderItem (orderID, itemID, quantity) values (47, 9, 3);
insert into orderItem (orderID, itemID, quantity) values (96, 10, 4);
insert into orderItem (orderID, itemID, quantity) values (7, 11, 5);
insert into orderItem (orderID, itemID, quantity) values (76, 1, 3);
insert into orderItem (orderID, itemID, quantity) values (2, 2, 1);
insert into orderItem (orderID, itemID, quantity) values (73, 3, 5);
insert into orderItem (orderID, itemID, quantity) values (17, 4, 4);
insert into orderItem (orderID, itemID, quantity) values (20, 5, 6);
insert into orderItem (orderID, itemID, quantity) values (58, 6, 4);
insert into orderItem (orderID, itemID, quantity) values (81, 7, 4);
insert into orderItem (orderID, itemID, quantity) values (110, 8, 7);
insert into orderItem (orderID, itemID, quantity) values (78, 9, 5);
insert into orderItem (orderID, itemID, quantity) values (9, 10, 6);
insert into orderItem (orderID, itemID, quantity) values (55, 11, 7);
insert into orderItem (orderID, itemID, quantity) values (2, 1, 4);
insert into orderItem (orderID, itemID, quantity) values (108, 2, 6);
insert into orderItem (orderID, itemID, quantity) values (92, 3, 4);
insert into orderItem (orderID, itemID, quantity) values (109, 4, 2);
insert into orderItem (orderID, itemID, quantity) values (59, 5, 7);
insert into orderItem (orderID, itemID, quantity) values (3, 6, 7);
insert into orderItem (orderID, itemID, quantity) values (104, 7, 2);
insert into orderItem (orderID, itemID, quantity) values (52, 8, 4);
insert into orderItem (orderID, itemID, quantity) values (20, 9, 7);
insert into orderItem (orderID, itemID, quantity) values (19, 10, 1);
insert into orderItem (orderID, itemID, quantity) values (57, 11, 2);
insert into orderItem (orderID, itemID, quantity) values (65, 1, 3);
insert into orderItem (orderID, itemID, quantity) values (101, 2, 7);
insert into orderItem (orderID, itemID, quantity) values (89, 3, 7);
insert into orderItem (orderID, itemID, quantity) values (87, 4, 7);
insert into orderItem (orderID, itemID, quantity) values (31, 5, 5);
insert into orderItem (orderID, itemID, quantity) values (76, 6, 6);
insert into orderItem (orderID, itemID, quantity) values (107, 7, 4);
insert into orderItem (orderID, itemID, quantity) values (14, 8, 7);
insert into orderItem (orderID, itemID, quantity) values (96, 9, 5);
insert into orderItem (orderID, itemID, quantity) values (96, 10, 4);
insert into orderItem (orderID, itemID, quantity) values (70, 11, 3);
insert into orderItem (orderID, itemID, quantity) values (70, 1, 1);
insert into orderItem (orderID, itemID, quantity) values (100, 2, 1);
insert into orderItem (orderID, itemID, quantity) values (52, 3, 6);
insert into orderItem (orderID, itemID, quantity) values (55, 4, 1);
insert into orderItem (orderID, itemID, quantity) values (21, 5, 1);
insert into orderItem (orderID, itemID, quantity) values (42, 6, 6);
insert into orderItem (orderID, itemID, quantity) values (95, 7, 3);
insert into orderItem (orderID, itemID, quantity) values (110, 8, 3);
insert into orderItem (orderID, itemID, quantity) values (1, 9, 7);
insert into orderItem (orderID, itemID, quantity) values (40, 10, 7);
insert into orderItem (orderID, itemID, quantity) values (21, 11, 2);
insert into orderItem (orderID, itemID, quantity) values (3, 1, 6);
insert into orderItem (orderID, itemID, quantity) values (35, 2, 7);
insert into orderItem (orderID, itemID, quantity) values (4, 3, 7);
insert into orderItem (orderID, itemID, quantity) values (56, 4, 2);
insert into orderItem (orderID, itemID, quantity) values (97, 5, 1);
insert into orderItem (orderID, itemID, quantity) values (106, 6, 7);
insert into orderItem (orderID, itemID, quantity) values (13, 7, 4);
insert into orderItem (orderID, itemID, quantity) values (51, 8, 3);
insert into orderItem (orderID, itemID, quantity) values (9, 9, 1);
insert into orderItem (orderID, itemID, quantity) values (77, 10, 7);
insert into orderItem (orderID, itemID, quantity) values (34, 11, 5);
insert into orderItem (orderID, itemID, quantity) values (99, 1, 5);
insert into orderItem (orderID, itemID, quantity) values (24, 2, 2);
insert into orderItem (orderID, itemID, quantity) values (56, 3, 7);
insert into orderItem (orderID, itemID, quantity) values (77, 4, 5);
insert into orderItem (orderID, itemID, quantity) values (33, 5, 4);
insert into orderItem (orderID, itemID, quantity) values (31, 6, 5);
insert into orderItem (orderID, itemID, quantity) values (115, 7, 6);
insert into orderItem (orderID, itemID, quantity) values (105, 8, 2);
insert into orderItem (orderID, itemID, quantity) values (48, 9, 1);
insert into orderItem (orderID, itemID, quantity) values (29, 10, 5);
insert into orderItem (orderID, itemID, quantity) values (25, 11, 1);
insert into orderItem (orderID, itemID, quantity) values (31, 1, 1);
insert into orderItem (orderID, itemID, quantity) values (30, 2, 4);
insert into orderItem (orderID, itemID, quantity) values (36, 3, 4);
insert into orderItem (orderID, itemID, quantity) values (99, 4, 3);
insert into orderItem (orderID, itemID, quantity) values (67, 5, 5);
insert into orderItem (orderID, itemID, quantity) values (19, 6, 7);
insert into orderItem (orderID, itemID, quantity) values (51, 7, 6);
insert into orderItem (orderID, itemID, quantity) values (78, 8, 1);
insert into orderItem (orderID, itemID, quantity) values (38, 9, 3);
insert into orderItem (orderID, itemID, quantity) values (8, 10, 7);
insert into orderItem (orderID, itemID, quantity) values (46, 11, 1);
insert into orderItem (orderID, itemID, quantity) values (78, 1, 2);
insert into orderItem (orderID, itemID, quantity) values (24, 2, 1);
insert into orderItem (orderID, itemID, quantity) values (80, 3, 1);
insert into orderItem (orderID, itemID, quantity) values (91, 4, 3);
insert into orderItem (orderID, itemID, quantity) values (61, 5, 5);
insert into orderItem (orderID, itemID, quantity) values (10, 6, 4);
insert into orderItem (orderID, itemID, quantity) values (66, 7, 5);
insert into orderItem (orderID, itemID, quantity) values (41, 8, 4);
insert into orderItem (orderID, itemID, quantity) values (67, 9, 1);
insert into orderItem (orderID, itemID, quantity) values (41, 10, 4);
insert into orderItem (orderID, itemID, quantity) values (50, 11, 6);
insert into orderItem (orderID, itemID, quantity) values (18, 1, 6);
insert into orderItem (orderID, itemID, quantity) values (77, 2, 2);
insert into orderItem (orderID, itemID, quantity) values (77, 3, 1);
insert into orderItem (orderID, itemID, quantity) values (37, 4, 4);
insert into orderItem (orderID, itemID, quantity) values (47, 5, 4);
insert into orderItem (orderID, itemID, quantity) values (93, 6, 7);
insert into orderItem (orderID, itemID, quantity) values (7, 7, 3);
insert into orderItem (orderID, itemID, quantity) values (111, 8, 3);
insert into orderItem (orderID, itemID, quantity) values (18, 9, 3);
insert into orderItem (orderID, itemID, quantity) values (20, 10, 1);
insert into orderItem (orderID, itemID, quantity) values (67, 11, 4);
insert into orderItem (orderID, itemID, quantity) values (92, 1, 2);
insert into orderItem (orderID, itemID, quantity) values (78, 2, 4);
insert into orderItem (orderID, itemID, quantity) values (113, 3, 1);
insert into orderItem (orderID, itemID, quantity) values (102, 4, 4);
insert into orderItem (orderID, itemID, quantity) values (69, 5, 7);
insert into orderItem (orderID, itemID, quantity) values (65, 6, 6);
insert into orderItem (orderID, itemID, quantity) values (18, 7, 4);
insert into orderItem (orderID, itemID, quantity) values (11, 8, 7);
insert into orderItem (orderID, itemID, quantity) values (29, 9, 7);
insert into orderItem (orderID, itemID, quantity) values (108, 10, 5);

-- UPDATE ORDERLIST TOTAL PRICE

UPDATE orderList
SET totalPrice = magic_table.total
FROM orderList
JOIN (
	SELECT o.orderID, sum(o.quantity * i.price) as total
	FROM orderItem o
	JOIN item i
		ON o.itemID = i.itemID
		GROUP BY orderID
) magic_table
ON orderList.orderID = magic_table.orderID;

select date, sum(totalPrice) from orderList group by date;

-- CREATE FUNCTION
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION GetLastDayWeek
(
	-- Add the parameters for the function here
	@InputDate DateTime
)
RETURNS DateTime
AS
BEGIN
	-- Declare the return variable here
	DECLARE @Result DateTime

	-- Add the T-SQL statements to compute the return value here
	-- 1 -> Sunday, 7 -> Saturday
	SELECT @Result = DATEADD(DAY, 7- DATEPART(DW, @InputDate), @InputDate)

	-- Return the result of the function
	RETURN @Result

END
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION GetFirstDayWeek
(
	-- Add the parameters for the function here
	@InputDate DateTime
)
RETURNS DateTime
AS
BEGIN
	-- Declare the return variable here
	DECLARE @Result DateTime

	-- Add the T-SQL statements to compute the return value here
	-- 1 -> Sunday, 7 -> Saturday
	SELECT @Result = DATEADD(DAY, 1- DATEPART(DW, @InputDate), @InputDate)

	-- Return the result of the function
	RETURN @Result

END
GO

