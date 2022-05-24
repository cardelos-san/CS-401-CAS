# CS-401-CAS: Lost & Found Project

This is a lost and found system developed for CS-401 at RIC. It is designed to help unify the lost & found functions within the college.

## Setup
1. This project may be imported into your choice of IDE as a Maven project using Java 8.
2. The database may be set up using the SQL scripts under /util/sql.
3. Copy /lostandfound/config.properties.example to /lostandfound/config.properties and replace the dbuser & dbpasword fields with the credentials necessary for the application to connect to the database. To use a configuration file stored elsewhere on the filesystem launch the application using the PROP_FILE property. Ex:

    -DPROP_FILE=PATH_TO_PROP_FILE
4. Once the application is launched it can be reached by navigating your browser to http://localhost:4567/

![alt text](https://github.com/cardelos-san/CS-401-CAS/blob/master/example.png?raw=true)

<img src = "/example.PNG">
