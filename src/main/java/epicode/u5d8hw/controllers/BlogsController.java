package epicode.u5d8hw.controllers;

import epicode.u5d8hw.entities.Blogpost;
import epicode.u5d8hw.exceptions.BadRequestException;
import epicode.u5d8hw.payloads.NewBlogPostDTO;
import epicode.u5d8hw.payloads.NewBlogResp;
import epicode.u5d8hw.services.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogsController {
    @Autowired
    BlogsService blogsService;

    // 1. - POST http://localhost:3001/blogs (+ req.body)
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public NewBlogResp saveBlog(@RequestBody @Validated NewBlogPostDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }

        return new NewBlogResp(this.blogsService.save(body).getId());
    }

    // 2. - GET http://localhost:3001/blogs
    @GetMapping("")
    public List<Blogpost> getBlogs(@RequestParam(required = false) Integer authorId) {
        if (authorId != null) return blogsService.findByAuthor(authorId);
        else return blogsService.getBlogs();
    }

    // 3. - GET http://localhost:3001/blogs/{id}
    @GetMapping("/{blogId}")
    public Blogpost findById(@PathVariable int blogId) {
        return blogsService.findById(blogId);
    }

    // 4. - PUT http://localhost:3001/blogs/{id} (+ req.body)
    @PutMapping("/{blogId}")
    public Blogpost findAndUpdate(@PathVariable int blogId, @RequestBody NewBlogPostDTO body) {
        return blogsService.findByIdAndUpdate(blogId, body);
    }

    // 5. - DELETE http://localhost:3001/blogs/{id
    @DeleteMapping("/{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findAndDelete(@PathVariable int blogId) {
        blogsService.findByIdAndDelete(blogId);
    }

    @PostMapping("/cover")
    public String uploadCoverBlog(@RequestParam("cover") MultipartFile image) throws IOException {
        return this.blogsService.uploadCover(image);
    }
}
