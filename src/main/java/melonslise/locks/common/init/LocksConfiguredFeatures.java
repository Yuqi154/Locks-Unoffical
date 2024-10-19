package melonslise.locks.common.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public final class LocksConfiguredFeatures
{
	public static final ConfiguredFeature<?, ?> CONFIGURED_LOCK_CHESTS = add("lock_chests", LocksFeatures.LOCK_CHESTS.get().configured(FeatureConfiguration.NONE).decorated(LocksConfiguredPlacements.CONFIGURED_CHEST));

	private LocksConfiguredFeatures() {}

	public static void addTo(BiomeLoadingEvent e)
	{
		e.getGeneration().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, LocksConfiguredFeatures.CONFIGURED_LOCK_CHESTS);
	}

	public static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> add(String name, ConfiguredFeature<FC, ?> cf)
	{
		return Registry.register(BuiltInRegistries.CONFIGURED_FEATURE, name, cf);
	}
}