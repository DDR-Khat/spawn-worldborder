package me.ddrkhat.spawnborder.events.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.network.ServerPlayerEntity;

public interface MoveCallback
{
    Event<MoveCallback> EVENT = EventFactory.createArrayBacked(MoveCallback.class, listeners -> (player, connection) ->
        {
            for(MoveCallback event : listeners)
            {
                event.onPlayerMoveEvent(player,connection);
            }
        });

    void onPlayerMoveEvent(ServerPlayerEntity player, ClientConnection connection);
}
