package me.ddrkhat.spawnborder.mixin;

import me.ddrkhat.spawnborder.events.callbacks.MoveCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(Entity.class)
public abstract class EntityMixin
{
    @Shadow public abstract boolean hasPassengers();

    @Shadow public abstract List<Entity> getPassengerList();

    @Inject(method = "move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V",at = @At("HEAD"))
    private void onMove(MovementType movementType, Vec3d movement, CallbackInfo ci)
    {
        // Check that the entity has a passenger. (So we only focus on "vehicles")
        if(!this.hasPassengers()) return;
        // Filter all passengers to only PLAYERS (AI doesn't need to see a fake border)
        for(Entity riders:this.getPassengerList().stream().filter(Entity::isPlayer).collect(Collectors.toList()))
        {
            // update their data in our records.
            MoveCallback.EVENT.invoker().onPlayerMoveEvent((ServerPlayerEntity)riders);
        }
    }
}
