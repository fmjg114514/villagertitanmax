package net.titan;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.titan.entity.PotionBallEntity;
import net.titan.entity.VillagerTitanEntity;
import net.titan.entity.WitchTitanEntity;
import software.bernie.geckolib.GeckoLib;

public class VillagerTitanMod implements ModInitializer {
    public static final String MOD_ID = "villagertitan";

    public static final EntityType<VillagerTitanEntity> VILLAGER_TITAN = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MOD_ID, "villager_titan"),
            EntityType.Builder.create(VillagerTitanEntity::new, MobCategory.MONSTER)
                    .dimensions(0.6f, 1.95f)
                    .build("villager_titan")
    );

    public static final EntityType<WitchTitanEntity> WITCH_TITAN = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MOD_ID, "witch_titan"),
            EntityType.Builder.create(WitchTitanEntity::new, MobCategory.MONSTER)
                    // 女巫泰坦：碰撞箱调为 0
                    .dimensions(0.001f, 0.001f)
                    .build("witch_titan")
    );

    public static final EntityType<PotionBallEntity> POTION_BALL = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(MOD_ID, "potion_ball"),
            EntityType.Builder.create(PotionBallEntity::new, MobCategory.MISC)
                    .dimensions(0.5f, 0.5f)
                    .build("potion_ball")
    );

    @Override
    public void onInitialize() {
        GeckoLib.initialize();

        FabricDefaultAttributeRegistry.register(VILLAGER_TITAN, VillagerTitanEntity.createAttributes());
        FabricDefaultAttributeRegistry.register(WITCH_TITAN, WitchTitanEntity.createAttributes());
    }
}
