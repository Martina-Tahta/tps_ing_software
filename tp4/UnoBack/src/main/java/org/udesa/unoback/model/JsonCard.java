package org.udesa.unoback.model;

import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class JsonCard {
    String color;
    Integer number;
    String type;
    boolean shout;

    public JsonCard() {}
    public JsonCard( String color, Integer number, String type, boolean shout ) {
        //if color not ["Red", "Blue", "Yellow", "Green"]
        this.color = color;

        // if number not in [0 .. 9]
        this.number = number;

        //if type not [types que hay]
        this.type = type;

        // if type not true o false --> creo que esto ya lo controla porque espera un boolean
        this.shout = shout;
    }

    public String toString() {
        return "{ " +
               "\"color\": \"" + color + "\", " +
               "\"number\": \"" + number + "\", " +
               "\"type\": \"" + type + "\", " +
               "\"shout\": \"" + shout + "\" " +
               "}";
    }

    @SneakyThrows public Card asCard() {
        return (Card)Class.forName( "org.udesa.unoback.model." + type )
                .getMethod( "asCard", getClass() )
                .invoke( getClass(), this );
    }
}

