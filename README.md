# lambda-pubg-telemetry-processor

This is an example project which uses Spring Boot to create a package deployable to AWS lambda. 
A "shaded" jar is created using gradle. A shaded jar is essentially a jar which includes all the dependencies required to run the project in a single jar.

## Installation

Gradle 5.4 has been tested with Oracle's Java 1.8.0_161.

```bash
#builds build/libs/lambda-pubg-telemetry-processor-1.0-SNAPSHOT-aws.jar
gradle clean build
```

## Usage

In order to run this code, the lambda-pubg-telemetry-processor-1.0-SNAPSHOT-aws.jar needs to be uploaded to AWS lambda.
The Handler needs to be set to **com.github.ryanp102694.handler.StreamHandler** *or* **com.github.ryanp102694.handler.Handler**. In the case that the StreamHandler is used, you can optionally provide an ObjectMapper to handle the deserialization from the Lambda input event to the Java POJO, which in this case is the **Foo** class.

**How Spring finds the Handler implementation**  
There are two different ways Spring Boot picks the correct function to run.
* You can specify an environment variable called FUNCTION_NAME. When FUNCTION_NAME is specified, Spring will load the bean specified by FUNCTION_NAME to use as the Lambda entry point. This project includes "fooHandler" and "scheduledEventHandler". 
The "fooHanlder" deserializes a JSON Foo object, while the "scheduledEventHandler" deserializes a ScheduledEvent object (like from a CloudWatch event)
* If no FUNCTION_NAME is defined, Spring Boot will automatically find the bean implementing Function<T,R> interface and use it as the Lambda entry point. The handler in this project is **HandlerImplementation**.

**Lambda Config**

* Runtime: Java 8
* Handler: **StreamHandler** *or* **Handler**
* Memory: 1024 MB (anything less and sometimes Spring Boot would sometimes fail to start)
* Timeout: 20 sec (anything less and sometimes Spring Boot would sometimes fail to start)

**Test Events**
```javascript
//Foo test event example
{
  "s3ObjectUrl": "testValue"
}



//ScheduledEvent test event example
{
  "id": "cdc73f9d-aea9-11e3-9d5a-835b269c0d9c",
  "detail-type": "Scheduled Event",
  "source": "aws.events",
  "account": "{{account-id}}",
  "time": "1970-01-01T00:00:00Z",
  "region": "us-east-1",
  "resources": [
    "arn:aws:events:us-east-1:123456789012:rule/ExampleRule"
  ],
  "detail": {}
}
```