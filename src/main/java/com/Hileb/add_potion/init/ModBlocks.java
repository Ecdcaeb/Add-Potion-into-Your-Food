package com.Hileb.add_potion.init;

import com.Hileb.add_potion.IdlFramework;
import com.Hileb.add_potion.blocks.BlockBase;
import com.Hileb.add_potion.blocks.PotionFactoryBlock;
import com.Hileb.add_potion.gui.ModGuiElementLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.Hileb.add_potion.IdlFramework.MODID;

public class ModBlocks {
	private static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

	public static final RegistryObject<Block> POTION_TABLE = REGISTER.register("potion_table", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(5.0F)) {
		@Override
		public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
			playerIn.openGui(IdlFramework.instance, ModGuiElementLoader.GUI_DEMO,playerIn.world,(int) playerIn.posX,(int)playerIn.posY,(int)playerIn.posZ);
			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
	});

	public static final RegistryObject<Block> POTION_FACTORY = REGISTER.register("potion_factory", () -> new PotionFactoryBlock(BlockBehaviour.Properties.of(Material.STONE).strength(5.0F)));

	public static void init(IEventBus bus) {
		REGISTER.register(bus);
	}
}
