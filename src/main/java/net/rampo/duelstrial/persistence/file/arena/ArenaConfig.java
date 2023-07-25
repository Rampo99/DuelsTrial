package net.rampo.duelstrial.persistence.file.arena;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ArenaConfig(
        @JsonProperty("world") String world,
        @JsonProperty("player_one_spawn") Spawn playerOneSpawn,
        @JsonProperty("player_two_spawn") Spawn playerTwoSpawn
) {
}


record Spawn (
        @JsonProperty("x") double x,
        @JsonProperty("y") double y,
        @JsonProperty("z") double z
){
}