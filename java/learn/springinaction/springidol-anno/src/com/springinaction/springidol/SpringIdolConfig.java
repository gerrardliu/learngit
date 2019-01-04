package com.springinaction.springidol;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.ArrayList;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan
public class SpringIdolConfig {

    @Bean
    public Performer duke() {
        return new PoeticJuggler(15, sonnet29());
    }

    @Bean
    public Performer kenny() {
        Instrumentalist kenny = new Instrumentalist();
        kenny.setSong("Jingle Bells");
        kenny.setInstrument(saxophone());
        return kenny;
    }

    @Bean
    public Performer benny() {
        Instrumentalist benny = new Instrumentalist();
        benny.setSong("Love");
        benny.setInstrument(piano());
        return benny;
    }

    @Bean
    public Performer hank() {
        OneManBand hank = new OneManBand();
        ArrayList<Instrument> instruments = new ArrayList<Instrument>();
        instruments.add(saxophone());
        instruments.add(piano());
        hank.setInstruments(instruments);
        return hank;
    }

    @Bean
    public Poem sonnet29() {
        return new Sonnet29();
    }

    @Bean
    public Instrument saxophone() {
        return new Saxophone();
    }

    @Bean
    public Instrument piano() {
        return new Piano();
    }

    @Bean
    public Audience audience()  { return new Audience(); }

    @Bean
    public Magician magician() { return new Magician(); }

    @Bean
    public Volunteer volunteer() { return new Volunteer(); }
}

