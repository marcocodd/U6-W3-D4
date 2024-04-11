package epicode.u5d8hw.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import epicode.u5d8hw.entities.Author;
import epicode.u5d8hw.entities.Blogpost;
import epicode.u5d8hw.exceptions.NotFoundException;
import epicode.u5d8hw.payloads.NewBlogPostDTO;
import epicode.u5d8hw.repositories.BlogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BlogsService {
    @Autowired
    private BlogsRepository blogsRepository;
    @Autowired
    private AuthorsService authorsService;

    @Autowired
    private Cloudinary cloudinaryUploader;

    public Blogpost save(NewBlogPostDTO body) {
        Author author = authorsService.findById(body.authorId());
        Blogpost newBlogPost = new Blogpost(body.category(), body.title(), body.cover(), body.content(), body.readingTime(), author);
//        newBlogPost.setReadingTime(body.getReadingTime());
//        newBlogPost.setContent(body.getContent());
//        newBlogPost.setTitle(body.getTitle());
//        newBlogPost.setAuthor(author);
//        newBlogPost.setCategory(body.getCategory());
//        newBlogPost.setCover("http://picsum.photos/200/300");
        return blogsRepository.save(newBlogPost);
    }

    public List<Blogpost> getBlogs() {
        return blogsRepository.findAll();
    }

    public Blogpost findById(int id) {
        return blogsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) {
        Blogpost found = this.findById(id);
        blogsRepository.delete(found);
    }

    public Blogpost findByIdAndUpdate(int id, NewBlogPostDTO body) {
        Blogpost found = this.findById(id);

        found.setReadingTime(body.readingTime());
        found.setContent(body.content());
        found.setTitle(body.title());
        found.setCategory(body.category());

        if (found.getAuthor().getId() != body.authorId()) {
            Author newAuthor = authorsService.findById(body.authorId());
            found.setAuthor(newAuthor);
        }

        return blogsRepository.save(found);
    }

    public List<Blogpost> findByAuthor(int authorId) {
        Author author = authorsService.findById(authorId);
        return blogsRepository.findByAuthor(author);
    }

    public String uploadCover(MultipartFile image) throws IOException {
        String url = (String) cloudinaryUploader.uploader().upload(image.getBytes(), ObjectUtils.emptyMap()).get("url");
        return url;
    }
}
