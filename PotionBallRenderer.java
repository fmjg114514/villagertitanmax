package net.titan.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.titan.VillagerTitanMod;
import net.titan.entity.PotionBallEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PotionBallRenderer extends GeoEntityRenderer<PotionBallEntity> {
    public PotionBallRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, VillagerTitanMod.POTION_BALL);
        // 巨大药水瓶
        this.withScale(4.0f);
    }
}
