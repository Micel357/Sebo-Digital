package com.sebo.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Transitional overloads for the message-first assertion order used by the
 * original JUnit 4 tests. New tests should use JUnit 5's message-last overloads.
 */
public final class JUnit5Assertions {
    private JUnit5Assertions() {
    }

    public static void assertTrue(String message, boolean condition) {
        assertTrue(condition, message);
    }

    public static void assertFalse(String message, boolean condition) {
        assertFalse(condition, message);
    }

    public static void assertNotNull(String message, Object actual) {
        assertNotNull(actual, message);
    }

    public static void assertNull(String message, Object actual) {
        assertNull(actual, message);
    }

    public static void assertEquals(String message, Object expected, Object actual) {
        assertEquals(expected, actual, message);
    }

    public static void assertEquals(String message, double expected, double actual, double delta) {
        assertEquals(expected, actual, delta, message);
    }
}
