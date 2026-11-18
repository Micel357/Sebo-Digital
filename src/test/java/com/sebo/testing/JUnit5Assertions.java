package com.sebo.testing;

/**
 * Transitional overloads for the message-first assertion order used by the
 * original JUnit 4 tests. New tests should use JUnit 5's message-last overloads.
 */
public final class JUnit5Assertions {
    private JUnit5Assertions() {
    }

    public static void assertTrue(String message, boolean condition) {
        org.junit.jupiter.api.Assertions.assertTrue(condition, message);
    }

    public static void assertFalse(String message, boolean condition) {
        org.junit.jupiter.api.Assertions.assertFalse(condition, message);
    }

    public static void assertNotNull(String message, Object actual) {
        org.junit.jupiter.api.Assertions.assertNotNull(actual, message);
    }

    public static void assertNull(String message, Object actual) {
        org.junit.jupiter.api.Assertions.assertNull(actual, message);
    }

    public static void assertEquals(String message, Object expected, Object actual) {
        org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
    }

    public static void assertEquals(String message, double expected, double actual, double delta) {
        org.junit.jupiter.api.Assertions.assertEquals(expected, actual, delta, message);
    }
}
