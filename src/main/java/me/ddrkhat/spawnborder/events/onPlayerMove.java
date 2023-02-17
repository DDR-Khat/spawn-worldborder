package me.ddrkhat.spawnborder.events;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.s2c.play.WorldBorderInitializeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.dimension.DimensionTypes;

import java.util.Objects;

import static me.ddrkhat.spawnborder.SpawnBorder.*;

public class onPlayerMove
{
    public static void onPlayerMoveEvent(ServerPlayerEntity player, ClientConnection connection)
    {
        if(!inSpawnChunks(player)) // If we're not in spawn
        {
            if(!playersInSpawn.contains(player.getUuid())) return; // If we weren't before, cease. (Nothing's changed)
            if(!inOverworldOrNether(player.getWorld().getDimensionKey())) return; // If we aren't in the Overworld/Nether, cease.
            playersInSpawn.remove(player.getUuid()); // Otherwise we were, take outselves out of the list of people in spawn.
            // And send the player the REAL world border.
            connection.send(new WorldBorderInitializeS2CPacket(Objects.requireNonNull(player.getServer()).getOverworld().getWorldBorder()));
        }
        else // Otherwise, we ARE in spawn.
        {
            if(playersInSpawn.contains(player.getUuid())) return; // If we were already, cease. (Nothing's changed)
            if(!inOverworldOrNether(player.getWorld().getDimensionKey())) return; // If we aren't in the Overworld/Nether, cease.
            playersInSpawn.add(player.getUuid()); // Add ourselves to the "in spawn" list.
            // And send the player a FAKE world border, set to Spawn Chunk's size (plus a bit extra).
            if(player.getWorld().getDimensionKey().equals(DimensionTypes.OVERWORLD)) connection.send(new WorldBorderInitializeS2CPacket(fakeWorldBorder));
            else connection.send(new WorldBorderInitializeS2CPacket(fakeNetherBorder));
        }
    }
}
