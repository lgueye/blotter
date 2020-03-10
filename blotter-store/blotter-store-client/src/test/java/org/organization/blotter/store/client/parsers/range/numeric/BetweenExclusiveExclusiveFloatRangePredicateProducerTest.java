package org.organization.blotter.store.client.parsers.range.numeric;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BetweenExclusiveExclusiveFloatRangePredicateProducerTest {
    private BetweenExclusiveExclusiveFloatRangePredicateProducer underTest;

    @Before
    public void before() {
        underTest = new BetweenExclusiveExclusiveFloatRangePredicateProducer();
    }

    @Test
    public void accept_ok() {
        assertTrue(underTest.accept("]78.2, 89.3["));
    }

    @Test
    public void accept_ko() {
        assertFalse(underTest.accept("]22.2,a["));
        assertFalse(underTest.accept("[]"));
        assertFalse(underTest.accept("[25.4,27]"));
        assertFalse(underTest.accept("[25.4,27["));
        assertFalse(underTest.accept("]25.4,27]"));
        assertFalse(underTest.accept("]25.4,27,28["));
        assertFalse(underTest.accept("]25.4,["));
        assertFalse(underTest.accept("]89.3, 78.2["));
        assertFalse(underTest.accept("]]"));
        assertFalse(underTest.accept("[["));
    }
}