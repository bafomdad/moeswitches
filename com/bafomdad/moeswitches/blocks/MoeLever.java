package com.bafomdad.moeswitches.blocks;

import com.bafomdad.moeswitches.MoeSwitches;

import net.minecraft.block.BlockLever;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoeLever extends BlockLever {

	public MoeLever() {
		
		setRegistryName("moelever");
		setTranslationKey(MoeSwitches.MOD_ID + ".moelever");
		setCreativeTab(CreativeTabs.REDSTONE);
	}
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	
		if (!world.isRemote && world.rand.nextInt(Math.max(MoeSwitches.MoeConfig.chatRate, 1)) == 0) {
			boolean flag = ((Boolean)state.getValue(POWERED)).booleanValue();
			if (!flag)
				player.sendMessage(new TextComponentTranslation("moeswitches.message.lever.on"));
			else
				player.sendMessage(new TextComponentTranslation("moeswitches.message.lever.off"));
		}
		return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
    	
        return BlockRenderLayer.CUTOUT;
    }
}
