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