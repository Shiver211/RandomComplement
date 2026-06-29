package com.circulation.random_complement.common.integration.ae2;

import com.circulation.random_complement.RCConfig;
import com.circulation.random_complement.RandomComplement;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.Loader;

public final class AEIntegrations {

    public static final AEIntegration INSTANCE = createDefault();

    private AEIntegrations() {
        throw new IllegalStateException("Utility class");
    }

    private static AEIntegration createDefault() {
        ConfigManager.sync(RandomComplement.MOD_ID, Config.Type.INSTANCE);
        return new AEIntegrationImpl(Loader::isModLoaded, RCConfig.AE2.Enable);
    }
}
