package com.vector.megabreakertools.item.custom.pickaxe;

import com.vector.megabreakertools.item.custom.IModeSwitchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

public class MegaAtomicBreakerItem extends DiggerItem implements IModeSwitchable {
    public MegaAtomicBreakerItem(Tier pTier, Properties pProperties) {
        super(pTier, BlockTags.MINEABLE_WITH_PICKAXE, pProperties);
    }

    @Override
    public int getRange() {
        return 6; /// 13x13x13 (range=6)
    }

    @Override
    public boolean is3DMining() {
        return true; /// false = 13x13 = 2D, true = 13x13x13 = 3D
    }

    /// This shows the text on the tooltip of the tool
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        addModeTooltip(stack, tooltipComponents);
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    /// Calculates an area of destruction based on the direction of impact
    @Override
    public List<BlockPos> getBlocksToBeDestroyed(int range, BlockPos initialBlockPos, ServerPlayer player) {
        List<BlockPos> positions = new ArrayList<>();

        BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(1f),
                (player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if(traceResult.getType() == HitResult.Type.MISS) {
            return positions;
        }

        if (traceResult.getDirection() == Direction.DOWN) {
            for (int x = -range; x <= range; x++) {
                for (int y = -range; y <= range; y++) {  // A destruição vai a partir do bloco para baixo
                    for (int z = -range; z <= range; z++) {
                        positions.add(new BlockPos(initialBlockPos.getX() + x, initialBlockPos.getY() + y + 6, initialBlockPos.getZ() + z));
                    }
                }
            }
        }

        // Destruição para DIREÇÃO UP
        if (traceResult.getDirection() == Direction.UP) {
            for (int x = -range; x <= range; x++) {
                for (int y = -range; y <= range; y++) {  // A destruição vai a partir do bloco para cima
                    for (int z = -range; z <= range; z++) {
                        positions.add(new BlockPos(initialBlockPos.getX() + x, initialBlockPos.getY() + y - 6, initialBlockPos.getZ() + z));
                    }
                }
            }
        }

        // Destruição para DIREÇÃO NORTH
        if (traceResult.getDirection() == Direction.NORTH) {
            for (int x = -range; x <= range; x++) {
                for (int y = -1; y <= 11; y++) {
                    for (int z = -range; z <= range; z++) {  // A destruição vai para frente (no eixo Z negativo)
                        positions.add(new BlockPos(initialBlockPos.getX() + x, initialBlockPos.getY() + y, initialBlockPos.getZ() + z + 6));
                    }
                }
            }
        }

        // Destruição para DIREÇÃO SOUTH
        if (traceResult.getDirection() == Direction.SOUTH) {
            for (int x = -range; x <= range; x++) {
                for (int y = -1; y <= 11; y++) {
                    for (int z = -range; z <= range; z++) {  // A destruição vai para frente (no eixo Z positivo)
                        positions.add(new BlockPos(initialBlockPos.getX() + x, initialBlockPos.getY() + y, initialBlockPos.getZ() + z - 6));
                    }
                }
            }
        }

        // Destruição para DIREÇÃO EAST
        if (traceResult.getDirection() == Direction.EAST) {
            for (int x = -range; x <= range; x++) {  // A destruição vai para frente (no eixo X positivo)
                for (int y = -1; y <= 11; y++) {
                    for (int z = -range; z <= range; z++) {
                        positions.add(new BlockPos(initialBlockPos.getX() + x - 6, initialBlockPos.getY() + y, initialBlockPos.getZ() + z));
                    }
                }
            }
        }

        // Destruição para DIREÇÃO WEST
        if (traceResult.getDirection() == Direction.WEST) {
            for (int x = -range; x <= range; x++) {  // A destruição vai para frente (no eixo X negativo)
                for (int y = -1; y <= 11; y++) {
                    for (int z = -range; z <= range; z++) {
                        positions.add(new BlockPos(initialBlockPos.getX() + x + 6, initialBlockPos.getY() + y, initialBlockPos.getZ() + z));
                    }
                }
            }
        }

        return positions;
    }
}

