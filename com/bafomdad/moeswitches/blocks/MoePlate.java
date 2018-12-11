package com.bafomdad.moeswitches.blocks;

import com.bafomdad.moeswitches.MoeSwitches;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoePlate extends BlockPressurePlate {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public MoePlate(Material material, BlockPressurePlate.Sensitivity sensitivity) {
		
		super(material, sensitivity);
		String mat = (material == Material.WOOD) ? "wooden" : "stone";
		setRegistryName("moeplate_" + mat);
		setTranslationKey(MoeSwitches.MOD_ID + ".moeplate" + mat);
		setCreativeTab(CreativeTabs.REDSTONE);
		
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, Boolean.valueOf(false)));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		
		return new BlockStateContainer(this, new IProperty[] { FACING, POWERED });
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		
		return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta)).withProperty(POWERED, Boolean.valueOf((meta & 8) > 0));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		
        int i = 0;
        i = i | ((EnumFacing)state.getValue(FACING)).getIndex();

        if (((Boolean)state.getValue(POWERED)).booleanValue())
        {
            i |= 8;
        }

        return i;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack) {
		
		world.setBlockState(pos, state.withProperty(FACING, entity.getHorizontalFacing()), 2);
	}
	
	@Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
		
		int i = this.computeRedstoneStrength(world, pos);
		
		super.onEntityCollision(world, pos, state, entity);
		
		boolean flag = this.getRedstoneStrength(state) > 0;
		boolean flag1 = i > 0;
		if (!world.isRemote && entity instanceof EntityPlayer) {
			if (flag1 && !flag) {
				if (world.rand.nextInt(Math.max(MoeSwitches.MoeConfig.chatRate, 1)) == 0) {
					if (this.getMaterial(state) == Material.WOOD)
						((EntityPlayer)entity).sendMessage(new TextComponentTranslation("moeswitches.message.woodplate.on"));
					else
						((EntityPlayer)entity).sendMessage(new TextComponentTranslation("moeswitches.message.stoneplate.on"));
				}
			}
			else if (!flag1 && flag) {
				if (world.rand.nextInt(Math.max(MoeSwitches.MoeConfig.chatRate, 1)) == 0) {
					if (this.getMaterial(state) == Material.WOOD)
						((EntityPlayer)entity).sendMessage(new TextComponentTranslation("moeswitches.message.woodplate.off"));
					else
						((EntityPlayer)entity).sendMessage(new TextComponentTranslation("moeswitches.message.stoneplate.off"));
				}
			}
		}
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
    	
        return BlockRenderLayer.CUTOUT;
    }
}
