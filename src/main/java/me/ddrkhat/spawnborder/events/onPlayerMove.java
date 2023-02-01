package me.ddrkhat.spawnborder.events;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.s2c.play.WorldBorderInitializeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

import static me.ddrkhat.spawnborder.SpawnBorder.*;

public class onPlayerMove
{
    public static void onPlayerMoveEvent(ServerPlayerEntity player, ClientConnection connection)
    {
        if(!inSpawnChunks(player)) // If we're not in spawn
        {
            if(!playersInSpawn.contains(player.getUuid())) return; // If we weren't before, cease. (Nothing's changed)
            playersInSpawn.remove(player.getUuid()); // Otherwise we were, take outselves out of the list of people in spawn.
            // And send the player the REAL world border.
            connection.send(new WorldBorderInitializeS2CPacket(Objects.requireNonNull(player.getServer()).getOverworld().getWorldBorder()));
        }
        else // Otherwise, we ARE in spawn.
        {
            if(playersInSpawn.contains(player.getUuid())) return; // If we were already, cease. (Nothing's changed)
            playersInSpawn.add(player.getUuid()); // Add ourselves to the "in spawn" list.
            // And send the player a FAKE world border, set to Spawn Chunk's size (plus a bit extra).
            connection.send(new WorldBorderInitializeS2CPacket(fakeWorldBorder));
        }
    }
}
