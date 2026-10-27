package net.titan.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRenderers;
import net.titan.VillagerTitanMod;
import net.titan.client.renderer.PotionBallRenderer;
import net.titan.client.renderer.VillagerTitanRenderer;
import net.titan.client.renderer.WitchTitanRenderer;

public class VillagerTitanClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRenderers.register(VillagerTitanMod.VILLAGER_TITAN, VillagerTitanRenderer::new);
        EntityRenderers.register(VillagerTitanMod.WITCH_TITAN, WitchTitanRenderer::new);
        EntityRenderers.register(VillagerTitanMod.POTION_BALL, PotionBallRenderer::new);
    }
}
