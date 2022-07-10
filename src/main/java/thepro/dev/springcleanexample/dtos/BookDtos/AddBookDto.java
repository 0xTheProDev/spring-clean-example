package thepro.dev.springcleanexample.dtos.BookDtos;

public class AddBookDto {
    private String name;

    public AddBookDto(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
