package me.ddrkhat.spawnborder;

import me.ddrkhat.spawnborder.events.Events;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
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
	public static final int chunkSize = 16; // Size of chunks (Just in case they change in the future)
	public static final int borderOverhang = 8; // How many blocks beyond the max (so they see it but can still pass through our fake border)
	public static final int extraChunkCount = 10; // Count THIS many checks beyond the ticket count, because of lazy chunk etc.
	public static WorldBorder fakeWorldBorder = new WorldBorder(), fakeNetherBorder = new WorldBorder();
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
	 * @return TRUE if we believe they are in spawn area. FALSE otherwise.
	*/
	public static boolean inSpawnChunks(ServerPlayerEntity player)
	{
		RegistryKey<DimensionType> dimension = player.getWorld().getDimensionKey();
		if(dimension.equals(DimensionTypes.OVERWORLD)) return player.getX() >= spawnArea.getStartX() && player.getX() <= spawnArea.getEndX() && player.getZ() >= spawnArea.getStartZ() && player.getZ() <= spawnArea.getEndZ();
		else return player.getX() >= (spawnArea.getStartX()/(float)8) && player.getX() <= (spawnArea.getEndX()/(float)8) && player.getZ() >= (spawnArea.getStartZ()/(float)8) && player.getZ() <= (spawnArea.getEndZ()/(float)8);
	}

	/**
	 * A simple boolean check for readability
	 * @param dimension the entity's dimension key.
	 * @return TRUE if they are in "OVERWORLD" or "NETHER", otherwise FALSE.
	 */
	public static boolean inOverworldOrNether(RegistryKey<DimensionType> dimension) { return dimension.equals(DimensionTypes.OVERWORLD) || dimension.equals(DimensionTypes.THE_NETHER); }
}
