package ru.javaops.masterjava.export.helpers;

import lombok.Value;

/**
 * Created by Alejandro on 02.04.2017.
 */
@Value
public class FailedIndex
{
    public String emailOrRange;
    public String reason;

    @Override
    public String toString() {
        return emailOrRange + " : " + reason;
    }
}
