# store-management-tool

Prerequisites:
1. Download and install docker => https://www.docker.com/
2. run the following command `docker-compose up -d `


Description of APIs:

 - add new product:
   - that method add a new product
   - check in database if there is another product with the same code
   - throw an exception if there is another product with the same code
   - add tests for 2 case when the product works and when it thrown an exception
 - add new product:
   - that method find a product by a code
   - check in database if there is a product with that code
   - handle a not found exception
   - add tests for 2 case when the product works and when it thrown an exception
     