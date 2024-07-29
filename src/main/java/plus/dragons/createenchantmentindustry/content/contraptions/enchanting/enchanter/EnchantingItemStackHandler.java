package plus.dragons.createenchantmentindustry.content.contraptions.enchanting.enchanter;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemHandlerHelper;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.world.item.ItemStack;

public class EnchantingItemStackHandler extends ItemStackHandler {
	public EnchantingItemStackHandler(int stacks) {
		super(stacks);
	}

	public long insertSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction) {
		if (slot < 0 || slot > getSlots())
			return 0;
		ItemStack stack = stacks[slot];
		if (!isItemValid(slot, resource))
			return 0;
		if (!stack.isEmpty()) { // add to an existing stack
			return insertToExistingStack(slot, stack, resource, maxAmount, transaction);
		} else { // create a new stack
			return insertToNewStack(slot, resource, maxAmount, transaction);
		}
	}

	public long extractSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction) {
		if (slot < 0 || slot > getSlots())
			return 0;
		ItemStack stack = stacks[slot];
		if (stack.isEmpty() || !resource.matches(stack))
			return 0;
		int count = stack.getCount();
		int extracted = (int) Math.min(maxAmount, count);
		boolean empty = extracted >= count;
		ItemStack newStack = empty ? ItemStack.EMPTY : ItemHandlerHelper.copyStackWithSize(stack, count - extracted);
		updateSnapshots(transaction);
		contentsChangedInternal(slot, newStack, transaction);
		return extracted;
	}
}
