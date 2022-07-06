package thepro.dev.springcleanexample.dtos.BookDtos;

import java.util.Optional;

public class UpdateBookDto {
    private Optional<Long> id;
    private String name;

    public Optional<Long> getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = Optional.of(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
