package me.ddrkhat.spawnborder;

import me.ddrkhat.spawnborder.events.Events;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.border.WorldBorder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.UUID;

public class SpawnBorder implements DedicatedServerModInitializer
{
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "spawnborder";
	public static ChunkArea spawnArea = new ChunkArea();
	public static final int chunkSize = 16;
	public static final int borderOverhang = 4;
	public static WorldBorder fakeWorldBorder = new WorldBorder();
	public static ArrayList<UUID> playersInSpawn = new ArrayList<>();

	public static void log(Level level, String message)
	{
		LOGGER.log(level, "["+MOD_ID+"] " + message);
	}

	@Override
	public void onInitializeServer()
	{
		log(Level.INFO,"Initializing");
		Events.init();
	}

	/** Try to evaluate if a player is in where we believe Spawn Chunks are.
	 * @param player The player's entity on the server
	 * @return TRUE if we believe they are in spawn area. False otherwise.
	*/
	public static boolean inSpawnChunks(ServerPlayerEntity player) { return player.getX() >= spawnArea.getStartX() && player.getX() <= spawnArea.getEndX() && player.getZ() >= spawnArea.getStartZ() && player.getZ() <= spawnArea.getEndZ(); }
}
