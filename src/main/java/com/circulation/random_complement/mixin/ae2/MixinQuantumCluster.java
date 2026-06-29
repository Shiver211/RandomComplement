package com.circulation.random_complement.mixin.ae2;

import appeng.me.cache.helpers.ConnectionWrapper;
import appeng.me.cluster.implementations.QuantumCluster;
import com.circulation.random_complement.common.interfaces.RCQuantumCluster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = QuantumCluster.class, remap = false)
public abstract class MixinQuantumCluster implements RCQuantumCluster {

    @Shadow
    private ConnectionWrapper connection;

    @Unique
    @Override
    public boolean r$noWork() {
        return this.connection == null || this.connection.getConnection() == null;
    }

}