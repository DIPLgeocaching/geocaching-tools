package org.geocachingtools.decoder;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import static org.geocachingtools.decoder.DecoderMethod.ExecutionTime.FAST;
import static org.geocachingtools.decoder.DecoderMethod.ExecutionTime.NORMAL;
import static org.geocachingtools.decoder.DecoderMethod.ExecutionTime.SLOW;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Simon
 */
public class DecoderMethodTest {

    public DecoderMethodTest() {
    }

    public DecoderMethod instance(String name) {
        return null;
    }

    @Test
    public void testExecutionPrioritization() {
        SortedSet<DecoderMethod> example = new TreeSet<>();
        example.add(new SlowDecoderMethod());
        //because the DecoderMethod.equals checks if the name is equals only 1 NormalDecoderMethod is added to the set
        example.add(new NormalDecoderMethod());
        example.add(new NormalDecoderMethod());
        example.add(new AnotherNormalDecoderMethod());
        example.add(new FastDecoderMethod());
        
        assertEquals(4, example.size());
        
        assertEquals(FAST, example.first().getExpectedExecutionTime());
        assertEquals(SLOW, example.last().getExpectedExecutionTime());

        Iterator<DecoderMethod> iterator = example.iterator();
        assertEquals(FAST, iterator.next().getExpectedExecutionTime());
        assertEquals(NORMAL, iterator.next().getExpectedExecutionTime());
        assertEquals(NORMAL, iterator.next().getExpectedExecutionTime());
        assertEquals(SLOW, iterator.next().getExpectedExecutionTime());

    }

    @Method(name = "Slow", type = String.class, expectedExecutionTime = SLOW)
    private static class SlowDecoderMethod extends DecoderMethod {

        @Override
        public DecoderResult decode(DecoderRequest request) {
            throw new UnsupportedOperationException("Just a slow decoder test method.");
        }
    }

    @Method(name = "AnotherNormal", type = String.class, expectedExecutionTime = NORMAL)
    private static class AnotherNormalDecoderMethod extends DecoderMethod {

        @Override
        public DecoderResult decode(DecoderRequest request) {
            throw new UnsupportedOperationException("Just another normal decoder test method.");
        }
    }


    @Method(name = "Normal", type = String.class, expectedExecutionTime = NORMAL)
    private static class NormalDecoderMethod extends DecoderMethod {

        @Override
        public DecoderResult decode(DecoderRequest request) {
            throw new UnsupportedOperationException("Just a normal decoder test method.");
        }
    }

    @Method(name = "Fast", type = String.class, expectedExecutionTime = FAST)
    private static class FastDecoderMethod extends DecoderMethod {

        @Override
        public DecoderResult decode(DecoderRequest request) {
            throw new UnsupportedOperationException("Just a fast decoder test method.");
        }
    }

}
