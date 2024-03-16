package com.github.inzan123.mixin.chunk.Both;

import com.github.inzan123.UnloadedActivity;
import com.github.inzan123.Utils;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import static java.lang.Math.min;

@Mixin(SnowBlock.class)
public abstract class SnowMixin extends Block {
    public SnowMixin(Settings settings) {
        super(settings);
    }

    @Override
    public boolean implementsSimulatePrecTicks() {
        return true;
    }
    @Override
    public boolean implementsSimulateRandTicks() {
        return true;
    }

    @Override
    public double getOdds(ServerWorld world, BlockPos pos) {
        return 1;
    }

    @Override
    public boolean canSimulateRandTicks(BlockState state, ServerWorld world, BlockPos pos) {
        if (!UnloadedActivity.instance.config.meltSnow) return false;
        if (world.getLightLevel(LightType.BLOCK, pos) <= 11) return false;
        return true;
    }

    @Override
    public void simulateRandTicks(BlockState state, ServerWorld world, BlockPos pos, Random random, long timePassed, int randomTickSpeed) {

        double pickOdds = Utils.getRandomPickOdds(randomTickSpeed)*this.getOdds(world,pos);;

        if (Utils.getOccurrences(timePassed, pickOdds, 1, random) != 0) {
            dropStacks(state, world, pos);
            world.removeBlock(pos, false);
        }

    }

    @Override
    public boolean canSimulatePrecTicks(BlockState state, ServerWorld world, BlockPos pos, long timeInWeather, Biome.Precipitation precipitation) {
        if (!UnloadedActivity.instance.config.accumulateSnow) return false;
        if (timeInWeather == 0) return false;
        int maxSnowHeight = 1;
        if (maxSnowHeight <= state.get(SnowBlock.LAYERS)) return false;
        Biome biome = world.getBiome(pos).value();
        if (!biome.canSetSnow(world, pos)) return false;
        return true;
    }

    @Override
    public void simulatePrecTicks(BlockState state, ServerWorld world, BlockPos pos, long timeInWeather, long timePassed, Biome.Precipitation precipitation, double precipitationPickChance) {

        int maxSnowHeight = 1;

        int currentSnowHeight = state.get(SnowBlock.LAYERS);

        int heightDifference = maxSnowHeight-currentSnowHeight;

        int newLayers = Utils.getOccurrences(timeInWeather, precipitationPickChance, heightDifference, world.random);

        if (newLayers == 0)
            return;

        world.setBlockState(pos, Blocks.SNOW.getDefaultState().with(SnowBlock.LAYERS, currentSnowHeight+heightDifference));
    }
}
