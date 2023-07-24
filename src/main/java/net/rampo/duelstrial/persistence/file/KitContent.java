package net.rampo.duelstrial.persistence.file;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record KitContent(

        @JsonProperty("name") String name,
        @JsonProperty("armor_content") ArmorContent armorContent,
        @JsonProperty("inventory_content") List<InventoryContent> inventoryContent
) {
}
