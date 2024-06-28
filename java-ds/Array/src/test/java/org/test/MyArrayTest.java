package org.test;

import org.josiahroa.MyArrayImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyArrayTest {
    private final MyArrayImpl emptyArray = new MyArrayImpl();

    @Test
    public void isEmpty() {
        assertEquals(0, emptyArray.getLength());
    }
}
