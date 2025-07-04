package rodrigolugao.simasAPI.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rodrigolugao.simasAPI.entities.*;
import rodrigolugao.simasAPI.repositories.*;
import rodrigolugao.simasAPI.enums.TipoVeiculo;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ModeloRepository modeloRepository;
    private final VeiculoRepository veiculoRepository;
    private final BannerRepository bannerRepository;
    private final CategoriaPecaRepository categoriaPecaRepository;
    private final PecaRepository pecaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(ModeloRepository modeloRepository, VeiculoRepository veiculoRepository,
                      BannerRepository bannerRepository, CategoriaPecaRepository categoriaPecaRepository,
                      PecaRepository pecaRepository, UsuarioRepository usuarioRepository,
                      PasswordEncoder passwordEncoder) {
        this.modeloRepository = modeloRepository;
        this.veiculoRepository = veiculoRepository;
        this.bannerRepository = bannerRepository;
        this.categoriaPecaRepository = categoriaPecaRepository;
        this.pecaRepository = pecaRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (modeloRepository.count() == 0 && veiculoRepository.count() == 0 &&
                        bannerRepository.count() == 0 && categoriaPecaRepository.count() == 0 &&
                        pecaRepository.count() == 0 && usuarioRepository.count() == 0) { // Adicionado usuarioRepository

            // --- Criar e Salvar Usuários ---
            // Senhas serão hasheadas aqui
            Usuario adminUser = new Usuario("admin", passwordEncoder.encode("admin"), true, "Admin da Silva", "admin@simasauto.com", "(99) 99999-9999"); // Admin
            Usuario regularUser = new Usuario("usuario", passwordEncoder.encode("usuario"), false, "Usuário de Souza", "usuario@simasauto.com", "(99) 99999-9998"); // Usuário comum

            usuarioRepository.save(adminUser);
            usuarioRepository.save(regularUser);
            System.out.println("Usuários admin e usuario criados com sucesso!");
            // --- Fim da criação de Usuários ---

            // --- Criar e Salvar Categorias de Peças (PRIMEIRO!) ---
            CategoriaPeca motorTransmissao = new CategoriaPeca(null, "Motor e Transmissão", "motor-e-transmissao", "Peças para o seu motor e a sua transmissão", "motor.jpg");
            CategoriaPeca sistemaFreios = new CategoriaPeca(null, "Sistema de Freios", "sistema-de-freios", "", "freios.jpg");
            CategoriaPeca suspensaoChassi = new CategoriaPeca(null, "Suspensão e Chassi", "suspensao-e-chassi", "", "chassi.jpg");
            CategoriaPeca eletricaIluminacao = new CategoriaPeca(null, "Elétrica e Iluminação", "eletrica-e-iluminacao", "", "eletrica.jpg");
            CategoriaPeca exaustaoAdmissao = new CategoriaPeca(null, "Exaustão e Admissão", "exaustao-e-admissao", "", "exaustao.jpg");
            CategoriaPeca carroceriaAcessorios = new CategoriaPeca(null, "Carroceria e Acessórios", "carroceria-e-acessorios", "", "acessorios.jpg");

            motorTransmissao = categoriaPecaRepository.save(motorTransmissao); // Salva e atualiza a referência com o ID gerado
            sistemaFreios = categoriaPecaRepository.save(sistemaFreios);
            suspensaoChassi = categoriaPecaRepository.save(suspensaoChassi);
            eletricaIluminacao = categoriaPecaRepository.save(eletricaIluminacao);
            exaustaoAdmissao = categoriaPecaRepository.save(exaustaoAdmissao);
            carroceriaAcessorios = categoriaPecaRepository.save(carroceriaAcessorios);
            // --- Fim da criação de Categorias de Peças ---


            // --- Criar e Salvar Peças ---
            // Usar LocalDate.of(ano, mes, dia) para datas
            pecaRepository.save(new Peca("motor1.jpg", "Bloco do Motor V8", "bloco-do-motor-v8", "Bloco de motor de alto desempenho.", true, 5, new BigDecimal("4999.99"), LocalDate.now(), motorTransmissao));
            pecaRepository.save(new Peca("motor2.jpg", "Embreagem Esportiva", "embreagem-esportiva", "Embreagem de alta performance para trocas rápidas.", true, 10, new BigDecimal("1299.99"), LocalDate.now(), motorTransmissao));
            pecaRepository.save(new Peca("freios1.jpg", "Disco de Freio Ventilado", "disco-de-freio-ventilado", "Disco de freio ventilado para melhor dissipação de calor.", true, 20, new BigDecimal("349.99"), LocalDate.now(), sistemaFreios));
            pecaRepository.save(new Peca("freios2.jpg", "Pastilhas de Freio Cerâmicas", "pastilhas-de-freio-ceramicas", "Pastilhas de freio para alta resistência e desempenho.", true, 15, new BigDecimal("299.99"), LocalDate.now(), sistemaFreios));
            pecaRepository.save(new Peca("chassi1.jpg", "Amortecedor Esportivo", "amortecedor-esportivo", "Amortecedor para melhor estabilidade em curvas.", true, 12, new BigDecimal("799.99"), LocalDate.now(), suspensaoChassi));
            pecaRepository.save(new Peca( "chassi2.jpg", "Molas Esportivas", "molas-esportivas", "Molas reforçadas para maior controle de altura.", true, 8, new BigDecimal("499.99"), LocalDate.now(), suspensaoChassi));
            pecaRepository.save(new Peca("eletrica1.jpg", "Bateria 70Ah", "bateria-70ah", "Bateria de longa duração para sistemas eletrônicos.", true, 10, new BigDecimal("699.99"), LocalDate.now(), eletricaIluminacao));
            pecaRepository.save(new Peca("eletrica2.jpg", "Faróis LED Ultra Brilho", "farois-led-ultra-brilho", "Faróis LED para melhor visibilidade noturna.", true, 6, new BigDecimal("899.99"), LocalDate.now(), eletricaIluminacao));
            pecaRepository.save(new Peca("exaustao1.jpg", "Filtro de Ar Esportivo", "filtro-de-ar-esportivo", "Filtro de ar de alto fluxo para melhor desempenho.", true, 15, new BigDecimal("249.99"), LocalDate.now(), exaustaoAdmissao));
            pecaRepository.save(new Peca("exaustao2.jpg", "Escapamento Esportivo", "escapamento-esportivo", "Sistema de escapamento para melhor potência e som.", true, 5, new BigDecimal("1299.99"), LocalDate.now(), exaustaoAdmissao));
            pecaRepository.save(new Peca("acessorios1.jpg", "Aerofólio Esportivo", "aerofolio-esportivo", "Aerofólio para estabilidade em alta velocidade.", true, 4, new BigDecimal("799.99"), LocalDate.now(), carroceriaAcessorios));
            pecaRepository.save(new Peca("acessorios2.jpg", "Parachoque Dianteiro Personalizado", "parachoque-dianteiro-personalizado", "Parachoque estilizado para melhor visual e aerodinâmica.", true, 3, new BigDecimal("1499.99"), LocalDate.now(), carroceriaAcessorios));
            pecaRepository.save(new Peca("motor3.jpg", "Turbo Compressor", "turbo-compressor", "Aumenta a potência do motor com indução forçada.", true, 7, new BigDecimal("3499.99"), LocalDate.now(), motorTransmissao));
            pecaRepository.save(new Peca("motor4.jpg", "Radiador de Alto Fluxo", "radiador-de-alto-fluxo", "Radiador reforçado para melhor resfriamento.", true, 12, new BigDecimal("1199.99"), LocalDate.now(), motorTransmissao));
            pecaRepository.save(new Peca("freios3.jpg", "Servo Freio", "servo-freio", "Melhora a eficiência do sistema de frenagem.", true, 8, new BigDecimal("799.99"), LocalDate.now(), sistemaFreios));
            pecaRepository.save(new Peca("freios4.jpg", "Pinça de Freio Esportiva", "pinca-de-freio-esportiva", "Pinça de freio para performance avançada.", true, 6, new BigDecimal("999.99"), LocalDate.now(), sistemaFreios));
            pecaRepository.save(new Peca("chassi3.jpg", "Braço de Controle Ajustável", "braco-de-controle-ajustavel", "Melhora o alinhamento da suspensão.", true, 10, new BigDecimal("599.99"), LocalDate.now(), suspensaoChassi));
            pecaRepository.save(new Peca("chassi4.jpg", "Barra Estabilizadora", "barra-estabilizadora", "Reduz a rolagem da carroceria em curvas.", true, 9, new BigDecimal("499.99"), LocalDate.now(), suspensaoChassi));
            pecaRepository.save(new Peca("eletrica3.jpg", "Alternador de Alta Potência", "alternador-de-alta-potencia", "Alternador reforçado para maior eficiência.", true, 5, new BigDecimal("899.99"), LocalDate.now(), eletricaIluminacao));
            pecaRepository.save(new Peca("eletrica4.jpg", "Módulo de Ignição Eletrônica", "modulo-de-ignicao-eletronica", "Sistema digital para ignição mais precisa.", true, 6, new BigDecimal("499.99"), LocalDate.now(), eletricaIluminacao));
            pecaRepository.save(new Peca("exaustao3.jpg", "Coletor de Escape Inox", "coletor-de-escape-inox", "Melhora a saída de gases do motor.", true, 7, new BigDecimal("1599.99"), LocalDate.now(), exaustaoAdmissao));
            pecaRepository.save(new Peca("exaustao4.jpg", "Filtro de Combustível de Alto Fluxo", "filtro-de-combustivel-de-alto-fluxo", "Garante combustível limpo e eficiente.", true, 8, new BigDecimal("299.99"), LocalDate.now(), exaustaoAdmissao));
            pecaRepository.save(new Peca("acessorios3.jpg", "Spoiler Dianteiro", "spoiler-dianteiro", "Melhora a aerodinâmica e visual.", true, 4, new BigDecimal("999.99"), LocalDate.now(), carroceriaAcessorios));
            pecaRepository.save(new Peca("acessorios4.jpg", "Capô em Fibra de Carbono", "capo-em-fibra-de-carbono", "Leve e resistente para melhor desempenho.", true, 3, new BigDecimal("2499.99"), LocalDate.now(), carroceriaAcessorios));
            pecaRepository.save(new Peca("motor5.jpg", "Correia Dentada Reforçada", "correia-dentada-reforcada", "Material durável para maior vida útil.", true, 7, new BigDecimal("349.99"), LocalDate.now(), motorTransmissao));
            pecaRepository.save(new Peca("freios5.jpg", "Reservatório de Fluido de Freio", "reservatorio-de-fluido-de-freio", "Garantia de eficiência no sistema hidráulico.", true, 9, new BigDecimal("199.99"), LocalDate.now(), sistemaFreios));
            pecaRepository.save(new Peca("chassi5.jpg", "Kit de Levantamento de Suspensão", "kit-de-levantamento-de-suspencao", "Ideal para off-road e terrenos acidentados.", true, 4, new BigDecimal("1599.99"), LocalDate.now(), suspensaoChassi));
            pecaRepository.save(new Peca("eletrica5.jpg", "Sistema de Som Automotivo", "sistema-de-som-automotivo", "Audio de alta qualidade para veículos.", true, 5, new BigDecimal("2499.99"), LocalDate.now(), eletricaIluminacao));
            pecaRepository.save(new Peca("exaustao5.jpg", "Kit de Admissão Direta", "kit-de-admissao-direta", "Melhora fluxo de ar para potência máxima.", true, 6, new BigDecimal("899.99"), LocalDate.now(), exaustaoAdmissao));
            pecaRepository.save(new Peca("acessorios5.jpg", "Jogo de Rodas Esportivas", "jogo-de-rodas-esportivas", "Rodas leves e resistentes para performance.", true, 5, new BigDecimal("3999.99"), LocalDate.now(), carroceriaAcessorios));
            // --- Fim da criação de Peças ---

            // Criar Modelos (mantidos aqui para contexto, assumindo que eles já estão salvos ou serão salvos em sequência)
            Modelo tumbler = new Modelo(null, "Tumbler", "Wayne", TipoVeiculo.CARRO, 2005, "Automático", "V8", 1, "Gasolina", "fotos-em-breve.png");
            Modelo regalia = new Modelo(null, "Regalia", "Lucis Motors", TipoVeiculo.CARRO, 2016, "Automático", "V12", 1, "Gasolina", "fotos-em-breve.png");
            Modelo delorean = new Modelo(null, "DeLorean DMC-12", "DMC", TipoVeiculo.CARRO, 1981, "Manual", "Elétrico 1.21GW", 1, "Fluxo de Energia", "fotos-em-breve.png");
            Modelo charger = new Modelo(null, "Charger R/T", "Dodge", TipoVeiculo.CARRO, 1970, "Manual", "V8 HEMI", 2, "Gasolina", "fotos-em-breve.png");
            Modelo db5 = new Modelo(null, "DB5", "Aston Martin", TipoVeiculo.CARRO, 1964, "Manual", "4.0L I6", 1, "Gasolina", "fotos-em-breve.png");
            Modelo lightRunner = new Modelo(null, "Light Runner", "Encom Motors", TipoVeiculo.MOTO, 2010, "Automático", "Plasma", 1, "Energia", "fotos-em-breve.png");
            Modelo mustang = new Modelo(null, "Mustang GT500", "Ford", TipoVeiculo.CARRO, 1967, "Manual", "V8 7.0L", 2, "Gasolina", "fotos-em-breve.png");
            Modelo ferrari = new Modelo(null, "250 GT California", "Ferrari", TipoVeiculo.CARRO, 1961, "Manual", "V12 3.0L", 1, "Gasolina", "fotos-em-breve.png");
            Modelo silverCloud = new Modelo(null, "Silver Cloud II", "Rolls-Royce", TipoVeiculo.CARRO, 1962, "Automático", "V8 6.2L", 1, "Gasolina", "fotos-em-breve.png");
            Modelo kitt = new Modelo(null, "Firebird Trans Am", "Pontiac", TipoVeiculo.CARRO, 1982, "Automático", "V8 5.0L", 1, "Gasolina", "fotos-em-breve.png");
            Modelo iron883 = new Modelo(null, "Iron 883", "Harley-Davidson", TipoVeiculo.MOTO, 2020, "Manual", "883cc V-Twin", 3, "Gasolina", "fotos-em-breve.png");
            Modelo ninja = new Modelo(null, "Ninja ZX-10R", "Kawasaki", TipoVeiculo.MOTO, 2023, "Manual", "998cc Inline-4", 2, "Gasolina", "fotos-em-breve.png");
            Modelo panigale = new Modelo(null, "Panigale V4", "Ducati", TipoVeiculo.MOTO, 2023, "Manual", "1103cc V4", 1, "Gasolina", "fotos-em-breve.png");

            modeloRepository.save(tumbler);
            modeloRepository.save(regalia);
            modeloRepository.save(delorean);
            modeloRepository.save(charger);
            modeloRepository.save(db5);
            modeloRepository.save(lightRunner);
            modeloRepository.save(mustang);
            modeloRepository.save(ferrari);
            modeloRepository.save(silverCloud);
            modeloRepository.save(kitt);
            modeloRepository.save(iron883);
            modeloRepository.save(ninja);
            modeloRepository.save(panigale);

            // Criar Veiculos
            veiculoRepository.save(new Veiculo(null, tumbler, 60000L, "O carro do Batman. Perfeito para lutar contra o crime.", "BATMO001", new BigDecimal("15000000"), LocalDate.now(), "https://m.media-amazon.com/images/I/61IsZo-irFL._AC_UF894,1000_QL80_.jpg", false, "Preto Fosco"));
            veiculoRepository.save(new Veiculo(null, regalia, 20000L, "Luxo e sofisticação em um veículo de reis.", "FFX001", new BigDecimal("2600000"), LocalDate.now(), "https://cache-na.finalfantasy.com/uploads/content/file/2021/11/24/13834/211125_ddoff_1.png", false, "Preto com detalhes em prata"));
            veiculoRepository.save(new Veiculo(null, delorean, 88888L, "De Volta para o Futuro com esse clássico.", "DMC1981", new BigDecimal("1000000"), LocalDate.now(), "delorean.jpg", false, "Prata Metálico"));
            veiculoRepository.save(new Veiculo(null, charger, 45000L, "O clássico Muscle Car do filme Velozes e Furiosos.", "CHARGER70", new BigDecimal("800000"), LocalDate.now(), "charger.jpg", false, "Preto"));
            veiculoRepository.save(new Veiculo(null, db5, 30000L, "O carro do James Bond, sinônimo de elegância e poder.", "DB50064", new BigDecimal("4000000"), LocalDate.now(), "db5.jpg", false, "Prata Clássico"));
            veiculoRepository.save(new Veiculo(null, lightRunner, 0L, "A moto futurista de Tron Legacy.", "TRON001", new BigDecimal("5000000"), LocalDate.now(), "lightcycle.jpg", false, "Azul Neon"));
            veiculoRepository.save(new Veiculo(null, mustang, 60000L, "A lenda dos muscle cars americanos.", "MUST67", new BigDecimal("900000"), LocalDate.now(), "fotos-em-breve.png", false, "Azul Escuro"));
            veiculoRepository.save(new Veiculo(null, ferrari, 40000L, "A icônica Ferrari do filme Curtindo a Vida Adoidado.", "FERRARI61", new BigDecimal("12000000"), LocalDate.now(), "fotos-em-breve.png", false, "Vermelho Ferrari"));
            veiculoRepository.save(new Veiculo(null, silverCloud, 50000L, "O carro do vilão Max Zorin, de 007.", "ROLLS62", new BigDecimal("7000000"), LocalDate.now(), "fotos-em-breve.png", false, "Prata Elegante"));
            veiculoRepository.save(new Veiculo(null, kitt, 55000L, "O carro da série K.I.T.T – Super Máquina.", "KITT82", new BigDecimal("1500000"), LocalDate.now(), "supermaquina/KITT.jpg", true, "Preto com detalhes vermelhos"));
            veiculoRepository.save(new Veiculo(null, iron883, 5000L, "Uma moto clássica, perfeita para aventuras urbanas.", "HD88320", new BigDecimal("55000"), LocalDate.now(), "fotos-em-breve.png", false, "Preto Fosco"));
            veiculoRepository.save(new Veiculo(null, ninja, 1000L, "Performance extrema para os apaixonados por velocidade.", "KAWAZX10R23", new BigDecimal("95000"), LocalDate.now(), "fotos-em-breve.png", false, "Verde Kawasaki"));
            veiculoRepository.save(new Veiculo(null, panigale, 300L, "A lendária moto esportiva italiana.", "DUCAV423", new BigDecimal("130000"), LocalDate.now(), "fotos-em-breve.png", false, "Vermelho Ducati"));

            // --- Criar Banners ---
            bannerRepository.save(new Banner(null, "Super Máquina por US$ 7.000.000", "K.I.T.T.: tecnologia avançada, estilo futurista e pronto para grandes aventuras sobre rodas.", "/veiculos/codigo/KITT82", "imagem-carrossel-supermaquina.jpg", LocalDate.now().minusDays(1)));
            bannerRepository.save(new Banner(null, "Peças e Acessórios", "Melhore seu veículo com volantes, bancos, rodas e escapamentos de alta qualidade. Personalização, desempenho e estilo em um só lugar!", "/pecas", "imagem-carrossel-pecas.png", LocalDate.now().minusDays(2)));
            bannerRepository.save(new Banner(null, "Tumbler por Apenas US$ 3 milhões", "Enfrente o crime com segurança e eficiência.", "/veiculos/1", "imagem-carrossel-tumbler.png", LocalDate.now().minusDays(3)));
            bannerRepository.save(new Banner(null, "Regalia por Apenas 2.600.000 gil", "Conquiste o luxo e a elegância com o carro dos reis.", "/veiculos/3", "imagem-carrossel-regalia.jpg", LocalDate.now().minusDays(4)));
            bannerRepository.save(new Banner(null, "Lançamento: Kaneda's Custom", "Reserve já a sua.", "/veiculos/4", "imagem-carrossel-kaneda.jpg", LocalDate.now().minusDays(5)));
            bannerRepository.save(new Banner(null, "Serviços de manutenção, revisão, customização e peças", "Garanta o cuidado total para o seu veículo.", "/servicos", "imagem-carrossel-manutenção.png", LocalDate.now().minusDays(6)));
            // --- Fim da criação de Banners ---

            System.out.println("Base de dados populada com sucesso.");
        } else {
            System.out.println("Base de dados já possui dados, seeder não executado.");
        }
    }
}