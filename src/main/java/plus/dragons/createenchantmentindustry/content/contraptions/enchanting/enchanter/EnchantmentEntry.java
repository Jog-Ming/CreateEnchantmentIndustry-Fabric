package plus.dragons.createenchantmentindustry.content.contraptions.enchanting.enchanter;

import java.util.Optional;

import com.simibubi.create.foundation.utility.Pair;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import plus.dragons.createenchantmentindustry.EnchantmentIndustry;
import plus.dragons.createenchantmentindustry.foundation.config.CeiConfigs;

public class EnchantmentEntry extends Pair<Enchantment, Integer> {
    public static final TagKey<Enchantment> HYPER_ENCHANTABLE =
        TagKey.create(Registry.ENCHANTMENT_REGISTRY, EnchantmentIndustry.genRL("hyper_enchantable"));
    public static final TagKey<Enchantment> HYPER_ENCHANTABLE_BLACKLIST =
            TagKey.create(Registry.ENCHANTMENT_REGISTRY, EnchantmentIndustry.genRL("hyper_enchantable_blacklist"));


    protected EnchantmentEntry(Enchantment first, Integer second) {
        super(first, second);
    }

    public static EnchantmentEntry of(Enchantment enchantment, Integer level) {
        return new EnchantmentEntry(enchantment, level);
    }

    public static EnchantmentEntry of(Enchantment enchantment, int level) {
        return new EnchantmentEntry(enchantment, level);
    }

    public boolean valid() {
        var enchantment = getFirst();
        int level = getSecond();
        int maxLevel = enchantment.getMaxLevel();
        Optional<Holder<Enchantment>> optional = Registry.ENCHANTMENT.getHolder(Registry.ENCHANTMENT.getId(enchantment));
        if (optional.isPresent()) {
            Holder<Enchantment> holder = optional.get();
            if (holder.is(HYPER_ENCHANTABLE_BLACKLIST)) {
                return level <= maxLevel;
            } else if (maxLevel == 1 && level > 1) {
                return holder.is(HYPER_ENCHANTABLE) && CeiConfigs.SERVER.enableHyperEnchant.get() && level <= maxLevel + CeiConfigs.SERVER.maxHyperEnchantingLevelExtension.get();
            }
        }
        return level <= maxLevel + (CeiConfigs.SERVER.enableHyperEnchant.get() ? CeiConfigs.SERVER.maxHyperEnchantingLevelExtension.get() : 0);
    }
}
