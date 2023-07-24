package net.rampo.duelstrial.duel;

import lombok.Getter;
import lombok.Setter;
import net.rampo.duelstrial.DuelsTrial;

import java.util.UUID;

@Getter
@Setter
public class Duel {

    private UUID player1;
    private UUID player2;
    private Kit kit;
    private States state;


    public Duel(UUID player1, UUID player2, Kit kit) {
        this.player1 = player1;
        this.player2 = player2;
        this.kit = kit == null ? DuelsTrial.kits.get(DuelsTrial.defaultKitName) : kit;
        this.state = States.INVITED;
    }

    public void start(){
        this.state = States.STARTING;
    }
}
