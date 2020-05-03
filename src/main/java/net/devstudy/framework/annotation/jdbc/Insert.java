package net.devstudy.framework.annotation.jdbc;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.devstudy.framework.handler.DefaultResultSetHandler;
import net.devstudy.framework.handler.ResultSetHandler;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Insert {
	Class<? extends ResultSetHandler> resultSetHandlerClass() default DefaultResultSetHandler.class;
}
