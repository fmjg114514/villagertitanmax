package net.titan.entity;

import com.mojang.datafixers.util.Unit;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.TicketType;
import net.titan.VillagerTitanMod;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class WitchTitanEntity extends Monster implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static AttributeSupplier createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1024.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 128.0D)
                .build();
    }

    public WitchTitanEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
    }

    // 碰撞箱为 0
    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.fixed(0.0f, 0.0f);
    }

    // 不受重力（碰撞箱为 0 时不会掉落）
    @Override
    public boolean isAffectedByGravity() {
        return false;
    }

    // 免疫一切伤害
    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    // 无法被删除
    @Override
    public void kill() {
    }

    @Override
    public void discard() {
    }

    @Override
    public void remove(RemovalReason reason) {
        if (reason == RemovalReason.KILLED || reason == RemovalReason.DISCARDED) {
            return;
        }
        super.remove(reason);
    }

    // 无近战攻击
    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "main", 0, state -> {
            if (this.isAggressive()) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("animation.titan.walk2"));
            }
            return state.setAndContinue(RawAnimation.begin().thenLoop("animation.titan.idle2"));
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            return;
        }
        ServerLevel sl = (ServerLevel) this.level();

        // 常加载：保证 tick 不会因为区块卸载而停止
        if (this.tickCount % 40 == 0) {
            sl.getChunkSource().addTicket(TicketType.FORCED, new ChunkPos(this.blockPosition()), 2, Unit.INSTANCE);
        }

        // 每 10 秒（200 tick）从天召唤 100 个巨大药水瓶
        if (this.tickCount % 200 == 0) {
            for (int i = 0; i < 100; i++) {
                PotionBallEntity ball = new PotionBallEntity(VillagerTitanMod.POTION_BALL, this.level());
                double ox = (this.random.nextDouble() - 0.5) * 60;
                double oz = (this.random.nextDouble() - 0.5) * 60;
                ball.setPos(this.getX() + ox, this.getY() + 40, this.getZ() + oz);
                ball.setDeltaMovement(0, -0.5, 0);
                this.level().addFreshEntity(ball);
            }
        }
    }
}
