package novianto.anggoro.spring.catalog.service.impl;

import lombok.AllArgsConstructor;
import novianto.anggoro.spring.catalog.domain.Address;
import novianto.anggoro.spring.catalog.domain.Author;
import novianto.anggoro.spring.catalog.dto.AuthorCreateRequestDTO;
import novianto.anggoro.spring.catalog.dto.AuthorQueryDTO;
import novianto.anggoro.spring.catalog.dto.AuthorResponseDTO;
import novianto.anggoro.spring.catalog.dto.AuthorUpdateRequestDTO;
import novianto.anggoro.spring.catalog.exception.BadRequestException;
import novianto.anggoro.spring.catalog.exception.ResourceNotFoundException;
import novianto.anggoro.spring.catalog.repository.AuthorRepository;
import novianto.anggoro.spring.catalog.service.AuthorService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorResponseDTO findAuthorById(String id) {
        // fetch data dari database
        Author author = authorRepository.findBySecureId(id).orElseThrow(() -> new ResourceNotFoundException("authorId.invalid"));
//        Author author = authorRepository.findBySecureId(id).orElseThrow(() -> new BadRequestException("authorId.invalid"));
        AuthorResponseDTO dto = new AuthorResponseDTO();
        dto.setAuthorName(author.getName());
        dto.setBirthDate(author.getBirthDate().toEpochDay());
        // ubah class author -> authorResponseDTO
        return dto;
    }

    @Override
    public void createNewAuthor(List<AuthorCreateRequestDTO> dtos) {
            List<Author> authors = dtos.stream().map((dto) -> {
                Author author = new Author();
                author.setName(dto.getAuthorName());
                author.setBirthDate(LocalDate.ofEpochDay(dto.getBirthDate()));
                List<Address> addresses = dto.getAddresses().stream().map(a -> {
                    Address address = new Address();
                    address.setCityName(a.getCityName());
                    address.setStreetName(a.getStreetName());
                    address.setZipCode(a.getZipCode());
                    address.setAuthor(author);
                    return address;
                }).collect(Collectors.toList());
                author.setAddresses(addresses);
                return author;
            }).collect(Collectors.toList());
            authorRepository.saveAll(authors);
    }

    @Override
    public void updateAuthor(String authorId, AuthorUpdateRequestDTO dto) {
        Author author = authorRepository.findBySecureId(authorId)
                .orElseThrow(() -> new BadRequestException("invalid.authorId"));
//        Map<Long, Address> addressMap = author.getAddresses().stream().map(a -> a).collect(Collectors.toMap(Address::getId, Function.identity()));
//        List<Address> addresses = dto.getAddresses().stream().map(a -> {
//            Address address = addressMap.get(a.getAddressId());
//            address.setCityName(a.getCityName());
//            address.setStreetName(a.getStreetName());
//            address.setZipCode(a.getZipCode());
//            return address;
//        }).collect(Collectors.toList());
//        author.setAddresses(addresses);
        author.setName(dto.getAuthorName() == null ? author.getName() : dto.getAuthorName());
        author.setBirthDate(
                dto.getBirthDate() == null ? author.getBirthDate() : LocalDate.ofEpochDay(dto.getBirthDate()));
        authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(String authorId) {
        // select data penulis
        // delete
        // or delete langsung (hard delete)
        // authorRepository.deleteById(authorId);
        // soft delete
        // 1. select data, deleted = false
        // Author author = authorRepository.findByIdAndDeletedFalse(authorId).orElseThrow(() -> new BadRequestException("authorId invalid"));
        // 2. update deleted = true
        // author.setDeleted(Boolean.TRUE);
        // authorRepository.save(author);
        Author author = authorRepository.findBySecureId(authorId).orElseThrow(() -> new BadRequestException("authorId invalid"));
//        Author author = authorRepository.findBySecureId(authorId).orElseThrow(() -> new ResourceNotFoundException("authorId invalid"));
        authorRepository.delete(author);
    }

    @Override
    public List<Author> findAuthor(List<String> authorIdList) {
        List<Author> authors = authorRepository.findBySecureIdIn(authorIdList);
        if(authors.isEmpty()) throw new BadRequestException("author cant empty");
        return authors;
    }

    @Override
    public List<AuthorResponseDTO> constructDTO(List<Author> authors) {
       // parsing list author menjadi list authorresponsedto
        return authors.stream().map((a) -> {
            AuthorResponseDTO dto = new AuthorResponseDTO();
            dto.setAuthorName(a.getName());
            dto.setBirthDate(a.getBirthDate().toEpochDay());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public Map<Long, List<String>> findAuthorMap(List<Long> bookIdList) {
        List<AuthorQueryDTO> queryList = authorRepository.findAuthorsByBookIdList(bookIdList);
        Map<Long, List<String>> authorMap = new HashMap<>();
        List<String> authorList = null;
        for (AuthorQueryDTO q:queryList){
            if(!authorMap.containsKey(q.getBookId())){
                authorList = new ArrayList<>();
            } else {
                authorList = authorMap.get(q.getBookId());
            }
//            authorList.add(q.getAuthorName());
//            authorMap.put(q.getBookId(), authorList);
        }
        return authorMap;
    }
}
