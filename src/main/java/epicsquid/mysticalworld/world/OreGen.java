package epicsquid.mysticalworld.world;

import epicsquid.mysticallib.world.DimensionalOreFeature;
import epicsquid.mysticallib.world.OreGenerator;
import epicsquid.mysticallib.world.OreProperties;
import epicsquid.mysticalworld.config.ConfigManager;
import epicsquid.mysticalworld.config.OreConfig;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;

import java.util.ArrayList;
import java.util.List;

public class OreGen {

  private static List<OreGenerator> generators = new ArrayList<>();

  public static void registerOreGeneration() {
    ConfigManager.ORE_CONFIG.stream().filter(OreConfig::shouldRegister).forEach(ore -> {
      generators.add(
          new OreGenerator(
              new OreProperties(
                  () -> new DimensionalOreFeature(Feature.ORE.withConfiguration(new OreFeatureConfig(
                      OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                      ore.getOre().getDefaultState(),
                      ore.getChance())), DimensionType.OVERWORLD),
                  new CountRangeConfig(
                      ore.getSize(),
                      ore.getMinY(),
                      0,
                      ore.getMaxY() - ore.getMinY())
              )
          )
      );
    });

    generators.forEach(OreGenerator::init);
  }
}
