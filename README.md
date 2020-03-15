# lambda-pubg-telemetry-processor

This is a project which uses Spring Boot to create a package deployable to AWS lambda. It is used to build training data for my PUBG machine learning project much quicker than my desktop can.
A "shaded" jar is created using gradle. A shaded jar is essentially a jar which includes all the dependencies required to run the project in a single jar.

## Installation

Gradle 5.4 has been tested with Oracle's Java 1.8.0_161.

```bash
#builds build/libs/lambda-pubg-telemetry-processor-1.0-SNAPSHOT-aws.jar
./gradlew clean build
```

## Usage

In order to run this code, the lambda-pubg-telemetry-processor-1.0-SNAPSHOT-aws.jar needs to be uploaded to AWS lambda.
The Handler needs to be set to **com.github.ryanp102694.handler.StreamHandler**. In the case that the StreamHandler is used, you can optionally provide an ObjectMapper to handle the deserialization from the Lambda input event to the Java POJO, which in this case is the **PubgTelemetryRequest** class.

**How Spring finds the Handler implementation**  
There are two different ways Spring Boot picks the correct function to run.
* You can specify an environment variable called FUNCTION_NAME. When FUNCTION_NAME is specified, Spring will load the bean specified by FUNCTION_NAME to use as the Lambda entry point. This project includes "fooHandler" and "scheduledEventHandler". 
The "pubgTelemetryHandler" deserializes a JSON PubgTelemetryRequest object,.
* If no FUNCTION_NAME is defined, Spring Boot will automatically find the bean implementing Function<T,R> interface and use it as the Lambda entry point. The handler in this project is **HandlerImplementation**.

**Lambda Config**

* Runtime: Java 8
* Handler: **StreamHandler**
* Memory: 1024 MB (anything less and sometimes Spring Boot would sometimes fail to start)
* Timeout: 20 sec (anything less and sometimes Spring Boot would sometimes fail to start)

**Test Events**
```javascript
//PubgTelemetryRequest test event example
{
  "s3TelemetryJsonKey": "testValue"
}
```