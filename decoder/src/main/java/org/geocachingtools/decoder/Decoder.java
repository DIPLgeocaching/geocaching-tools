/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.decoder;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geocachingtools.decoder.methods.DecoderMethodTest;
/**
 *
 * @author Simon
 */
public class Decoder {

    private static final Decoder DECODER_INSTANCE;
    private static final String DECODER_METHOD_PACKAGE = "org.geocachingtools.decoder.methods";
    static {
        DECODER_INSTANCE = new Decoder();
        //DECODER_INSTANCE.methods.put(String.class, Arrays.asList(new DecoderMethodTest()));
        try {
            List<Class<?>> classes = ClassFinder.find(DECODER_METHOD_PACKAGE);
            System.out.println(classes);
            for(Class<?> c : classes) {
                Method annotation = c.getAnnotation(Method.class);
                if(annotation != null) {
                    List<DecoderMethod> list = DECODER_INSTANCE.methods.get(annotation.type());
                    if(list == null)
                        list = new ArrayList<>();
                    list.add((DecoderMethod) c.newInstance());
                    DECODER_INSTANCE.methods.put(annotation.type(), list);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Decoder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Map<Class<?>,List<DecoderMethod>> methods = new HashMap<>();
      
    private Decoder() {
    }
    
    /**
     *
     * @param key
     * @return
     */
    public Collection<DecoderMethod> getMethods(Class<?> key) {
        return methods.getOrDefault(key,Collections.EMPTY_LIST);
    }
    
    public <T> Future<DecoderResult> decode(DecoderRequest<T> request) {
        return new Future<DecoderResult>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public boolean isCancelled() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public boolean isDone() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public DecoderResult get() throws InterruptedException, ExecutionException {
                return request.getMethod().decode(request);
            }

            @Override
            public DecoderResult get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
    
    
    public static Decoder getInstance() {
        return DECODER_INSTANCE;
    }
    
}