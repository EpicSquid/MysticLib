package epicsquid.mysticalworld.setup;

import com.google.common.collect.Sets;
import epicsquid.mysticalworld.MysticalWorld;
import epicsquid.mysticalworld.api.IPlayerShoulderCapability;
import epicsquid.mysticalworld.capability.AnimalCooldownCapability;
import epicsquid.mysticalworld.capability.AnimalCooldownCapabilityStorage;
import epicsquid.mysticalworld.capability.PlayerShoulderCapability;
import epicsquid.mysticalworld.capability.PlayerShoulderCapabilityStorage;
import epicsquid.mysticalworld.events.*;
import epicsquid.mysticalworld.init.ModBlocks;
import epicsquid.mysticalworld.init.ModCompost;
import epicsquid.mysticalworld.init.ModEntities;
import epicsquid.mysticalworld.init.ModFeatures;
import epicsquid.mysticalworld.network.Networking;
import epicsquid.mysticalworld.potions.PotionRecipes;
import epicsquid.mysticalworld.world.OreGen;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

import java.util.Arrays;
import java.util.Set;

public class CommonSetup {
  public CommonSetup() {
  }

  public void init(FMLCommonSetupEvent event) {
    ModCompost.init();
    ModEntities.registerEntities();
    Networking.INSTANCE.registerMessages();
    CapabilityManager.INSTANCE.register(AnimalCooldownCapability.class, new AnimalCooldownCapabilityStorage(), AnimalCooldownCapability::new);
    CapabilityManager.INSTANCE.register(IPlayerShoulderCapability.class, new PlayerShoulderCapabilityStorage(), PlayerShoulderCapability::new);

    OreGen.registerOreGeneration();
    PotionRecipes.registerRecipes();

/*    try {
      ModifyWaterCap.modify();
    } catch (IllegalAccessException e) {
      MysticalWorld.LOG.error("Unable to modify water cap", e);
    }*/

    Set<Block> validBlocks = ObfuscationReflectionHelper.getPrivateValue(TileEntityType.class, TileEntityType.SKULL, "field_223046_I");
    if (validBlocks != null) {
      Set<Block> newValidBlocks = Sets.newHashSet(validBlocks);
      newValidBlocks.addAll(Arrays.asList(ModBlocks.DROWNED_HEAD.get(), ModBlocks.DROWNED_WALL_HEAD.get(), ModBlocks.HUSK_HEAD.get(), ModBlocks.HUSK_WALL_HEAD.get(), ModBlocks.STRAY_HEAD.get(), ModBlocks.STRAY_WALL_HEAD.get(), ModBlocks.PILLAGER_HEAD.get(), ModBlocks.PILLAGER_WALL_HEAD.get(), /*ModBlocks.WITCH_HEAD.get(), ModBlocks.WITCH_WALL_HEAD.get(), */ModBlocks.ZOMBIE_PIGMAN_HEAD.get(), ModBlocks.ZOMBIE_PIGMAN_WALL_HEAD.get(), /*ModBlocks.ENDERMAN_HEAD.get(), ModBlocks.ENDERMAN_WALL_HEAD.get(), */ModBlocks.VILLAGER_HEAD.get(), ModBlocks.VILLAGER_WALL_HEAD.get(), ModBlocks.ZOMBIE_VILLAGER_HEAD.get(), ModBlocks.ZOMBIE_VILLAGER_WALL_HEAD.get()));
      ObfuscationReflectionHelper.setPrivateValue(TileEntityType.class, TileEntityType.SKULL, newValidBlocks, "field_223046_I");
    } else {
      MysticalWorld.LOG.error("Unable to replace valid blocks for SkullTileEntity.");
    }

  }

  public void loadComplete (FMLLoadCompleteEvent event) {
    ModFeatures.loadComplete();
  }

  public void serverStarting(FMLServerStartedEvent event) {
/*    try {
      ModifyWaterCap.modifySquid();
    } catch (IllegalAccessException e) {
      MysticalWorld.LOG.error("Unable to modify squid spawn cap", e);
    }*/
  }

  public void serverAboutToStart(FMLServerAboutToStartEvent event) {
    ModifyLoot.modify();
  }

  @SuppressWarnings("Duplicates")
  public void registerListeners() {
    MinecraftForge.EVENT_BUS.addListener(LeafHandler::onBlockDrops);
    MinecraftForge.EVENT_BUS.addListener(DamageHandler::onAttackDamage);
    MinecraftForge.EVENT_BUS.addListener(EntityHandler::onEntityInteract);
    MinecraftForge.EVENT_BUS.addListener(ShoulderHandler::onDeath);
    MinecraftForge.EVENT_BUS.addListener(ShoulderHandler::onRightClickBlock);
    MinecraftForge.EVENT_BUS.addListener(LootHandler::onLootLoad);
    MinecraftForge.EVENT_BUS.addListener(LootHandler::onLooting);
    MinecraftForge.EVENT_BUS.addListener(CapabilityHandler::attachCapability);
    MinecraftForge.EVENT_BUS.addListener(CapabilityHandler::onSquidMilked);
    MinecraftForge.EVENT_BUS.addListener(CapabilityHandler::onPlayerJoin);

    DistExecutor.runWhenOn(Dist.CLIENT, () -> ClientSetup::registerListeners);
  }
}