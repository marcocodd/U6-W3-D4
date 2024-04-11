package epicode.u5d8hw.payloads;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

public record NewBlogPostDTO(

        @NotEmpty(message = "Categoria è obbligatoria") String category,
        @NotEmpty(message = "Titolo è obbligatorio") String title,
        @NotEmpty(message = "Contenuto obbligatorio") String content,
        @Size(min = 1, message = "Il tempo minimo di lettura è 1 minuto") double readingTime,
        @NotEmpty(message = "Deve avere un id autore") int authorId,
        String cover

) {
}
