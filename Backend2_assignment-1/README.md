<p align="center">
  <img 
    src="https://user-images.githubusercontent.com/91671880/169507466-bae6de4d-161e-4763-9118-c7d00fd948d3.png"
   alt="Logo">
</p>

[//]: # (![GitHub code size]&#40;https://img.shields.io/github/languages/code-size/AliAMoteirek/Backend2_assignment&#41;)

[//]: # (![GitHub repo size]&#40;https://img.shields.io/github/repo-size/AliAMoteirek/Backend2_assignment&#41;)

[//]: # (![GitHub commits per week]&#40;https://img.shields.io/github/commit-activity/w/AliAMoteirek/Backend2_assignment&#41;)

[//]: # (![GitHub latest commit]&#40;https://img.shields.io/github/last-commit/AliAMoteirek/Backend2_assignment&#41;)

[//]: # (![GitHub languages uses]&#40;https://img.shields.io/github/languages/count/AliAMoteirek/Backend2_assignment&#41;)

[//]: # (![GitHub most language used]&#40;https://img.shields.io/github/languages/top/AliAMoteirek/Backend2_assignment&#41;)

# Backend2 Assignment

### Installation

_Below is an example of how you can install and set up the app._

1. Clone the repo

    ```sh
    git clone https://github.com/AliAMoteirek/Backend2_assignment.git
    ```
2. Navigate to the cloned repo using your terminal
     ```sh
   cd ~/[filepath]/
   ```

3. Use docker compose to build and run
   ```sh
   docker compose up --build
   ```
5. Open the documentation at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
   ```http
   http://localhost:8080/swagger-ui/index.html
   ```
6. Open all the products at [http://localhost:80801/products](http://localhost:8081/products)
   ```http
   http://localhost:80801/products
   ```

> [Open swagger here (Documentation)](http://localhost:8080/swagger-ui/index.html)

* [x] API Service
* [x] Frontend Service
* [x] Receipt Service
* [x] Dockerized
    * [x] Fixed the Dockerfile to build without skipping the tests.
* [x] PDF
* [x] Unit Testing

## API Reference

#### Get all items

```http
  GET /orders
```

| Parameter | Type     | Description                  |
|:----------|:---------|:-----------------------------|
| `order`   | `string` | **Required**. Your order key |

#### Get a specific item

```http
  GET /orders/{customerId}
```

| Parameter | Type     | Description                           |
|:----------|:---------|:--------------------------------------|
| `id`      | `string` | **Required**. Id of customer to fetch |

#### Get all customers

```http
  GET /customers
```

| Parameter   | Type     | Description                      |
|:------------|:---------|:---------------------------------|
| `customers` | `string` | **Required**. Your customers key |

#### Get a specific customer

```http
  GET /customers/{customerId}
```

| Parameter    | Type     | Description                           |
|:-------------|:---------|:--------------------------------------|
| `customerId` | `string` | **Required**. Id of customer to fetch |

#### Post a new customer

```http
  Post /customers
```

| Parameter         | Type     | Description                                   |
|:------------------|:---------|:----------------------------------------------|
| `customer object` | `object` | **Required**. json object of customer to post |

#### Post login

```http
  Post /login
```

| Parameter               | Type     | Description                                                |
|:------------------------|:---------|:-----------------------------------------------------------|
| `username and password` | `object` | **Required**. json object of username and password to post |

#### Get all items

```http
  GET /items
```

| Parameter | Type     | Description                 |
|:----------|:---------|:----------------------------|
| `items `  | `String` | **Required**. Your item key |

#### Get a specific item

```http
  GET /items/{name}
```

| Parameter      | Type     | Description                         |
|:---------------|:---------|:------------------------------------|
| `name of item` | `string` | **Required**. Name of item to fetch |

#### Post a new item

```http
  Post /items/{name}
```

| Parameter      | Type     | Description                        |
|:---------------|:---------|:-----------------------------------|
| `name of item` | `string` | **Required**. Name of item to post |

## Authors

- [@Ali Moteirek](https://github.com/AliAMoteirek)
- [@Federico Barberi](https://github.com/F3f0)
- [@Roberto Mendez](https://github.com/robmndz)
- [@Yasser Al dahwi](https://github.com/YasserAldahwi)


