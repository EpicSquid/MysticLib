package epicsquid.mysticalworld.entity;

import epicsquid.mysticalworld.config.ConfigManager;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class EntityHellSprout extends EntitySprout {
  public static final ResourceLocation LOOT_TABLE_HELL = new ResourceLocation("mysticalworld:entity/sprout_hell");

  public EntityHellSprout(World world) {
    super(world);
    this.isImmuneToFire = true;
    this.hell = true;
  }

  @Nullable
  @Override
  public EntityAgeable createChild(EntityAgeable ageable) {
    return new EntityHellSprout(ageable.world);
  }

  @Override
  protected void initEntityAI() {
    super.initEntityAI();
    this.tasks.addTask(3, new AIPlantNetherwart(this));
  }

  @Override
  public boolean isEntityInvulnerable(DamageSource source) {
    if (source.isFireDamage() || source == DamageSource.LAVA || source == DamageSource.ON_FIRE || source == DamageSource.IN_FIRE) {
      return true;
    }

    return super.isEntityInvulnerable(source);
  }

  @Override
  public void onLivingUpdate() {
    if (this.world.isRemote) {
      if (rand.nextInt(7) == 0) {
        this.world.spawnParticle(rand.nextInt(3) == 0 ? EnumParticleTypes.SMOKE_NORMAL : EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * 0.5, this.posY + 0.3 + (this.rand.nextDouble() - 0.5D) * 0.5, this.posZ + (this.rand.nextDouble() - 0.5D) * 0.3, 0, 0, 0);
      }
    }
    super.onLivingUpdate();
  }

  @Override
  public boolean getCanSpawnHere() {
    return this.world.rand.nextInt(3) != 0;
  }

  public static class AIPlantNetherwart extends EntityAIBase {
    private final EntitySprout sprout;
    private int ticker = 0;

    public AIPlantNetherwart(EntitySprout sprout) {
      this.sprout = sprout;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
      if (this.sprout.onGround && ticker >= 20) {
        ticker = 0;
        BlockPos pos = this.sprout.getPosition();
        IBlockState state = this.sprout.world.getBlockState(pos);
        IBlockState state2 = this.sprout.world.getBlockState(pos.down());
        if (canPlaceBlock(this.sprout.world, pos, state, state2)) {
          return this.sprout.getRNG().nextInt(ConfigManager.hellSprout.plantChance) == 0;
        }
      }

      ticker++;
      return false;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void updateTask() {
      Random random = this.sprout.getRNG();
      World world = this.sprout.world;
      BlockPos blockpos = this.sprout.getPosition();
      IBlockState netherWart = Blocks.NETHER_WART.getDefaultState().withProperty(BlockNetherWart.AGE, random.nextInt(3) + random.nextInt(5) == 0 ? 1 : 0);

      if (!net.minecraftforge.event.ForgeEventFactory.onBlockPlace(sprout, new net.minecraftforge.common.util.BlockSnapshot(world, blockpos, netherWart), net.minecraft.util.EnumFacing.UP).isCanceled()) {
        world.setBlockState(blockpos, netherWart, 3);
      }
    }

    private boolean canPlaceBlock(World world, BlockPos pos, IBlockState state, IBlockState down) {
      if (!state.getBlock().isAir(state, world, pos) || !state.getBlock().isReplaceable(world, pos)) {
        return false;
      }
      return down.getBlock().canSustainPlant(down, world, pos.down(), EnumFacing.UP, (BlockNetherWart) Blocks.NETHER_WART);
    }
  }

  @Override
  public ResourceLocation getLootTable() {
    return LOOT_TABLE_HELL;
  }
}
