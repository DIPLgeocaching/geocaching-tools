/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.decoder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Simon
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Method {
    String name();
    boolean requiresPassword() default false;
    Class<?> type();
    DecoderMethod.ExecutionTime expectedExecutionTime() default DecoderMethod.ExecutionTime.NORMAL;
    
}