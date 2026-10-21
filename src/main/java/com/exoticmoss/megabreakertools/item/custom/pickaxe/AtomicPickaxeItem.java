package com.exoticmoss.megabreakertools.item.custom.pickaxe;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.TooltipDisplay;
import java.util.function.Consumer;

import com.exoticmoss.megabreakertools.item.custom.IModeSwitchable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

public class AtomicPickaxeItem extends Item implements IModeSwitchable {
    public AtomicPickaxeItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getRange() {
        return 6; /// 13x13 (range=6)
    }

    @Override
    public boolean is3DMining() {
        return false; /// false = 13x13 = 2D, true = 13x13x13 = 3D
    }

    /// This shows the text on the tooltip of the tool
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        addModeTooltip(stack, tooltipComponents);
        super.appendHoverText(stack, context, display, tooltipComponents, tooltipFlag);
    }

    /// Calculates an area of destruction based on the direction of impact
    @Override
    public List<BlockPos> getBlocksToBeDestroyed(int range, BlockPos initalBlockPos, ServerPlayer player) {
        List<BlockPos> positions = new ArrayList<>();

        BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(1f),
                (player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if(traceResult.getType() == HitResult.Type.MISS) {
            return positions;
        }

        if (traceResult.getDirection() == Direction.DOWN || traceResult.getDirection() == Direction.UP) {
            for (int x = -range; x <= range; x++) {
                for (int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY(), initalBlockPos.getZ() + y));
                }
            }
        }

        if(traceResult.getDirection() == Direction.NORTH || traceResult.getDirection() == Direction.SOUTH) {
            for(int x = -range; x <= range; x++) {
                for(int y = -1; y <= 11; y++) {
                    positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY() + y, initalBlockPos.getZ()));
                }
            }
        }

        if(traceResult.getDirection() == Direction.EAST || traceResult.getDirection() == Direction.WEST) {
            for(int x = -range; x <= range; x++) {
                for(int y = -1; y <= 11; y++) {
                    positions.add(new BlockPos(initalBlockPos.getX(), initalBlockPos.getY() + y, initalBlockPos.getZ() + x));
                }
            }
        }

        return positions;
    }
}

