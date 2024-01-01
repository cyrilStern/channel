package io.purpura.channel.runner;

public interface Runner{


    @FunctionalInterface
    interface Function6<One, Two, Three, Four, Five, Six> {
        public Six apply(One one, Two two, Three three, Four four, Five five);
    }
    @FunctionalInterface
    interface Function1<One, Two> {
        public Two apply(One one);
    }
    @FunctionalInterface
    interface Function2<One, Two, Three> {
        public Three apply(One one, Two two);
    }

    @FunctionalInterface
    interface Function3<One, Two, Three, Four> {
        public Four apply(One one, Two two, Three three);
    }

    @FunctionalInterface
    interface Function4<One, Two, Three, Four, Five> {
        public Five apply(One one, Two two, Three three, Four four);
    }

    @FunctionalInterface
    interface Function5<One, Two, Three, Four, Five,Six> {
        public Six apply(One one, Two two, Three three, Four four, Five five);
    }

}
