package rodrigolugao.simasAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rodrigolugao.simasAPI.entities.Modelo;
import rodrigolugao.simasAPI.exceptions.ModeloConflictException;
import rodrigolugao.simasAPI.repositories.ModeloRepository;

import java.util.List;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    public List<Modelo> getAllModelos(){
        return modeloRepository.findAll();
    }

    public Modelo createModelo(Modelo modelo) {
        boolean exists = modeloRepository.existsByNomeAndAno(modelo.getNome(), modelo.getAno());

        if (exists) {
            throw new ModeloConflictException("Já existe um modelo com nome " + modelo.getNome() + " e ano " + modelo.getAno());
        }

        return modeloRepository.save(modelo);
    }
}
