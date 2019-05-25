package vj.development.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vj.development.demo.domain.Bookmark;
import vj.development.demo.repository.BookmarkRepository;
import vj.development.demo.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    
    public BookmarkService(BookmarkRepository bookmarkRepository, UserRepository userRepository) {
        this.bookmarkRepository = bookmarkRepository;
        this.userRepository = userRepository;
    }
    
    public Bookmark saveBookmark(Bookmark bookmark){
        return bookmarkRepository.save(bookmark);
    }
    public void saveImageFile(Long bookmarkId, MultipartFile file) {
        
        try {
            Bookmark bookmark= bookmarkRepository.findById(bookmarkId).get();
            
            byte[] bytes = new byte[file.getBytes().length];
            int i = 0;
            
            for (byte b : file.getBytes()){
                bytes[i++] = b;
            }
            
            bookmark.setImage(bytes);
    
            bookmarkRepository.save(bookmark);
        } catch (IOException e) {
            //todo handle better
            
            e.printStackTrace();
        }
    }
    public List<Bookmark> getAllBookmarks(){
        return bookmarkRepository.findAll();
    }

    public List<Bookmark> getBookmarksByCategory(Long categoryId){
       List<Bookmark> bookmarkList = bookmarkRepository.findAll();
       List<Bookmark> categoryBookmarks = new ArrayList<>();
        for (Bookmark bm:bookmarkList) {
            if(bm.getKategorija().getId()== categoryId){
                categoryBookmarks.add(bm);
            }
        }
        return categoryBookmarks;
    }

    public void deleteBookmarkById(Long id ){
        bookmarkRepository.deleteById(id);
    }
    
}
