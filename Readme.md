######  Tech Test for Senior and Principle Developers - Description

Create an example of a Springboot app which registers and authenticates users with an Authentication Token.

You should use standard libraries or frameworks to achieve this and include and example of how to use the token to authenticate a user.

Bonus points for including some kind of Role Management.
Bonus points also for correct handling of passwords.


######  Tech Test for Senior and Principle Developers - Solution

I've used Spring Boot/Security/Web to create a simple API which allows a user to register a new user, generate an authentication token for that user, and then use that token to fetch all account details or a single user's details. 

To demonstrate roles, I've enforced the rule that a STUDENT can only fetch their own details and nobody else's.

TODO: add more Integration Test and Unit Tests

I'm using the H2 in memory database as a datastore and it has the admin user already loaded.


**USING THE API WITH CURL COMMANDS:**  "mvn spring-boot:run" to run from command line

To get the admin's bearer token, call the _authenticate_ endpoint:

`curl -X POST -H "Content-Type: application/json" -d "@json/admin.json" http://localhost:8080/authenticate`

`{"token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU3ODg3MjE1NywiaWF0IjoxNTc4NzY0MTU3fQ.099BslnXJP_oe7nJm8X-OMIvBCBclGnQlMOTmopgyHyDFiEb1sLdTS5QD7JpWQf3HkkC4s01brrI0jqtUiItUQ"}`

To register a teacher, call the _register_ endpoint.

`curl -X POST -H "Content-Type: application/json" -d "@json/teacher.json" http://localhost:8080/register`

`registered user: teacher`


To get the teacher's bearer token, call the _authenticate_ endpoint. Then view all accounts using that token on the _accounts_ endpoint.

`curl -X GET --header 'authorization: Bearer <BEARER_TOKEN>'  http://localhost:8080/accounts`

`[{"username":"admin","password":"$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6","details":null,"type":"ADMIN"},{"username":"teacher","password":"$2a$10$RwjTmFj26fiOriLXM1gHf.enHAgD8NdfzdQ9vsAqSzRBROQCA24bm","details":"science teacher","type":"TEACHER"}]
`

To do the same for a student, call the _register_ and  _authenticate_ endpoints:
`curl -X POST -H "Content-Type: application/json" -d "@json/student.json" http://localhost:8080/register`
  
`registered user: student`

`curl -X POST -H "Content-Type: application/json" -d "@json/student.json" http://localhost:8080/authenticate`

Then view the student's details:
`curl -X GET --header 'authorization: Bearer <BEARER_TOKEN>'  http://localhost:8080/accounts/student
{"username":"student","password":"$2a$10$2FYz8HLeEocLC2L3LJxSMOmPT7/506m3uRhaTL.uruaeiPSTwBimm","details":"science student","type":"STUDENT"}
`

And to prove that this student cannot view any other's details:
`$ curl -X GET --header 'authorization: Bearer <BEARER_TOKEN>'  http://localhost:8080/accounts/notstudent
{"timestamp":"2020-01-11T17:38:25.207+0000","status":401,"error":"Unauthorized","message":"Unauthorized","path":"/accounts/notstudent"}`
