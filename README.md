# ContactService

##This is a demo project for RESTful web service.

###This project develops a "Contact List" service, which provides the following features:
- Store a set of contacts with name, phone number and address
- Search a contact by exact name or substring in the name
- Return the list of contacts
- Add a new contact
- Remove a contact
- Modify a contact

###Technologies
-It is written using SpringBoot framework. 
-The project is maintained by GIT.
-Dependencies are managed by Maven.
-Tests are written in JUnit.

###Package explanation:
-com.tang.contactservice: Main entry for the project. Please run it as an application.
-com.tang.contactservice.model: POJO for a Contact object
-com.tang.contactservice.service: Concrete implementations for Contact Service (CRUD operations)
-com.tang.contactservice.controller: API for web service
-com.tang.ContactService: Junit test package
