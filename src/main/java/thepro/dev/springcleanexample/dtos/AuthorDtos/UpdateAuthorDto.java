package thepro.dev.springcleanexample.dtos.AuthorDtos;

import java.util.Optional;

public class UpdateAuthorDto {
    private Optional<Long> id;
    private String firstName;
    private String lastName;

    public UpdateAuthorDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UpdateAuthorDto(Long id, String firstName, String lastName) {
        this.id = Optional.of(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Optional<Long> getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = Optional.of(id);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
