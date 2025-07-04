package rodrigolugao.simasAPI.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rodrigolugao.simasAPI.entities.Veiculo;
import rodrigolugao.simasAPI.entities.Modelo;
import rodrigolugao.simasAPI.enums.TipoVeiculo;
import rodrigolugao.simasAPI.exceptions.VeiculoNaoEncontradoException;
import rodrigolugao.simasAPI.exceptions.ModeloNaoEncontradoException;
import rodrigolugao.simasAPI.exceptions.ModeloNaoEnviadoException;
import rodrigolugao.simasAPI.repositories.VeiculoRepository;
import rodrigolugao.simasAPI.repositories.ModeloRepository;

@Slf4j
@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    public Page<Veiculo> buscarVeiculosComFiltro(
            String nomeModelo,
            String fabricante,
            TipoVeiculo tipo,
            Integer anoMin,
            Integer anoMax,
            Double precoMin,
            Double precoMax,
            Integer kmsMin,
            Integer kmsMax,
            String cambio,
            String cor,
            Pageable pageable) {

        return veiculoRepository.getVeiculosWithPagination(
                nomeModelo, fabricante, tipo,
                anoMin, anoMax, precoMin, precoMax,
                kmsMin, kmsMax, cambio, cor,
                pageable
        );
    }

    public Veiculo createVeiculo(Veiculo veiculo) {
        Modelo modelo = veiculo.getModelo();
        if(modelo == null) throw new ModeloNaoEnviadoException("Id do modelo não foi enviado.");
        Long modeloId = veiculo.getModelo().getId();

        modelo = modeloRepository.findById(modeloId)
                .orElseThrow(() -> new ModeloNaoEncontradoException("Modelo com id " + modeloId + " não encontrado."));

        veiculo.setModelo(modelo); // garante que é um modelo válido do banco
        return veiculoRepository.save(veiculo);
    }

    @Transactional
    public Veiculo updateVeiculo(Veiculo veiculo) {
       veiculoRepository.recuperarVeiculoPorIdComLock(veiculo.getId())
                .orElseThrow(() -> new VeiculoNaoEncontradoException(
                        "Veículo de id" + veiculo.getId() + " não encontrado."));

        Long modeloId = veiculo.getModelo().getId();

        Modelo modelo = modeloRepository.findById(modeloId)
                .orElseThrow(() -> new ModeloNaoEncontradoException("Modelo com id " + modeloId + " não encontrado."));
        veiculo.setModelo(modelo);
        return veiculoRepository.save(veiculo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeVeiculo(long id) {
        veiculoRepository.deleteById(id);
    }

    public Veiculo getVeiculoById(long id) {
        return veiculoRepository.getVeiculoById(id)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(
                        "Veículo de id" + id + " não encontrado."));
    }

    public Page<Veiculo> getVeiculosByCodigoWithPagination(Pageable pageable, String codigo) {
        return veiculoRepository.getVeiculosWithPaginationByCodigo(pageable, "%" + codigo + "%");
    }

    public Page<Veiculo> getVeiculosByModelo(Pageable pageable, Long idModelo) {
        return veiculoRepository.getVeiculoByModeloWithPagination(pageable, idModelo);
    }

    public Page<Veiculo> getVeiculosByModeloNome(Pageable pageable, String nomeModelo) {
        return veiculoRepository.getVeiculoByModeloNameWithPagination(pageable, nomeModelo);
    }

    public Veiculo getVeiculoByCodigo(String codigo) {
        return veiculoRepository.getVeiculoByCodigo(codigo)
                .orElseThrow(() -> new VeiculoNaoEncontradoException(
                        "Veículo de codigo" + codigo + " não encontrado."));
    }
}
