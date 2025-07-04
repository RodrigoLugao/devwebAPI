package rodrigolugao.simasAPI.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rodrigolugao.simasAPI.entities.Banner;
import rodrigolugao.simasAPI.exceptions.BannerNaoEncontradoException;
import rodrigolugao.simasAPI.repositories.BannerRepository;

import java.time.LocalDate;

@Slf4j
@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    // Método para obter todos os banners com paginação e ordenação
    public Page<Banner> getBanners(Pageable pageable) {
        return bannerRepository.findAll(pageable);
    }

    // Método para obter um banner por ID
    public Banner getBannerById(Long id) {
        return bannerRepository.findById(id)
                .orElseThrow(() -> new BannerNaoEncontradoException("Banner com ID " + id + " não encontrado."));
    }

    // Método para criar um novo banner
    public Banner createBanner(Banner banner) {
        if (banner.getDataCadastro() == null) {
            banner.setDataCadastro(LocalDate.now());
        }
        return bannerRepository.save(banner);
    }

    // Método para atualizar um banner existente
    @Transactional
    public Banner updateBanner(Banner banner) {
        bannerRepository.findById(banner.getId())
                .orElseThrow(() -> new BannerNaoEncontradoException("Banner com ID " + banner.getId() + " não encontrado para atualização."));
        return bannerRepository.save(banner);
    }

    // Método para remover um banner por ID
    @Transactional
    public void deleteBanner(Long id) {
        if (!bannerRepository.existsById(id)) {
            throw new BannerNaoEncontradoException("Banner com ID " + id + " não encontrado para exclusão.");
        }
        bannerRepository.deleteById(id);
    }
}