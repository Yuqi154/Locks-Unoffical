package melonslise.locks.common.init;

import melonslise.locks.Locks;
import melonslise.locks.common.worldgen.ChestPlacement;
import melonslise.locks.common.worldgen.LockChestsFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class LocksPlacements
{
	public static final DeferredRegister<Feature<?>> PLACEMENTS = DeferredRegister.create(ForgeRegistries.FEATURES, Locks.ID);

	public static final RegistryObject<Feature<NoneFeatureConfiguration>>
		CHEST = add("chest", new LockChestsFeature(NoneFeatureConfiguration.CODEC));

	private LocksPlacements() {}

	public static void register()
	{
		PLACEMENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	public static <T extends FeatureConfiguration> RegistryObject<Feature<T>> add(String name, Feature<T> pl)
	{
		return PLACEMENTS.register(name, () -> pl);
	}
}