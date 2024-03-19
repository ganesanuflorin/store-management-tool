# store-management-tool

Prerequisites:
1. Download and install docker => https://www.docker.com/
2. run the following command `docker-compose up -d `


Description of APIs:

 - add new product:
   - that method add a new product
   - check in database if there is another product with the same code
   - throw an exception if there is another product with the same code
   - add tests for 2 case when we can to add a product and when it thrown an exception
   - sample case of api call POST : localhost:8089/api/v1/product/add
 - find a product:
   - that method find a product by a code
   - check in database if there is a product with that code
   - handle a not found exception
   - add tests for 2 case when we can to find and when it thrown an exception
   - sample case of api call GET : localhost:8089/api/v1/product/findBy?code=1231241231
 - update product:
    - that method update price of the product
    - fot it you only need a newPrice and productCode
    - handle if there is not a product with that code with not found exception
    - add tests for 2 case when we have a product and we can to change that price and when it thrown an exception
    - sample case of api call PUT : localhost:8089/api/v1/product/change/15.1/code/1231241231
 - remove product:
    - that method remove a product by code
    - handle if there is not a product with that code with not found exception
    - add tests when we have a product and remove it and when we don't have
    - sample case of api call DELETE : localhost:8089/api/v1/product/remove/product/1231241231
- find all products:
   - that method find all products from database
   - add tests when we have products and when we don't have
   - sample case of api call GET : localhost:8089/api/v1/product/findAll
    
Describe the JWT Authentication & Authorization

- start to adding the spring-security dependency in pom
- implements UserDetails method in User class and the roles for users
- create a UserRepository to find a user by username in database
- implement UserDetailsImpl for UserDetailsService to get the user from database 
- add the JwtService class where I generate the jwtToken which contains headers, 
subject and signature for signature I use HS256 to create a secret_key. Here add
all details about token and check if it s valid.
- add JwtAuthenticationFilter which has the role to intercept all the HTTP request
and verify if these have a valid jwtToken, if it is valid the filter create a
context for authentication.
- create an AuthenticationResponse to pass the token.
- add AuthenticationController for login and register part and Authentication service
here create the logic for register and login.
- add Security config where I configure the filer of security and give access
based on role and also need to be authenticated to access the APIs.
