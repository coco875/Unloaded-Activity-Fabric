package com.github.inzan123.mixin;

import com.github.inzan123.SimulateRandomTicks;
import com.github.inzan123.UnloadedActivity;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import static java.lang.Math.pow;

@Mixin(SweetBerryBushBlock.class)
public abstract class BerryBushSimulateTimeMixin extends PlantBlock {
    public BerryBushSimulateTimeMixin(Settings settings) {
        super(settings);
    }

    @Shadow @Final public static IntProperty AGE;
    @Shadow @Final public static int MAX_AGE;

    @Override
    public double getOdds(ServerWorld world, BlockPos pos) {
        return 0.2;
    }
    @Override public boolean canSimulate() {return true;}
    public boolean shouldSimulate(BlockState state, ServerWorld world, BlockPos pos) {
        if (state == null) return false;
        if (!UnloadedActivity.instance.config.growSweetBerries) return false;
        if (getCurrentAgeUA(state) >= getMaxAgeUA() || world.getBaseLightLevel(pos.up(), 0) < 9) return false;
        return true;
    }

    @Override public int getCurrentAgeUA(BlockState state) {
        return state.get(AGE);
    }

    @Override public int getMaxAgeUA() {
        return MAX_AGE;
    }

    @Override
    public void simulateTime(BlockState state, ServerWorld world, BlockPos pos, Random random, long timePassed, int randomTickSpeed) {

        if (shouldSimulate(state, world, pos)) {

            int age = getCurrentAgeUA(state);
            int ageDifference = getMaxAgeUA() - age;

            double randomPickChance = getRandomPickOdds(randomTickSpeed);
            double totalOdds = getOdds(world, pos) * randomPickChance;

            int growthAmount = getOccurrences(timePassed, totalOdds, ageDifference, random);

            if (growthAmount != 0) {
                state = state.with(AGE, age + growthAmount);
                world.setBlockState(pos, state, 2);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
            }
        }
        super.simulateTime(state, world, pos, random, timePassed, randomTickSpeed);
    }
}
