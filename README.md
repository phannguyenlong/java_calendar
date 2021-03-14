# Table of Content
- [WHAT IS CONVINIENT CALENDAR MANAGEMENT for BUSINESS?](#what-is-convinient-calendar-management-for-business-)
- [INSTALLATION GUIDE](#installation-guide)
  * [SIMPLE INSTALLTION](#simple-installtion)
    + [HOW TO DEPLOY THE SERVER](#how-to-deploy-the-server)
    + [HOW TO RUN APP](#how-to-run-app)
  * [MANUAL INSTALLATION FROM COMMAND LINE](#manual-installation-from-command-line)
    + [HOW TO DEPLOY THE SERVER](#how-to-deploy-the-server-1)
    + [HOW TO RUN APP](#how-to-run-app-1)
- [API Documentations](#api-documentations)
    + [I. Login](#i-login)
    + [II. Manager Calendar Management](#ii-manager-calendar-management)
    + [III. Manager Employee Management](#iii-manager-employee-management)
    + [IV. Cashier Order Management](#iv-cashier-order-management)
    + [V. Cashier Order History](#v-cashier-order-history)
    + [VI. Finace Report](#vi-finace-report)

# WHAT IS CONVINIENT CALENDAR MANAGEMENT for BUSINESS?
- 

# INSTALLATION GUIDE
- For full documentation about installation please access to: https://drive.google.com/drive/folders/1KtcV-3zLrDAOjrmNctejq_deI5UBsnaO?usp=sharing
- Please clone/download the project and follow the following step
- There are 2 way for install our project:
    - Simple Installation: simple, fast
    - Maunal Installation from Command Line

## SIMPLE INSTALLTION 

### HOW TO DEPLOY THE SERVER
1. Config `config.properties`
2. Run `setup.bat`
3. Start Tomcat server from D://stuff/tomcat.../bin/startup 
OR 
4. run file ".war" on Tomcat server

### HOW TO RUN APP
1. run `run.bat`

## MANUAL INSTALLATION FROM COMMAND LINE

### HOW TO DEPLOY THE SERVER
1. Config `config.properties`
2. go to ./SQL_server/webserver
3. run "mvn clean package cargo:redeploy"
4. access to ./SQL_server/webseverver/target
5. Start Tomcat server from D://stuff/tomcat.../bin/startup 
OR 
6. run file ".war" on Tomcat server

### HOW TO RUN APP
1. go to ./SQL_Calendar
2. run "mvn package"
3. run "java -jar target\SQL_Calendar-1.0-SNAPSHOT.jar"

# API Documentations

### I. Login

`GET` **/auth?username=' '&password=' '**
- **input:** user name and password
- **output:** Employee
-  *Employee(essn, name, sex, bdate, address, type, phone)*

### II. Manager Calendar Management

**1. Month view**

`GET` **/manager/calendar/month?date=' '**
- **input:** date in that month
- **output:** MonthView(date, dayIncome, monthIncome)
- *monthIncome is cumsum format, get last value to get total sum*

**2. Week view**

**a. Get list of event Instance**

`GET` **/manager/calendar/week?date=' '**
- **input:** any day in week that need to view
- **output:** List of EventInstance 
-  *EventInstance (essn, eventID, eventName, startDate, endDate, date, StartTime, endTime, name, phone, sex, type, eventType, status)*

 **b. find employee with name**

`GET` **/manager/employee?name=' '**
- **input:** input name of employee
- **output:** List of Employee
-  *Employee(essn, name, sex, bdate, address, type, phone)*

**c. add employee to event**

`POST` **/manager/calendar/event?ssn=' '&eventID=''&date=' '**
- **input:** ssn, eventID of that event and date
- *This will make new EventInstance in database*

**d. delete employee from event**

`DELETE` **/manager/calendar/event?ssn=' '&eventID=' '&date=' '**
- **input:** ssn, eventID of that event and date
- *This will delete 1 EventInstance in database*

**3. Day view**

**a. Get list of event Instance**

`GET` **/manager/calendar/day?date=' '**
- **input:** day that need to view
- **output:** List of EventInstance 
-  *EventInstance (essn, eventID, eventName, startDate, endDate, date, StartTime, endTime, name, phone, sex, type, eventType, status)*

**b. Count hourly income**

`GET` **/manager/calendar/day/hour?date=' '**
- **input:** day that need to view
- **output:** List of HourlyIncome Object
-  *HourlyIncome (onHour, HourlyIncome)*

**c. Count Shift income**

`GET` **/manager/calendar/day/shift?date=' '&startTime=' '&endTime =' '**
- **input:** day that need to view, shift start time and end time
- **output:** return ShiftIncome (shiftIncome)

**d. Timekeeping for employee**

`PUT` **/manager/calendar/day/shift?essn=' '&eventID=' '&date=' '&status=' '**
- **input:** essn, eventID, date and status (present, absent)

**e. Delete an event**

`DELETE` **/manager/calendar/event/action?eventID=' '**
- **input:** eventID
- *This will delete eventID from event table*

**4. Add new Event**

`POST` **/manager/calendar/event/action?eventName=' '&startDate=' '&endDate=' '&startTime=' '&endTime=' '&eventType=' '**
- **input:** eventName, startDate, endDate, startTime, endTime, eventType (daily, weekly, no repeate)
- *This will create new Event in event Table*

### III. Manager Employee Management

 **1. Find all employee**

`GET` **/manager/employee?name=all**
- **input:** input name=all
- **output:** List of Employee
-  *Employee(essn, name,  bdate, address, sex, type, phone)*

 **2. find employee with name**

`GET` **/manager/employee?name=' '**
- **input:** input name of employee
- **output:** List of Employee
-  *Employee(essn, name,  bdate, address, sex, type, phone)*

 **3. Update employee**

`PUT` **/manager/employee?ssn=' '&address=' '&phone=' '&type=' '**
- **input:** input ssn, address, phone, type

 **4. Add new employee**

`POST` **/manager/employee?ssn=' '&fname=' '&lname=' '&address=' '&bdate=' '&sex=' '&phone=' '&type=' '&username=' '&password=' '**
- **input:** input ssn, fname, lname, address, bdate, phone, type, username, password.

 **5. Delete employee**

`DELETE` **/manager/employee?ssn=' ' **
- **input:** input ssn that need to delete

### IV. Cashier Order Management

 **1. Get list item**

 `GET` **/cashier/item/all**
 - **input:** none
- **output:** List of Item
-  *Item(ItemID, itemName,  price)*

 **2. Make new Order**

 `POST` **/cashier/order/new?date=' ',&time=' '&essn=' '&listOrderItem=' '**
 - **input:** date, time, essn and list of OrderItem

### V. Cashier Order History

 **1. Get all order**

`GET` **/cashier/order/all**
- **input:** none
- **output:** List of Order
-  *Order(orderID, date,  time, name, total)*

 **2. Get specific order**

`GET` **/cashier/order?orderID=' '**
- **input:** input orderID
- **output:** List of OrderItem
-  *OrderItem(ItemID, quantity,  price)*

### VI. Finace Report

 **1. Day View**
 
 **a. Get List of hourly income**

`GET` **/finance/day?date=' '**
- **input:** date need to view
- **output:** List of FinanceIncome
-  *FinanceIncome(number, total)*

`GET` **/finance/week?date=' '**
- **input:** date need to view
- **output:** List of FinanceIncome
-  *FinanceIncome(number, total)*

`GET` **/finance/month?date=' '**
- **input:** date need to view
- **output:** List of FinanceIncome
-  *FinanceIncome(number, total)*