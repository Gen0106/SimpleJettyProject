# SimpleJettyProject

This is a simple website project using jetty server.

There are 2 ways to install the website.
1. Execution through IDEA
  - If you have a Java development environment, you can execute the project using IntelliJ or Eclipse.
  - In this case, you must execute the initial query to create a database and to create a table on your MySQL database firstly. You can see the init.sql file in the root.
  - Uncomment <Set name="Url">jdbc:mysql://localhost:3306/task?allowPublicKeyRetrieval=true</Set> in the src/main/webapp/WEN-INF/jetty-env.xml file and modify your MySQL port. Comment <Set name="Url">jdbc:mysql://db:3306/task?allowPublicKeyRetrieval=true</Set> in the jetty-env.xml file
  - Excute the project on the IDEA(jetty-run command)

2. Execution through Docker
  - Install Docker and Docker Compose. You can refer https://docs.docker.com/engine/install/
  - Execute "docker-compose up" on the root directory of this project.
  
You can see the website on http://localhost:8090/.
Also, you can check the data on the task/data table of MySQL
  - you can use http://localhost/phpMyAdmin/ in the case of IDEA execution.
  - or you can use http://localhost:8091 in the case of Docker execution.
