package vj.development.demo.db;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import vj.development.demo.domain.Bookmark;
import vj.development.demo.domain.Category;
import vj.development.demo.domain.Role;
import vj.development.demo.domain.AppUser;
import vj.development.demo.repository.BookmarkRepository;
import vj.development.demo.repository.CategoryRepository;
import vj.development.demo.repository.RoleRepository;
import vj.development.demo.repository.UserRepository;
import vj.development.demo.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Seed implements CommandLineRunner {
    
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final BookmarkRepository bookmarkRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    
    public Seed(UserService userService, CategoryRepository categoryRepository, BookmarkRepository bookmarkRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.userService = userService;
        this.categoryRepository = categoryRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }
    
    @Override
    public void run(String... args) throws Exception
    {
        databaseSeed();
    }

    public void databaseSeed() {
        
        Role roleAdmin = new Role();
        roleAdmin.setType(Role.RoleType.ROLE_ADMIN);
        Role rolaAdmin = roleRepository.save(roleAdmin);
        
        Role roleUser = new Role();
        roleUser.setType(Role.RoleType.ROLE_USER);
        Role rolaUser =roleRepository.save(roleUser);
        
        Set<Role> adminRole = new HashSet<>();
        adminRole.add(rolaAdmin);
        adminRole.add(rolaUser);
        
        Set<Role> userRole = new HashSet<>();
        userRole.add(rolaUser);
        
        
        // *** Dodaavanje kategorija ***
        Category categorySport = new Category("Sport", "Live sport results, news, events");
        Category categoryNews = new Category("News", "Live news, events");
        Category categoryIT = new Category("IT", "Information technology");
        Category categoryMovies = new Category("Movies", "Best new  movies");
        Category categoryTvShows = new Category("TvShows", "Best new tv shows");
        Category adultContent = new Category("adultContent","Adult Content, not for kids");
        
        // *** Ubacivanje u listu ***
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(categoryIT);
        categoryList.add(categoryMovies);
        categoryList.add(categoryNews);
        categoryList.add(categorySport);
        categoryList.add(categoryTvShows);
        categoryList.add(adultContent);
        
        categoryRepository.saveAll(categoryList);
        //*** Dodavanje korisnika ***
        
        AppUser appUserVlada = new AppUser("Jewraaa", "jewraaaa@gmail.com", "sifra");
        appUserVlada.setAge(26);
        appUserVlada.setRoles(adminRole);
        
        AppUser appUserIlija = new AppUser("Ilija", "ilija@gmail.com", "sifra2");
        appUserIlija.setRoles(userRole);
        appUserIlija.setAge(15);
        AppUser appUserStojdza = new AppUser("Stojdza", "stanoje@gmail.com", "sifra3");
        appUserStojdza.setRoles(userRole);
        appUserStojdza.setAge(27);


        // *** Ubacivanje korsnika  u listu ***
        
        List<AppUser> appUserList = new ArrayList<>();
        appUserList.add(appUserVlada);
        appUserList.add(appUserIlija);
        appUserList.add(appUserStojdza);
        
        
        appUserList.forEach(u -> userRepository.save(u));
        
        List<Bookmark> bookmarkList = new ArrayList<>();
        
        Bookmark bookmarkLiveScore = new Bookmark("http://www.livescore.com/", "Live Score", "First live score site on the Internet, powered by LiveScore.com since 1998. Live Soccer from all around the World");
        bookmarkLiveScore.setKorisnik(appUserVlada);
        bookmarkLiveScore.setKategorija(categorySport);
        
        Path pathLiveScore = Paths.get("C:/Users/jevremovv/Desktop/bookmark/src/main/resources/static/img/ls.png");
        try {
            bookmarkLiveScore.setImage(Files.readAllBytes(pathLiveScore));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bookmark bookmarkSportBible = new Bookmark("http://www.sportbible.com/", "Sport Bible", "Sport Bible is one of the largest communities for sports fans across the world. With the latest sports new, pictures and videos!");
        bookmarkSportBible.setKorisnik(appUserVlada);
        bookmarkSportBible.setKategorija(categorySport);
        
        Path pathSportBible = Paths.get("C:/Users/jevremovv/Desktop/bookmark/src/main/resources/static/img/sp.jpg");
        try {
            bookmarkSportBible.setImage(Files.readAllBytes(pathSportBible));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bookmark bookmarkMozzart = new Bookmark("https://www.mozzartbet.com/", "Mozzart Bet", "https://www.mozzartbet.com/");
        bookmarkMozzart.setKorisnik(appUserVlada);
        bookmarkMozzart.setKategorija(categorySport);
        
        Path pathMozzartSport = Paths.get("C:/Users/jevremovv/Desktop/bookmark/src/main/resources/static/img/ms.jpg");
        try {
            bookmarkMozzart.setImage(Files.readAllBytes(pathMozzartSport));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Bookmark bookmarkBBC = new Bookmark("http://www.bbc.co.uk/news", "BBC News", "The best of the BBC, with the latest news and sport headlines, weather, TV & radio highlights and much more from across the whole of BBC Online");
        bookmarkBBC.setKorisnik(appUserIlija);
        bookmarkBBC.setKategorija(categoryNews);
        
        Bookmark bookmarkCNN = new Bookmark("https://edition.cnn.com/", "CNN", "View the latest news and breaking news today for U.S., world, weather, entertainment, politics and health at CNN.com.");
        bookmarkCNN.setKategorija(categoryNews);
        bookmarkCNN.setKorisnik(appUserIlija);
        
        Bookmark bookmarkNYT = new Bookmark("https://www.nytimes.com/", "New York Times", "The New York Times: Find breaking news, multimedia, reviews & opinion on Washington, business, sports, movies, travel, books, jobs, education, real estate");
        bookmarkNYT.setKorisnik(appUserIlija);
        bookmarkNYT.setKategorija(categoryNews);
        
        Bookmark adultContentBookmark = new Bookmark("http://www.telegraf.rs/","adultContentBookmark","adultContent description");
        adultContentBookmark.setKorisnik(appUserStojdza);
        adultContentBookmark.setKategorija(adultContent);


        bookmarkList.add(bookmarkLiveScore);
        bookmarkList.add(bookmarkSportBible);
        bookmarkList.add(bookmarkMozzart);
        bookmarkList.add(bookmarkBBC);
        bookmarkList.add(bookmarkCNN);
        bookmarkList.add(bookmarkNYT);
        bookmarkList.add(adultContentBookmark);

        bookmarkRepository.saveAll(bookmarkList);
        
        
    }
}
