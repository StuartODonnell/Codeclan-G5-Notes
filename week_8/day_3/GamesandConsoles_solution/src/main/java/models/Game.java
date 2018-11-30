package models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {

    private int id;
    private String title;
    private Genre genre;
    private List<Console> consolesAvialableFor;
    private Console hostConsole;
    private List<Owner> ownersFavedBy;

    public Game() {
    }

    public Game(String title, Genre genre) {
        this.title = title;
        this.genre = genre;
        this.consolesAvialableFor = new ArrayList<Console>();
        this.hostConsole = null;
        this.ownersFavedBy = new ArrayList<Owner>();
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

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Enumerated(value = EnumType.STRING)
    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }


    @ManyToMany
    @JoinTable(name = "consoles_games",
            joinColumns = {@JoinColumn(name = "game_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "console_id", nullable = false, updatable = false)})
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    public List<Console> getConsolesAvialableFor() {
        return consolesAvialableFor;
    }

    public void setConsolesAvialableFor(List<Console> consolesAvialableFor) {
        this.consolesAvialableFor = consolesAvialableFor;
    }

    @OneToOne(mappedBy = "gameBeingPlayed", fetch = FetchType.LAZY)
    public Console getHostConsole() {
        return hostConsole;
    }

    public void setHostConsole(Console hostConsole) {
        this.hostConsole = hostConsole;
    }

    @OneToMany(mappedBy = "favGame", fetch = FetchType.LAZY)
    public List<Owner> getOwnersFavedBy() {
        return ownersFavedBy;
    }

    public void setOwnersFavedBy(List<Owner> ownersFavedBy) {
        this.ownersFavedBy = ownersFavedBy;
    }

    public void addConsoleToAvailableFor(Console console){
        this.consolesAvialableFor.add(console);
    }
}
