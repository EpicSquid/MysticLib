package epicsquid.mysticalworld.block;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.block.BlockCropBase;
import epicsquid.mysticalworld.init.ModItems;
import net.minecraft.item.Item;
import net.minecraftforge.common.EnumPlantType;

public class BlockDewgoniaCrop extends BlockCropBase {

  public BlockDewgoniaCrop(@Nonnull String name, @Nonnull EnumPlantType plantType) {
    super(name, plantType);
  }

  /**
   * Gets the seed to drop for the crop
   */
  @Override
  @Nonnull
  public Item getSeed() {
    return ModItems.dewgonia;
  }

  /**
   * Gets the crop to drop for the plant
   */
  @Override
  @Nonnull
  public Item getCrop() {
    return ModItems.dewgonia;
  }

}
