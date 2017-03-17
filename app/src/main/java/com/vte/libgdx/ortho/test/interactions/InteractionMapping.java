package com.vte.libgdx.ortho.test.interactions;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vincent on 13/02/2017.
 */

public class InteractionMapping {
    public String id;
    public String template;
    public boolean isPersistent;
    public ArrayList<InteractionEventAction> eventsAction;
    public ArrayList<InteractionEvent> outputEvents;
    public HashMap properties;

}
