
# Traceable spring services
Library to trace the calls to spring services easily using annotations. The source code of the services will be cleaner due to less calls to send log  messages. 

Each method of the services can be configured to print messages for specific log level, to print only the start or the end of the method or even to print selected input arguments and/or the return value.

## Instructions
The library is designed to be used with spring applications. To use it, its only needed to add one dependency and configure one bean.
 
### 1. Dependencies.
First of all, you must add the dependency of the services.traceable library in your application. The jars are located in maven central (soon), so adding these lines in your pom.xml will be sufficient:

```xml
<dependency>
    <groupId>es.com.arisnegro.spring</groupId>
    <artifactId>services.traceable</artifactId>
    <version>0.0.2-SNAPSHOT</version>
</dependency>
```

### 2. Configure the Aspect.
The services.traceable functionality is based on an aspect to intercept each method invocation and to execute its logic before and after the execution. To make the aspect available in your application you must configure it:

```java
@org.springframework.context.annotation.Configuration
public class Configuration {

	@Bean
	public TraceableAspect traceableAspect() {
		
		return new TraceableAspect();
	}
}
```

### 3. Annotate the methods.
To trace the invocations of a method, it must be annotated with @Traceable. The annotation works at method level or at class level. If you annotate a class, all public methods will be traced in logs. Method annotations have higher priority, so you can combine class level annotations with method level annotations.

For an example:

```java
@Traceable
public String tracedMethod(String id) {
	
	return "#" + id + "#";
}
```

### 4. Configure the attributes of @Traceable.

The annotation have several attributes used to customize the logged messages. For example you can select the level used to write the messages or you can choose to write the messages when an execution starts, but not when an execution ends.

The attributes that can be configured are the next:

* level
* levelStart
* levelEnd
* printInput
* inputArgsIndex
* printOutput

If you want to know the meaning of each attribute, see the javadoc of [Traceable.java](./blob/v0.0.2-SNAPSHOT/src/main/java/es/com/arisnegro/spring/services/traceable/annotations/Traceable.java)

### 5. Output example.

This is an example of log messages written by an application that uses the [spring.services.traceable](https://github.com/arisnegro/spring.services.traceable):

```bash
2020-08-22 16:36:32.425  INFO 4908 --- [           main] e.c.a.s.s.traceable.demo.Application     : Application starts
2020-08-22 16:36:32.475 DEBUG 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : Start tracedMethod: Args [ 1 ]
2020-08-22 16:36:32.524 DEBUG 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : End tracedMethod: Output [ #1# ]
2020-08-22 16:36:32.527 ERROR 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : Start tracedMethodFatalLevel: Args [ 2 ]
2020-08-22 16:36:32.528 ERROR 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : End tracedMethodFatalLevel: Output [ #2# ]
2020-08-22 16:36:32.531 ERROR 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : Start tracedMethodErrorLevel: Args [ 3 ]
2020-08-22 16:36:32.532 ERROR 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : End tracedMethodErrorLevel: Output [ #3# ]
2020-08-22 16:36:32.533  WARN 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : Start tracedMethodWarnLevel: Args [ 4 ]
2020-08-22 16:36:32.533  WARN 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : End tracedMethodWarnLevel: Output [ #4# ]
2020-08-22 16:36:32.535  INFO 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : Start tracedMethodInfoLevel: Args [ 5 ]
2020-08-22 16:36:32.536  INFO 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : End tracedMethodInfoLevel: Output [ #5# ]
2020-08-22 16:36:32.538 DEBUG 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : Start tracedMethodDebugLevel: Args [ 6 ]
2020-08-22 16:36:32.538 DEBUG 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : End tracedMethodDebugLevel: Output [ #6# ]
2020-08-22 16:36:32.540 TRACE 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : Start tracedMethodTraceLevel: Args [ 7 ]
2020-08-22 16:36:32.540 TRACE 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : End tracedMethodTraceLevel: Output [ #7# ]
2020-08-22 16:36:32.544  INFO 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : Start tracedMethodStartInfoEndDebug: Args [ 8 ]
2020-08-22 16:36:32.544 DEBUG 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : End tracedMethodStartInfoEndDebug: Output [ #8# ]
2020-08-22 16:36:32.545 DEBUG 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : Start tracedMethodSkipInput
2020-08-22 16:36:32.545 DEBUG 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : End tracedMethodSkipInput: Output [ #9# ]
2020-08-22 16:36:32.551 DEBUG 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : Start tracedMethodSkipOutput: Args [ 10 ]
2020-08-22 16:36:32.552 DEBUG 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : End tracedMethodSkipOutput
2020-08-22 16:36:32.553 DEBUG 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : Start tracedMethodSelectedParameters: Args [ 11, 13, 15 ]
2020-08-22 16:36:32.554 DEBUG 4908 --- [           main] e.c.a.s.s.traceable.demo.TestService     : End tracedMethodSelectedParameters: Output [ #11#12#13#14#15# ]
2020-08-22 16:36:32.554  INFO 4908 --- [           main] e.c.a.s.s.traceable.demo.Application     : Application ends
```

As you can see, each method invocation is traced with a "start" and an "end" message. In some methods the input arguments are printed and in some methods the return value are printed too.

In addition each method and service can be configured to print messages in a specific level, so the main services can be configured with higher level and the secondary services can be configured with lower level.