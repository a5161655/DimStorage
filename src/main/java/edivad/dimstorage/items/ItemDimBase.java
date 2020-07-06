package edivad.dimstorage.items;

import java.util.List;

import edivad.dimstorage.Main;
import edivad.dimstorage.api.Frequency;
import edivad.dimstorage.setup.Registration;
import edivad.dimstorage.tile.TileFrequencyOwner;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemDimBase extends BlockItem {

	public ItemDimBase(Block blockIn)
	{
		super(blockIn, Registration.globalProperties);
	}

	private Frequency getFreq(ItemStack stack)
	{
		if(stack.hasTag())
		{
			CompoundNBT stackTag = stack.getTag();
			if(stackTag.contains("Frequency"))
			{
				return new Frequency(stackTag.getCompound("Frequency"));
			}
		}
		return new Frequency();
	}

	@Override
	protected boolean placeBlock(BlockItemUseContext context, BlockState state)
	{
		if(super.placeBlock(context, state))
		{
			World world = context.getWorld();
			BlockPos pos = context.getPos();
			ItemStack stack = context.getItem();

			TileFrequencyOwner tile = (TileFrequencyOwner) world.getTileEntity(pos);
			tile.setFreq(getFreq(stack));
			return true;
		}
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		Frequency frequency = getFreq(stack);
		if(frequency.hasOwner())
			tooltip.add(new TranslationTextComponent("gui." + Main.MODID + ".owner").appendText(" " + frequency.getOwner()).applyTextStyle(TextFormatting.DARK_RED));
		if(stack.hasTag())
			tooltip.add(new TranslationTextComponent("gui." + Main.MODID + ".frequency").appendText(" " + frequency.getChannel()));
	}
}
