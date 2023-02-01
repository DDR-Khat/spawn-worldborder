package me.ddrkhat.spawnborder.mixin;

import me.ddrkhat.spawnborder.events.callbacks.MoveCallback;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin
{
    @Shadow public abstract ServerPlayerEntity getPlayer();
    @Shadow public abstract ClientConnection getConnection();

    @Inject(method = "onPlayerMove(Lnet/minecraft/network/packet/c2s/play/PlayerMoveC2SPacket;)V",at = @At("TAIL"))
    private void onPlayerMove(PlayerMoveC2SPacket packet, CallbackInfo ci)
    {
        //Grab player and connection from the packet so we can use it for determining if they are in the border.
        MoveCallback.EVENT.invoker().onPlayerMoveEvent(this.getPlayer(),this.getConnection());
    }

    @Inject(method = "onVehicleMove(Lnet/minecraft/network/packet/c2s/play/VehicleMoveC2SPacket;)V",at = @At("HEAD"))
    private void onVehicleMove(VehicleMoveC2SPacket packet, CallbackInfo ci)
    {
        //Grab player + connection from a player's vehicle movement packet.
        MoveCallback.EVENT.invoker().onPlayerMoveEvent(this.getPlayer(),this.getConnection());
    }

}
