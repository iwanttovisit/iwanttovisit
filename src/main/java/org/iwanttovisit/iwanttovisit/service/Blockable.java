package org.iwanttovisit.iwanttovisit.service;

import java.util.UUID;

public interface Blockable {

    void block(
            UUID id
    );

    void unblock(
            UUID id
    );

}
