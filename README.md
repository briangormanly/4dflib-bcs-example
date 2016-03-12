<h1>Black Car Service / BCS</h1>
<h3>4DFLib example application!!!</h3>
This is an example application showing how you can use 4dflib (https://github.com/briangormanly/4dflib) to manage all your applications data over time and it is an ORM tool to boot, abstracting away your database so you can consentrate on the really important things, like coding the next app that will take over the world!

<h3>Instructions</h3>
<strong>prerequisites</strong>
  * Assumes MySQL is running, and no root password is set (default).  If this is true, you may run, else see 'Database Settings' Below

<strong>Clone and Run!</strong>
clone this repository: git clone https://github.com/briangormanly/4dflib-bcs-example.git
run: gradle run (in project folder)

Congratualtions!

<h3>Database Configuration</h3>
To configure database settings, setting the root password, changing to using PostgreSQL, or other database connection related details see this file: 4dflib-bcs-example/src/main/java/com/fdflib/example/BlackCarService.java
Change the following as appropriate to your needs:
Root username and password:
````Java
fdfSettings.DB_ROOT_USER = "root";
fdfSettings.DB_ROOT_PASSWORD = "";
````
PostgreSQL: (uncomment) and remove / comment out the MySQL ones
````Java
    // PostgreSQL settings
    fdfSettings.PERSISTENCE = DatabaseUtil.DatabaseType.POSTGRES;
    fdfSettings.DB_PROTOCOL = DatabaseUtil.DatabaseProtocol.JDBC_POSTGRES;
    
    // MySQL settings
    //fdfSettings.PERSISTENCE = DatabaseUtil.DatabaseType.MYSQL;
    //fdfSettings.DB_PROTOCOL = DatabaseUtil.DatabaseProtocol.JDBC_MYSQL;
    
    // Note that you will likely have to set the root password for postgreSQL as well
    fdfSettings.DB_ROOT_PASSWORD = "mycoolpasssword";
````

<h2>Now that it is working, here is what you need to know about how it works</h2>

