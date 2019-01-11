package indianpoker.domain.game;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
public class Dealer extends AbstractEntity {

    @Size(min = 0, max = 10)
    @Column
    private int firstPlayerCardNum;

    @Size(min = 0, max = 10)
    @Column
    private int secondPlayerCardNum;



}
