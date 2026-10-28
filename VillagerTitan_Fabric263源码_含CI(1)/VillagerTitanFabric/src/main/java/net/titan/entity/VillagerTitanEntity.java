package net.titan.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.titan.world.BoltFx;
import net.titan.world.PlatformBuilder;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VillagerTitanEntity extends Monster implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public static AttributeSupplier createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 1024.0D)
                .add(Attributes.ATTACK_DAMAGE, 2147483647.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 128.0D)
                .build();
    }

    public VillagerTitanEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
    }

    // 免疫一切伤害（无法被杀死 / 无法删除）
    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public void kill() {
    }

    @Override
    public void discard() {
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
        // 首次生成：建造黑曜石平台
        if (this.tickCount == 1) {
            PlatformBuilder.build((ServerLevel) this.level(), this.blockPosition());
        }
        // 红色雷击（村民泰坦专属）
        if (this.tickCount % 100 == 0) {
            BoltFx.spawn(this.level(), this.position(), 0xFF0000);
            for (LivingEntity le : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(8))) {
                if (le != this) {
                    le.hurt(this.level().damageSources().lightningBolt(), 2147483647.0F);
                }
            }
        }
    }
}
