##  Rest-Assured Java Test Automation Framework

### Tools & Libraries
    * Java 8
    * JUnit 4
    * Maven 3.6.1
    * Git on Github
    * Rest-Assured
    * JSON schema validator library

####   Design Decisions
   Why Rest-Assured? 
   
   Rest-Assured is a Java based DSL (Domain Specific Language) which is most commonly 
   used to test out REST based services.Rest-Assured presents a great advantage 
   because it supports multiple HTTP requests and can validate and/or verify the responses.
    
   Environments
   
   There are two environments available
   
   Development [https://localhost:3000](https://localhost:3000)
   
   Production  [https://jsonplaceholder.typicode.com](https://localhost:3000)
   
   By default the tests will execute in Production environment
   There is a provision to switch the enivronment in which the tests should execute
    
####  How to run tests from command line
   Open a command prompt window and change directory to your Maven project.
   You should be in a directory that contains pom.xml file
  1. Build and Run the tests with clean
     * `mvn clean install`
  2. Run without tests
     * `mvn clean install -DskipTests`
  3. Run to generate Reports
     * `mvn site`
  4. Environment of test execution can be specified 
     in environment.properties file or can be specified 
     from command line.
     * `mvn clean install -Denvironment=DEV`
  5. Run all the test in a test class
     *  `mvn -Dtest=PostsTest test `
     where the validateGetAllPosts is a Test class with test method  

#### Observations and Defects
  1. API is un-versioned, versioning should be done for backward incompatible changes
  2. JSON schema validation fails for the post resource
  3. When a post is deleted and you try to access it returns a 200 OK status code but expected in 404.
  4. When you create a post and try to access newly create post a 404 Not found status code  is returned
  5. GET	/posts/1/comments  response is expected to return comments for postId=1
     where as the response returns all the comments.
     
     


