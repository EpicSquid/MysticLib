package epicsquid.mysticalworld.entity;

import epicsquid.mysticalworld.MysticalWorld;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class EntityDeer extends EntityAnimal {
  public static final ResourceLocation LOOT_TABLE = new ResourceLocation(MysticalWorld.MODID, "entity/deer");

  public static final DataParameter<Boolean> hasHorns = EntityDataManager.createKey(EntityDeer.class, DataSerializers.BOOLEAN);

  public EntityDeer(@Nonnull World world) {
    super(world);
    setSize(1.0f, 1.0f);
    this.experienceValue = 3;
  }

  @Override
  protected void entityInit() {
    super.entityInit();
    this.getDataManager().register(hasHorns, rand.nextBoolean());
  }

  @Override
  protected void initEntityAI() {
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(1, new EntityAIPanic(this, 1.5D));
    this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
    this.tasks.addTask(3, new EntityAITempt(this, 1.25D, Items.WHEAT, false));
    this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
    this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    this.tasks.addTask(7, new EntityAILookIdle(this));
  }

  @Override
  public void onUpdate() {
    super.onUpdate();
    this.rotationYaw = this.rotationYawHead;
  }

  @Override
  public void setScaleForAge(boolean child) {
    this.setScale(child ? 0.5f : 1.0f);
  }

  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
  }

  @Override
  @Nonnull
  public EntityAgeable createChild(@Nonnull EntityAgeable ageable) {
    return new EntityDeer(ageable.world);
  }

  @Override
  @Nonnull
  public ResourceLocation getLootTable() {
    return LOOT_TABLE;
  }

  @Override
  public float getEyeHeight() {
    return this.isChild() ? this.height : 1.3F;
  }

  @Override
  public void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    getDataManager().set(hasHorns, compound.getBoolean("hasHorns"));
    getDataManager().setDirty(hasHorns);
  }

  @Override
  public void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    compound.setBoolean("hasHorns", getDataManager().get(hasHorns));
  }
}