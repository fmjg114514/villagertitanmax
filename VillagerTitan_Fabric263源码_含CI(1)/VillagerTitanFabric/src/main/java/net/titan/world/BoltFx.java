package net.titan.world;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class BoltFx {
    /**
     * 生成一道指定颜色的闪电（粒子光柱）。
     * color 为 0xRRGGBB。
     */
    public static void spawn(Level level, Vec3 pos, int color) {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;
        DustParticleOptions opts = new DustParticleOptions(new Vector3f(r, g, b), 2.0f);
        for (int i = 0; i < 30; i++) {
            double y = pos.y + i * 0.7;
            double ox = (level.random.nextDouble() - 0.5) * 0.6;
            double oz = (level.random.nextDouble() - 0.5) * 0.6;
            level.addParticle(opts, pos.x + ox, y, pos.z + oz, 0, 0.2, 0);
        }
    }
}
