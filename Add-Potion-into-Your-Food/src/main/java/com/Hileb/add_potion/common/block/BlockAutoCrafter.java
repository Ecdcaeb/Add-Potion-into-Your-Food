package com.Hileb.add_potion.common.block;

import com.Hileb.add_potion.APConfig;
import com.Hileb.add_potion.api.PotionProcess;
import com.Hileb.add_potion.common.APRegisterHandler;
import com.Hileb.add_potion.common.potion.ApplyUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Project Add-Potion-into-Your-Food
 * @Author Hileb
 * @Date 2023/10/22 10:32
 **/
public class BlockAutoCrafter extends BaseEntityBlock {
    public BlockAutoCrafter() {
        super(
                BlockBehaviour.Properties.of().
                        mapColor(MapColor.STONE).
                        instrument(NoteBlockInstrument.BASEDRUM).
                        requiresCorrectToolForDrops().strength(1.5F, 6.0F));

    }
    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TileEntity(pPos,pState);
    }

    @Override @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, APRegisterHandler.TE_AUTO_CRAFTING_TABLE.get(), TileEntity::tick);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if(!pNewState.is(pState.getBlock())) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if(blockEntity instanceof TileEntity potionFactoryBlockEntity) {
                if (!pLevel.isClientSide){
                    potionFactoryBlockEntity.drop();
                }
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            }
        }
        super.onRemove(pState,pLevel,pPos,pNewState,pMovedByPiston);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if(blockEntity instanceof TileEntity potionFactoryBlockEntity) {
            if (!pLevel.isClientSide) {
                pLevel.playSound(pPlayer, pPos.getX(),pPos.getY(),pPos.getZ(), SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.5f,pLevel.random.nextFloat() * 0.15F + 0.6F);
                potionFactoryBlockEntity.drop();
            }
            pLevel.updateNeighbourForOutputSignal(pPos, this);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public static class TileEntity extends BlockEntity implements WorldlyContainer {
        public static final int SLOT_POTION = 0;
        public static final int SLOT_FOOD = 1;
        public static final int SLOT_RESULT = 2;
        public static final int SLOT_COUNT = 3;
        public static final int[] SLOT_FOT_UP=new int[]{SLOT_POTION};
        public static final int[] SLOT_FOT_SIDE=new int[]{SLOT_FOOD};
        public static final int[] SLOT_FOT_DOWN=new int[]{SLOT_RESULT};
        protected NonNullList<ItemStack> items=NonNullList.withSize(SLOT_COUNT,ItemStack.EMPTY);
        public TileEntity( BlockPos pPos, BlockState pBlockState) {
            super(APRegisterHandler.TE_AUTO_CRAFTING_TABLE.get(), pPos, pBlockState);
        }
        @Override
        public void load(CompoundTag compound) {
            super.load(compound);
            this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
            ContainerHelper.loadAllItems(compound, this.items);
        }

        @Override
        public void saveAdditional(CompoundTag compound) {
            super.saveAdditional(compound);
            ContainerHelper.saveAllItems(compound, this.items);
        }

        @Override
        public int[] getSlotsForFace(Direction pSide) {
            if (pSide==Direction.UP)return SLOT_FOT_UP;
            else if (pSide==Direction.DOWN)return SLOT_FOT_DOWN;
            else return SLOT_FOT_SIDE;
        }

        @Override
        public boolean canPlaceItem(int pIndex, ItemStack pStack) {
            if (pIndex==SLOT_POTION)return ApplyUtil.canApplyAsPotion(null,pStack);
            else if (pIndex==SLOT_FOOD)return ApplyUtil.canApplyStackAsFoods(null,pStack);
            else return false;
        }

        @Override
        public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
            return canPlaceItem(pIndex,pItemStack);
        }

        @Override
        public boolean canTakeItem(Container pTarget, int pIndex, ItemStack pStack) {
            return pIndex==0 || pIndex==1 || pIndex==2;
        }

        @Override
        public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
            return pIndex==0 || pIndex==1 || pIndex==2;
        }

        @Override
        public int getContainerSize() {
            return SLOT_COUNT;
        }

        @Override
        public ItemStack getItem(int pSlot) {
            return this.items.get(pSlot);
        }

        @Override
        public void setItem(int pSlot, ItemStack pStack) {
            items.set(pSlot,pStack);
        }

        @Override
        public ItemStack removeItem(int pIndex, int pCount) {
            return ContainerHelper.removeItem(this.items, pIndex, pCount);
        }
        @Override
        public ItemStack removeItemNoUpdate(int pIndex) {
            return ContainerHelper.takeItem(this.items, pIndex);
        }

        @Override
        public boolean stillValid(Player pPlayer) {
            return Container.stillValidBlockEntity(this, pPlayer);
        }

        @Override
        public void clearContent() {
            items.clear();
        }

        @Override
        public boolean isEmpty() {
            for(int i=0;i<SLOT_COUNT;i++){
                if (!items.get(i).isEmpty())return false;
            }
            return true;
        }
        LazyOptional<? extends IItemHandler>[] handlers =
                SidedInvWrapper.create(this,
                        Direction.from3DDataValue(0),Direction.from3DDataValue(1),
                        Direction.from3DDataValue(2),Direction.from3DDataValue(3),
                        Direction.from3DDataValue(4),Direction.from3DDataValue(5));

        @Override @NotNull
        public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
            if (!this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER) {
                return this.handlers[facing.get3DDataValue()].cast();
            }
            return super.getCapability(capability, facing);
        }
        public void drop(){
            BlockPos pPos=this.getBlockPos();
            int x=pPos.getX();
            int y=pPos.getY();
            int z=pPos.getZ();;
            for(ItemStack stack:this.items){
                if (!stack.isEmpty()){
                    Containers.dropItemStack(level, x,y,z, stack);
                }
            }
            this.clearContent();
        }
        public static void tick(Level level, BlockPos blockPos, BlockState state, TileEntity tileEntity) {
            if (!level.isClientSide){
                if (level.hasNeighborSignal(blockPos)){
                    if (tileEntity.getItem(SLOT_RESULT).isEmpty()){
                        InteractionResultHolder<ItemStack> result=PotionProcess.process(tileEntity.getItem(SLOT_FOOD),tileEntity.getItem(SLOT_POTION));
                        if (result.getResult()==InteractionResult.SUCCESS){
                            level.playSound(null, blockPos.getX(),blockPos.getY(),blockPos.getZ(), SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.5f,level.random.nextFloat() * 0.15F + 0.6F);
                            tileEntity.getItem(SLOT_POTION).shrink(1);
                            if (APConfig.ap_maxIs1_desc){
                                tileEntity.getItem(SLOT_FOOD).shrink(1);
                                result.getObject().setCount(1);
                            }
                            else tileEntity.setItem(SLOT_FOOD,ItemStack.EMPTY);
                            tileEntity.setItem(SLOT_RESULT,result.getObject());
                        }
                    }
                }
            }
        }
    }
}
