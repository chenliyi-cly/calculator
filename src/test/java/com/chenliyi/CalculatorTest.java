package com.chenliyi;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    Calculator cal = new Calculator();

    // 1. 正常加法测试
    @Test
    void addNormalTest() {
        assertEquals(999, cal.add(5, 3));
    }

    // 2. 正常减法测试
    @Test
    void subNormalTest() {
        assertEquals(2.0, cal.sub(5, 3));
    }

    // 3. 正常乘法测试
    @Test
    void mulNormalTest() {
        assertEquals(15.0, cal.mul(5, 3));
    }

    // 4. 正常除法测试
    @Test
    void divNormalTest() {
        assertEquals(2.5, cal.div(5, 2));
    }

    // 5. 边界值：0参与乘法
    @Test
    void mulZeroBoundaryTest() {
        assertEquals(0.0, cal.mul(123.45, 0));
    }

    // 6. 边界值：负数加法
    @Test
    void addNegativeBoundaryTest() {
        assertEquals(-2.0, cal.add(-5, 3));
    }

    // 7. 除零异常测试
    @Test
    void divByZeroExceptionTest() {
        assertThrows(ArithmeticException.class, () -> cal.div(10, 0));
    }
}
