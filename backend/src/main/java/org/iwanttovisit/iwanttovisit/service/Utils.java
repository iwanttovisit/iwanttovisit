package org.iwanttovisit.iwanttovisit.service;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Utils {

    public static String modifyFTS(
            final String query
    ) {
        return Arrays.stream(query.split("\\s+"))
                .map(s -> s + ":*")
                .collect(Collectors.joining(" & "));
    }

}
