set LB_HOME=c:\java\liquibase
call %LB_HOME%\liquibase.bat --driver=org.postgresql.Driver ^
--classpath=%LB_HOME%\lib ^
--changeLogFile=changelog.xml ^
--url="jdbc:postgresql://localhost:5434/masterjava" ^
--username=postgres ^
--password=postgres ^
migrate