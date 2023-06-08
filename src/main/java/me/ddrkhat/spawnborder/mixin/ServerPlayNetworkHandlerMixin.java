package me.ddrkhat.spawnborder.mixin;

import me.ddrkhat.spawnborder.events.callbacks.MoveCallback;
import net.minecraft.entity.Entity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.stream.Collectors;

import static me.ddrkhat.spawnborder.SpawnBorder.playersInSpawn;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin
{
    @Shadow public abstract ServerPlayerEntity getPlayer();

    @Inject(method = "onPlayerMove(Lnet/minecraft/network/packet/c2s/play/PlayerMoveC2SPacket;)V",at = @At("TAIL"))
    private void onPlayerMove(PlayerMoveC2SPacket packet, CallbackInfo ci)
    {
        //Grab player so we can determine if they are in the border.
        MoveCallback.EVENT.invoker().onPlayerMoveEvent(this.getPlayer());
    }

    @Inject(method = "onVehicleMove(Lnet/minecraft/network/packet/c2s/play/VehicleMoveC2SPacket;)V",at = @At("HEAD"))
    private void onVehicleMove(VehicleMoveC2SPacket packet, CallbackInfo ci)
    {
        // On movement packet, make sure the player has a vehicle.
        if(this.getPlayer().getRootVehicle()!=null)
        {
            //Fetch all PLAYER entities in it
            for(Entity riders:this.getPlayer().getRootVehicle().getPassengerList().stream().filter(Entity::isPlayer).collect(Collectors.toList()))
            {
                //Send their coordinate data to update their listings.
                MoveCallback.EVENT.invoker().onPlayerMoveEvent((ServerPlayerEntity)riders);
            }
        }
    }

    @Inject(method = "onDisconnected(Lnet/minecraft/text/Text;)V",at = @At("HEAD"))
    private void onDisconnect(Text reason, CallbackInfo ci)
    {
        //If they disconnect, remove them from the list (they'll get re-added when they rejoin anyway)
        playersInSpawn.remove(this.getPlayer().getUuid());
    }

}
