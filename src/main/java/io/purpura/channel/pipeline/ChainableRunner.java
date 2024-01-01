package io.purpura.channel.pipeline;

public interface ChainableRunner {
    <T> T apply();
}
