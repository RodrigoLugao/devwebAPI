package rodrigolugao.simasAPI.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rodrigolugao.simasAPI.entities.Carro;
import rodrigolugao.simasAPI.entities.Modelo;
import rodrigolugao.simasAPI.exceptions.CarroNaoEncontradoException;
import rodrigolugao.simasAPI.exceptions.ModeloNaoEncontradoException;
import rodrigolugao.simasAPI.exceptions.ModeloNaoEnviadoException;
import rodrigolugao.simasAPI.models.PageModel;
import rodrigolugao.simasAPI.repositories.CarroRepository;
import rodrigolugao.simasAPI.repositories.ModeloRepository;

import java.util.List;

@Slf4j
@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    public Page<Carro> getAllCarrosWithModelo(Pageable pageable) {
        return carroRepository.getCarroWithPagination(pageable);
    }

    public Carro createCarro(Carro carro) {
        Modelo modelo = carro.getModelo();
        if(modelo == null) throw new ModeloNaoEnviadoException("Id do modelo não foi enviado.");
        Long modeloId = carro.getModelo().getId();

        modelo = modeloRepository.findById(modeloId)
                .orElseThrow(() -> new ModeloNaoEncontradoException("Modelo com id " + modeloId + " não encontrado."));

        carro.setModelo(modelo); // garante que é um modelo válido do banco
        return carroRepository.save(carro);
    }

    @Transactional
    public Carro updateCarro(Carro carro) {
       carroRepository.recuperarCarroPorIdComLock(carro.getId())
                .orElseThrow(() -> new CarroNaoEncontradoException(
                        "Carro de id" + carro.getId() + " não encontrado."));

        Long modeloId = carro.getModelo().getId();

        Modelo modelo = modeloRepository.findById(modeloId)
                .orElseThrow(() -> new ModeloNaoEncontradoException("Modelo com id " + modeloId + " não encontrado."));
        carro.setModelo(modelo);
        return carroRepository.save(carro);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeCarro(long id) {
        carroRepository.deleteById(id);
    }

    public Carro getCarById(long id) {
        return carroRepository.getCarroById(id)
                .orElseThrow(() -> new CarroNaoEncontradoException(
                        "Carro de id" + id + " não encontrado."));
    }

    public Page<Carro> getCarroWithPagination(Pageable pageable, String nome) {
        return carroRepository.getCarroWithPaginationByCodigo(pageable, "%" + nome + "%");
    }

    public Page<Carro> getCarroByModelo(Pageable pageable, Long idModelo) {
        return carroRepository.getCarroByModeloWithPagination(pageable, idModelo);
    }

}
