/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geocachingtools.decoder;

/**
 *
 * @author Simon
 * @param <T>
 */
public abstract class DecoderMethod<T> {
    public abstract DecoderResult decode(DecoderRequest<T> request);
    
    public final boolean getRequiresPassword() {
        return this.getClass().getAnnotation(Method.class).requiresPassword();
    }
    public final String getName() {
        return this.getClass().getAnnotation(Method.class).name();
    }
    public final Class<?> getType() {
        return this.getClass().getAnnotation(Method.class).type();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj.getClass().equals(DecoderMethod.class)) {
            return getName().equals(((DecoderMethod)obj).getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
    
    
    
}
