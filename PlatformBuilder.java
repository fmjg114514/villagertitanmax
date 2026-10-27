package net.titan.world;

import com.mojang.datafixers.util.Unit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.TicketType;
import net.minecraft.world.level.block.Blocks;

public class PlatformBuilder {
    /**
     * 在指定中心位置生成 30x30 黑曜石平台，并把该区块设为常加载。
     */
    public static void build(ServerLevel level, BlockPos center) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int dx = -15; dx <= 14; dx++) {
            for (int dz = -15; dz <= 14; dz++) {
                pos.set(center.getX() + dx, center.getY(), center.getZ() + dz);
                level.setBlockAndUpdate(pos, Blocks.OBSIDIAN.defaultBlockState());
            }
        }
        // 常加载：保证平台所在区块不会因距离而停止 tick
        level.getChunkSource().addTicket(TicketType.FORCED, new ChunkPos(center), 2, Unit.INSTANCE);
    }
}
