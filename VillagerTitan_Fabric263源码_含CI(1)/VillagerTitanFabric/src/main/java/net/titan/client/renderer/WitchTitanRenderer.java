package net.titan.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.titan.VillagerTitanMod;
import net.titan.entity.WitchTitanEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WitchTitanRenderer extends GeoEntityRenderer<WitchTitanEntity> {
    public WitchTitanRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, VillagerTitanMod.WITCH_TITAN);
        // 体型 20 倍（碰撞箱为 0）
        this.withScale(20.0f);
    }
}
