package models;

import javax.persistence.*;

@Entity
@Table(name = "owners")
public class Owner {

    private int id;
    private Game favGame;

    public Owner() {
    }

    public Owner(Game favGame) {
        this.favGame = favGame;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    public Game getFavGame() {
        return favGame;
    }

    public void setFavGame(Game favGame) {
        this.favGame = favGame;
    }
}
