package epicsquid.mysticalworld.loot.conditions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import epicsquid.mysticalworld.entity.EntityLavaCat;
import net.minecraft.entity.Entity;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

import java.util.Random;

public class IsObsidian implements LootCondition {
  private final boolean inverse;

  public IsObsidian(boolean inverseIn) {
    this.inverse = inverseIn;
  }

  @Override
  public boolean testCondition(Random rand, LootContext context) {
    boolean flag;
    Entity looted = context.getLootedEntity();
    if (looted instanceof EntityLavaCat) {
      EntityLavaCat cat = (EntityLavaCat) looted;
      flag = !cat.getIsLava();
    } else {
      flag = false;
    }
    return flag == !this.inverse;
  }

  public static class Serializer extends LootCondition.Serializer<IsObsidian> {
    public Serializer() {
      super(new ResourceLocation("is_obsidian"), IsObsidian.class);
    }

    @Override
    public void serialize(JsonObject json, IsObsidian value, JsonSerializationContext context) {
      json.addProperty("inverse", value.inverse);
    }

    @Override
    public IsObsidian deserialize(JsonObject json, JsonDeserializationContext context) {
      return new IsObsidian(JsonUtils.getBoolean(json, "inverse", false));
    }
  }
}

