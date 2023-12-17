package org.interswitch.bookstore.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.interswitch.bookstore.dto.*;
import org.interswitch.bookstore.dto.request.AuthorRequest;
import org.interswitch.bookstore.entities.Author;
import org.interswitch.bookstore.service.AuthorService;
import org.interswitch.bookstore.utils.AppendableReferenceUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.lang.reflect.InvocationTargetException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/writer")
@PreAuthorize("hasAnyRole('ADMIN')")
@Tag(name = "Author", description = "The Author API. Contains Endpoints for author")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping(value = "/create", produces = "application/json")
    public ResponseEntity<ResponseDto> createAuthor(@RequestBody @Valid @NotBlank AuthorRequest authorRequest) {
        Author author = authorService.createAuthor(authorRequest);
        return new ResponseEntity<>(ResponseDto.wrapSuccessResult(AuthorDto.toDto(author),"successful"), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('UPDATE_PRIVILEGE') and hasRole('ADMIN')")
    public void updateAuthor(@RequestParam String authorId, @RequestBody AuthorRequest authorRequest) throws InvocationTargetException, IllegalAccessException {
        long id = AppendableReferenceUtils.getIdFrom(authorId);
        authorService.updateAuthor(id, authorRequest);
    }

    @DeleteMapping(value = "/delete", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('DELETE_PRIVILEGE') and hasRole('ADMIN')")
    public void removeAuthor(@RequestParam String authorId) {
        long id = AppendableReferenceUtils.getIdFrom(authorId);
        authorService.deleteAuthor(id);
    }
}
