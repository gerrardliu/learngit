package com.springinaction.springidol;

import java.util.Collection;

public class OneManBand implements Performer {

    public OneManBand() {
    }

    private Collection<Instrument> instruments;

    public Collection<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(Collection<Instrument> instruments) {
        this.instruments = instruments;
    }

    @Override
    public void perform() {
        for (Instrument instrument : instruments) {
            instrument.play();
        }
    }
}
