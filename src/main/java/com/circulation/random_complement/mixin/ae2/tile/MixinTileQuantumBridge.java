package com.circulation.random_complement.mixin.ae2.tile;

import appeng.me.cluster.implementations.QuantumCluster;
import appeng.tile.grid.AENetworkInvTile;
import appeng.tile.qnb.TileQuantumBridge;
import appeng.util.Platform;
import com.circulation.random_complement.common.interfaces.RCQuantumCluster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TileQuantumBridge.class, remap = false)
public abstract class MixinTileQuantumBridge extends AENetworkInvTile {

    @Shadow
    private QuantumCluster cluster;

    @Inject(method = "isPowered", at = @At("HEAD"), cancellable = true)
    public void isPoweredI(CallbackInfoReturnable<Boolean> cir) {
        if (!Platform.isClient()) {
            if (this.cluster == null) {
                cir.setReturnValue(Boolean.TRUE);
            } else if (((RCQuantumCluster) this.cluster).r$noWork()) {
                cir.setReturnValue(Boolean.TRUE);
            }
        }
    }

}