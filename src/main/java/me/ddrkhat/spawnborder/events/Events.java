package me.ddrkhat.spawnborder.events;

import me.ddrkhat.spawnborder.events.callbacks.MoveCallback;

public class Events
{
    public static void init()
    {
        MoveCallback.EVENT.register(onPlayerMove::onPlayerMoveEvent);
    }
}
