# Spring Boot Rest CRUD

## Introduction

This is a simple Spring Boot Rest CRUD application. It uses Spring Boot, Spring Data JPA, Lombok, JUnit, Mockito, Maven and PostgreSQL.

This application is a simple CRUD application for a `Product` entity. It has the following endpoints:
 * `api/v1/`
   * `GET product/` - Get all products
   * `GET product/{ids}` - Get a product by id. `product/1` or `product/1,2,3`
   * `GET /codes/{codes}` - Get a product by code. `codes/1` or `codes/1,2,3`
   * `GET /name/{name}` - Get a product by name. `name/1`
   * `GET /priceHrk/{priceHrk}` - Get a product by price. `priceHrk/1.00`
   * `GET /priceEur/{priceEur}` - Get a product by price. `priceEur/1.00`
   * `GET /description/{description}` - Get a product by description. `description/1`
   * `GET /availability/{availability}` - Get a product by availability. `availability/AVAILABLE` or `availability/NOT_AVAILABLE`

    * `POST /product` - Create a new product
    * `PUT /product` - Update a product

## Requirements

* Have a PostgreSQL database running on your machine
* Java 17
* Maven

## Setup

* Run init.bat to create the database and the user.



