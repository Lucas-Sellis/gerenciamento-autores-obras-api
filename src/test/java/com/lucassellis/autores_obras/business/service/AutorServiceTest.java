package com.lucassellis.autores_obras.business.service;
import com.lucassellis.autores_obras.business.dto.AutorDTO.AutorDTO;
import com.lucassellis.autores_obras.business.mapper.AutorMapper;
import com.lucassellis.autores_obras.infrastructure.repository.AutorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class AutorServiceTest {
    @Mock
    private AutorRepository repository;
    @Mock
    private AutorMapper mapper;
    @InjectMocks
    private AutorService service;
    @Test
    @DisplayName("Deve lançar erro ao tentar criar autor brasileiro sem CPF")
    void deveLancarErroQuandoBrasileiroSemCpf() {
        AutorDTO dto = new AutorDTO();
        dto.setNome("Escritor BR");
        dto.setPais("Brasil");
        dto.setCpf(null); 
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.criar(dto);
        });
        assertEquals("CPF é obrigatório para autores do Brasil", exception.getMessage());
        verifyNoInteractions(repository);
    }
    @Test
    @DisplayName("Deve lançar erro quando o e-mail já existir")
    void deveLancarErroQuandoEmailDuplicado() {
        AutorDTO dto = new AutorDTO();
        dto.setEmail("duplicado@teste.com");
        dto.setPais("Portugal"); 
        when(repository.existsByEmail(dto.getEmail())).thenReturn(true);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.criar(dto);
        });
        assertEquals("Este e-mail já está em uso!", exception.getMessage());
    }
}