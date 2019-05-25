package vj.development.demo.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "korisnik")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, name = "korisnickoIme")
    private String username;

    @Column(unique = true)
    @Email
    private String email;

    @Column(name = "broj_godina")
    private int age;

    @Column(name = "sifra")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "korisnik")
    private Set<Bookmark> bookmarkList;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "korisnik_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public AppUser() {
    }

    public AppUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Bookmark> getBookmarkList() {
        return bookmarkList;
    }

    public Set<Bookmark> getBookmarkList(AppUser appUser) {
        if(appUser.isUnderAge()){
            Set<Bookmark> underAgeBookmarksList = new HashSet<>();
            for (Bookmark bm:this.bookmarkList) {
                if(!bm.getKategorija().getName().equals("adultContent")){
                    underAgeBookmarksList.add(bm);
                }
            }
            return underAgeBookmarksList;
        }
        return bookmarkList;
    }

    public void setBookmarkList(Set<Bookmark> bookmarkList) {
        this.bookmarkList = bookmarkList;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isUnderAge() {
        return (this.age >= 18) ? false : true;
    }

}
