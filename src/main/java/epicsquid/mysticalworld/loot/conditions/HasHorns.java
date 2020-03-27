package epicsquid.mysticalworld.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import epicsquid.mysticalworld.MysticalWorld;
import epicsquid.mysticalworld.entity.DeerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

public class HasHorns implements ILootCondition {
  private final boolean inverse;

  public HasHorns(boolean inverseIn) {
    this.inverse = inverseIn;
  }

  @Override
  public boolean test(LootContext lootContext) {
    boolean flag;
    Entity looted = lootContext.get(LootParameters.THIS_ENTITY);
    if (looted instanceof DeerEntity) {
      DeerEntity deer = (DeerEntity) looted;
      flag = deer.getDataManager().get(DeerEntity.hasHorns);
    } else {
      flag = false;
    }
    return flag == !this.inverse;
  }

  public static class Serializer extends ILootCondition.AbstractSerializer<HasHorns> {
    public Serializer() {
      super(new ResourceLocation(MysticalWorld.MODID, "has_horns"), HasHorns.class);
    }

    @Override
    public void serialize(JsonObject json, HasHorns value, JsonSerializationContext context) {
      json.addProperty("inverse", value.inverse);
    }

    @Override
    public HasHorns deserialize(JsonObject json, JsonDeserializationContext context) {
      return new HasHorns(JSONUtils.getBoolean(json, "inverse", false));
    }
  }

  private static HasHorns INSTANCE = new HasHorns(false);

  public static ILootCondition.IBuilder builder() {
    return () -> INSTANCE;
  }
}

