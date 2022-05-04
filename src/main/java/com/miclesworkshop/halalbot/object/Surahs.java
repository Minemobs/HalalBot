package com.miclesworkshop.halalbot.object;

public class Surahs {

    private final String name;
    private final Ayahs[] ayahs;

    public Surahs(String name, Ayahs[] ayahs) {
        this.name = name;
        this.ayahs = ayahs;
    }

    public String getName() {
        return name;
    }

    public Ayahs[] getAyahs() {
        return ayahs;
    }
}
