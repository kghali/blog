package com.nexio.blog.aop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author Khaled Ghali
 */
@Aspect
@Configuration
public class BeforeAopAspect {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Before(value = "execution(* com.nexio.blog.controller.*.*(..))")
  public void after(JoinPoint joinPoint) {
    List<String> args = new ArrayList<>();
    for (int i = 0; i < joinPoint.getArgs().length; i++) {
      args.add(String.valueOf(joinPoint.getArgs()[i]));
    }
    String argValues = args.stream().collect( Collectors.joining( "," ) );
    logger.info("before execution of {} with arguments ({})", joinPoint.getSignature(), argValues);
  }
}
