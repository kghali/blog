package com.nexio.blog.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.nexio.blog.exception.BlogException;

/**
 * @author Khaled Ghali
 */
@Aspect
@Configuration
public class AfterAopAspect {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @After(value = "execution(* com.nexio.blog.controller.*.*(..))")
  public void after(JoinPoint joinPoint) {
    logger.info("after execution of {}", joinPoint.getSignature());
  }

  @AfterReturning(value = "execution(* com.nexio.blog.controller.*.*(..))", returning = "result")
  public void afterReturning(JoinPoint joinPoint, Object result) {
    logger.info("{} returned with value {}", joinPoint.getSignature(), result);
  }

  @AfterThrowing(value = "execution(* com.nexio.blog.controller.*.*(..))", throwing = "ex")
  public void afterThrowing(JoinPoint joinPoint, BlogException ex) {
    logger.error("{} has thrown an exception {}", joinPoint.getSignature(), ex.getHttpStatus(), ex.getMessage());
  }
}