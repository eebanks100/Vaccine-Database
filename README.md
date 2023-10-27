# Vaccine_Database:
A small Java / MySQL project which involved setting up a database on the local host computer. The user can navigate the different tables within the database and insert, update, and delete information in the database.



## Set-Up:
1) MySQL Installer: `t https://dev.mysql.com/downloads/installer/`, leave the option `Developer Default` in the Select Products and Features window on.
2) Open the MySQL Command Line Client
3) Create a new user with the following command: `CREATE USER 'testuser'@'localhost' IDENTIFIED BY 'password'`;
4) Grant that new user all the privileges on the database: `GRANT ALL PRIVILEGES ON vaccine_database . * TO 'testuser'@'localhost';`



### Running the Program:
1) The code for the program was created within the Eclipse IDE and was ran from there.