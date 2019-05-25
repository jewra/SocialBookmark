package vj.development.demo.domain;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;

@Entity
@Table(name = "bookmark")
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @URL(message = "Adresa nije validna")
    private String url;
    @Column(name = "ime")
    private String name;
    @Lob
    @Column(name = "opis")
    private String description;

    @ManyToOne()
    private AppUser korisnik;

    @ManyToOne()
    private Category kategorija;
    
    @Lob
    @Column(name = "slika")
    private byte[] image;
    
    public Bookmark() {
    }
    
    public Bookmark(@URL String url, String name, String description) {
        this.url = url;
        this.name = name;
        this.description = description;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AppUser getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(AppUser korisnik) {
        this.korisnik = korisnik;
    }

    public Category getKategorija() {
        return kategorija;
    }

    public void setKategorija(Category kategorija) {
        this.kategorija = kategorija;
    }
    
    public byte[] getImage() {
        return image;
    }
    
    public void setImage(byte[] image) {
        this.image = image;
    }
}
