USE SQLCalendar

-- ===============Login===============
-- input username + password
SELECT ssn, (fname + ' '  + lname) as name, sex, bdate, address, type, phone
FROM employee
	WHERE username = 'mpudana' AND password = '8Z68tj';


-- ===============Manager Calendar Management===============

-- Month view (input month)
SELECT * , SUM(temp.dayIncome) OVER (ORDER BY date) as monthIncome
FROM (
SELECT 
	date, 
	SUM(totalPrice) as dayIncome
FROM orderList
	WHERE 
		Month(date) = Month('12/23/2020') AND
		Year(date) = Year('12/23/2020')
	GROUP BY date
) AS temp;

-- Week view
-- get list of event of that week (input date atm)
SET DATEFIRST 7;
SELECT 
	essn, ev.eventID, ev.eventName, startDate, endDate, date, 
	FORMAT(startTime, 'hh\:mm') as startTime, 
	FORMAT(endTime, 'hh\:mm') as endTime, 
	(fname + ' ' + lname) AS name, phone, sex, type, eventType, status
FROM eventInstance evI
JOIN employee e ON (evI.essn = e.ssn)
RIGHT JOIN event ev ON (evI.eventID = ev.eventID)
	WHERE DATEPART(week, '1/10/2021') = DATEPART(week, date)
	OR (ev.eventType = 'no repeat' AND DATEPART(week, '1/10/2021') = DATEPART(week, ev.startDate))
	OR (ev.eventType = 'daily' AND DATEPART(week, '1/10/2021') <= DATEPART(week, ev.endDate) AND DATEPART(week, ev.startDate) <= DATEPART(week, '1/10/2021') AND DATEPART(week, '1/10/2021') = DATEPART(week, date))
	OR (ev.eventType = 'daily' AND DATEPART(week, '1/10/2021') <= DATEPART(week, ev.endDate) AND DATEPART(week, ev.startDate) <= DATEPART(week, '1/10/2021') AND date IS NULL)
	OR (ev.eventType = 'daily' AND DATEPART(week, '1/10/2021') <= DATEPART(week, ev.endDate) AND DATEPART(week, ev.startDate) <= DATEPART(week, '1/10/2021') AND NOT EXISTS ( SELECT * FROM eventInstance WHERE eventID = ev.eventID AND DATEPART(week, date) = DATEPART(week, '1/10/2021')))
	OR (ev.eventType = 'weekly' AND dbo.GetLastDayWeek('1/10/2021') <= ev.endDate AND ev.startDate <= dbo.GetFirstDayWeek('1/10/2021'));
	-- OR (ev.eventType = 'monthly' AND '12/27/2020' <= ev.endDate);

-- add new employee to event
-- input name
SELECT ssn, (fname + ' ' + lname) as name, bdate, address, sex, type, phone
FROM employee
WHERE fname + ' '  + lname LIKE '%in%';
-- add new employee into eventInstance (input ssn, evendID, date, status = null)
insert into eventInstance (essn, eventID, date, status) values ('765-59-1185', 1, '12/28/2020', NULL);

-- Delete employee from event (input ssn, event, date)
DELETE FROM eventInstance WHERE essn = '765-59-1185' AND eventID = 1 AND date = '12/28/2020';

-- DAY view
-- Count hourly incomes (input current day)
SELECT DATEPART(hour,time) AS OnHour,
       ISNULL(SUM(totalPrice),0) as HourlyIncome
FROM orderList
GROUP BY CAST(date as date),
       DATEPART(hour,time)
HAVING date = '12/23/2020';
-- Shift incomes (input date, start time, end time)
SELECT SUM(totalPrice) as shiftIncome
FROM orderList
WHERE time <= '23:00' AND time >= '7:00' AND date = '12/23/2020'
-- Get list of event of that date (input current date)
SET DATEFIRST 7;
SELECT 
	essn, ev.eventID, ev.eventName, startDate, date, 
	FORMAT(startTime, 'hh\:mm') as startTime, 
	FORMAT(endTime, 'hh\:mm') as endTime, 
	(fname + ' ' + lname) AS name, phone, sex, type, eventType, status
FROM eventInstance evI
JOIN employee e ON (evI.essn = e.ssn)
RIGHT JOIN event ev ON (evI.eventID = ev.eventID)
	WHERE '12/27/2020' = date
	OR (ev.eventType = 'no repeat' AND '12/27/2020' = ev.startDate)
	OR (ev.eventType = 'daily' AND '12/27/2020' <= ev.endDate AND ev.startDate <= '12/27/2020')
	OR (ev.eventType = 'weekly' 
		AND DATEPART(dw, '12/27/2020') = DATEPART(dw, date) 
		AND dbo.GetLastDayWeek('12/27/2020') <= ev.endDate
		AND ev.startDate <= dbo.GetFirstDayWeek('12/27/2020'))
	OR (ev.eventType = 'weekly' AND date IS NULL
		AND DATEPART(dw, '12/27/2020') = DATEPART(dw, startDate) 
		AND dbo.GetLastDayWeek('12/27/2020') <= ev.endDate
		AND ev.startDate <= dbo.GetFirstDayWeek('12/27/2020'));
-- Timekeeping for employee (input essn, eventID, date, status)
UPDATE eventInstance
SET status = 'present'
WHERE essn = '' AND eventID = '' AND date = '';
-- Delete Event (input eventID)
DELETE FROM event
WHERE eventID = 1;
-- Add new Event (input Event Object To String)
INSERT INTO event (eventName, startDate, endDate, startTime, endTime, eventType)
VALUES ('','','','','','');

-- ===============Manager Employee Management===============
-- Get all emplopyee
SELECT ssn, (fname + ' '  + lname) as name, bdate, address, sex, phone, type
FROM employee;
-- Get employee with query
SELECT ssn, (fname + ' '  + lname) as name, bdate, address, sex, phone, type
FROM employee
WHERE fname + ' ' + lname LIKE '%in%';
-- Update employee (input ssn, fname, lname, address, bdate, phone, type
UPDATE employee
SET address = '', phone = '', type = ''
WHERE ssn = '';
-- Add employee (input ssn, fname, lname, bdate, address, sex, phone, type, username, password) 
INSERT INTO employee (ssn, fname, lname, bdate, address, sex, phone, type, username, password)
VALUES ('','','','','','','','','','');
-- Delete Employee (input ssn)
DELETE FROM employee
WHERE ssn = '';

-- ===============Cashier Management===============
-- Get list of item
SELECT * FROM item;
-- Create new Order (inser date, time, essn, list of orderItem(itemID, quantity))
-- new orderList
INSERT INTO orderList (date, time, essn)
VALUES ('12/3/2020', '7:00', '369-60-6431');
-- insert order ITEM (make a for loop to run through list of orderItem)
declare @temp TINYINT;
SET @temp = (SELECT max(orderID) from orderList);
INSERT INTO orderItem(orderID, itemID, quantity)
VALUES (@temp, '3', '3');
INSERT INTO orderItem(orderID, itemID, quantity)
VALUES (@temp, '2', '3');
-- update total price from orderList (input orderID)
UPDATE orderList
SET totalPrice = magic_table.total
FROM orderList
JOIN (
	SELECT o.orderID, sum(o.quantity * i.price) as total
	FROM orderItem o
	JOIN item i
		ON o.itemID = i.itemID
		GROUP BY orderID
	HAVING orderID = @temp
) magic_table
ON orderList.orderID = @temp;

-- ===============Cashier Order History===============
-- Get all order
SELECT orderID, date, time, (lname + ' ' + fname) as name, ISNULL(totalPrice, 0) as total
FROM orderList o
	JOIN employee e on (o.essn = e.ssn)
ORDER BY date DESC, time DESC;
-- Get specific order (input orderID)
SELECT o.itemID, o.quantity, i.price FROM orderItem o
	JOIN item i on (o.itemID = i.itemID)
WHERE o.orderID = 116;

-- ===============Finance Report===============
-- Day view (input current date)
-- group by hours
SELECT DATEPART(hour,time) AS number,
       ISNULL(SUM(totalPrice),0) as total
FROM orderList
GROUP BY CAST(date as date),
       DATEPART(hour,time)
HAVING date = '12/23/2020';
-- Get sum, min, max
SELECT SUM(HourlyIncome) as sum, MAX(HourlyIncome) as max, AVG(HourlyIncome) as mean
FROM 
( 
	SELECT DATEPART(hour,time) AS OnHour,
		ISNULL(SUM(totalPrice),0) as HourlyIncome
	FROM orderList
	GROUP BY CAST(date as date),
		DATEPART(hour,time)
	HAVING date = '12/23/2020'
) as matric_table;
-- Week View (input current date)
SET DATEFIRST 7;
SELECT DATEPART(week, date) as number, SUM(totalPrice) as total
FROM orderList
GROUP BY date
HAVING DATEPART(week, date) = DATEPART(week, '12/27/2020');
-- Month View (input current date) - translate to first day and last day of week by java
SELECT (DATEPART(week, date) - DATEPART(WEEK, DATEADD(MM, DATEDIFF(MM,0,'12/27/2020'), 0))+ 1) as number, SUM(TotalPrice) as total
FROM orderList
WHERE date <= EOMONTH('12/27/2020') AND date >= (CAST('12/27/2020' as datetime)-DAY('12/27/2020')+1)
GROUP BY DATEPART(week, date);
