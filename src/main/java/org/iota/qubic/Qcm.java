package org.iota.qubic;

import java.util.function.Consumer;

public class Qcm {
    /*
        Messages to Environment 0:
        - Attach (below me)
        - Attach (parallel to me)
        - Detach (below me)
        - Detach (parallel to me)
        - Decay (me)
     */
    public static class Environment {
        String id;
        public Environment () {}
        public Environment (String myId) { id = myId;}
    }
    public static class Effect {
        byte[] value;
    }
    public static class Entity {
        enum Ability {
            ABILITY_NONE,
            ABILITY_VM,
            ABILITY_ATTACH,
        }
        Ability ability;
        Consumer<Effect> inputs[];
        Effect[] outputs;
    }
}
