package yavi_validation.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Let's first understand some @keywords used in AOP :
 
 1) JoinPoint : A JoinPoint represents a point during the execution of a program where an aspect can be applied.
                It provides access to the method being executed, its arguments, and other details.
                
 2) PointCut :A PointCut is an expression that matches certain methods in the application.
 			  It defines the "where" in the code where advice (action) should be applied.
  			  A PointCut can be used to specify the methods to intercept based on criteria such as method names or parameters.
 
 3) Advice :  Advice is the action taken by an aspect at a specific JoinPoint.
 			  It defines "what" should be done when the PointCut expression matches.
  			  Common types of advice include @Before, @After, @Around, and @AfterThrowing.
  			  
 4) AOP Proxies : AOP Proxies are objects created by the AOP framework to intercept method calls.
  				 They act as intermediaries between the client code and the actual target object.
  					There are two types of proxies: JDK Dynamic Proxy (for interfaces) and CGLIB Proxy (for classes).
 
 5) PointCut Designator : Spring AOP supports the following AspectJ pointcut designators (PCD) for use in pointcut expressions:
 						a) execution  b) within  c) this d)target and manymore.
 *
 */

@Slf4j
@Aspect // This annotation make below class as implementation of aspect programming.
@Component
public class LoggingAndPerformanceAspect {
	
	
	@Pointcut("execution(* yavi_validation.controller.AopDemoController.*(..))"    // First * indicate return type of method can be anything
			+ "|| execution(* yavi_validation.service.AopDemoService.*(..))")
	@Order(1)
    protected void loggingOperation()
    {
	 // Not Required for Point cut	
    }
	
	
    /**
     * @param joinPoint
     * Aspect
     * The following aspect will be applied at the beginning of the method execution when the method name matches the pointcut expression.
     */
    @Before("loggingOperation()") 
    @Order(2)
    public void logMethodEntry(JoinPoint joinPoint) {
    	 log.info("Start for Execution : " + joinPoint.getSignature().getName() + ":: " + joinPoint.getSignature().getDeclaringTypeName() );
    	 log.info("Arguments of the Methods Executing : " + Arrays.toString(joinPoint.getArgs()));  
     }
    

    /**
     * @param joinPoint
     * @param exception
     * Aspect
     * The following aspect will be applied if jointpoint throws any exception when the method name matches the pointcut expression.
     */
    @AfterThrowing(pointcut = "loggingOperation()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        log.info("Exception in method: " + joinPoint.getSignature().getName());
        log.info("Exception message: " + exception.getMessage());
    }
    
    /**
     * @param joinPoint
     * @param result
     * Aspect
     * The following aspect will be applied after method return  any result when the method name matches the pointcut expression.
     */
    @AfterReturning(pointcut = "loggingOperation()", returning="result" )
    @Order(3)
    public void logAfterExecution(JoinPoint joinPoint,Object result) {
        log.info("Execution done : " + joinPoint.getSignature().getName());
        log.info("Return value : {}" , result);
    }
    
    /**
     * Measures the execution time of methods in the AopDemoController.
     * This aspect wraps the method execution, calculates the time taken, 
     * and logs the execution time in milliseconds.
     *
     * @param joinPoint The join point representing the method being executed.
     * @return The result of the method execution.
     * @throws Throwable If an error occurs during method execution.
     */
    @Around("loggingOperation()")
    @Order(4)
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed(); // Proceed with method execution
        long endTime = System.currentTimeMillis();
        log.info("Method " + joinPoint.getSignature().getName() + " executed in " + (endTime - startTime) + " ms");
        return result;
    }
  
}

 /**
  * 
  * Flow of Execution with AOP:
	1) Request to Controller: When a request is made to a method in the controller, it first goes through the proxy.
    2) PointCut Matching: The proxy checks if the method matches any PointCut expression you’ve defined.
	 Advice Execution:
    3) Before advice: Runs before the method executes.
	4) Method Execution: If the PointCut matches, the method is executed (via joinPoint.proceed() in an @Around advice).	
	5) After advice: Executes after the method, whether it completes normally or throws an exception.
	6) Return to Client: The proxy then returns the result of the method (or a value modified by the advice) to the client.
  */
