package com.springinaction.springidol;

public class Instrumentalist implements Performer {

    private String song;
    private Instrument instrument;

    public Instrumentalist() {
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSong() {
        return song;
    }

    public String screamSong() {
        return song;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    @Override
    public void perform() {
        System.out.print("Singing " + song + " : ");
        instrument.play();
    }
}
