hi :)
in this application, I made an employee model using .avsc file 
as well as implemented spring security with register and login functionality 
with user class and authorization restriction
to certain endpoints by using ADMIN/USER enum.
I have made use of docker-compose.yml to start the zookeeper and Kafka service 
as well as schema-registry and control center.

the project is composed of two modules one is the producer sending the employee 
and the other is the consumer who consumes the message containing the JSON.

I saved the user records in posgres database.

in order to use the application 
run "docker-compose up -d" 
use "./mvnw clean install" if you wish to create the employee class using the <Employee>.avsc file

