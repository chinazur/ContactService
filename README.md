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
- It is written using SpringBoot framework. 
- The project is maintained by GIT.
- Dependencies are managed by Maven.
- Tests are written in JUnit.

### Development process
- At first, i tried to use Eclipse's template "Dynamic Web Project" in order to create web service, with Axis2. But the structure is very heavy, and the result is in XML.

- So i've changed my mind to use Jersey with RESTfUL, which is the web service trend. But it seems i was not able to initialize the contactList somehow, as the APIs are called directly.

- So i've switched to SpringBoot, where i can use @Autowired to declare an object, which will be initialized automatically by the spring framework. And it works!

- Next, i was thinking of how to achieve the concurrency requirement. There are at least two ways
- 1. ConcurrentHashMap
- 2. SynchronizedHashMap
- I've chosen ConcurrentHashMap as it takes care of synchronization itself. Also, in the POJO class, i only put getters to avoid unwanted manipulations to the object fields. But there has to be setters for the ID field in order to display nicely the contact information.


- In order to test concurrenct request, i've chosen TestNG framework becasue it offers annotation to simulate concurrency.-

###Package explanation:
- com.tang.contactservice: Main entry for the project. Please run it as an application.
- com.tang.contactservice.model: POJO for a Contact object
- com.tang.contactservice.service: Concrete implementations for Contact Service (CRUD operations)
- com.tang.contactservice.controller: API for web service
- com.tang.ContactService: Junit test package
