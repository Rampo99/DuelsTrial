package net.rampo.duelstrial.duel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@Builder
public class Kit {
    private ItemStack helmet;
    private ItemStack chestPlate;
    private ItemStack leggings;
    private ItemStack boots;
    private HashMap<Integer,ItemStack> inventoryContent;
}
