package net.titan.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.titan.VillagerTitanMod;
import net.titan.entity.VillagerTitanEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class VillagerTitanRenderer extends GeoEntityRenderer<VillagerTitanEntity> {
    public VillagerTitanRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, VillagerTitanMod.VILLAGER_TITAN);
        // 体型从 10 倍扩大为 20 倍（碰撞箱不变，仍为注册时的 0.6x1.95）
        this.withScale(20.0f);
    }
}
