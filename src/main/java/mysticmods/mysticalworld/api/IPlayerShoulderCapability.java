package mysticmods.mysticalworld.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public interface IPlayerShoulderCapability {
  CompoundNBT getAnimalSerialized();

  boolean isShouldered();

  ResourceLocation getRegistryName();

  @Nullable
  EntityType<?> getEntityType(ResourceLocation registryName);

  @Nullable
  EntityType<?> getEntityType();

  void drop();

  void shoulder(Entity entity);

  CompoundNBT writeNBT();

  CompoundNBT generateShoulderNBT();

  void readNBT(CompoundNBT incoming);
}
