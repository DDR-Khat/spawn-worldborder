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
        spawnArea.setStartX(pos.getStartX()-(radius*chunkSize)); // Store Lowest X
        spawnArea.setStartZ(pos.getStartZ()-(radius*chunkSize)); // Store Lowest Z
        spawnArea.setEndX(pos.getEndX()+(radius*chunkSize)); // Store Highest X
        spawnArea.setEndZ(pos.getEndZ()+(radius*chunkSize)); // Store Highest Z
        fakeWorldBorder.setCenter(pos.getCenterX(),pos.getCenterZ()); // Position FakeBorder @ Spawn's centered chunk block position.
        fakeWorldBorder.setSize(Math.max(spawnArea.getxDifference(), spawnArea.getzDifference())+borderOverhang); // Set the size of the border to the length of X or Z, plus overhang so the player shouldn't collide with it.
    }
}
