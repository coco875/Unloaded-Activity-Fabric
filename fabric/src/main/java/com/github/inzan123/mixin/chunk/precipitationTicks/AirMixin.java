package com.github.inzan123.mixin.chunk.precipitationTicks;

import com.github.inzan123.UnloadedActivity;
import com.github.inzan123.Utils;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;

import static java.lang.Math.min;

@Mixin(AirBlock.class)
public abstract class AirMixin extends Block {
    public AirMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean implementsSimulatePrecTicks() {
        return true;
    }

    @Override
    public boolean canSimulatePrecTicks(BlockState state, ServerWorld world, BlockPos pos, long timeInWeather, Biome.Precipitation precipitation) {
        if (!UnloadedActivity.instance.config.accumulateSnow) return false;
        if (timeInWeather == 0) return false;
        int maxSnowHeight = 1;
        if (maxSnowHeight <= 0) return false;
        Biome biome = world.getBiome(pos).value();
        if (!biome.canSetSnow(world, pos)) return false;
        return true;
    }

    @Override
    public void simulatePrecTicks(BlockState state, ServerWorld world, BlockPos pos, long timeInWeather, long timePassed, Biome.Precipitation precipitation, double precipitationPickChance) {

        int maxSnowHeight = 1;

        int layers = Utils.getOccurrences(timeInWeather, precipitationPickChance, min(maxSnowHeight, SnowBlock.MAX_LAYERS), world.random);

        if (layers == 0)
            return;

        world.setBlockState(pos, Blocks.SNOW.getDefaultState().with(SnowBlock.LAYERS, layers));
    }
}
