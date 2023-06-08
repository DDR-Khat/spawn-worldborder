package me.ddrkhat.spawnborder.mixin;

import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.ddrkhat.spawnborder.SpawnBorder.*;

@Mixin(ServerChunkManager.class)
public abstract class ServerChunkManagerMixin
{
    @Inject(method = "addTicket(Lnet/minecraft/server/world/ChunkTicketType;Lnet/minecraft/util/math/ChunkPos;ILjava/lang/Object;)V",at = @At("HEAD"))
    private <T> void onAddTicket(ChunkTicketType<T> ticketType, ChunkPos pos, int radius, T argument, CallbackInfo ci)
    {
        if(ticketType != ChunkTicketType.START) return; // Cancel if not what we think is a SpawnChunk request
        spawnArea.setStartX((pos.getStartX()-(radius*chunkSize))-(extraChunkCount*chunkSize)); // Store Lowest X + Bonus
        spawnArea.setStartZ((pos.getStartZ()-(radius*chunkSize))-(extraChunkCount*chunkSize)); // Store Lowest Z + Bonus
        spawnArea.setEndX((pos.getEndX()+(radius*chunkSize))+(extraChunkCount*chunkSize)); // Store Highest X + Bonus
        spawnArea.setEndZ((pos.getEndZ()+(radius*chunkSize))+(extraChunkCount*chunkSize)); // Store Highest Z + Bonus
        fakeWorldBorder.setCenter(pos.getCenterX(),pos.getCenterZ()); // Position FakeBorder @ Spawn's centered chunk block position.
        fakeNetherBorder.setCenter(pos.getCenterX(),pos.getCenterZ()); // Position FakeBorder @ Nether "Spawn"'s centered chunk block position.
        fakeWorldBorder.setSize(Math.max(spawnArea.getxDifference(), spawnArea.getzDifference())+borderOverhang); // Set the size of the border to the length of X or Z, plus overhang so the player shouldn't collide with it.
        fakeNetherBorder.setSize((Math.max(spawnArea.getxDifference(), spawnArea.getzDifference())/(float)8)+borderOverhang); // Set the size of the border to the length of X or Z, plus overhang so the player shouldn't collide with it.
    }
}
