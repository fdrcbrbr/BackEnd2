<p align="center">
  <img 
    src="https://user-images.githubusercontent.com/91671880/169507466-bae6de4d-161e-4763-9118-c7d00fd948d3.png"
  >
</p>

<!-- Task Lists -->
## API Service
___
> This is the main backend service (inherited from Backend1).
> Main goal in the service is to add security configuration, we use JWT authentication.
> ```
> We use RabbitMQ to send the invoice from the api to the Receipt service
> ```

> [Open swagger here (Documentation)](http://localhost:8080/swagger-ui/index.html)


### Task Lists
- - - 
* [x] Create a Security Config.
* [x] Create an Encoder.
* [x] Create our customized JWTUtility.
* [x] Override UserService.
* [x] Customize our JwtFilter.
* [x] Create a RabbitMQ configuration.
* [x] Create a RabbitMQMessageProducer.
* [x] Unit test.
* [x] Fixed the Dockerfile to build without skipping the tests.