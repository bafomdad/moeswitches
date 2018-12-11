package com.bafomdad.moeswitches;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.bafomdad.moeswitches.blocks.*;

@Mod.EventBusSubscriber
@Mod(modid=MoeSwitches.MOD_ID, name=MoeSwitches.NAME, version=MoeSwitches.VERSION)
public class MoeSwitches {

	public static final String MOD_ID = "moeswitches";
	public static final String NAME = "Moe Switches";
	public static final String VERSION = "1.1";
	
	@Mod.Instance(MOD_ID)
	public static MoeSwitches instance;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {}
	
	public static Block moeStoneButton, moeWoodenButton, moeLever, moePlateWooden, moePlateStone;
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		
		moeStoneButton = new MoeButtonStone().setHardness(0.5F);
		moeWoodenButton = new MoeButtonWooden().setHardness(0.5F);
		moeLever = new MoeLever().setHardness(0.5F);
		moePlateWooden = new MoePlate(Material.WOOD, BlockPressurePlate.Sensitivity.EVERYTHING).setHardness(0.5F);
		moePlateStone = new MoePlate(Material.ROCK, BlockPressurePlate.Sensitivity.MOBS).setHardness(0.5F);
		
		event.getRegistry().register(moeStoneButton);
		event.getRegistry().register(moeWoodenButton);
		event.getRegistry().register(moeLever);
		event.getRegistry().register(moePlateWooden);
		event.getRegistry().register(moePlateStone);
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		
		event.getRegistry().register(new ItemBlock(moeStoneButton).setRegistryName(moeStoneButton.getRegistryName()));
		event.getRegistry().register(new ItemBlock(moeWoodenButton).setRegistryName(moeWoodenButton.getRegistryName()));
		event.getRegistry().register(new ItemBlock(moeLever).setRegistryName(moeLever.getRegistryName()));
		event.getRegistry().register(new ItemBlock(moePlateWooden).setRegistryName(moePlateWooden.getRegistryName()));
		event.getRegistry().register(new ItemBlock(moePlateStone).setRegistryName(moePlateStone.getRegistryName()));
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		
		registerBlockModel(moeStoneButton);
		registerBlockModel(moeWoodenButton);
		registerBlockModel(moeLever);
		registerBlockModel(moePlateWooden);
		registerBlockModel(moePlateStone);
	}
	
	private static void registerBlockModel(Block block) {
		
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
	
	@Config(modid=MoeSwitches.MOD_ID)
	public static class MoeConfig {
		
		@Comment({"Determines how often the switches should speak. Higher numbers means less often."})
		public static int chatRate = 10;
	}
	
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		
		if (event.getModID().equals(MoeSwitches.MOD_ID))
			ConfigManager.load(MoeSwitches.MOD_ID, Config.Type.INSTANCE);
	}
}
