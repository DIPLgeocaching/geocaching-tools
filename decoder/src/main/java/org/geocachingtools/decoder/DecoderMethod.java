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
public abstract class DecoderMethod<T> implements Comparable<DecoderMethod> {
    
    public static enum ExecutionTime {
        SLOW,NORMAL,FAST
    }
    
    
    public abstract DecoderResult decode(DecoderRequest<T> request);
    
    public final boolean getRequiresPassword() {
        return this.getClass().getAnnotation(Method.class).requiresPassword();
    }
    public final String getName() {
        return this.getClass().getAnnotation(Method.class).name();
    }
    public final String getCleanName() {
        return getName().replace(" ", "").replace("\t","").replace("\n","");
    }
    
    public final Class<?> getType() {
        return this.getClass().getAnnotation(Method.class).type();
    }
    public final ExecutionTime getExpectedExecutionTime(){
        return this.getClass().getAnnotation(Method.class).expectedExecutionTime();
    }

    public DecoderMethod() {
    }

    @Override
    public int compareTo(DecoderMethod o) {
        int result = o.getExpectedExecutionTime().compareTo(getExpectedExecutionTime());
        //Two different decoder methods with the same priority must not return 0 because a SortedSet would not allow these 'duplicates'
        //Therefore we have to add another sorting criteria.
        if(result == 0)
            result = o.getName().compareTo(getName());
        return result;
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
