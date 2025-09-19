package br.com.fiap.universidade_fiap.control;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import br.com.fiap.universidade_fiap.model.EnumPerfil;
import br.com.fiap.universidade_fiap.model.Usuario;
import br.com.fiap.universidade_fiap.repository.UsuarioRepository;
import jakarta.validation.Valid;

@Controller
public class UsuarioController {

	private final PasswordEncoder encoder;
	private final UsuarioRepository repU;

	public UsuarioController(PasswordEncoder encoder, UsuarioRepository repU) {
		this.encoder = encoder;
		this.repU = repU;
	}

	@GetMapping("/usuario/novo")
	public ModelAndView retornarCadUsuario() {
		ModelAndView mv = new ModelAndView("/usuario/novo");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		repU.findByEmail(auth.getName()).ifPresent(usuario -> mv.addObject("usuario_logado", usuario));

		mv.addObject("usuario", new Usuario());
		mv.addObject("perfis", EnumPerfil.values());
		return mv;
	}

    @PostMapping("/insere_usuario")
    public ModelAndView inserirUsuario(@Valid @ModelAttribute Usuario usuario, BindingResult bindingResult) {
        ModelAndView mv = new ModelAndView();

        // Log de debug para cadastro de usuário
        System.out.println("=== CADASTRO DE USUÁRIO ===");
        System.out.println("Nome: " + (usuario != null ? usuario.getNomeFilial() : "null"));
        System.out.println("Email: " + (usuario != null ? usuario.getEmail() : "null"));
        System.out.println("CNPJ: " + (usuario != null ? usuario.getCnpj() : "null"));
        System.out.println("Perfil: " + (usuario != null ? usuario.getPerfil() : "null"));
        System.out.println("Tem erros de validação: " + bindingResult.hasErrors());

        try {
            // Adicionar usuário logado para o navbar
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getName() != null) {
                repU.findByEmail(auth.getName()).ifPresent(u -> mv.addObject("usuario_logado", u));
            }

            // Verificar erros de validação
            if (bindingResult.hasErrors()) {
                System.out.println("Erros de validação encontrados:");
                bindingResult.getAllErrors().forEach(error -> {
                    System.out.println("- " + error.getDefaultMessage());
                });
                
                mv.setViewName("/usuario/novo");
                mv.addObject("usuario", usuario);
                mv.addObject("perfis", EnumPerfil.values());
                mv.addObject("erro", "Por favor, corrija os erros no formulário.");
                return mv;
            }

            // Normalizar CNPJ (remover caracteres especiais para comparação)
            String cnpjLimpo = usuario.getCnpj().replaceAll("[^0-9]", "");
            
            // Verificar se email já existe
            if (repU.findByEmail(usuario.getEmail()).isPresent()) {
                System.out.println("Email já existe: " + usuario.getEmail());
                return criarModelViewComErro(mv, usuario, "E-mail já cadastrado no sistema!");
            }

            // Verificar se CNPJ já existe (comparar apenas números)
            boolean cnpjExiste = repU.findAll().stream()
                    .anyMatch(u -> u.getCnpj().replaceAll("[^0-9]", "").equals(cnpjLimpo));
            
            if (cnpjExiste) {
                System.out.println("CNPJ já existe: " + usuario.getCnpj());
                return criarModelViewComErro(mv, usuario, "CNPJ já cadastrado no sistema!");
            }

            // Validar senha
            if (usuario.getSenhaHash() == null || usuario.getSenhaHash().trim().isEmpty()) {
                System.out.println("Senha não fornecida");
                return criarModelViewComErro(mv, usuario, "Senha é obrigatória!");
            }

            // Criptografar senha e salvar usuário
            System.out.println("Criptografando senha...");
            usuario.setSenhaHash(encoder.encode(usuario.getSenhaHash()));
            
            System.out.println("Salvando usuário no banco...");
            Usuario usuarioSalvo = repU.save(usuario);
            System.out.println("Usuário salvo com ID: " + usuarioSalvo.getId());

            mv.setViewName("redirect:/usuario/novo?sucesso=true");
            return mv;
        } catch (Exception e) {
            System.out.println("ERRO ao cadastrar usuário: " + e.getMessage());
            e.printStackTrace();
            return criarModelViewComErro(mv, usuario, "Erro interno do servidor: " + e.getMessage());
        }
    }

    @GetMapping("/usuario/lista")
    public ModelAndView listarUsuarios() {
        System.out.println("=== LISTANDO USUÁRIOS ===");
        ModelAndView mv = new ModelAndView("/usuario/lista");
        try {
            // Adicionar usuário logado para o navbar
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            repU.findByEmail(auth.getName()).ifPresent(usuario -> mv.addObject("usuario_logado", usuario));
            
            List<Usuario> usuarios = repU.findAll();
            System.out.println("Total de usuários: " + usuarios.size());
            mv.addObject("usuarios", usuarios);
            return mv;
        } catch (Exception e) {
            System.out.println("ERRO ao listar usuários: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @GetMapping("/usuario/excluir/{id}")
    public ModelAndView excluirUsuario(@PathVariable Long id) {
        System.out.println("=== EXCLUINDO USUÁRIO ===");
        System.out.println("ID do usuário a ser excluído: " + id);
        
        try {
            // Verificar se o usuário existe
            Usuario usuario = repU.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
            
            System.out.println("Usuário encontrado: " + usuario.getEmail());
            
            // Excluir o usuário
            System.out.println("Excluindo usuário...");
            repU.deleteById(id);
            System.out.println("Usuário excluído com sucesso!");
            
            return new ModelAndView("redirect:/usuario/lista?sucesso=true");
        } catch (Exception e) {
            System.out.println("ERRO ao excluir usuário: " + e.getMessage());
            e.printStackTrace();
            return new ModelAndView("redirect:/usuario/lista?erro=" + e.getMessage());
        }
    }

    /**
     * Método auxiliar para criar ModelAndView com erro
     * Aplica o princípio DRY (Don't Repeat Yourself)
     */
    private ModelAndView criarModelViewComErro(ModelAndView mv, Usuario usuario, String mensagemErro) {
        mv.setViewName("/usuario/novo");
        mv.addObject("erro", mensagemErro);
        mv.addObject("usuario", usuario);
        mv.addObject("perfis", EnumPerfil.values());
        return mv;
    }

}
