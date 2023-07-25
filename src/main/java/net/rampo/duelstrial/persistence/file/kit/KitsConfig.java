package net.rampo.duelstrial.persistence.file.kit;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record KitsConfig(
    @JsonProperty("default_kit") String defaultKit,
    @JsonProperty("kits") List<KitContent> kits
) {
}

