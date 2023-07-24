package net.rampo.duelstrial.persistence.file;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public record KitsConfig(
    @JsonProperty("default_kit") String defaultKit,
    @JsonProperty("kits") List<KitContent> kits
) {
}

