package vectorwing.farmersdelight.items.drinks;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;

public class MilkBottleItem extends DrinkItem
{
	public MilkBottleItem(Properties properties) {
		super(properties, false, true);
	}

	@Override
	public void affectConsumer(ItemStack stack, World worldIn, LivingEntity consumer) {
		Iterator<EffectInstance> itr = consumer.getActivePotionEffects().iterator();
		ArrayList<Effect> compatibleEffects = new ArrayList<>();

		while (itr.hasNext()) {
			EffectInstance effect = itr.next();
			if (effect.isCurativeItem(new ItemStack(Items.MILK_BUCKET))) {
				compatibleEffects.add(effect.getPotion());
			}
		}

		if (compatibleEffects.size() > 0) {
			EffectInstance selectedEffect = consumer.getActivePotionEffect(compatibleEffects.get(worldIn.rand.nextInt(compatibleEffects.size())));
			if (selectedEffect != null && !net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent(consumer, selectedEffect))) {
				consumer.removePotionEffect(selectedEffect.getPotion());
			}
		}
	}
}