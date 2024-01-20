<p align="center">
  <img 
    src="https://user-images.githubusercontent.com/91671880/169507466-bae6de4d-161e-4763-9118-c7d00fd948d3.png"
  >
</p>

# Frontend Service
___
> This is the main frontend service.
> Here we view all the items from api service.
> ```
> We use HTTP Requests using RestTemplate in Spring Boot
> ```

## *Main Method*
___
```java

@Value("${template.data-binder.base-url}")
private String apiBaseUrl;

public List<Item> getAllProducts() {
    ResponseEntity<Item[]> responseEntity =
            restTemplate.getForEntity( apiBaseUrl + "/items", Item[].class);
    Item[] ItemArray = responseEntity.getBody();
    assert ItemArray != null;
    return Arrays.stream(ItemArray)
            .collect(Collectors.toList());
}

```