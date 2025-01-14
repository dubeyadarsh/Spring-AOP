# Logging and Performance Aspect

This project demonstrates the use of Aspect-Oriented Programming (AOP) in a Spring Boot application. It includes aspects for logging method entry, handling exceptions, logging after method execution, and measuring method execution time.

## Table of Contents

- [Introduction](#introduction)
- [Keywords](#keywords)
  - [JoinPoint](#joinpoint)
  - [PointCut](#pointcut)
  - [Advice](#advice)
  - [AOP Proxies](#aop-proxies)
  - [PointCut Designator](#pointcut-designator)
- [Aspects](#aspects)
  - [Logging Method Entry](#logging-method-entry)
  - [Handling Exceptions](#handling-exceptions)
  - [Logging After Execution](#logging-after-execution)
  - [Measuring Execution Time](#measuring-execution-time)
- [Flow of Execution with AOP](#flow-of-execution-with-aop)
- [Dependencies](#dependencies)

## Introduction

Aspect-Oriented Programming (AOP) is a programming paradigm that aims to increase modularity by allowing the separation of cross-cutting concerns. This project includes various aspects to demonstrate the logging and performance measurement in a Spring Boot application.

## Keywords

### JoinPoint

A `JoinPoint` represents a point during the execution of a program where an aspect can be applied. It provides access to the method being executed, its arguments, and other details.

### PointCut

A `PointCut` is an expression that matches certain methods in the application. It defines the "where" in the code where advice (action) should be applied. A `PointCut` can be used to specify the methods to intercept based on criteria such as method names or parameters.

### Advice

`Advice` is the action taken by an aspect at a specific `JoinPoint`. It defines "what" should be done when the `PointCut` expression matches. Common types of advice include `@Before`, `@After`, `@Around`, and `@AfterThrowing`.

### AOP Proxies

AOP Proxies are objects created by the AOP framework to intercept method calls. They act as intermediaries between the client code and the actual target object. There are two types of proxies: JDK Dynamic Proxy (for interfaces) and CGLIB Proxy (for classes).

### PointCut Designator

Spring AOP supports the following AspectJ pointcut designators (PCD) for use in pointcut expressions:
- `execution`
- `within`
- `this`
- `target`
- and many more

## Aspects

### Logging Method Entry

The `logMethodEntry` aspect logs the start of the method execution and the arguments of the method being executed.

### Handling Exceptions

The `logException` aspect logs any exceptions thrown by the method.

### Logging After Execution

The `logAfterExecution` aspect logs the method execution and the return value after the method completes.

### Measuring Execution Time

The `measureExecutionTime` aspect measures and logs the execution time of methods in the `AopDemoController`.

## Flow of Execution with AOP

1. **Request to Controller:** When a request is made to a method in the controller, it first goes through the proxy.
2. **PointCut Matching:** The proxy checks if the method matches any `PointCut` expression you’ve defined.
3. **Before Advice:** Runs before the method executes.
4. **Method Execution:** If the `PointCut` matches, the method is executed (via `joinPoint.proceed()` in an `@Around` advice).
5. **After Advice:** Executes after the method, whether it completes normally or throws an exception.
6. **Return to Client:** The proxy then returns the result of the method (or a value modified by the advice) to the client.

## Dependencies

Ensure you have the following dependencies in your `pom.xml` or `build.gradle`:

For Maven:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-logging</artifactId>
</dependency>
