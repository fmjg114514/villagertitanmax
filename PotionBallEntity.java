package net.titan.entity;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.titan.world.BoltFx;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PotionBallEntity extends ThrowableProjectile implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public PotionBallEntity(EntityType<? extends ThrowableProjectile> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public Item getDefaultItem() {
        return Items.AIR;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "spin", 0,
                state -> state.setAndContinue(RawAnimation.begin().thenLoop("animation.potion_ball.spin"))));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    protected void onHit(HitResult result) {
        if (!this.level().isClientSide) {
            // 紫色雷击特效（女巫泰坦）
            BoltFx.spawn(this.level(), this.position(), 0xAA00FF);

            // 爆炸伤害
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 6.0F, Level.ExplosionInteraction.MOB);

            // 范围内全部负面效果（不含失明）
            for (LivingEntity le : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(8))) {
                le.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 255));
                le.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 600, 255));
                le.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600, 255));
                le.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 255));
                le.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600, 255));
                le.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 255));
                le.addEffect(new MobEffectInstance(MobEffects.WITHER, 600, 255));
                le.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 600, 255));
                // 失明：按要求不施加
            }
        }
        this.discard();
    }
}
