package LikelionProject.Service;

import LikelionProject.Controller.AnnounceController;
import LikelionProject.Domain.Announcement;
import LikelionProject.Repository.AnnounceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnnounceService {

    @Autowired
    private AnnounceRepository announceRepository;

    public Page<Announcement> getAnnounceList(int page) {
        return announceRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id")));
    }

    @Transactional
    public Announcement getView(Long id) {
        return announceRepository.findById(id).get();
    }

    public void create(String subject, String content) {
        Announcement A = new Announcement();
        A.setTitle(subject);
        A.setContent(content);
        A.setDateTime(LocalDateTime.now());
        this.announceRepository.save(A);
    }

    public byte[] getImage(String imagePath) throws Exception {

        try (InputStream inputStream = new FileInputStream(imagePath)){

            byte[] byteArray = inputStream.readAllBytes();
            return byteArray;
        }catch (Exception e){
            return null;
        }

    }
}
