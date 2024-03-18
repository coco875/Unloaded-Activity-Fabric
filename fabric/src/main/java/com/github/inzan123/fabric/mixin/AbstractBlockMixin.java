package com.github.inzan123.fabric.mixin;

import com.github.inzan123.fabric.SimulateChunkBlocks;
import net.minecraft.block.AbstractBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin implements SimulateChunkBlocks {
}