package com.circulation.random_complement.common.integration.ae2;

/**
 * Centralized AE ecosystem state queries.
 * <p>
 * The root switch combines the AE2 loaded state and the config switch so callers
 * can make one decision and avoid scattering special-case checks across the mod.
 */
public interface AEIntegration {

    /**
     * Root switch for the whole AE ecosystem.
     *
     * @return true when AE-related features are allowed to initialize
     */
    boolean isEnabled();

    /**
     * AE2 Fluid Craft support state.
     *
     * @return true when AE2FC-specific features may initialize
     */
    boolean isAe2fcEnabled();

    /**
     * Extended Crafting Terminals for AE2 support state.
     *
     * @return true when ae2exttable-specific features may initialize
     */
    boolean isAe2ExtTableEnabled();

    /**
     * NAE2 support state.
     *
     * @return true when NAE2-specific features may initialize
     */
    boolean isNae2Enabled();

    /**
     * ExtendedAE support state.
     *
     * @return true when ExtendedAE-specific features may initialize
     */
    boolean isExtendedAeEnabled();

    /**
     * Not Enough Energistics support state.
     *
     * @return true when NEE-specific features may initialize
     */
    boolean isNeeEnabled();

    /**
     * Thaumic Energistics support state.
     *
     * @return true when Thaumic Energistics-specific features may initialize
     */
    boolean isThaumicEnergisticsEnabled();

    /**
     * Lazy AE / ThrEng support state.
     *
     * @return true when ThrEng-specific features may initialize
     */
    boolean isThrEngEnabled();
}
