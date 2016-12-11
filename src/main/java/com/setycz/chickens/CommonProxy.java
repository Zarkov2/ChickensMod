package com.setycz.chickens;

import com.setycz.chickens.liquidEgg.ItemLiquidEgg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by setyc on 18.02.2016.
 */
public class CommonProxy {
    public void init() {

    }

    public void registerChicken(ChickensRegistryItem chicken) {
        if (chicken.isDye() && chicken.isEnabled()) {
            GameRegistry.addShapelessRecipe(
                    new ItemStack(ChickensMod.coloredEgg, 1, chicken.getDyeMetadata()),
                    new ItemStack(Items.EGG), new ItemStack(Items.DYE, 1, chicken.getDyeMetadata())
            );
        }
    }

    public void registerLiquidEgg(LiquidEggRegistryItem liquidEgg) {
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ChickensMod.liquidEgg, new DispenseLiquidEgg());
    }

    class DispenseLiquidEgg extends BehaviorDefaultDispenseItem {
        @Override
        protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            ItemLiquidEgg itemLiquidEgg = (ItemLiquidEgg) stack.getItem();
            BlockPos blockpos = source.getBlockPos().offset(BlockDispenser.getFacing(source.getBlockMetadata()));
            Block liquid = LiquidEggRegistry.findById(stack.getMetadata()).getLiquid();
            if (!itemLiquidEgg.tryPlaceContainedLiquid(null, source.getWorld(), blockpos, liquid)) {
                return super.dispenseStack(source, stack);
            }
            stack.stackSize--;
            return stack;
        }
    }
}
