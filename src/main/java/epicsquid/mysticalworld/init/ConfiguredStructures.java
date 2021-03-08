package epicsquid.mysticalworld.init;

import epicsquid.mysticalworld.MysticalWorld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import noobanidus.libs.noobutil.registry.ConfiguredRegistry;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ConfiguredStructures {
  public static final ConfiguredRegistry<StructureFeature<?, ?>> REGISTRY = new ConfiguredRegistry<>(MysticalWorld.MODID, WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE);

  public static Map<Structure<?>, StructureFeature<?, ?>> CONFIGURED_STRUCTURES = new HashMap<>();

  public static StructureFeature<?, ?> CONFIGURED_HUT = register("hut", ModStructures.HUT_STRUCTURE, ModStructures.HUT_STRUCTURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
  public static StructureFeature<?, ?> CONFIGURED_BARROW = register("barrow", ModStructures.BARROW_STRUCTURE, ModStructures.BARROW_STRUCTURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
  public static StructureFeature<?, ?> CONFIGURED_RUINED_HUT = register("ruined_hut", ModStructures.RUINED_HUT_STRUCTURE, ModStructures.RUINED_HUT_STRUCTURE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));

  private static StructureFeature<?, ?> register(String id, @Nullable Structure<?> structure, StructureFeature<?, ?> feature) {
    StructureFeature<?, ?> result = REGISTRY.register(id, feature);
    CONFIGURED_STRUCTURES.put(structure, result);

    return result;
  }

  public static void registerStructures() {
    CONFIGURED_STRUCTURES.forEach(FlatGenerationSettings.STRUCTURES::put);
  }
}
