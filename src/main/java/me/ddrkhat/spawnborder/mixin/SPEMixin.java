package me.ddrkhat.spawnborder.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.ddrkhat.spawnborder.SpawnBorder.inOverworldOrNether;
import static me.ddrkhat.spawnborder.SpawnBorder.playersInSpawn;

@Mixin(ServerPlayerEntity.class)
public abstract class SPEMixin extends PlayerEntity
{
    public SPEMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "moveToWorld(Lnet/minecraft/server/world/ServerWorld;)Lnet/minecraft/entity/Entity;",at = @At("HEAD"))
    private void onPlayerTeleport(ServerWorld destination, CallbackInfoReturnable<Entity> cir)
    {
        if(inOverworldOrNether(this.getWorld().getDimensionKey())) playersInSpawn.remove(this.getUuid());
    }
}
