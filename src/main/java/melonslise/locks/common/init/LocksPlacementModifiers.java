package melonslise.locks.common.init;

import melonslise.locks.Locks;
import melonslise.locks.common.worldgen.RightChestFilter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class LocksPlacementModifiers {
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, Locks.ID);

    public static RegistryObject<PlacementModifierType<RightChestFilter>> RIGHT_CHEST_FILTER = PLACEMENT_MODIFIER.register("right_chest_filter", () -> () -> RightChestFilter.CODEC);

    public static void register(){
        PLACEMENT_MODIFIER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
