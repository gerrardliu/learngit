package com.springinaction.springidol;

public class Saxophone implements Instrument {

    public Saxophone() {
    }

    @Override
    public void play() {
        System.out.println("playing saxophone");
    }
}
