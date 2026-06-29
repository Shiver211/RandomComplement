package com.circulation.random_complement.mixin;

import com.circulation.random_complement.RCConfig;
import com.circulation.random_complement.common.integration.ae2.AEIntegration;
import com.circulation.random_complement.common.integration.ae2.AEIntegrations;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public class rcLateMixinLoader implements ILateMixinLoader {

    public static final Logger LOG = LogManager.getLogger("RC");
    public static final String LOG_PREFIX = "[RC]" + ' ';
    private final Map<String, BooleanSupplier> mixinConfigs;

    public rcLateMixinLoader() {
        this(AEIntegrations.INSTANCE, Loader::isModLoaded, rcLateMixinLoader::isClassPresent);
    }

    rcLateMixinLoader(AEIntegration aeIntegration, Predicate<String> modLoaded, Predicate<String> classPresent) {
        this.mixinConfigs = buildMixinConfigs(aeIntegration, modLoaded, classPresent);
    }

    private Map<String, BooleanSupplier> buildMixinConfigs(
        AEIntegration aeIntegration,
        Predicate<String> modLoaded,
        Predicate<String> classPresent
    ) {
        Map<String, BooleanSupplier> configs = new Object2ObjectLinkedOpenHashMap<>();
        if (aeIntegration.isEnabled()) {
            addMixinCFG(configs, "mixins.random_complement.ae2.json");
            addModdedMixinCFG(configs, modLoaded, "mixins.random_complement.ae2ctl.json", "ae2ctl");
            addModdedMixinCFG(configs, modLoaded, "mixins.random_complement.ae2.jei.json", "jei");
            if (aeIntegration.isAe2ExtTableEnabled()) {
                addMixinCFG(configs, "mixins.random_complement.ae2e.json");
            }
            if (aeIntegration.isNae2Enabled()) {
                addMixinCFG(configs, "mixins.random_complement.nae2.json");
            }
            if (aeIntegration.isExtendedAeEnabled()) {
                addMixinCFG(configs, "mixins.random_complement.extendedae.json");
            }
            if (aeIntegration.isNeeEnabled()) {
                addMixinCFG(configs, "mixins.random_complement.nee.new_patten_gui.json", () -> RCConfig.AE2.newPattenGui);
                addMixinCFG(configs, "mixins.random_complement.nee.json");
                addModdedMixinCFG(configs, modLoaded, "mixins.random_complement.nee.baubles.json", "baubles");
                if (aeIntegration.isAe2ExtTableEnabled()) {
                    addMixinCFG(configs, "mixins.random_complement.nee.ae2e.json");
                }
            }
            if (classPresent.test("github.kasuminova.mmce.common.tile.MEPatternProvider")) {
                addMixinCFG(configs, "mixins.random_complement.mmce.json");
            }
        }
        if (modLoaded.test("botania")) {
            addMixinCFG(configs, "mixins.random_complement.botania.json");
            addMixinCFG(configs, "mixins.random_complement.botaniverse.json",
                () -> RCConfig.Botania.FlowerLinkPool && modLoaded.test("botaniverse"));
        }
        if (aeIntegration.isThrEngEnabled()) {
            addMixinCFG(configs, "mixins.random_complement.threng.json", () -> RCConfig.LazyAE.EnableRepair);
        }
        if (aeIntegration.isAe2fcEnabled()) {
            addMixinCFG(configs, "mixins.random_complement.ae2fc.json");
            if (classPresent.test("com.glodblock.github.common.tile.TileFluidLevelMaintainer")) {
                addMixinCFG(configs, "mixins.random_complement.ae2fc.old.json");
            } else {
                addMixinCFG(configs, "mixins.random_complement.ae2fc.new.json");
            }
        }
        if (aeIntegration.isThaumicEnergisticsEnabled()) {
            addMixinCFG(configs, "mixins.random_complement.thaumicenergistics.json");
        }
        addModdedMixinCFG(configs, modLoaded, "mixins.random_complement.ic2.json", "ic2");
        addModdedMixinCFG(configs, modLoaded, "mixins.random_complement.te5.json", "thermalexpansion");
        addModdedMixinCFG(configs, modLoaded, "mixins.random_complement.ftbu.json", "ftbutilities");
        addModdedMixinCFG(configs, modLoaded, "mixins.random_complement.tf5.json", "thermalfoundation");
        addModdedMixinCFG(configs, modLoaded, "mixins.random_complement.cofhcore.json", "cofhcore");
        addModdedMixinCFG(configs, modLoaded, "mixins.random_complement.shulkertooltip.json", "shulkertooltip");
        addModdedMixinCFG(configs, modLoaded, "mixins.random_complement.fluxnetworks.json", "fluxnetworks");
        addModdedMixinCFG(configs, modLoaded, "mixins.random_complement.jeiu.json", "jeiutilities");
        addModdedMixinCFG(configs, modLoaded, "mixins.random_complement.de.json", "draconicevolution");
        addModdedMixinCFG(configs, modLoaded, "mixins.random_complement.jei.json", "jei");
        addModdedMixinCFG(configs, modLoaded, "mixins.random_complement.packagedauto.jei.json", "packagedauto", "jei");
        addModdedMixinCFG(configs, modLoaded, "mixins.random_complement.ftblib.json", "ftblib");
        addModdedMixinCFG(configs, modLoaded, "mixins.random_complement.enderutilities.ftblib.json", "enderutilities", "ftblib");
        return configs;
    }

    private static void addModdedMixinCFG(
        final Map<String, BooleanSupplier> configs,
        final Predicate<String> modLoaded,
        final String mixinConfig,
        final String modID
    ) {
        configs.put(mixinConfig, () -> modLoaded.test(modID));
    }

    private static void addModdedMixinCFG(
        final Map<String, BooleanSupplier> configs,
        final Predicate<String> modLoaded,
        final String mixinConfig,
        final String modID,
        final String... modIDs
    ) {
        configs.put(mixinConfig, () -> modLoaded.test(modID) && Arrays.stream(modIDs).allMatch(modLoaded));
    }

    private static void addMixinCFG(final Map<String, BooleanSupplier> configs, final String mixinConfig) {
        configs.put(mixinConfig, () -> true);
    }

    private static void addMixinCFG(
        final Map<String, BooleanSupplier> configs,
        final String mixinConfig,
        final BooleanSupplier conditions
    ) {
        configs.put(mixinConfig, conditions);
    }

    private static boolean isClassPresent(String className) {
        String classFilePath = className.replace('.', '/') + ".class";
        ClassLoader classLoader = rcLateMixinLoader.class.getClassLoader();
        return classLoader.getResource(classFilePath) != null;
    }

    @Override
    public List<String> getMixinConfigs() {
        return new ObjectArrayList<>(this.mixinConfigs.keySet());
    }

    @Override
    public boolean shouldMixinConfigQueue(final String mixinConfig) {
        BooleanSupplier supplier = this.mixinConfigs.get(mixinConfig);
        if (supplier == null) {
            LOG.warn(LOG_PREFIX + "Mixin config {} is not found in config map! It will never be loaded.", mixinConfig);
            return false;
        }
        return supplier.getAsBoolean();
    }
}
