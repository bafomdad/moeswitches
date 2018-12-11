package com.bafomdad.moeswitches.blocks;

import com.bafomdad.moeswitches.MoeSwitches;

import net.minecraft.block.BlockButtonStone;
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

public class MoeButtonStone extends BlockButtonStone {

	public MoeButtonStone() {
		
		setRegistryName("moebutton_stone");
		setTranslationKey(MoeSwitches.MOD_ID + ".moebuttonstone");
		setCreativeTab(CreativeTabs.REDSTONE);
	}
	
	@Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	
		if (!world.isRemote && !((Boolean)state.getValue(POWERED)).booleanValue()) {
			if (world.rand.nextInt(Math.max(MoeSwitches.MoeConfig.chatRate, 1)) == 0)
				player.sendMessage(new TextComponentTranslation("moeswitches.message.stonebutton"));
		}
		return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
    	
        return BlockRenderLayer.CUTOUT;
    }
}
