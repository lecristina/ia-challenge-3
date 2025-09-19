package br.com.fiap.universidade_fiap.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.universidade_fiap.model.EnumPerfil;
import br.com.fiap.universidade_fiap.model.EnumStatus;
import br.com.fiap.universidade_fiap.model.Moto;
import br.com.fiap.universidade_fiap.model.StatusMoto;
import br.com.fiap.universidade_fiap.model.Usuario;
import br.com.fiap.universidade_fiap.repository.MotoRepository;
import br.com.fiap.universidade_fiap.repository.StatusMotoRepository;
import br.com.fiap.universidade_fiap.repository.UsuarioRepository;

@Service
public class DataInitializationService implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private MotoRepository motoRepository;
    
    @Autowired
    private StatusMotoRepository statusMotoRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== INICIALIZANDO DADOS DO BANCO ===");
        
        // Limpar dados existentes para forçar reinicialização
        System.out.println("Limpando dados existentes...");
        statusMotoRepository.deleteAll();
        motoRepository.deleteAll();
        usuarioRepository.deleteAll();
        System.out.println("Dados limpos com sucesso.");
        
        // Criar usuários
        createUsers();
        
        // Criar motos
        createMotos();
        
        // Criar status das motos
        createStatusMotos();
        
        System.out.println("=== DADOS INICIALIZADOS COM SUCESSO ===");
    }
    
    private void createUsers() {
        System.out.println("Criando usuários...");
        
        List<Usuario> usuarios = Arrays.asList(
            new Usuario(null, "Admin TrackZone", "admin@teste.com", 
                passwordEncoder.encode("admin1234"), "12345678000199", 
                "Rua das Flores, 123 - São Paulo/SP", "(11) 99999-9999", EnumPerfil.ADMIN),
            
            new Usuario(null, "Filial Centro", "gerente@teste.com", 
                passwordEncoder.encode("admin1234"), "98765432000188", 
                "Av. Paulista, 456 - São Paulo/SP", "(11) 88888-8888", EnumPerfil.GERENTE),
            
            new Usuario(null, "Operador Filial", "operador@teste.com", 
                passwordEncoder.encode("admin1234"), "11111111000111", 
                "Rua da Consolação, 789 - São Paulo/SP", "(11) 77777-7777", EnumPerfil.OPERADOR),
            
            new Usuario(null, "Filial Norte", "norte@teste.com", 
                passwordEncoder.encode("admin1234"), "22222222000222", 
                "Av. Tietê, 1000 - São Paulo/SP", "(11) 66666-6666", EnumPerfil.GERENTE),
            
            new Usuario(null, "Filial Sul", "sul@teste.com", 
                passwordEncoder.encode("admin1234"), "33333333000333", 
                "Rua Augusta, 2000 - São Paulo/SP", "(11) 55555-5555", EnumPerfil.OPERADOR)
        );
        
        usuarioRepository.saveAll(usuarios);
        System.out.println("Usuários criados: " + usuarios.size());
    }
    
    private void createMotos() {
        System.out.println("Criando motos...");
        
        Usuario admin = usuarioRepository.findByEmail("admin@teste.com").orElse(null);
        if (admin == null) {
            System.out.println("Usuário admin não encontrado!");
            return;
        }
        
        List<Moto> motos = Arrays.asList(
            new Moto(null, "ABC-1234", "CHASSI001", "Honda CG 160", admin),
            new Moto(null, "DEF-5678", "CHASSI002", "Yamaha Fazer 250", admin),
            new Moto(null, "GHI-9012", "CHASSI003", "Honda CB 600", admin),
            new Moto(null, "JKL-3456", "CHASSI004", "Kawasaki Ninja 300", admin),
            new Moto(null, "MNO-7890", "CHASSI005", "Suzuki GSX 750", admin),
            new Moto(null, "PQR-2468", "CHASSI006", "Honda PCX 160", admin),
            new Moto(null, "STU-1357", "CHASSI007", "Yamaha NMAX 160", admin),
            new Moto(null, "VWX-9753", "CHASSI008", "Honda Biz 125", admin),
            new Moto(null, "YZA-8642", "CHASSI009", "Kawasaki Z400", admin),
            new Moto(null, "BCD-1111", "CHASSI010", "Suzuki Burgman 200", admin),
            new Moto(null, "XYZ-9999", "CHASSI011", "Honda CB 1000", admin),
            new Moto(null, "WUV-8888", "CHASSI012", "Yamaha R1", admin),
            new Moto(null, "TSR-7777", "CHASSI013", "Kawasaki ZX-10R", admin),
            new Moto(null, "QPO-6666", "CHASSI014", "Suzuki GSX-R 1000", admin),
            new Moto(null, "NML-5555", "CHASSI015", "Honda CBR 1000", admin)
        );
        
        motoRepository.saveAll(motos);
        System.out.println("Motos criadas: " + motos.size());
    }
    
    private void createStatusMotos() {
        System.out.println("Criando status das motos...");
        
        Usuario admin = usuarioRepository.findByEmail("admin@teste.com").orElse(null);
        if (admin == null) {
            System.out.println("Usuário admin não encontrado!");
            return;
        }
        
        List<Moto> motos = motoRepository.findAll();
        if (motos.isEmpty()) {
            System.out.println("Nenhuma moto encontrada!");
            return;
        }
        
        List<StatusMoto> statusMotos = Arrays.asList(
            new StatusMoto(null, motos.get(0), EnumStatus.PRONTA, "Moto pronta para uso", "Garagem Principal", admin),
            new StatusMoto(null, motos.get(1), EnumStatus.ALUGADA, "Moto em uso", "Em Uso", admin),
            new StatusMoto(null, motos.get(2), EnumStatus.MANUTENCAO_AGENDADA, "Manutenção agendada", "Oficina", admin),
            new StatusMoto(null, motos.get(3), EnumStatus.REPARO_SIMPLES, "Reparo simples necessário", "Oficina", admin),
            new StatusMoto(null, motos.get(4), EnumStatus.DANOS_ESTRUTURAIS, "Danos estruturais", "Oficina", admin),
            new StatusMoto(null, motos.get(5), EnumStatus.MOTOR_DEFEITUOSO, "Motor com defeito", "Oficina", admin),
            new StatusMoto(null, motos.get(6), EnumStatus.AGUARDANDO_ALUGUEL, "Aguardando aluguel", "Garagem Secundária", admin),
            new StatusMoto(null, motos.get(7), EnumStatus.SEM_PLACA, "Sem placa", "Garagem Principal", admin),
            new StatusMoto(null, motos.get(8), EnumStatus.PENDENTE, "Status pendente", "Garagem Principal", admin),
            new StatusMoto(null, motos.get(9), EnumStatus.PRONTA, "Moto pronta para uso", "Garagem Secundária", admin),
            new StatusMoto(null, motos.get(10), EnumStatus.PRONTA, "Moto pronta para uso", "Garagem Principal", admin),
            new StatusMoto(null, motos.get(11), EnumStatus.ALUGADA, "Moto em uso", "Em Uso", admin),
            new StatusMoto(null, motos.get(12), EnumStatus.MANUTENCAO_AGENDADA, "Manutenção agendada", "Oficina", admin),
            new StatusMoto(null, motos.get(13), EnumStatus.REPARO_SIMPLES, "Reparo simples necessário", "Oficina", admin),
            new StatusMoto(null, motos.get(14), EnumStatus.DANOS_ESTRUTURAIS, "Danos estruturais", "Oficina", admin)
        );
        
        statusMotoRepository.saveAll(statusMotos);
        System.out.println("Status das motos criados: " + statusMotos.size());
    }
}
