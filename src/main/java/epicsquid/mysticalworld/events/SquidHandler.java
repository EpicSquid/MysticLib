package epicsquid.mysticalworld.events;

import epicsquid.mysticallib.capability.EntityCooldownCapability;
import epicsquid.mysticalworld.MysticalWorld;
import epicsquid.mysticalworld.capability.AnimalCooldownCapabilityProvider;
import epicsquid.mysticalworld.init.ModItems;
import epicsquid.mysticalworld.init.ModSounds;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MysticalWorld.MODID)
public class SquidHandler {
  @SubscribeEvent
  public static void onSquidMilked(PlayerInteractEvent.EntityInteract event) {
    EntityPlayer player = (EntityPlayer) event.getEntity();
    ItemStack heldItem = player.getHeldItem(event.getHand());
    if (!heldItem.isEmpty() && heldItem.getItem() instanceof ItemGlassBottle) {
      if (event.getTarget() instanceof EntitySquid) {
        event.setCanceled(true);
        event.setCancellationResult(EnumActionResult.SUCCESS);
        if (!event.getWorld().isRemote) {
          EntityCooldownCapability cap = event.getTarget().getCapability(AnimalCooldownCapabilityProvider.ANIMAL_COOLDOWN_CAPABILITY, null);
          if (cap != null) {
            if (cap.canHarvest()) {
              cap.setCooldown(20 * 15);
              event.getWorld().playSound(null, player.posX, player.posY, player.posZ, ModSounds.General.SQUID_MILK, SoundCategory.PLAYERS, 0.5F, event.getWorld().rand.nextFloat() * 0.25F + 0.6F);
              if (!player.isCreative()) heldItem.shrink(1);
              player.inventory.addItemStackToInventory(new ItemStack(ModItems.ink_bottle));
              return;
            }
          }
          player.sendStatusMessage(new TextComponentTranslation("message.squid.cooldown").setStyle(new Style().setColor(TextFormatting.BLUE).setBold(true)), true);
        }
      }
    }
  }
}
