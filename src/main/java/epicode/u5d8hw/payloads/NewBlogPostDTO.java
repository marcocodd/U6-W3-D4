package epicode.u5d8hw.payloads;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

public record NewBlogPostDTO(

        @NotEmpty(message = "Categoria è obbligatoria") String category,
        @NotEmpty(message = "Titolo è obbligatorio") String title,
        @NotEmpty(message = "Contenuto obbligatorio") String content,
        double readingTime,
        @Size(min = 1, message = "Deve avere un id autore") int authorId,
        @NotEmpty String cover

) {
}
