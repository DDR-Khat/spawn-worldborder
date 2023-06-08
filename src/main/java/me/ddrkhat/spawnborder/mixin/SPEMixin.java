package me.ddrkhat.spawnborder.mixin;

import com.mojang.authlib.GameProfile;
import me.ddrkhat.spawnborder.events.callbacks.MoveCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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
        //If we teleport, remove from the list, we can re-check when they move again.
        if(inOverworldOrNether(this.getWorld().getDimensionKey())) playersInSpawn.remove(this.getUuid());
    }

    @Inject(method = "onDeath(Lnet/minecraft/entity/damage/DamageSource;)V",at = @At("HEAD"))
    private void onDeath(DamageSource damageSource, CallbackInfo ci)
    {
        //If we die, remove from the list, we can re-check when they move again.
        playersInSpawn.remove(this.getUuid());
    }

}
