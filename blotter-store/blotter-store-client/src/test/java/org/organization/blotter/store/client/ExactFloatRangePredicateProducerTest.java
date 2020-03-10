package org.organization.blotter.store.client;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExactFloatRangePredicateProducerTest {
    private ExactFloatRangePredicateProducer underTest;

    @Before
    public void before() {
        underTest = new ExactFloatRangePredicateProducer();
    }

    @Test
    public void accept_ok() {
        assertTrue(underTest.accept("[78.2]"));
        assertTrue(underTest.accept("25.9"));
    }

    @Test
    public void accept_ko() {
        assertFalse(underTest.accept("[22.2,22.6]"));
        assertFalse(underTest.accept("[]"));
        assertFalse(underTest.accept("[25.4["));
        assertFalse(underTest.accept("]25.4]"));
        assertFalse(underTest.accept("]]"));
        assertFalse(underTest.accept("[["));
    }
}