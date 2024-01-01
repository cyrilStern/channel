package io.purpura.channel.pipeline;

public interface Pipeline {

    <T> T then();
    <T> T before();
}
