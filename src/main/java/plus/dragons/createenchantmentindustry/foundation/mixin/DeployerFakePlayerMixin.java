package plus.dragons.createenchantmentindustry.foundation.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import plus.dragons.createenchantmentindustry.foundation.config.CeiConfigs;


@Mixin(value = DeployerFakePlayer.class, remap = false)
public class DeployerFakePlayerMixin {

    @Inject(method = "deployerKillsDoNotSpawnXP", at = @At(value = "HEAD"), cancellable = true)
    private static void deployerKillsSpawnXpNuggets(int i, Player player, CallbackInfoReturnable<Integer> cir) {
		if (player instanceof DeployerFakePlayer FakePlayer){
			int xp = i;
			if (xp <= 0) return;
			if (FakePlayer.getRandom().nextFloat() > CeiConfigs.SERVER.deployerXpDropChance.getF())
				return;
			int amount = xp / 3 + (FakePlayer.getRandom().nextInt(3) < xp % 3 ? 1 : 0);
			if (amount <= 0) return;
			Item nugget = AllItems.EXP_NUGGET.get();
			int maxStackSize = nugget.getMaxStackSize();
			for (int j = amount / maxStackSize; j > 0; --j) {
				FakePlayer.getInventory().placeItemBackInInventory(new ItemStack(nugget, maxStackSize));
			}
			amount %= maxStackSize;
			if (amount > 0)
				FakePlayer.getInventory().placeItemBackInInventory(new ItemStack(nugget, amount));
			cir.setReturnValue(0);
		}
    }

}
