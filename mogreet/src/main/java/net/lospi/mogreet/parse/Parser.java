package net.lospi.mogreet.parse;

import net.lospi.exception.MogreetException;
import org.w3c.dom.Document;

public interface Parser<T> {
    T parse(Document xmlDoc) throws MogreetException;
}
