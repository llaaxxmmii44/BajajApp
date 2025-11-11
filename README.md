Bajaj Finserv Health - Spring Boot Starter Project
------------------------------------------------
Location: /src/main/java/com/bajaj/health/

How to build and run:
1) Ensure Java 17+ and Maven are installed.
2) From the project root (/mnt/data/bajajapp):
   mvn clean package
   mvn spring-boot:run
   OR
   mvn clean package
   java -jar target\bajajapp-1.0.0.jar

Notes:
- Update regNo in WebhookService.requestBody if you need odd/even behavior.
- Replace finalQuery computation logic with the SQL solution required by the challenge.
