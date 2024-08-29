package br.edu.ufage.topicos.catalogo.controlador;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufage.topicos.catalogo.basica.Categoria;
import br.edu.ufage.topicos.catalogo.controlador.requisicao.CategoriaRequest;
import br.edu.ufage.topicos.catalogo.controlador.resposta.CategoriaResponse;
import br.edu.ufage.topicos.catalogo.fachada.Catalogo;
import jakarta.validation.Valid;

@RestController
public class ControladorCategoria {

    @Autowired
    private Catalogo catalogo;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/categoria")
    public Categoria cadastrarCategoria (@Valid @RequestBody CategoriaRequest newObj) {
        return catalogo.salvarCategoria(newObj.converterParaClasseBasica());
    }

    @GetMapping("/categoria")
    @PreAuthorize("hasAnyAuthority('CADASTRAR_PRODUTO')")
    public List<CategoriaResponse> listarCategorias() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt principal = (Jwt) authentication.getPrincipal();
            System.out.println(principal.getClaimAsString("email"));
            System.out.println(principal.getId());
            System.out.println(authentication.getAuthorities());
            System.out.println(authentication.getCredentials());
            System.out.println(authentication.getName());

        List<CategoriaResponse> response = new ArrayList<>();
        for (Categoria c : catalogo.listarCategorias()) {
            response.add(new CategoriaResponse(c));
        }
        return response;
    }

    @GetMapping("/categoria/{id}")
    public CategoriaResponse carregarCategoria(@PathVariable long id) {
        return new CategoriaResponse(catalogo.encontrarCategoria(id));
    }
}
