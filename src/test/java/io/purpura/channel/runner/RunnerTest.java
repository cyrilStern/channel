package io.purpura.channel.runner;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RunnerTest {
    private static final String COLOR_RED = "red";

    @Test
    void run() throws InterruptedException {
        var color = SimpleRunner.run(red -> red, COLOR_RED);
        assertEquals(COLOR_RED, color);
    }

    @Test
    void run_method() throws InterruptedException {
        String colorRed = "red";
        var color = SimpleRunner.run(this::colorMethod, colorRed);
        assertEquals(COLOR_RED, color);
    }

    @Test
    void run_method_three() throws InterruptedException {
        String colorRed = "red";
        var color = SimpleRunner.run(this::colorMethod_3, colorRed, colorRed, colorRed);
        assertEquals(COLOR_RED, color);
    }

    private String colorMethod(String color){
        return color;
    }

    private String colorMethod_3(String color, String a, String b){
        return color;
    }
}
